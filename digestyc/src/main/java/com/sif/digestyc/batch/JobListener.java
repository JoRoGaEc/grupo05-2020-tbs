package com.sif.digestyc.batch;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.sif.digestyc.Controller.Validacion.NotificacionController;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.Notificacion;
import com.sif.digestyc.Repository.Carga.TablaRepository;
import com.sif.digestyc.Repository.Validacion.DatoTablaRepository;
import com.sif.digestyc.Repository.Validacion.ErrorTablaDinamicaRepository;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.NotificacionServiceImpl;

public class JobListener extends JobExecutionListenerSupport{
	
	private static Logger LOG = LoggerFactory.getLogger(JobListener.class);
	
	private TablaServiceImpl tablaService;
	private ErrorTablaDinamicaServiceImpl errorService;
	private JdbcTemplate jdbcTemplate;
	private Long idTabla;
	private SimpMessagingTemplate template;
	private NotificacionServiceImpl notificacionService;
	
	@Autowired
	public JobListener(JdbcTemplate jdbcTemplate, Long idTabla, TablaServiceImpl tablaService, ErrorTablaDinamicaServiceImpl errorService, SimpMessagingTemplate template, NotificacionServiceImpl notificacionService) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.idTabla=idTabla;
		this.tablaService = tablaService;
		this.errorService = errorService;
		this.template = template;
		this.notificacionService= notificacionService; 
	}
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOG.info("Empezado el job"+idTabla);
		if(idTabla!=null) {
			//Actualizamos la tabla
			Optional<Tabla> t = tablaService.buscar(idTabla);
			System.out.println(t);
			if(t.isPresent()) {
				Tabla tablaActualizar = t.get();
				tablaActualizar.setValidating();
				tablaService.actualizar(tablaActualizar);
			}
		}
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("Termino el job");
			//Cuando termina el Job tenemos que verificar que la tabla no tenga ningun error entonces la pasamos a estado == 2
			if(idTabla!=null) {
				//Actualizamos la tabla
				Optional<Tabla> t = tablaService.buscar(idTabla);
				if(t.isPresent()) {
					long cantidad = errorService.contarErrores(t.get().getId());
					if(cantidad==0) {
						Tabla tablaActualizar = t.get();
						tablaActualizar.setValidated();
						tablaService.actualizar(tablaActualizar);	
						LOG.info("Actualizando estado = 2");
					}
					Notificacion notificacion = new Notificacion(
							"Validación de tabla "+t.get().getNombre() +" con "+cantidad +" de errores encontrados", 
							"Tabla: "+t.get().getNombre(), 
							0, 
							cantidad , 
							"Errores: "+cantidad,
							t.get().getId(),
							false, 
							"/reparar/"+t.get().getId()+"/tabla/10/0/previsualizacion?notificacion=");
					notificacion  = notificacionService.actualizar(notificacion);
					notificacion.setUrl(notificacion.getUrl()+notificacion.getId());
					notificacion  = notificacionService.actualizar(notificacion);
					template.convertAndSend("/topic/notificacion", notificacion); //enviamos la notificacion
				}
			}
		}
		if(jobExecution.getStatus() == BatchStatus.STARTING) {
			LOG.info("Empezando el job");
		}
		if(jobExecution.getStatus() == BatchStatus.STARTED) {
			LOG.info("Empezado el job");
		}
		
	}

	
	
}
