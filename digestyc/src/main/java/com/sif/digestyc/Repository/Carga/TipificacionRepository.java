package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Tipificacion;

@Repository("tipificacionRepository")
public interface TipificacionRepository extends JpaRepository<Tipificacion, Serializable>{
	
	@Query(value = "select * from tipificacion where id = ?", nativeQuery = true)
	public Tipificacion findById(int id);


	@Query(value = "select t.id, t.* from tipificacion as t inner join institucion_tipificacion as i on i.tipificacion_id = t.id and i.institucion_id = ?1", nativeQuery = true)
	public List<Tipificacion> findByInstitucion(int id);
	
	@Query(value = "select top 1 t.* from tipificacion as t inner join institucion_tipificacion as i on i.tipificacion_id = t.id and i.institucion_id = ?1 and t.nombre = \'?2\'", nativeQuery = true)
	public Optional<Tipificacion> findByInstitucion(Long id, String nombre);

	@Query(value = "select * from tipificacion"
			+ " except "
			+ " select t.* from tipificacion as t inner join institucion_tipificacion as i on i.tipificacion_id = t.id and i.institucion_id = ?1", nativeQuery = true)
	public List<Tipificacion> findAllLessId(int id);
	
	
}
