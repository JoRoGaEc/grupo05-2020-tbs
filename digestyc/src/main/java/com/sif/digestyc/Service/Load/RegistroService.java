package com.sif.digestyc.Service.Load;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Entity.LoadModule.Registro;


public interface RegistroService {
	
	public abstract List<Registro> findAll();
	public abstract Registro buscarRegistroPorId(Long id);
	public abstract Registro buscarRegistroPorIdJpa(Long id);
	public void uploadFile(MultipartFile file) throws Exception ;
	public abstract void uploadFile2(MultipartFile file);
	public abstract Registro buscarRegistroConColumnas(Long id);
	
	public abstract String codigoRegistro(Long registro_id);
}
