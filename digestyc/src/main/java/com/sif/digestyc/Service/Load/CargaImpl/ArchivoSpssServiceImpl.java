 package com.sif.digestyc.Service.Load.CargaImpl;

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

import com.bedatadriven.spss.SpssDataFileReader;
import com.bedatadriven.spss.SpssVariable;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.PlantillaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Service.Load.ArchivoSpssService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("archivoSpssServiceImpl")
public class ArchivoSpssServiceImpl implements ArchivoSpssService {

	private static final Logger LOG = LoggerFactory.getLogger(ArchivoSpssServiceImpl.class);
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
	public ArrayList<Map<String, String>> leerDatosSpssParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro) {
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		Map<String, String> mapaParaFila = null; // Declarar

		List<String> columnasSpss = leerColumnasSpssDelServidor(ubicacion);// Las columnas del archivo
		SpssDataFileReader reader = null;
		try {// Leer el archivo especifico SPSS
			reader = new SpssDataFileReader(ubicacion);
			int numeroColumnas = reader.getVariables().size();

			while (reader.readNextCase()) {
				mapaParaFila = new HashMap<String, String>();// Despues de la fila
				mapaParaFila = llenarFilaConVacios(pColumnas, mapaParaFila); //Esta fila es nueva
				for (int j = 0; j < numeroColumnas; j++) {
					String dato = null;
					if (reader.getStringValue(j) != null) {
						dato = reader.getStringValue(j);
					} else {
						dato = String.valueOf(reader.getDoubleValue(j));
					}
					/*Aqui ya le esta pasando el nombre de la columna en la que va insertar los datos*/
					mapaParaFila.put(columnasSpss.get(j).toUpperCase(), dato);
				}
				datos.add(mapaParaFila);
			}
		} catch (Exception e) {

		}
		return (ArrayList<Map<String, String>>) datos;
	}
	
	private Map<String, String> llenarFilaConVacios(List<PlantillaColumna> pColumnas, Map<String, String> mapaParaFila) {
		for(PlantillaColumna pc : pColumnas) {
			mapaParaFila.put(pc.getNombre(), "");
		}
		return mapaParaFila;
	}


	/* SERIA EL MISMO PARA TODOS. 
	 * Para verificar que las columnas que se tiene en la
	 * plantilla coinciden con las que se van a subir del documento
	 */
	@Override
	@Transactional
	public boolean validarColumnasSpss(String ubicacion, Registro registro) {
		boolean columnasCoinciden = false;
		VersionPlantilla versionPlantillaActiva  = versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada(registro.getId());		
		//Plantilla plantillaDb  =  plantillaRepositoryImpl.buscarPlantillaPorRegistroJpa(registro.getId());						
		List<String> listadoColumnasBd = new ArrayList<String>();
	
		
		List<String> listadocolumnasSpss = leerColumnasSpssDelServidor(ubicacion); // Listado columnas Spss
		for (ColumnaVersionPlantilla pc : versionPlantillaActiva.getVersionesColumna()) {
			listadoColumnasBd.add(pc.getPlantillaColumna().getNombre().toUpperCase());
		}
		for (String col : listadocolumnasSpss) {
			if (listadocolumnasSpss.size() != listadoColumnasBd.size()) {
				columnasCoinciden = false;
				
				break;
			} else {
				if (listadoColumnasBd.contains(col.toUpperCase())) {
					columnasCoinciden = true;
					continue;// Continue funciona par for, while, y Do-while
				} else {
					columnasCoinciden = false;
					break;
				}
			}
		}
		LOG.info("RETORNO : " + columnasCoinciden);
		return columnasCoinciden;
	}

	// Leer las columnas del archivo Spss
	@Override
	@Transactional
	public List<String> leerColumnasSpssDelServidor(String ubicacion) {
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna
		SpssDataFileReader reader = null;
		try {
			reader = new SpssDataFileReader(ubicacion);
			for (SpssVariable var : reader.getVariables()) { // Variables = Columnas en SPSS
				listadoColumnas.add(var.getVariableName());// Con esto se recupera los nombres de las columnas
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}

	@Override
	@Transactional
	public List<String> getColumnasFromSpss(InputStream fis) {
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna
		SpssDataFileReader reader = null;
		try {
			reader = new SpssDataFileReader(fis);
			for (SpssVariable var : reader.getVariables()) { // Variables = Columnas en SPSS
				listadoColumnas.add(var.getVariableName().toUpperCase());// Con esto se recupera los nombres de las columnas
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}

	
	
}
