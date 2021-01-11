package com.sif.digestyc.Repository.Carga;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.HistorialPlantillaRegistro;

@Repository("historialplantillaregistroRepository")
public interface HistorialPlantillaRegistroRepository extends CrudRepository<HistorialPlantillaRegistro, Long>{

	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value="exec sp_plantilla_registro ?1,?2", nativeQuery = true)
	public void PlantillaRegistro(Long registro_id,Long plantilla_id);
	
}
