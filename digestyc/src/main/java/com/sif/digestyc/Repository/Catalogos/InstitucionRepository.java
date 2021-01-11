package com.sif.digestyc.Repository.Catalogos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Registro;

@Repository("institucionRepository")
public interface InstitucionRepository extends CrudRepository<Institucion, Long>{
	
	//public abstract  Optional<Institucion> findById(Long id);
	public abstract Institucion findByNombre(String nombre); //Esta tiene nomenclatura especial no vamos a implementarlo
	
	@Query("select i from Institucion i where i.id = ?1")
	public abstract Institucion findByIdSql(Long id);
	
	
	@Query("select r from Registro r where r.institucion.id = ?1 and r.tipoRegistro.id = ?2")
	public List<Registro> registroAdministraticoByInstitucion(Long id, Long idTipo);
	
	
	
}
