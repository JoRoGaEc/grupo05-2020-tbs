package com.sif.digestyc.Repository.Estandarizacion;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;

@Repository
public interface ColumnaCorrespondenciaRepository extends JpaRepository<ColumnaCorrespondencia, Serializable>{
	
	public Optional<ColumnaCorrespondencia> findById(long id);

	@Query(value="select cc.* from columna_correspondencia cc where columna_version_plantilla_id = ?", nativeQuery=true)
	public ColumnaCorrespondencia findByVersionColumn(long versionColumnaId);

	
	
}
