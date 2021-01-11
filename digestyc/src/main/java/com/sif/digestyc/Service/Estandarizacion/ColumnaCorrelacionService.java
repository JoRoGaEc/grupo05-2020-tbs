package com.sif.digestyc.Service.Estandarizacion;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;

public interface ColumnaCorrelacionService {
	
	public abstract ColumnaCorrespondencia actualizar(ColumnaCorrespondencia columnaCorrespondencia);
	public abstract List<ColumnaCorrespondencia> buscarTodas();
	public abstract ColumnaCorrespondencia buscarPorVersionColumna(long versionColumnaId);
	public abstract Optional<ColumnaCorrespondencia> buscarPorId(long id);
	
}
