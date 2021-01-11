package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Repository.Catalogos.ValorTipicoRepository;
import com.sif.digestyc.Repository.Estandarizacion.CorrespondenciaRepository;
import com.sif.digestyc.Repository.Estandarizacion.ValorTipicoEstandarRepository;
import com.sif.digestyc.Service.Estandarizacion.CorrespondenciaService;

@Service("correspondenciaServiceImpl")
public class CorrespondenciaServiceImpl implements CorrespondenciaService{
	
	@Autowired
	private ValorTipicoRepository valorTipicoRepository;
	
	
	@Autowired
	private ValorTipicoEstandarRepository valorTipicoEstandarRepository;
	
	@Autowired
	private CorrespondenciaRepository correspondenciaRepository;
		
	
	@Override
	public Map<String, Object> guardarCorrespondencias(List<Map<String, Object>> datos) {
		Long valorTipicoId=0L;
		Long valorTipicoEstandarId= 0L;
		ValorTipico objetoVT   = new ValorTipico();
		ValorTipicoEstandar  objetoVTE  =  new ValorTipicoEstandar();
		
		for (Map<String, Object> map : datos) {
			if(map.containsKey("valorT")) {
				valorTipicoId =  Long.parseLong(map.get("valorT").toString());
			}
			if(map.containsKey("valorTEstandar")) {
				valorTipicoEstandarId =   Long.parseLong(map.get("valorTEstandar").toString());
			}
			Correspondencia correspondencia  =  new Correspondencia();
			objetoVT  =  valorTipicoRepository.findById(valorTipicoId).get();
			objetoVTE = valorTipicoEstandarRepository.findById(valorTipicoEstandarId).get();
			
			correspondencia.setValorTipico(objetoVT);
			correspondencia.setValorTipicoEstandar(objetoVTE);
			correspondenciaRepository.save(correspondencia);
			
		}
		return null;
	}


	@Override
	public List<Correspondencia> findAllByEstandar(Long id) {
		return correspondenciaRepository.findAllByEstandar(id);
	}

	
}
