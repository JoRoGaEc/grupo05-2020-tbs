package com.sif.digestyc.batch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.Notificacion;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.TablaCorrespondenciaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.NotificacionServiceImpl;

public class JobListenerInsertar extends JobExecutionListenerSupport{
	
	private static Logger LOG = LoggerFactory.getLogger(JobListenerInsertar.class);
	
	private TablaServiceImpl tablaService;
	private Long idTabla;
	private SimpMessagingTemplate template;
	private NotificacionServiceImpl notificacionService;
	
	@Autowired
	public JobListenerInsertar(Long idTabla, TablaServiceImpl tablaService, SimpMessagingTemplate template, NotificacionServiceImpl notificacionService) {
		super();
		this.idTabla=idTabla;
		this.tablaService = tablaService;
		this.template = template;
		this.notificacionService= notificacionService; 
	}
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOG.info("Empezado el job, tabla_id = "+idTabla);
		if(idTabla!=null) {
			//Actualizamos la tabla
			Optional<Tabla> t = tablaService.buscar(idTabla);
			if(t.isPresent()) {
				Tabla tablaActualizar = t.get();
				tablaActualizar.setPassingData();
				tablaService.actualizar(tablaActualizar);
			}
		}
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("Termino el job");
			if(idTabla!=null) {
				//Actualizamos la tabla
				LOG.info("Job finalizado tabla = "+idTabla);
				Optional<Tabla> t = tablaService.buscar(idTabla);
				if(t.isPresent()) {
					Tabla tabla = t.get();
					tabla.setDataInserted();
					tabla = tablaService.actualizar(tabla);
					String tablaDestino = tablaService.getTablaDestino(tabla.getId());
					Date date = new Date(Calendar.getInstance().getTimeInMillis());
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					String fecha = formatter.format(date);
					Notificacion notificacion = new Notificacion(
							"Se insertaron los datos de "+tabla.getNombre()+" a "+tablaDestino, 
							"Tabla destino: "+tablaDestino, 
							0,
							0,
							fecha,
							t.get().getId(),
							false,
							"/reparar/"+t.get().getId()+"/datos_completos/10/0/tabla_correlativa"+"?notificacion=");
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
