package com.sif.digestyc.Service.Load.CatalogosImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Service.Load.TipoDatoService;
import com.sif.digestyc.Entity.LoadModule.TipoDato;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Repository.Catalogos.TipoDatoRepository;

@Service("TipoDatoServiceImpl")
public class TipoDatoServiceImpl implements TipoDatoService{
	
	@Autowired
	@Qualifier("TipoDatoRepository")
	private TipoDatoRepository tipoDatoRepository;

	@Override
	public Optional<TipoDato> buscarTipoDatoPorId(TipoDato tipoDato) {
		return (Optional<TipoDato>) tipoDatoRepository.findById(tipoDato.getId());
	}

	@Override
	public List<TipoDato> listarTipoDato() {
		return (List<TipoDato>)tipoDatoRepository.findAll(); //devuelve un Iterable por eso lo casteamos
	}

	@Override
	public TipoDato agregarTipoDato(TipoDato TipoDato) {
		return tipoDatoRepository.save(TipoDato);
		
	}

	@Override
	public void eliminarTipoDato(Long id) {
		tipoDatoRepository.deleteById(id);
		
	}

	@Override
	public TipoDato actualizadTipoDato(TipoDato TipoDato) {
		return tipoDatoRepository.save(TipoDato);
	}

	@Override
	public TipoDato buscarTipoDatoPorId(Long id) {		
		return tipoDatoRepository.findByIdSql(id);
	}

	
	
	

}
