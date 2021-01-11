package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Archivo;

@Repository("ArchivoRepository")
public interface ArchivoRepository extends CrudRepository<Archivo, Long>{
	
	public abstract Archivo findByCodigo(String codigo);
	
	@Modifying
	@Transactional
	@Query("delete from Archivo a where a.id =?1")
	public abstract void deleteArchivoById(Long id);
	
	@Modifying
	@Transactional
	@Query(value="update a set a.datos_cargados = 1, a.fecha_subido = GETDATE() from archivo a where a.id = ?1", nativeQuery =  true)
	public void cambiarEstadoArchivoCargado(Long idArchivo);

}
