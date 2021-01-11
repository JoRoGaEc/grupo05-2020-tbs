package com.sif.digestyc.Service.Estandarizacion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;

public interface IEstandarService {
	
	public void save(Estandar estandar);
	public List<Estandar> findAll();
	public Optional<Estandar> findById(long id);
	public abstract void eliminarEstandar(Estandar estandar);
	public Map<String, Object> guardarEstandarConValoresTipicos(Estandar estandar, List<Map<String, Object>> elementos);

	public abstract Estandar actualizarEstandar(Estandar estandares);

}
