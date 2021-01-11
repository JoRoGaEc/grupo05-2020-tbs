package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Repository.Carga.FormatoRepository;
import com.sif.digestyc.Service.Load.FormatoService;

@Service("formatoServiceImpl")
public class FormatoServiceImpl implements FormatoService{

	
	@Autowired
	private FormatoRepository formatoRepository;

	@Override
	@Transactional
	public Optional<Formato> buscarFormato(Long id) {
		return formatoRepository.findById(id);
	}

	@Override
	@Transactional
	public Formato buscarFormatoPorExtension(String extension) {
	
		return formatoRepository.findByExtension(extension);
	}
	
	


}
