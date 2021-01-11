package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.TipoRegistro;
import com.sif.digestyc.Repository.Carga.TipoRegistroRepository;
import com.sif.digestyc.Service.Load.TipoRegistroService;

@Service("tipoRegistroService")
public class TipoRegistroServiceImpl implements TipoRegistroService{

	@Autowired
	@Qualifier("tipoRegistroRepository")
	private TipoRegistroRepository tipoRegistroRepository;
	
	@Override
	@Transactional
	public List<TipoRegistro> listarTipoRegistros() {
		return (List<TipoRegistro>) tipoRegistroRepository.findAll();
	}

	@Override
	@Transactional
	public TipoRegistro buscarTipoRegistroPorId(Long id) {
			return tipoRegistroRepository.findByIdSql(id);
	}

}
