package com.sif.digestyc.Service.Load;

import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Formato;

public interface FormatoService {
	public abstract Optional<Formato> buscarFormato(Long id);
	
	public abstract Formato buscarFormatoPorExtension(String extension);
}
