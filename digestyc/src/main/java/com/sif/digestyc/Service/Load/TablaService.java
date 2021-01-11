package com.sif.digestyc.Service.Load;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Tabla;

public interface TablaService {

	public abstract Tabla actualizar(Tabla tabla);
	public abstract Optional<Tabla> buscar(Long id);
	public abstract List<Tabla> getTablas();
	public abstract void eliminar(Tabla tabla);
	public abstract List<String> obtenerColumnas(String tabla);
	public abstract Optional<Tabla> buscar(String nombre, long archivo_id, long id);
	public abstract List<Tabla> getTablasValidas();
	public abstract boolean existeTabla(String table);
	
	public abstract void colocarArchivoId(Long idTabla, Long idArchivo);
	
	public abstract List<ArrayList<String>> getDescripcionTabla(String tabla);
	public abstract String getTablaDestino(long idTabla);
	public abstract boolean tieneTipificacion(Long id);
	

}
