package com.sif.digestyc.Service.Load.CargaImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.Periodo;
import com.sif.digestyc.Repository.Carga.PeriodoRepository;
import com.sif.digestyc.Service.Load.PeriodoService;

@Service("periodoServiceImpl")
public class PeriodoServiceImpl implements PeriodoService{
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Override
	public Periodo update(Periodo periodo) {
		return periodoRepository.save(periodo);
	}
	
}
