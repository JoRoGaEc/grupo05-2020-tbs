package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Registro;


@Repository("registroRepository")
public interface RegistroRepository extends CrudRepository<Registro, Long>{
	
	@Query(value="Select * from registro where id=?1", nativeQuery = true)
	public abstract Registro buscarRegistroPorId(Long id);
	
	@Query(value="Select r from Registro r where r.id = ?1")
	public abstract Registro buscarRegistroPorIdJpa(Long id);
	
	@Query("select r from Registro r JOIN r.plantilla p JOIN p.plantillaColumnas where r.id  = :idRegistro and p.habilitado = True")
	public abstract Registro buscarRegistroAndColumnas(@Param("idRegistro") Long id);
	
	@Query(value = "select codigo from registro where id=?1", nativeQuery = true)
	public abstract String codigoRegistro(Long registro_id);
}
