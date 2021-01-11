package com.sif.digestyc.Service.Security;

import java.util.List;

import com.sif.digestyc.Entity.Security.Usuario;

public interface UsuarioService {

	public abstract Usuario findById(int id);

	public abstract List<Usuario> findAll();

	public abstract Usuario update(Usuario usuario);

	public abstract boolean delete(Usuario usuario);
	
	public abstract List<Usuario> findAll(Boolean enable);
	
	public abstract Usuario findByUsername(String username);


}
