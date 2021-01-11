package com.sif.digestyc.Service.Load;

import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Registro;

public interface CrearRegistroService {

	public abstract Optional<Registro> buscarPorId(Long id);
	public abstract void guardarRegistro(Registro registro);
	
	public abstract String Fn_generarCodigoRegistro(Long tipo);
	
}
