package com.sif.digestyc.Repository.Security;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Security.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Serializable> {

	@Query(value = "select * from [seguridad].usuario where id = ?", nativeQuery = true)
	public Usuario findById(int id);

	public abstract Usuario findByUsername(String username);
	
	@Query(value = "select * from [seguridad].usuario where enabled = ?", nativeQuery = true)
	public abstract List<Usuario> findAll(Boolean enable);
	

}
