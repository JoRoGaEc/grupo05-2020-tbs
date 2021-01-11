package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;

@Repository
public interface VersionPlantillaRepository extends CrudRepository<VersionPlantilla	, Long>{
	
	@Query("select vp from VersionPlantilla vp "
			+ "JOIN vp.plantilla p "
			+ "JOIN p.registro r where vp.habilitada = 1 and r.id = ?1")
	public abstract VersionPlantilla buscarPlantillaHabilitada(Long id);
	
	@Modifying
	@Transactional	
	@Query(value="update VersionPlantilla vp set vp.habilitada = 0 where vp.id =?1 and vp.plantilla.id  = ?2")
	public void desabilitarDemasPlantillas(Long id, Long p_id);
	
	@Modifying
	@Transactional	
	@Query(value="update vp set vp.habilitada = 0 from version_plantilla vp where vp.plantilla_id = ?1", nativeQuery = true)
	public int desabilitarTodasLasPlantillas(Long idPlantilla);

	@Modifying
	@Transactional	
	@Query(value="update vp set vp.habilitada = 1 from version_plantilla vp where vp.id =?1 and vp.plantilla_id = ?2", nativeQuery = true)
	public int habilitarVersionPlantilla(Long idVersion, Long idPlantilla);

	@Query(value="select count (id) from entrega where version_plantilla_id = ?", nativeQuery=true)
	public abstract long canEdit(Long id);
	
	@Transactional
	@Query(value="select dbo.Fn_CodVersionPlantilla(?1)", nativeQuery = true)
	public String Fn_CodVersionPlantilla(Long id);
	
}
