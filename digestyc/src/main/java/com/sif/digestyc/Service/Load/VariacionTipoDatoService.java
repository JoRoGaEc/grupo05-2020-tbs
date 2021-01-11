package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;

public interface VariacionTipoDatoService {

	
	public abstract void saveVariacionTipoDato(VariacionTipoDato varTipoDato);
	public abstract List<VariacionTipoDato> variacionPorTipo(Long id);
	public abstract List<VariacionTipoDato> variacionPorTipoParaTipificacion(Long id);
	
	public abstract Optional<VariacionTipoDato> buscarVariacionPorId(Long id);
	
	public abstract void delete(VariacionTipoDato vtd);
	
}
