package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Tabla;

@Repository
public interface TablaRepository extends JpaRepository<Tabla, Serializable>{
		
	Optional<Tabla> findById(Long id);
	
	@Query(value="select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?", nativeQuery=true)
	public List<String> getColumn(String table);

	@Query(value="select top 1 * from tabla where nombre = ? and archivo_id=? and id = ?", nativeQuery=true)
	public Optional<Tabla> findByName(String nombre, long archivo_id, long id);

	@Query(value="select * from tabla where estado>=0 and estado<=5", nativeQuery=true)
	public List<Tabla> findTablaValida();

	@Query(value="select name from sysobjects where name=? and xtype=\'U\'", nativeQuery=true)
	public List<String> existsTable(String table);
	
	@Modifying
	@Transactional
	@Query(value="update t set t.estado = -1 from tabla t where t.nombre= ?1 and t.archivo_id = ?2", nativeQuery = true)
	public void actualizarEstados(String nombreTabla, Long id);


	@Modifying
	@Transactional
	@Query(value="update t set t.arhivo_id = ?2 from tabla t where t.id=?1", nativeQuery = true)
	public void colocarArchivoId(Long idTabla, Long idArchivo);

	@Query(value="SELECT COLUMN_NAME, IS_NULLABLE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?", nativeQuery = true)
	public List<ArrayList<String>> getDescriptionTable(String table);

	@Query(value="select top 1 nombre_tabla_correspondiente from tabla_correspondencia where tabla_id = ?", nativeQuery = true)
	public String getTableToInsert(long idTabla);

	@Query(value = "select count(*) from tipificacion as t inner join columna_version_plantilla cvp on t.id=cvp.tipificacion_id inner join version_plantilla as vp on cvp.version_plantilla_id = vp.id inner join entrega as e on e.version_plantilla_id = vp.id and e.id = ?", nativeQuery=true)
	public long haveTipificacion(Long id);

	@Query(value = "select count(*) from columna_version_plantilla cvp inner join version_plantilla as vp on cvp.version_plantilla_id = vp.id inner join entrega as e on e.version_plantilla_id = vp.id and e.id = ?", nativeQuery=true)
	public long haveColumnasVersion(Long id);
	
}
