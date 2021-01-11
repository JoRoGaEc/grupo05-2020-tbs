package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Repository.Carga.VariacionTipoDatoRepository;
import com.sif.digestyc.Service.Load.VariacionTipoDatoService;

@Service("variacionTipoDatoServiceImpl")
public class VariacionTipoDatoServiceImpl implements VariacionTipoDatoService {
	
	@Autowired
	@Qualifier("variacionTipoDatoRepository")
	private VariacionTipoDatoRepository variacionTDR;
	
	@Override
	@Transactional
	public void saveVariacionTipoDato(VariacionTipoDato varTipoDato) {
		variacionTDR.save(varTipoDato);
		
	}

	@Override
	@Transactional
	public List<VariacionTipoDato> variacionPorTipo(Long id) {
		
		return variacionTDR.variacionesPorTipo(id);
	}

	@Override
	@Transactional
	public Optional<VariacionTipoDato> buscarVariacionPorId(Long id) {
		return variacionTDR.findById(id);
	}

	@Override
	@Transactional
	public void delete(VariacionTipoDato varTipoDato) {
		variacionTDR.delete(varTipoDato);

	}

	@Override
	@Transactional
	public List<VariacionTipoDato> variacionPorTipoParaTipificacion(Long id) {
		return variacionTDR.variacionesPorTipoParaTipificacion(id);
	}

	
	
	
}
