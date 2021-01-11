package com.sif.digestyc.Service.Load;

import com.sif.digestyc.Entity.LoadModule.Registro;

public interface EditarRegistroService {
	
	public abstract Registro findById(Long id);
	public abstract void updateRegistro(Registro registro);



}
