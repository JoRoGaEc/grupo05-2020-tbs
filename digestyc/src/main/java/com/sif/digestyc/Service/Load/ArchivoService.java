package com.sif.digestyc.Service.Load;

import com.sif.digestyc.Entity.LoadModule.Archivo;


public interface ArchivoService {
	
	public abstract Archivo estadoArchivo(Long id);
	
	public abstract void guardarArchivo(Archivo archivo);
	
	
	public void deleteById(Long id);
	
	public void cambiarEstadoArchivoCargado(Long idArchivo);

}
