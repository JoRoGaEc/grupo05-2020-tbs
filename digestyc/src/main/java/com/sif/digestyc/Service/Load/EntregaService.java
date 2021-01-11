package com.sif.digestyc.Service.Load;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Registro;


public interface EntregaService {
	
	public abstract List<Entrega> findAll();
	public abstract List<Entrega> buscarEntregaRegistroPorId(Long id);
	public  void nuevaEntrega(Long registro_id,Long periodo_id,Long formato,String codigo);
	public int Fn_VerificarEntregaArchivo(Long registro_id,Long periodo_id);
	public String Fn_EntregaArchivoNombre(Long registro_id,Long periodo_id);
	public String directorio();
	public abstract  List<String> añosEntregas(Long id);
	public abstract  List<Entrega> buscarEntregaRegistroPorAnio(Long id,Long anio);
	public abstract  List<Entrega> buscarEntregaRegistroTodos(Long id);
	public abstract  Integer EntregaRegistroPorAnio();

	public abstract  Boolean camposPlantilla(Long registro_id,String campos);
	public void borrarDatosEntrega(Long registro_id,Long entrega_id);
	
	public abstract Entrega entregaPorId(Long id);
	public abstract  Entrega buscarEntrega(Long id);
	
	public abstract List<Entrega> findByInstitucion(String institucion);
	public abstract List<Entrega> findByTipo(String tiporegistro);
	public abstract List<Entrega> findByPeriodicidad(String periodicidad);
	public abstract List<Entrega> findByInstTipoPerio(String institucion, String tiporegistro, String periodicidad);
	public Long buscarPeriodo(Long id);
	
	public abstract ArrayList<Map<String, String>> leerDatosTxtCsvParaInsert(String ubicacion, Registro registro, int tipo);

	public abstract void actualizar(Entrega entrega);
	
	public abstract void colocarVersionPlantilla(Long entrega_id, Long version_plantilla_id);
	
	public void establecerRutaArchivoEntregado(String ubicacion, Long idEntrega);
	
	public Map<String, Object> realizarEntregaArchivo(MultipartFile file, Long id_periodo, Long id_entrega, Long id_registro, Integer anio, Boolean esEstrega);
	
	public void setFalsoEstadoEntrega(Long id);
	
	public Map<String, Object> asignarVersionPlantillaUsadaParaSubirArchivo(Entrega entrega);
	

}
