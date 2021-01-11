package com.sif.digestyc.Repository.Carga;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Entrega;


@Repository("entregaRepository")
public interface EntregaRepository extends CrudRepository<Entrega, Long> {
	


	@Query(value="Select * from entrega where registro_id=?1 and YEAR(fecha_inicio_entrega)=year(getdate())", nativeQuery = true)
	public abstract  List<Entrega> buscarEntregaRegistroPorId(Long id);
	
	@Query(value="Select * from entrega where registro_id=?1 ", nativeQuery = true)
	public abstract  List<Entrega> buscarEntregaRegistroTodos(Long id);
	
	@Query(value="select top 1 e.* from entrega e inner join periodo p on p.id=e.periodo_id where e.id=?1 ", nativeQuery = true)
	public abstract  Entrega buscarEntrega(Long id);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	//@Modifying
	@Query(value="exec sp_nuevaEntrega ?1,?2,?3,?4", nativeQuery = true)
	public void nuevaEntrega(Long registro_id,Long periodo_id, Long formato,String codigo);
	
	@Transactional
	@Modifying
	@Query(value="exec sp_entregaVersionPlantillaUsada ?1,?2", nativeQuery = true)
	public void colocarVersionPlantilla(Long registro_id,Long version_planntilla_id);
	
	@Transactional
	@Modifying(clearAutomatically=true)	
	@Query(value="exec sp_borrarDatosEntrega ?1,?2", nativeQuery = true)
	public void borrarDatosEntrega(Long registro_id,Long entrega_id);
	

	@Transactional
	//@Modifying(clearAutomatically=true)
	@Query(value="select dbo.Fn_VerificarEntregaArchivo(?1,?2)", nativeQuery = true)
	public int Fn_VerificarEntregaArchivo(Long registro_id,Long periodo_id);
	
	@Transactional
	//@Modifying(clearAutomatically=true)
	@Query(value="select dbo.Fn_EntregaArchivoNombre(?1,?2)", nativeQuery = true)
	public String Fn_EntregaArchivoNombre(Long registro_id,Long periodo_id);
	
	@Query(value="select top 1 ubicacion from directorio where activo=1", nativeQuery = true)
	public String directorio();
	
	@Query(value="select distinct YEAR(fecha_inicio_entrega) From entrega where registro_id=?1", nativeQuery = true)
	public abstract  List<String> añosEntregas(Long id);
	
	@Query(value="Select * from entrega where registro_id=?1 and YEAR(fecha_inicio_entrega)=?2", nativeQuery = true)
	public abstract  List<Entrega> buscarEntregaRegistroPorAnio(Long id,Long anio);
	
	@Query(value="select YEAR(GETDATE())", nativeQuery = true)
	public abstract  Integer EntregaRegistroPorAnio();
	
	@Query(value = "select e from Entrega e where e.id = ?1")
	public abstract Entrega buscarEntregaPorId(Long id);
	


	//FALTA AGREGAR VALIDACION PARA EL CAMPO HABILITADO DE PLANTILLA 
	@Query(value="select pc.nombre from plantilla p inner join registro r on r.plantilla_id=p.id inner join plantilla_columna pc on pc.plantilla_id=p.id where r.id=?1", nativeQuery = true)
	public abstract  List<String> camposPlantilla(Long registro_id);
	

	//FILTROS INDIVIDUALES
		@Query(value = "select e.* from entrega e "
				+ "inner join registro r on e.registro_id=r.id "
				+ "inner join institucion i on r.institucion_id=i.id "
				+ "where i.nombre LIKE %?%", nativeQuery = true)
		public List<Entrega> findByInstitucion(String institucion);
		
		@Query(value = "select e.* from entrega e "
				+ "inner join registro r on e.registro_id=r.id"
				+ "inner join tipo_registro t_r on r.tipo_registro_id=t_r.id "
				+ "where t_r.nombre LIKE %?%", nativeQuery = true)
		public List<Entrega> findByTipo(String tiporegistro);
		
		@Query(value = "select e.* from entrega e "
				+ "inner join registro r on e.registro_id=r.id "
				+ "inner join periodicidad pe on r.periodicidad_id=pe.id"
				+ " where pe.nombre LIKE %?%", nativeQuery = true)
		public List<Entrega> findByPeriodicidad(String periodicidad);
		
		
		//FILTROS COMBINADOS LOS TRES
		@Query(value = "select e.* from entrega e "
				+ "inner join registro r on e.registro_id=r.id "
				+ "inner join institucion i on r.institucion_id=i.id "
				+ "inner join tipo_registro t_r on r.tipo_registro_id=t_r.id "
				+ "inner join periodicidad p on r.periodicidad_id=p.id "
				+ "where i.nombre LIKE %?% AND t_r.nombre LIKE %?% AND p.nombre LIKE %?%", nativeQuery = true)
		public List<Entrega> findByInstTipoPerio(String institucion, String tiporegistro, String periodicidad);
		
		@Query(value="select periodo_id from entrega e inner join registro r on e.registro_id = r.id where r.id= ?1", nativeQuery = true)
		public Long buscarPeriodo(Long id);
		
		
		@Transactional
		@Modifying(clearAutomatically=true)	
		@Query(value ="update e set e.ubicacion = ?1, e.fecha_entrega = getdate() from entrega e where e.id = ?2", nativeQuery = true)
		public void establecerRutaArchivoEntregado(String ubicacion, Long idEntrega);
		
		@Transactional
		@Modifying(clearAutomatically = true)
		@Query(value="delete r from ?1 r where r.entrega_id = ?2", nativeQuery = true)
		public void eliminarDatosDeEntregaX(String tabla, Long id);
		
		
		@Transactional
		@Modifying(clearAutomatically  = true)
		@Query(value = "update a set a.datos_cargados = 0 from archivo a where a.entrega_id = ?1", nativeQuery = true)
		public void setFalsoEstadoEntrega(Long id);

}
