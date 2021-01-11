package com.sif.digestyc.Repository.Estandarizacion;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;

@Repository
public interface TablaCorrespondenciaRepository extends JpaRepository<TablaCorrespondencia, Serializable>{
	
	@Query(value="select * from tabla_correspondencia where tabla_id=?", nativeQuery=true)
	public TablaCorrespondencia findByTabla(long tablaId);

}
