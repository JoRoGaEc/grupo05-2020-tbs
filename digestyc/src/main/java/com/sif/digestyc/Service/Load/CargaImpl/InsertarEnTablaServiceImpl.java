package com.sif.digestyc.Service.Load.CargaImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.Mapping.ObjectData;
import com.sif.digestyc.Entity.Mapping.ObjectDataMapping;
import com.sif.digestyc.Service.Load.InsertarEnTablaService;
import com.sif.digestyc.utilities.ConexionSqlServer;
import com.sif.digestyc.utilities.rendimiento.MeasurementUtils;

import de.bytefish.jsqlserverbulkinsert.SqlServerBulkInsert;

@Service("insertarEnTablaServiceImpl")
public class InsertarEnTablaServiceImpl extends ConexionSqlServer implements InsertarEnTablaService{
	private Logger LOG = LoggerFactory.getLogger(InsertarEnTablaServiceImpl.class);	
	
	@Override
	@Transactional
	public boolean insertarConBulk(long registro_id, long entrega_id, List<Map<String, String>> datos, List<String> columnas, String tabla){
		LOG.info("ini - insertarEnTablaService --");
		List<ObjectDataMapping> datosInsertar = new ArrayList<>();
		for(Map<String, String> mapa : datos) {
			//LOG.info("MAPA "+ mapa);
			datosInsertar.add(new ObjectDataMapping(mapa, registro_id, entrega_id));
		}
		try {
			this.abrirConexion();	
			LOG.info("TABLA " + tabla);
			this.crearTablaSiNoExiste(tabla, columnas);
			ObjectData data = new ObjectData(tabla, columnas);
			SqlServerBulkInsert<ObjectDataMapping> bulkInsert = new SqlServerBulkInsert<>(data);
			MeasurementUtils.MeasureElapsedTime("INSERT ONLY DATA", () -> {
				bulkInsert.saveAll(conexion, datosInsertar.stream());
			});
			this.cerrarConexion();			
		}catch (Exception e) {
			System.out.println("Msj en el catch de insertar con bulk " + e.getMessage());
			return false;
		}
		LOG.info("fin - insertarEnTablaService --");
		return true;
	}	
}