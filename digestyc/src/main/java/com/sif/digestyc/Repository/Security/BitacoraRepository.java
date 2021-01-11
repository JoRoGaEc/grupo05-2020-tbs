package com.sif.digestyc.Repository.Security;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Security.Bitacora;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Serializable>{
	
	@Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as row FROM  [seguridad].bitacora) a WHERE a.row > ?1 and a.row <= ?2", nativeQuery = true)
	public List<Bitacora> findBitacorasSinceUntil(int inf, int sup);
	
	public Optional<Bitacora> findById(Long id);

	@Query(value = "select count(*) as cantidad from [seguridad].bitacora", nativeQuery = true)
	public int getCantidad();
	

	@Query(value = "select count(*) as cantidad from [seguridad].bitacora where accion like %?% or descripcion like %?% or usuario like %?% or tabla like %?%", nativeQuery = true)
	public int getCantidad(String buscar1, String buscar2 ,String buscar3 ,String buscar4);
	
	@Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as row FROM  [seguridad].bitacora where accion like %?% or descripcion like %?% or usuario like %?% or tabla like %?%) a WHERE a.row > ? and a.row <= ?", nativeQuery = true)
	public List<Bitacora> findBitacorasSinceUntil(String val1, String val2, String val3, String val4, int inf, int sup);
	
}
