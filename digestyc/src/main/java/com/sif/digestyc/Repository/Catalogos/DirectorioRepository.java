package com.sif.digestyc.Repository.Catalogos;

import java.io.Serializable;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Directorio;

@Repository
public interface DirectorioRepository extends JpaRepository<Directorio, Serializable>{
	
	@Query(value="select top 1 * from directorio where activo = ?1",nativeQuery=true)
	public Optional<Directorio> obtenerDirectoryActivo(boolean activo); 
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update directorio set activo = 0", nativeQuery = true)
	public void desactivarDirectorios();
	
	
	
}
