package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.TipoDatoEstandar;
import com.sif.digestyc.Repository.Estandarizacion.TipoDatoEstandarRepository;
import com.sif.digestyc.Service.Estandarizacion.TipoDatoEstandarService;

@Service("tipoDatoEstandarServiceImpl")
public class TipoDatoEstandarServiceImpl implements TipoDatoEstandarService{

	@Autowired
	@Qualifier("tipoDatoEstandarRepository")
	private TipoDatoEstandarRepository tipoDatoEstandarRepository;

	@Override
	public List<TipoDatoEstandar> findAll() {
		return tipoDatoEstandarRepository.findAll();
	}

	@Override
	public TipoDatoEstandar find(Long id) {
		return tipoDatoEstandarRepository.findById(id).orElse(null);
	}

	
}
