package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Repository.Estandarizacion.ValorTipicoEstandarRepository;
import com.sif.digestyc.Service.Estandarizacion.ValorTipicoEstandarService;


@Service("valorTipicoEstandarServiceImpl")
public class ValorTipicoEstandarServiceImpl implements ValorTipicoEstandarService{
	
	@Autowired
	private ValorTipicoEstandarRepository valorTipicoRepository;

	@Override
	public void eliminarValorEstandar(ValorTipicoEstandar valorTipico) {
		valorTipicoRepository.delete(valorTipico);
		
	}

}
