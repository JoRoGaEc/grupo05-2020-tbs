package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Periodicidad;
import com.sif.digestyc.Entity.LoadModule.TipoRegistro;

@Repository("tipoRegistroRepository")
public interface TipoRegistroRepository extends CrudRepository<TipoRegistro, Long>{

	@Query("select i from TipoRegistro i where i.id = ?1")
	public abstract TipoRegistro findByIdSql(Long id);
}
