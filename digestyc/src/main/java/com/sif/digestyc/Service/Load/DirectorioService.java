package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Directorio;

public interface DirectorioService {
	
	public abstract List<Directorio> obtenerDirectorios();
	public abstract Optional<Directorio> obtenerDirectorioActivo(boolean activo);
	public abstract Directorio actualizar(Directorio directorio, boolean root);
	public abstract void desactivarDirectorios();
	public abstract void eliminar(Directorio directorio);
	public abstract boolean crearDirectorio(String ubicacion);
	

}
