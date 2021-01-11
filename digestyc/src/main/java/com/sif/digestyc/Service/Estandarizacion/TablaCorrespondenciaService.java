package com.sif.digestyc.Service.Estandarizacion;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;

public interface TablaCorrespondenciaService {
	
	public abstract TablaCorrespondencia actualizar(TablaCorrespondencia tabla);
	public abstract Optional<TablaCorrespondencia> encontrarPorId(Long id);
	public abstract List<TablaCorrespondencia> buscarTodo();
	public abstract TablaCorrespondencia buscarPorTabla(long tablaId);

}
