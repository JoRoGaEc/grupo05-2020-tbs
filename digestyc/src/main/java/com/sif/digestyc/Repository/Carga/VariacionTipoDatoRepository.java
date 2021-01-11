package com.sif.digestyc.Repository.Carga;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;

@Repository("variacionTipoDatoRepository")
public interface VariacionTipoDatoRepository extends CrudRepository<VariacionTipoDato, Long>{

	@Query(value="select * from variacion_tipo_dato vtd where vtd.tipo_dato_id = ?1 "
    	+ "except select * from variacion_tipo_dato var where var.id between 1 and 7" , nativeQuery = true)
	public abstract List<VariacionTipoDato> variacionesPorTipo(Long id);

	@Query(value="select * from variacion_tipo_dato vtd where vtd.tipo_dato_id = ?1", nativeQuery = true)
	public abstract List<VariacionTipoDato> variacionesPorTipoParaTipificacion(Long id);
	
	
	public abstract Optional<VariacionTipoDato> findById(Long id);
	
	
	
} 
