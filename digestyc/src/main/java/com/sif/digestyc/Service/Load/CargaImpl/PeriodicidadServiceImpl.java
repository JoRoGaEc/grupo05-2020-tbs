package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Periodicidad;
import com.sif.digestyc.Repository.Carga.PeriodicidadRepository;
import com.sif.digestyc.Service.Load.PeriodicidadService;

@Service("periodicidadService")
public class PeriodicidadServiceImpl implements PeriodicidadService{

	@Autowired
	@Qualifier("periodicidadRepository")
	private PeriodicidadRepository periodicidadRepository;
	
	@Override
	@Transactional
	public List<Periodicidad> listarPeriodicidades() {
		return (List<Periodicidad>) periodicidadRepository.findAll();
	}

	@Override
	@Transactional
	public Periodicidad buscarPeriodicidadPorId(Long id) {
		return periodicidadRepository.findByIdSql(id);
	}

	
}
