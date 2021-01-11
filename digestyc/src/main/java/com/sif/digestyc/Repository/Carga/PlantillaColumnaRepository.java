package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;

@Repository
public interface PlantillaColumnaRepository extends JpaRepository<PlantillaColumna, Serializable>{
	
	@Query(value="select * from plantilla_columna where id = ?1", nativeQuery=true)
	public abstract PlantillaColumna findById(int id);

	@Query(value = "select pc.* from plantilla_columna pc inner join plantilla p on p.id = pc.plantilla_id inner join registro r on p.id = r.plantilla_id and r.institucion_id = ?", nativeQuery = true)
	public abstract ArrayList<PlantillaColumna> findbyInstitucion(Long id);
	
	@Query(value="select pc from PlantillaColumna pc where pc.plantilla.id = ?1")
	public abstract List<PlantillaColumna>  getColumnasDePlantilla(Long id);
	
	@Query(value="select pc from PlantillaColumna pc where pc.plantilla.id= ?1 and pc.codigo = ?2")
	public abstract PlantillaColumna recuperarColumnaDePlantilla(Long id, String codigo);
}
