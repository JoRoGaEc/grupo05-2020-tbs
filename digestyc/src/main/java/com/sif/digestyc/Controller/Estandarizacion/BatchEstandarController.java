package com.sif.digestyc.Controller.Estandarizacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.TablaConTipoDatoDinamicaServiceImpl;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.TablaCorrespondenciaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.NotificacionServiceImpl;
import com.sif.digestyc.batch.BatchJob;
import com.sif.digestyc.batch.JobListener;
import com.sif.digestyc.batch.JobListenerInsertar;

@Controller
@RequestMapping("/estandarizar")
public class BatchEstandarController {
	
	private static final String INDEX = "estandarizacion/registroPorEstandarizar";
	private static final Logger LOG = LoggerFactory.getLogger(BatchEstandarController.class);

	@Autowired
	@Qualifier("jobAsincrono")
	JobLauncher jobLauncher;

	@Autowired
	BatchJob batchJob;

	@Autowired
	JdbcTemplate JdbcTemplate;

	@Autowired
	TablaServiceImpl tablaService;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private NotificacionServiceImpl notificacionService;

	@Autowired
	ErrorTablaDinamicaServiceImpl errorService;
	
	@Autowired
	TablaCorrespondenciaServiceImpl tablaCorrespondenciaService;
	
	@Autowired
	TablaConTipoDatoDinamicaServiceImpl tablaConTipoDatoDinamicaService;

	@PreAuthorize("hasUrl('/estandarizar/start_job')")
	@GetMapping("/start_job")
	public String getIndex(Model model, 
			@RequestParam(name = "validating", required = false) String validating,
			@RequestParam(name = "validated", required = false) String validated,
			@RequestParam(name = "tableNotFound", required = false) String tableNotFound,
			@RequestParam(name = "notArchivo", required = false) String notArchivo,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "NotEstandarCorrespondencias", required = false) String NotEstandarCorrespondencias,
			@RequestParam(name = "NotTipificacion", required = false) String NotTipificacion,
			@RequestParam(name = "NotTipificacionCorrespondencias", required = false) String NotTipificacionCorrespondencias,
			@RequestParam(name = "NotEstandar", required = false) String NotEstandar,
			@RequestParam(name = "error", required = false) String error) {
		
		model.addAttribute("tablas", tablaService.getTablasValidas());
		model.addAttribute("validated", validated);
		model.addAttribute("validating", validating);
		model.addAttribute("tableNotFound", tableNotFound);
		model.addAttribute("notArchivo", notArchivo);
		model.addAttribute("success", success);
		model.addAttribute("NotEstandarCorrespondencias", NotEstandarCorrespondencias);
		model.addAttribute("NotTipificacionCorrespondencias", NotTipificacionCorrespondencias);
		model.addAttribute("NotEstandar", NotEstandar);
		model.addAttribute("NotTipificacion", NotTipificacion);
		model.addAttribute("error", error);
		return INDEX;
	}

	@PreAuthorize("hasUrl('/estandarizar/start_job/post')")
	@PostMapping("/start_job")
	public String startJob(@ModelAttribute("nombre") String nombre, @ModelAttribute("id") Long id) {
		Optional<Tabla> t = tablaService.buscar(id);
		String add = "";
		if (t.isPresent()) {
			Tabla tabla = t.get();
			if (tabla.isValidated() && tabla.getTablaCorrespondencia()!=null) {
				/*
				 * validar las siguientes posibilidades
				 * 	-> no tiene estandares
				 *  -> no tiene asignado todos los posibles valores de estandares
				 *  -> no tiene asignado todos los posibles valores de tipificacion
				 *  -> no tiene tipificacion 
				 */
				for(ColumnaVersionPlantilla cvp : tabla.getTablaCorrespondencia().getColumnaVersionPlantillas()) {
					if(cvp.getColumnaCorrespondencia().getEstandar()!=null) {
						for(ValorTipicoEstandar vte : cvp.getColumnaCorrespondencia().getEstandar().getValoresTipicosEstandar()) {
							if(vte.getCorrespondencias().isEmpty()) {
								LOG.info(vte.getValorTipico()+" "+vte.getCorrespondencias());
								return "redirect:/estandarizar/start_job?NotEstandarCorrespondencias=true";
							}
						}
					}else {
						return "redirect:/estandarizar/start_job?NotEstandar=true";
					}
					
					if(cvp.getTipificacion() != null) {
						for(ValorTipico vt : cvp.getTipificacion().getValorTipico()) {
							if(vt.getCorrespondencias().isEmpty() && vt.getInicioRango()== vt.getFinRango() && (vt.getFechaInicio() == null && vt.getFechaFin()==null)) {
								return "redirect:/estandarizar/start_job?NotTipificacionCorrespondencias=true";
							}
						}
					}else {
						return "redirect:/estandarizar/start_job?NotTipificacion=true";
					}
					
				}
				
				
				LOG.info("EJECUTANDO JOB");
				JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters(); //parametros
				try {
					jobLauncher.run(batchJob.insertarEnTabla(
							new JobListenerInsertar(t.get().getId(), tablaService, template,
									notificacionService),
							t.get().getNombre(), t.get().getId(), t.get().getArchivo().getEntrega().getId()),
							jobParameters);
					add = "?success=true";
				} catch (JobExecutionAlreadyRunningException e) {
					e.printStackTrace();
				} catch (JobRestartException e) {
					e.printStackTrace();
				} catch (JobInstanceAlreadyCompleteException e) {
					e.printStackTrace();
				} catch (JobParametersInvalidException e) {
					e.printStackTrace();
				}				
				
			} else
				add = t.get().isValidated() ? "?validated=true" : "?validating=true";
		} else {
			add = "?tableNotFound=true";
		}
		return "redirect:/estandarizar/start_job" + add;
	}
	
	
	
	
	@PreAuthorize("hasUrl('/estandarizar/create_table')")
	@PostMapping("/create_table")
	public String createTable(@ModelAttribute("nombreEstandar") String tabla, @ModelAttribute("idEstandar") Long id) {
		LOG.info("Creando las tablas");
		Optional<Tabla> t = tablaService.buscar(id);
		Map<String, String> newColumns = new HashMap<String, String>();
		String nombreTabla = null;
		String add = "";
		if (t.isPresent()) {
			if (t.get().isValidated() && t.get().getTablaCorrespondencia()==null) {
				if (t.get().getArchivo() != null) {
					Entrega entrega = t.get().getArchivo().getEntrega();
					if(entrega !=null) {
						List<ColumnaVersionPlantilla> columnas = entrega.getVersionPlantilla()!=null?entrega.getVersionPlantilla().getVersionesColumna():new ArrayList<ColumnaVersionPlantilla>();
						if(!columnas.isEmpty()) {
							nombreTabla = t.get().getNombre()+"_estandar";
							for(ColumnaVersionPlantilla cvp : columnas) {								
								//validar que todas tengan columnaCorrespondencia
								if(cvp.getColumnaCorrespondencia()!=null) {
									Estandar estandar = cvp.getColumnaCorrespondencia().getEstandar();
									if(estandar != null) {
										//ya tenemos el estandar, necesitamos saber el tipo de dato y todo
										if(estandar.getTipoDato()!=null) {
											String key = cvp.getPlantillaColumna().getCodigo(); //el nombre de la columna
											String dato =estandar.getTipoDato();
											
											if(estandar.getLongitudN()!=null) {
												dato+=" ("+estandar.getLongitudN()+")";
											}else {
												if(estandar.getPrecision() !=null && estandar.getEscala()!=null) {
													dato+=" ("+estandar.getPrecision()+", "+estandar.getEscala()+")";
												}else {
													if(estandar.getPrecision()!=null) {
														dato+=" ("+estandar.getPrecision()+")";
													}
												}
											}
											//dato += estandar.getEsVacio()?"":" NOT NULL";
											newColumns.put(key, dato);
										}else {
											add ="?error=NotTypeData";
											return "redirect:/estandarizar/start_job" + add;
										}
									}else {
										add = "?error=NotFoundEstandar";
										return "redirect:/estandarizar/start_job" + add;
									}
								}else {
									add = "?error=NotFoundColumnaCorrespondencia";
									return "redirect:/estandarizar/start_job" + add;
								}
							}//end for
							TablaCorrespondencia tablaCorrespondencia = new TablaCorrespondencia();
							tablaCorrespondencia.setNombreTablaCorrespondiente(nombreTabla);
							tablaCorrespondencia.setTabla(t.get());
							tablaCorrespondencia = tablaCorrespondenciaService.actualizar(tablaCorrespondencia);
							Tabla t2 = t.get();
							t2.setTablaCorrespondencia(tablaCorrespondencia);
							tablaService.actualizar(t2);
							tablaCorrespondencia.setColumnaVersionPlantillas(columnas);
							tablaCorrespondenciaService.actualizar(tablaCorrespondencia);
						}else {
							add = "?error=columnEmpty";
						}
					}else {
						add = "?error=notEntrega";
					}
				} else {
					add = "?error=notArchivo";
				}
			} else
				add = t.get().isValidated() ? "?error=TablaCorrespondencia" : "?error=validating";
		} else {
			add = "?error=tableNotFound";
		}
		
		Iterator<String> iterador = newColumns.keySet().iterator();
		while(iterador.hasNext()) {
			String key = iterador.next();
			String valor = newColumns.get(key);
			LOG.info(key+":"+valor);
		}
		if(nombreTabla!=null) {
			if(tablaService.existeTabla(nombreTabla)) {
				LOG.info("REsultado: "+tablaConTipoDatoDinamicaService.crearTabla(nombreTabla, newColumns));
			}else {
				List<String> columnasActuales = tablaService.obtenerColumnas(nombreTabla);
				List<String> nuevasColumnas = columnasActuales.stream().filter(c-> c.equalsIgnoreCase(newColumns.get(c))).collect(Collectors.toList());
				if(!nuevasColumnas.isEmpty()) {
					Map<String, String> addColumn = new HashMap<>();
					for(String c : nuevasColumnas) {
						addColumn.put(c, newColumns.get(c).replace("NOT NULL", ""));//las nuevas columnas deben estar null
					}
					tablaConTipoDatoDinamicaService.addColumn(nombreTabla, addColumn);
				}else {
					LOG.info("Tabla existe y tiene todas las columnas actuales");
				}
			}
		}
		
		return "redirect:/estandarizar/start_job" + add;
	}
	
	@PreAuthorize("hasUrl('/estandarizar/preparar/')")
	@PostMapping("/preparar/")
	public String getPreparar(@ModelAttribute("idTabla") Long id) {
		String redireccion = "redirect:/validacion/start_job";
		Optional<Tabla> t = tablaService.buscar(id);
		if(t.isPresent()) {
			Tabla tabla = t.get();
			tabla.setNoValidating();
			tablaService.actualizar(tabla);
		}
		return redireccion;
	}

	
}
