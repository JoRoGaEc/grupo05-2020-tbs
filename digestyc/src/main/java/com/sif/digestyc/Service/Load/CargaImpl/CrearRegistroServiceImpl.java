package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Repository.Carga.CrearRegistroRepository;
import com.sif.digestyc.Service.Load.CrearRegistroService;

@Service("crearRegistroService")
public class CrearRegistroServiceImpl  implements CrearRegistroService{

	@Autowired
	@Qualifier("crearRegistroRepository")
	private CrearRegistroRepository crearRegistroRepository;
	
	@Override
	@Transactional
	public Optional<Registro> buscarPorId(Long id) {		
		return crearRegistroRepository.findById(id);
	}

	@Override
	@Transactional
	public void guardarRegistro(Registro registro) {
		crearRegistroRepository.save(registro);
		
	}


	@Transactional
	public String Fn_generarCodigoRegistro(Long tipo) {
		return crearRegistroRepository.Fn_generarCodigoRegistro(tipo);
		
	}

	
}
