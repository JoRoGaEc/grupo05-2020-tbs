package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;

@Repository
public interface PlantillaRepository extends JpaRepository<Plantilla, Serializable>{
	
	public abstract Optional<Plantilla> findById(Long id);

	@Query(value="select p.id, p.habilitado, p.nombre from plantilla as p inner join registro as r on r.plantilla_id =p.id and r.id = ?1 and p.habilitado = 1", nativeQuery = true)
	public abstract Plantilla buscarPlantillaPorRegistro(Long id);
	
	@Query(value="select p from Plantilla p JOIN p.registro r JOIN FETCH p.plantillaColumnas pc where r.id=?1")
	public abstract Plantilla buscarPlantillaPorRegistroJpa(Long id);
	
	
}
