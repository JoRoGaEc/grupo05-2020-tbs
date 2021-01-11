package com.sif.digestyc.Service.Load.CargaImpl; 
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.PlantillaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Service.Load.ArchivoDbfService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("archivoDbfServiceImpl")
public class ArchivoDbfServiceImpl  implements ArchivoDbfService{

	private static final Logger LOG=  LoggerFactory.getLogger(ArchivoDbfServiceImpl.class);
	
	@Autowired
	@Qualifier("registroRepository")
	private RegistroRepository registroRepositoryImpl;
	
	@Autowired
	private PlantillaRepository plantillaRepositoryImpl;

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;
	
	@Override
	@Transactional
	public ArrayList<Map<String, String>> leerDatosDbfParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro) {
		List<Map<String, String>> datos =  new ArrayList<Map<String,String>>();//para pasarle los dato	
		Map<String, String> mapaParaFila = null;
		
		List<String> columnasExcel =  leerColumnasDbfDelServidor(ubicacion);
		
		DBFReader reader = null;
		try {
			reader = new DBFReader(new FileInputStream(ubicacion));
			Object[] rowObjects;

			int j=0;
			LOG.info("ANTES DEL WHILE ");
			while ((rowObjects = reader.nextRecord()) != null) {
				mapaParaFila = new HashMap<String, String>(); 
				mapaParaFila = llenarFilaConVacios(pColumnas, mapaParaFila); //Esta fila es nueva
				for (int i = 0; i < rowObjects.length; i++) {
					mapaParaFila.put(columnasExcel.get(i).toUpperCase(), String.valueOf(rowObjects[i]));
				}
				datos.add(mapaParaFila);
			}
		
		
		}
         catch(Exception e) {
        	 
         }finally{
        	 DBFUtils.close(reader);
         }
		
		return (ArrayList<Map<String, String>>) datos;
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<String> leerColumnasDbfDelServidor(String ubicacion) {
		DBFReader reader = null;
		List<String> listadoColumnas = new ArrayList<String>();
		try {
			reader = new DBFReader(new FileInputStream(ubicacion));
			int numberOfFields = reader.getFieldCount();//Numero de columnas
			for (int i = 0; i < numberOfFields; i++) {
				String campo ="";
				DBFField field = reader.getField(i);
				listadoColumnas.add(field.getName()); 
			}		
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}

	/*Para verificar que las columnas que se tiene en la plantilla coinciden con las que se van a subir del documento */
	@Override
	@Transactional(readOnly = true)
	public boolean validarColumnasDbf(String ubicacion, Registro registro) {
		LOG.info("ID EL REGISTRO " + registro.getId());
		boolean columnasCoinciden = false;
		
		/*Cambios aqui para recuperar columnas del servidor
		 * versionPlantillaServiceImpl nos permitira recuperar la plantilla habilitada de un registro
		 * pasandole el ID*/
		VersionPlantilla versionPlantillaActiva  = versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada(registro.getId());		
		List<String> listadoColumnasBd = new ArrayList<String>();				
		/**/		
		List<String> listadoColumnasExcel= leerColumnasDbfDelServidor(ubicacion);
		for(ColumnaVersionPlantilla pc : versionPlantillaActiva.getVersionesColumna()) {
			listadoColumnasBd.add(pc.getPlantillaColumna().getNombre().toUpperCase());
		}
		for(String col : listadoColumnasExcel) {
			if(listadoColumnasExcel.size() != listadoColumnasBd.size()) {
				columnasCoinciden =false;
				break;
			}else {
				if(listadoColumnasBd.contains(col.toUpperCase())) {//Se verifica si el de la base contiene un similar en el Excel
					//Si en el excel hay repetidos va a haber uno que ya no coincida
					//Falta validar que no haya repetidos
					columnasCoinciden = true;
					continue;//Continue funciona par for, while, y Do-while				
				}else {
					columnasCoinciden =  false;
					break;
				}
					
			}
		}
		
		return columnasCoinciden;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getColumnasFromDbf(InputStream is) {
		DBFReader reader = null;
		List<String> listadoColumnas = new ArrayList<String>();
		try {
			reader = new DBFReader(is);
			int numberOfFields = reader.getFieldCount();//Numero de columnas
			for (int i = 0; i < numberOfFields; i++) {
				String campo ="";
				DBFField field = reader.getField(i);
				listadoColumnas.add(field.getName()); 
			}		
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}
	
	
	private Map<String, String> llenarFilaConVacios(List<PlantillaColumna> pColumnas, Map<String, String> mapaParaFila) {
		for(PlantillaColumna pc : pColumnas) {
			mapaParaFila.put(pc.getNombre(), "");
		}
		return mapaParaFila;
	}

	
	
}
