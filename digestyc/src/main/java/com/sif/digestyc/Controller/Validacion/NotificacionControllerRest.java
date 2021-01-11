package com.sif.digestyc.Controller.Validacion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sif.digestyc.Entity.ValidationModule.Notificacion;
import com.sif.digestyc.Service.Validation.ValidationImpl.NotificacionServiceImpl;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/notificacion_rest")
public class NotificacionControllerRest {
	private static Logger LOG = LoggerFactory.getLogger(NotificacionControllerRest.class);
	
	@Autowired
	private NotificacionServiceImpl notificacionService;
	
	@PreAuthorize("hasUrl('/notificacion_rest/notificaciones')")
	@GetMapping("/notificaciones")
	public List<Notificacion> findNotificacion(Model model){
		LOG.info("solicitando datos");
		return notificacionService.buscarPrimeras(5);
	}

	@PreAuthorize("hasUrl('/notificacion_rest/countNotification')")
	@GetMapping("/countNotification")
	public Integer countNotification(Model model){
		return notificacionService.contarNotificacion();
	}

	
}
