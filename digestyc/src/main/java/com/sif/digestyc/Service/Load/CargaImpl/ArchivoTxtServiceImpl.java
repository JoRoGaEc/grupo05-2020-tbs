package com.sif.digestyc.Service.Load.CargaImpl;

import java.io.BufferedReader;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.PlantillaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Service.Load.ArchivoTxtService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("archivoTxtServiceImpl")
public class ArchivoTxtServiceImpl implements ArchivoTxtService{

	private static final Logger LOG = LoggerFactory.getLogger(ArchivoTxtServiceImpl.class);

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
	public ArrayList<Map<String, String>> leerDatosTxtParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro,int formato) {
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		Map<String, String> mapaParaFila = null; // Declarar

		List<String> columnasTxt = leerColumnasTxtDelServidor(ubicacion);// Las columnas del archivo
		int numeroColumnas =  columnasTxt.size();
		//SpssDataFileReader reader = null;
		try {// Leer el archivo especifico txt
			String linea;
		     List<String> lineas;
		     
		     switch(formato) {
		     case 2:
				 BufferedReader reader = new BufferedReader(new FileReader(ubicacion));
			      //System.out.println(reader.readLine());
			       linea=reader.readLine();
			     int i=0;
			      while ((linea=reader.readLine())!= null) 
			      {
			    	  mapaParaFila = new HashMap<String, String>();
		        	mapaParaFila = llenarFilaConVacios(pColumnas, mapaParaFila); //Esta fila es nueva
			    	 // System.out.println("p1");
			    	  String ultimo=linea.substring(linea.length() - 1);
			    	  System.out.println("Ultimo caracter : "+ultimo);
			    	  if(ultimo==",")//SE VALIDA QUE NO VAYA COMA AL FINAL
			    	  {
			    		  linea=linea+"-";
			    	  }
			    	   lineas = Arrays.asList(linea.split(","));
			    	   
			    	   if(lineas.size()<numeroColumnas)// SE VALIDA QUE NO TENGA MENORES VALORES QUE LA COLUMNA
			    	   {
			    		   System.out.println("ENTRA A MENOS VALOR");
				    	   while(lineas.size()<numeroColumnas)
				    	   {
				    		   linea=linea+",-";
				    		   lineas = Arrays.asList(linea.split(","));
				    	   }
			    	   }
			    	   

			    	  
			    	  // System.out.println("p3");
		            	for(int j =0; j<numeroColumnas; j++) {
		            		String dato =lineas.get(j);
		            		//LOG.info("COL :" + columnasTxt.get(j) + "Value : " + dato + "\n");
		            		mapaParaFila.put(columnasTxt.get(j).toUpperCase(), dato);
		            	}
		            	datos.add(mapaParaFila);
			    	 
			    	  
			      }

			      reader.close();
		     break;
		     case 3:
			     
		     break;
		     
		     }

		
		} catch (Exception e) {

		}
		return (ArrayList<Map<String, String>>) datos;
	}

	/* SERIA EL MISMO PARA TODOS. 
	 * Para verificar que las columnas que se tiene en la
	 * plantilla coinciden con las que se van a subir del documento
	 */
	@Override
	@Transactional
	public boolean validarColumnasTxt(String ubicacion, Registro registro, int formato) { // Formato = 2(TXT) , 3(CSV)
		boolean columnasCoinciden = false;
		VersionPlantilla plantillaDb  = versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada(registro.getId());		
		//Plantilla plantillaDb = plantillaRepositoryImpl.buscarPlantillaPorRegistroJpa(registro.getId()); // Recuperar la plantilla que pertenece al registro
		List<String> listadoColumnasBd = new ArrayList<String>(); // Almacenar columnas de BD
		List<String> listadocolumnasSpss=new ArrayList<String>();
		switch(formato)
		{
			case 2:				
				 listadocolumnasSpss = leerColumnasTxtDelServidor(ubicacion); // Listado columnas txt
				 break;
			case 3:
				 listadocolumnasSpss = leerColumnasCsvDelServidor(ubicacion); // Listado columnas csv
				break;
				
		}
		for (ColumnaVersionPlantilla pc : plantillaDb.getVersionesColumna()) {
			listadoColumnasBd.add(pc.getPlantillaColumna().getNombre().toUpperCase());
			
		}
		String col1;
		for (String col : listadocolumnasSpss) {
			if (listadocolumnasSpss.size() != listadoColumnasBd.size()) {
				columnasCoinciden = false;
				
				break;
			} else {
				
				col=col.toLowerCase();
				int i=0;
				col=col.replace(";", "");
				int ascii = (int) col.charAt(0);
				//System.out.println("col ascii:"+ascii);
				
				if(ascii==65279)
				{
					col=col.substring(1);					
				}

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

	// Leer las columnas del archivo Txt
	@Override
	@Transactional
	public List<String> leerColumnasTxtDelServidor(String ubicacion) {
		List<String> lines = Collections.emptyList();
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna
		SpssDataFileReader reader = null;
		try {

			lines = Files.readAllLines(Paths.get(ubicacion), StandardCharsets.UTF_8);
			System.out.println(lines.get(0));
			String campos = lines.get(0);			
			listadoColumnas=Arrays.asList(campos.split(","));
	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}
	
	// Leer las columnas del archivo Csv
	@Override
	@Transactional
	public List<String> leerColumnasCsvDelServidor(String ubicacion) {
		List<String> lines = Collections.emptyList();
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna
		//SpssDataFileReader reader = null;
		/*try (BufferedReader br = new BufferedReader(new FileReader(ubicacion))){

			
			String campos = br.readLine();	
			listadoColumnas=Arrays.asList(campos.split(","));
	
		}*/
		 try (BufferedReader reader = new BufferedReader(new FileReader(ubicacion))){
		        List<String[]> list = new ArrayList<>();
		        String line = "";

		        String campos =  reader.readLine();	
				listadoColumnas=Arrays.asList(campos.split(","));
		    }
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}

	
	private Map<String, String> llenarFilaConVacios(List<PlantillaColumna> pColumnas, Map<String, String> mapaParaFila) {
		for(PlantillaColumna pc : pColumnas) {
			mapaParaFila.put(pc.getNombre().toUpperCase(), "");
		}
		return mapaParaFila;
	}

	
	@Override
	public List<String> getColumnaFromTxt(InputStream is) {
		List<String> lines = Collections.emptyList();
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna

			
		 try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
		        List<String[]> list = new ArrayList<>();
		        String line = "";

		        String campos =  reader.readLine();	
				listadoColumnas=Arrays.asList(campos.split(","));
		    }
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}

	@Override
	public List<String> getColumnaFromCsv(InputStream is) {
		List<String> lines = Collections.emptyList();
		List<String> listadoColumnas = new ArrayList<String>(); // Agregar nombres de la columna
		 try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
		        List<String[]> list = new ArrayList<>();
		        String line = "";

		        String campos =  reader.readLine();	
				listadoColumnas=Arrays.asList(campos.split(","));
		    }
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return listadoColumnas;
	}
}
