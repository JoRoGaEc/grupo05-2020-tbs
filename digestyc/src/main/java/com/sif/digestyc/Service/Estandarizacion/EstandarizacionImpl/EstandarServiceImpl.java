package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Repository.Estandarizacion.EstandarRepository;
import com.sif.digestyc.Repository.Estandarizacion.ValorTipicoEstandarRepository;
import com.sif.digestyc.Service.Estandarizacion.IEstandarService;


@Service("estandarServiceImpl")
public class EstandarServiceImpl implements IEstandarService{

	@Autowired
	private EstandarRepository estandarRepository;
	
	@Override
	public void save(Estandar estandar) {
		estandarRepository.save(estandar);		
	}

	@Override
	public List<Estandar> findAll() {
		return IterableUtils.toList(estandarRepository.findAll());
	}

	@Override
	public Optional<Estandar> findById(long id) {
		return estandarRepository.findById(id);
	}

	@Override
	public Map<String, Object> guardarEstandarConValoresTipicos(Estandar estandar, List<Map<String, Object>> elementos) {
		List<ValorTipicoEstandar> valoresTipicosDeEstandar =  new ArrayList<>();
		Map<String, Object> resultados = new HashMap<>();
		for(Map<String, Object> datos: elementos ) {
			/*for(String key : datos.keySet()) {
				Object value = datos.get(key);
				
			}*/
			ValorTipicoEstandar  vte =  new ValorTipicoEstandar();
			String valorTipico = datos.get("valorTipico").toString();
			String descripcion =  datos.get("descripci").toString();			
			vte.setValorTipico(valorTipico);
			vte.setDescripcion(descripcion);
			valoresTipicosDeEstandar.add(vte);
			vte.setEstandar(estandar);
			
		}
		estandar.setNombre(estandar.getNombre().trim().toUpperCase());
		estandar.setValoresTipicosEstandar(valoresTipicosDeEstandar);
		save(estandar);
		return resultados;
	}

	@Override
	public void eliminarEstandar(Estandar estandar) {
		estandarRepository.delete(estandar);
		
	}

	@Override
	public Estandar actualizarEstandar(Estandar estandares) {
		return estandarRepository.save(estandares);		
	}



	
}
