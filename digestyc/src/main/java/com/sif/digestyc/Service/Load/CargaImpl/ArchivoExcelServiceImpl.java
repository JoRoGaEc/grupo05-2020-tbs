package com.sif.digestyc.Service.Load.CargaImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.PlantillaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Service.Especial.FicherosService;
import com.sif.digestyc.Service.Load.ArchivoExcelService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("archivoExcelService")
public class ArchivoExcelServiceImpl implements ArchivoExcelService {

	private static final Logger LOG = LoggerFactory.getLogger(ArchivoExcelServiceImpl.class);

	@Autowired
	@Qualifier("registroRepository")
	private RegistroRepository registroRepositoryImpl;

	@Autowired
	private PlantillaRepository plantillaRepositoryImpl;

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;

	@Autowired
	@Qualifier("ficherosServiceImpl")
	private FicherosService ficherosServices;

	/*
	 * Para verificar que las columnas que se tiene en la plantilla coinciden con
	 * las que se van a subir del documento
	 */
	@Override
	@Transactional
	public boolean validarColumnasExcel(String ubicacion, Registro registro, String formato) {
		boolean columnasCoinciden = false;
		VersionPlantilla versionPlantillaActiva = versionPlantillaServiceImpl
				.recuperarVersionPlantillaHabilitada(registro.getId());
		// Plantilla plantillaDb =
		// plantillaRepositoryImpl.buscarPlantillaPorRegistroJpa(registro.getId());
		List<String> listadoColumnasBd = new ArrayList<String>();

		List<String> listadoColumnasExcel = leerColumnasExcelDelServidor(ubicacion, formato);
		for (ColumnaVersionPlantilla pc : versionPlantillaActiva.getVersionesColumna()) {
			listadoColumnasBd.add(pc.getPlantillaColumna().getCodigo().toUpperCase().trim());
		}
		/*for (String col : listadoColumnasExcel) {
			LOG.info("COL : "+ col);
			if (listadoColumnasExcel.size() != listadoColumnasBd.size()) {
				columnasCoinciden = false;
				break;
			} else {
				if (listadoColumnasBd.contains(col.toUpperCase())) {
					listadoColumnasBd.forEach(c->{
						LOG.info("ColumnasBd: " + c);
					});
					columnasCoinciden = true;
					continue;// Continue funciona par for, while, y Do-while
				} else {
					columnasCoinciden = false;
					break;
				}

			}
			
		}*/
		LOG.info("COICIDEN ?" + arraysMatch(listadoColumnasBd, listadoColumnasExcel));
		
		return arraysMatch(listadoColumnasExcel, listadoColumnasBd);
	}
	
	public boolean arraysMatch(List<String> elements1, List<String> elements2) {
	    if (elements1.size() != elements2.size()) {
	        return false;
	    }
	    List<String> work = new ArrayList(elements2);
	    for (String element : elements1) {
	        if (!work.remove(element)) {
	            return false;
	        }
	    }
	    
	    return work.isEmpty();
	}

	@Override
	@Transactional
	public List<String> leerColumnasExcelDelServidor(String ubicacion, String formato) {
		List<String> listadoColumnas = new ArrayList<String>();
		if (formato.equals("xls")) {
			listadoColumnas = leerColumnasXls(ubicacion, formato);
		} else if (formato.equals("xlsx")) {
			listadoColumnas = leerColumnasXlsx(ubicacion, formato);
		}

		return listadoColumnas;
	}

	private List<String> leerColumnasXls(String ubicacion, String formato) {
		List<String> listadoColumnas = new ArrayList<String>();
		if (formato.equals("xls")) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(new File(ubicacion));
				ZipSecureFile.setMinInflateRatio(-1.0d);
				HSSFWorkbook libro;
				try {
					libro = new HSSFWorkbook(fis);
					HSSFSheet hoja = libro.getSheetAt(0);
					HSSFRow fila1 = hoja.getRow(0);
					String campo = null;
					for (int i = 0; i < fila1.getPhysicalNumberOfCells(); i++) {
						if (fila1.getCell(i) != null) {
							campo = "";
							campo = fila1.getCell(i).getStringCellValue();
							listadoColumnas.add(campo.trim());
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}

		return listadoColumnas;
	}

	public List<String> leerColumnasXlsx(String ubicacion, String formato) {
		List<String> listadoColumnas = new ArrayList<String>();
		if (formato.equals("xlsx")) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(new File(ubicacion));
				ZipSecureFile.setMinInflateRatio(-1.0d);
				XSSFWorkbook libro;
				try {
					libro = new XSSFWorkbook(fis);
					XSSFSheet hoja = libro.getSheetAt(0);
					XSSFRow fila1 = hoja.getRow(0);
					String campo = null;
					for (int i = 0; i < fila1.getPhysicalNumberOfCells(); i++) {
						if (fila1.getCell(i) != null) {
							campo = "";
							campo = fila1.getCell(i).getStringCellValue();
							listadoColumnas.add(campo.trim());
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}

		}

		return listadoColumnas;
	}

	@Override
	public ArrayList<Map<String, String>> leerDatosExcelParaInsercion(List<PlantillaColumna> pColumnas,
			String ubicacion, Registro registro, String formato) {
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		if (formato.equals("xlsx")) {
			datos = leerDatosXlsx(pColumnas, ubicacion, registro, formato);
		} else if (formato.equals("xls")) {
			datos = leerDatosXls(pColumnas, ubicacion, registro, formato);
		}

		return (ArrayList<Map<String, String>>) datos;
	}

	private ArrayList<Map<String, String>> leerDatosXls(List<PlantillaColumna> pColumnas, String ubicacion,
		Registro registro, String formato) {
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		Map<String, String> mapaParaFila = null;
		List<String> columnasExcel = leerColumnasExcelDelServidor(ubicacion, formato);
		int numeroColumnas = columnasExcel.size();
		DataFormatter dataFormatter = new DataFormatter();
		try {
			FileInputStream fis = new FileInputStream(new File(ubicacion));
			ZipSecureFile.setMinInflateRatio(-1.0d);
			HSSFWorkbook libro = new HSSFWorkbook(fis);
			HSSFSheet hoja = libro.getSheetAt(0);
			HSSFRow fila0 = hoja.getRow(0); // La fila 1 para poder obtener los nombres de las columnas
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			for (int i = 1; i < hoja.getPhysicalNumberOfRows(); i++) {
				mapaParaFila = new HashMap<String, String>();
				HSSFRow filaActual = hoja.getRow(i);
				mapaParaFila = llenarFilaConVacios(pColumnas, mapaParaFila); // Esta fila es nueva
				int cuentaNoVacios = 0;				
				for (int j = 0; j < numeroColumnas; j++) {
					if (filaActual!= null && filaActual.getCell(j) != null) {
						CellType tipoCelda = filaActual.getCell(j).getCellType();
						if (tipoCelda == CellType.NUMERIC) {
							if (DateUtil.isCellDateFormatted(filaActual.getCell(j))) {
								Date dato = filaActual.getCell(j).getDateCellValue();
								String datoFechaString = dateFormat.format(dato);
								String campo = fila0.getCell(j).getStringCellValue();
								mapaParaFila.put(campo.trim().toUpperCase(), datoFechaString);
							}else {
								String dato = dataFormatter.formatCellValue(filaActual.getCell(j));
								String campo = fila0.getCell(j).getStringCellValue();// Aqui obtenemos el nombre del campo
								mapaParaFila.put(campo.trim().toUpperCase(), dato);
							}
						} else if (tipoCelda == CellType.STRING) {
							String dato = dataFormatter.formatCellValue(filaActual.getCell(j));
							if(fila0!=null && fila0.getCell(j)!=null && !fila0.getCell(j).getStringCellValue().isEmpty()) {
								String campo = fila0.getCell(j).getStringCellValue();// Aqui obtenemos el nombre del campo
								mapaParaFila.put(campo.trim().toUpperCase(), dato);								
							}

						}

					}

				}
				boolean todosVacios = false;
				for (String key : mapaParaFila.keySet()) {
					String valor = mapaParaFila.get(key);
					if (valor != null && !valor.equals("") && valor.length() > 0) {
						cuentaNoVacios++;
					}
				}
				if (cuentaNoVacios > 0) {
					datos.add(mapaParaFila);
				}

			}

			libro.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return (ArrayList<Map<String, String>>) datos;

	}

	private ArrayList<Map<String, String>> leerDatosXlsx(List<PlantillaColumna> pColumnas, String ubicacion,
			Registro registro, String formato) {
		LOG.info("PCOLUMNAS: "+ pColumnas.size());
		pColumnas.forEach(c->{
			LOG.info("COLn }. "+ c.getCodigo());
		});
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		Map<String, String> mapaParaFila = null;
		//List<String> columnasExcel = leerColumnasExcelDelServidor(ubicacion, formato);
		int numeroColumnas = pColumnas.size();
		DataFormatter dataFormatter = new DataFormatter();
		try {
			File file = new File(ubicacion);
			FileInputStream fis = new FileInputStream(file);
			ZipSecureFile.setMinInflateRatio(-1.0d);
			XSSFWorkbook libro = new XSSFWorkbook(fis);
			XSSFSheet hoja = libro.getSheetAt(0);
			XSSFRow fila0 = hoja.getRow(0); // La fila 1 para poder obtener los nombres de las columnas
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			for (int i = 1; i < hoja.getPhysicalNumberOfRows(); i++) {
				mapaParaFila = new HashMap<String, String>();
				XSSFRow filaActual = hoja.getRow(i);
				mapaParaFila = llenarFilaConVacios(pColumnas, mapaParaFila); // Esta fila es nueva
				int cuentaNoVacios = 0;				
				for (int j = 0; j < numeroColumnas; j++) {
					if (filaActual!= null && filaActual.getCell(j) != null) {
						CellType tipoCelda = filaActual.getCell(j).getCellType();
						if (tipoCelda == CellType.NUMERIC) {
							if (DateUtil.isCellDateFormatted(filaActual.getCell(j))) {
								Date dato = filaActual.getCell(j).getDateCellValue();
								String datoFechaString = dateFormat.format(dato);
								String campo = fila0.getCell(j).getStringCellValue();
								mapaParaFila.put(campo.trim().toUpperCase(), datoFechaString);
							}else {
								String dato = dataFormatter.formatCellValue(filaActual.getCell(j));
								String campo = fila0.getCell(j).getStringCellValue();// Aqui obtenemos el nombre del campo
								mapaParaFila.put(campo.trim().toUpperCase(), dato);
							}
						} else if (tipoCelda == CellType.STRING) {
							String dato = dataFormatter.formatCellValue(filaActual.getCell(j));
							if(fila0!=null && fila0.getCell(j)!=null && !fila0.getCell(j).getStringCellValue().isEmpty()) {
								String campo = fila0.getCell(j).getStringCellValue();// Aqui obtenemos el nombre del campo
								mapaParaFila.put(campo.trim().toUpperCase(), dato);								
							}

						}

					}

				}
				boolean todosVacios = false;
				for (String key : mapaParaFila.keySet()) {
					String valor = mapaParaFila.get(key);
					if (valor != null && !valor.equals("") && valor.length() > 0) {
						cuentaNoVacios++;
					}
				}
				if (cuentaNoVacios > 0) {
					datos.add(mapaParaFila);
				}

			}

			libro.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);

		}

		return (ArrayList<Map<String, String>>) datos;
	}

	private Map<String, String> llenarFilaConVacios(List<PlantillaColumna> pColumnas,
			Map<String, String> mapaParaFila) {
		for (PlantillaColumna pc : pColumnas) {
			mapaParaFila.put(pc.getCodigo(), "");
		}
		return mapaParaFila;
	}

}
