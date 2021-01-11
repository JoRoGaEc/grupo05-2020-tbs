package com.sif.digestyc.Service.Security;

import java.util.List;

import com.sif.digestyc.Entity.Security.Permiso;

public interface PermisoService {

	public abstract Permiso findById(int id);

	public abstract List<Permiso> findByRole(int id_role);

	public abstract List<Permiso> findAll();

	public abstract Permiso update(Permiso permiso);

	public abstract void delete(Permiso permiso);

	public abstract List<Permiso> findByName(String name);
	
	public abstract Permiso save(Permiso permiso);
	
	public abstract List<Permiso> findAllLessRole(int id_role);

}
