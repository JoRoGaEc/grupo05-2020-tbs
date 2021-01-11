package com.sif.digestyc.Service.Security;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.Security.Bitacora;

public interface BitacoraService {
	
	public abstract List<Bitacora> buscarBitacoras(int inf, int sup);
	public abstract Bitacora update(Bitacora bitacora);
	public abstract List<Bitacora> buscarTodo();
	public abstract void eliminar(Bitacora bitacora);
	public abstract Optional<Bitacora> buscarPotId(Long id);
	public abstract int getCantidad(String buscar);
	public abstract List<Bitacora> buscarBitacoras(int inf, int sup, String valor);

}
