package com.sif.digestyc.Service.Load;

import java.util.List;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.TipoRegistro;

public interface TipoRegistroService {

	public abstract List<TipoRegistro>  listarTipoRegistros();
	public abstract TipoRegistro buscarTipoRegistroPorId(Long id);
	
}
