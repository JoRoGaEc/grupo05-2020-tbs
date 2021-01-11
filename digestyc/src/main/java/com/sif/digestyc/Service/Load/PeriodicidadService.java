package com.sif.digestyc.Service.Load;

import java.util.List;

import com.sif.digestyc.Entity.LoadModule.Periodicidad;
import com.sif.digestyc.Entity.LoadModule.TipoRegistro;

public interface PeriodicidadService {

	public abstract List<Periodicidad> listarPeriodicidades();
	public abstract Periodicidad buscarPeriodicidadPorId(Long id);
	
}
