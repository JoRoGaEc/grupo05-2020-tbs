package com.sif.digestyc.Repository.Carga;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;

@Repository
public interface ColumnaVersionPlantillaRepository extends CrudRepository<ColumnaVersionPlantilla, Long>{

	@Query(value ="select distinct cvp.* from columna_version_plantilla cvp inner join version_plantilla vp on cvp.version_plantilla_id = vp.id inner join registro r on r.plantilla_id = vp.plantilla_id and r.id = ?", nativeQuery=true)
	List<ColumnaVersionPlantilla> findVersionColumnByIdRegister(Long id);

}


