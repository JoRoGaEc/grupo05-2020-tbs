package com.sif.digestyc.Service.Estandarizacion;

import java.util.List;

import com.sif.digestyc.Entity.StandardizationModule.TipoDatoEstandar;

public interface TipoDatoEstandarService {

	public List<TipoDatoEstandar> findAll();
	
	public TipoDatoEstandar find(Long id);
}
