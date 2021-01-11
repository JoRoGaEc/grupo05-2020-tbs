package com.sif.digestyc.Service.Load.CargaImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Archivo;
import com.sif.digestyc.Repository.Carga.ArchivoRepository;
import com.sif.digestyc.Service.Load.ArchivoService;

@Service("ArchivoServiceImpl")
public class ArchivoServiceImpl implements ArchivoService{
	
	@Autowired
	@Qualifier("ArchivoRepository")
	private ArchivoRepository archivoRepository;
	

	@Override
	@Transactional
	public Archivo estadoArchivo(Long id) {
		return null;
	}


	@Override
	@Transactional
	public void guardarArchivo(Archivo archivo) {
		archivoRepository.save(archivo);
		
	}


	@Override
	public void deleteById(Long id) {
		archivoRepository.deleteById(id);
		
	}


	@Override
	public void cambiarEstadoArchivoCargado(Long idArchivo) {
		archivoRepository.cambiarEstadoArchivoCargado(idArchivo);
		
	}

	

}
