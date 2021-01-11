package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Periodicidad;

@Repository("periodicidadRepository")
public interface PeriodicidadRepository  extends CrudRepository<Periodicidad, Long>{
	
	@Query("select i from Periodicidad i where i.id = ?1")
	public abstract Periodicidad findByIdSql(Long id);
}
