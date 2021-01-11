package com.sif.digestyc.Repository.Catalogos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.TipoDato;

@Repository("TipoDatoRepository")
public interface TipoDatoRepository  extends CrudRepository<TipoDato, Long>{
	
	//public abstract VariacionTipoDato findByNombre(String nombre); //Esta tiene nomenclatura especial no vamos a implementarlo
	
	@Query(value = "select * from tipo_dato where id = ?1", nativeQuery = true)//lenguaje solo para SQL
	//@Query("select i from TipoDato i where i.id = ?1")
	public abstract TipoDato findByIdSql(Long id); 
		
	

}
