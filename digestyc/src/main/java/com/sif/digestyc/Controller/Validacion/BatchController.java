package com.sif.digestyc.Controller.Validacion;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
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
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Controller.IndexController;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.NotificacionServiceImpl;
import com.sif.digestyc.batch.BatchJob;
import com.sif.digestyc.batch.JobListener;

@Controller
@RequestMapping("/validacion")
public class BatchController {

	private static final String INDEX = "validacion/registrosPorValidar";
	private static final Logger LOG = LoggerFactory.getLogger(BatchController.class);

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

	@PreAuthorize("hasUrl('/validacion/start_job')")
	@GetMapping("/start_job")
	public String getIndex(Model model, @RequestParam(name = "validating", required = false) String validating,
			@RequestParam(name = "validated", required = false) String validated,
			@RequestParam(name = "tableNotFound", required = false) String tableNotFound,
			@RequestParam(name = "notArchivo", required = false) String notArchivo,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "ready", required = false) String ready,
			@RequestParam(name = "notEntrega", required = false) String notEntrega,
			@RequestParam(name = "notTipificacion", required = false) String notTipificacion,
			@RequestParam(name = "withOutError", required = false) String withOutError) {
		model.addAttribute("tablas", tablaService.getTablasValidas());
		model.addAttribute("validated", validated);
		model.addAttribute("validating", validating);
		model.addAttribute("tableNotFound", tableNotFound);
		model.addAttribute("notArchivo", notArchivo);
		model.addAttribute("success", success);
		model.addAttribute("ready", ready);
		model.addAttribute("notEntrega", notEntrega);
		model.addAttribute("notTipificacion", notTipificacion);
		model.addAttribute("withOutError", withOutError);
		return INDEX;
	}

	@PreAuthorize("hasUrl('/validacion/start_job/post')")
	@PostMapping("/start_job")
	public String startJob(@ModelAttribute("nombre") String tabla, @ModelAttribute("id") Long id) {
		Optional<Tabla> t = tablaService.buscar(id);
		StringBuilder add = new StringBuilder();
		if (t.isPresent()) {
			if (t.get().isNotValid()) {
				if (t.get().getArchivo() != null) {
					if(t.get().getArchivo().getEntrega()!=null) {
						if(tablaService.tieneTipificacion(t.get().getArchivo().getEntrega().getId())) {
							JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
									.toJobParameters();
							try {
								jobLauncher.run(batchJob.validarTablJob(
										new JobListener(JdbcTemplate, t.get().getId(), tablaService, errorService, template,
												notificacionService),
										t.get().getNombre(), t.get().getId(), t.get().getArchivo().getEntrega().getId()),
										jobParameters);
								add.append("?success=true");
							} catch (JobExecutionAlreadyRunningException e) {
								e.printStackTrace();
							} catch (JobRestartException e) {
								e.printStackTrace();
							} catch (JobInstanceAlreadyCompleteException e) {
								e.printStackTrace();
							} catch (JobParametersInvalidException e) {
								e.printStackTrace();
							}						
						}else {
							add.append("?notTipificacion=true");
						}
					}else {
						add.append("?notEntrega=true");
					}
				} else {
					add.append("?notArchivo=true");
				}
			} else
				add.append(t.get().isValidated() ? "?validated=true" : "?validating=true");
		} else {
			add.append("?tableNotFound=true");
		}
		return "redirect:/validacion/start_job" + add.toString();
	}

}
