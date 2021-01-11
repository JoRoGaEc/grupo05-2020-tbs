package com.sif.digestyc.Service.Validation.ValidationImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.ValidationModule.Notificacion;
import com.sif.digestyc.Repository.Validacion.NotificacionRepository;
import com.sif.digestyc.Service.Validation.NotificacionService;

@Service("notificacionServiceImpl")
public class NotificacionServiceImpl implements NotificacionService{
	
	@Autowired
	private NotificacionRepository notificacionRepository;

	@Override
	public Optional<Notificacion> buscar(long id) {
		return notificacionRepository.findById(id);
	}

	@Override
	public List<Notificacion> buscarTodas() {
		return notificacionRepository.findAll();
	}

	@Override
	public List<Notificacion> buscarPorEstado(boolean estado) {
		return notificacionRepository.findByEstado(estado);
	}

	@Override
	public Notificacion actualizar(Notificacion notificacion) {
		return notificacionRepository.save(notificacion);
	}

	@Override
	public List<Notificacion> buscarPrimeras(int i) {
		return notificacionRepository.findFirst(i);
	}
	
	
	@Override
	public Integer contarNotificacion() {
		return notificacionRepository.countNotification();
	}

	@Override
	public Object buscarNotificacionesActivas() {
		return notificacionRepository.findActivate();
	}
	
	
	
	
	
}
