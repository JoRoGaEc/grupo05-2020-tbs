package com.sif.digestyc.Service.Validation;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.ValidationModule.Notificacion;

public interface NotificacionService {
	
	public abstract Optional<Notificacion> buscar(long id);
	public abstract List<Notificacion> buscarTodas();
	public abstract List<Notificacion> buscarPorEstado(boolean estado);
	public abstract Notificacion actualizar(Notificacion notificacion);
	public abstract List<Notificacion> buscarPrimeras(int i);
	public abstract Integer contarNotificacion();
	public abstract Object buscarNotificacionesActivas();
	
}
