package com.sif.digestyc.Service.Load.CargaImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.DatoOrigen;
import com.sif.digestyc.Entity.LoadModule.FilaOrigen;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Entity.Mapping.DatoMapping;
import com.sif.digestyc.Repository.Carga.ColumnaOrigenRepository;
import com.sif.digestyc.Repository.Carga.FilaRepository;
import com.sif.digestyc.Repository.Carga.TablaRepository;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.utilities.ConexionSqlServer;
import com.sif.digestyc.utilities.rendimiento.MeasurementUtils;

import de.bytefish.jsqlserverbulkinsert.SqlServerBulkInsert;

@Service("cargarArchivoServiceImpl")
public class CargarArchivoServiceImpl extends ConexionSqlServer implements CargarArchivoService{
	
	private static final Logger LOG=  LoggerFactory.getLogger(CargarArchivoServiceImpl.class);
	
	private static final String cadenas = "string varchar char character";
	private static final String enteros = "integer int long";
	private static final String flotante = "float double numeric decimal";
	private static final String fecha = "datetime calendar";
	
	@Autowired
	private TablaRepository tablaRepository;
	
	@Autowired
	private FilaRepository filaRepository;
	
	@Autowired
	private ColumnaOrigenRepository columnaOrigenRepository;

	@Override
	@Transactional
	public boolean existeColumna(List<PlantillaColumna> plantillaColumnas, String columna) {
		Optional<PlantillaColumna> columnaResponse = plantillaColumnas.stream().filter(c-> c.getNombre().equalsIgnoreCase(columna) || c.getCodigo().equalsIgnoreCase(columna)).findFirst();
		return columnaResponse.isPresent();
	}
			
	
	@Override
	@Transactional
	public boolean insertarConBulk(Tabla tabla, ArrayList<Map<String, String>> datos)  throws SQLException {
		boolean retorno = false;
		LOG.info("ANTES DE LA CONSULTA ");
		List<ColumnaOrigen> columnas = columnaOrigenRepository.buscarColumnaPorTabla(tabla.getId());
		List<DatoOrigen> datosOrigen = new ArrayList<>();
		if(!columnas.isEmpty()) {
			for(Map<String, String> mapa : datos) {
				Iterator<String> iterador = mapa.keySet().iterator(); //Obtenemos todas las columnas y sus valores
				FilaOrigen fila = new FilaOrigen();
				fila.setTabla(tabla);
				fila = filaRepository.save(fila);
				while(iterador.hasNext()) {
					String key = iterador.next(); // Columna
					String value = mapa.get(key); //valor de esa columna
					Optional<ColumnaOrigen> col = columnas.stream().filter(c-> c.getCodigo().equalsIgnoreCase(key)).findFirst();
					if(col.isPresent()) {
						ColumnaOrigen c = col.get();
						DatoOrigen dato = new DatoOrigen();
						dato.setColumnaOrigen(c);
						dato.setDato(value);
						LOG.info("COLUMNA: "+ c.getId() + "-" + c.getCodigo());
						LOG.info("EN LA FILA " + fila.getId());
						dato.setFilaOrigen(fila);
						datosOrigen.add(dato);
					}
				}

			}
		}
		/*
		 * Aqui va la magia de Bulk
		 */
		//Primero se va a probar sin meter datos
		this.abrirConexion();		
		DatoMapping datoMapping = new DatoMapping();
		LOG.info("ANTES DE HACER EL VOLCADO");
		SqlServerBulkInsert<DatoOrigen> bulkInsert = new SqlServerBulkInsert<>(datoMapping);
		MeasurementUtils.MeasureElapsedTime("INSERT ONLY DATA", () -> {
			// Now save all entities of a given stream:
			bulkInsert.saveAll(conexion, datosOrigen.stream());
		});
		this.cerrarConexion();
		retorno = true;
		return retorno;
	}
	
	@Override
	@Transactional
	public void actualizarColumnas(List<String> columnas, String tabla) {
		List<String> columnasActuales = tablaRepository.getColumn(tabla);
		for(String columna : columnas) {
			Optional<String> col = columnasActuales.stream().filter(s-> s.equalsIgnoreCase(columna)).findFirst();
			if(!col.isPresent()) { //Insertamos solo las columnas que no estan presente
				addColumn(tabla, columna);
			}
		}
	}
	

	
}
