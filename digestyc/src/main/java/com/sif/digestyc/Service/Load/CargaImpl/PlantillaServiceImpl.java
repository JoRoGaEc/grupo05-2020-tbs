package com.sif.digestyc.Service.Load.CargaImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Repository.Carga.PlantillaRepository;
import com.sif.digestyc.Service.Especial.FicherosService;
import com.sif.digestyc.Service.Load.ArchivoDbfService;
import com.sif.digestyc.Service.Load.ArchivoSpssService;
import com.sif.digestyc.Service.Load.PlantillaService;

@Service("plantillaService")
public class PlantillaServiceImpl implements PlantillaService{
	
	private static final Logger LOG=  LoggerFactory.getLogger(PlantillaServiceImpl.class);
	
	@Autowired
	private PlantillaRepository plantillaRepository;

	@Autowired
	@Qualifier("archivoSpssServiceImpl")
	private ArchivoSpssService archivoSpssServiceImpl;	
	
	@Autowired
	@Qualifier("archivoDbfServiceImpl")
	private ArchivoDbfService archivoDbfServiceImpl;
	
	@Autowired
	@Qualifier("archivoTxtServiceImpl")
	private ArchivoTxtServiceImpl archivoTxtServiceImpl;

	@Autowired
	@Qualifier("ficherosServiceImpl")
	private FicherosService ficherosServices;
	
	@Override
	public Optional<Plantilla> buscarPorId(Long id){
		return plantillaRepository.findById(id);
	}
	

	@Override
	@Transactional
	public List<String> leerCampos(MultipartFile archivo) {
		List<String> listaCamposTemporal  =  new ArrayList<String>();
		
		String extension = archivo.getOriginalFilename().split("\\.")[1].toUpperCase();
		LOG.info("Extension : "+ extension);

			switch(extension) {
			case "XLS":
			    listaCamposTemporal = 	leerColumnasExcel(archivo);
				break;
			case "XLSX":
				 listaCamposTemporal = 	leerColumnasExcel(archivo);
				break;
			case "SAV":
				try {
					listaCamposTemporal =  archivoSpssServiceImpl.getColumnasFromSpss(archivo.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "CSV":
				try {
					listaCamposTemporal =  archivoTxtServiceImpl.getColumnaFromCsv(archivo.getInputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				break;
			case "TXT":
				try {
					listaCamposTemporal =  archivoTxtServiceImpl.getColumnaFromTxt(archivo.getInputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "DBF":
				 try {
					listaCamposTemporal = archivoDbfServiceImpl.getColumnasFromDbf(archivo.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;
					
			}


		
		return listaCamposTemporal;
	}
	
	public List<String> leerColumnasExcel(MultipartFile archivoExcel) {
		
		String extension =  ficherosServices.recuperarExtensionArchivo(archivoExcel);
		List<String> listadoColumnas = new ArrayList<String>();
		if(extension.equals("xls")) {
			listadoColumnas = leerColumnasXls(archivoExcel, extension);
		}else if(extension.equals("xlsx")) {
			listadoColumnas = leerColumnasXlsx(archivoExcel, extension);
		}
		return listadoColumnas;

	}
	
	private List<String> leerColumnasXlsx(MultipartFile archivoExcel, String formato) {
		LOG.info("ERA: XLSX");
		List<String> listadoColumnas = new ArrayList<String>();
		if(formato.equals("xlsx")) {
			FileInputStream fis;
			try {
				fis = (FileInputStream) archivoExcel.getInputStream();
				ZipSecureFile.setMinInflateRatio(-1.0d);
				XSSFWorkbook  libro;
				try {
					libro = new XSSFWorkbook(fis); 
					XSSFSheet hoja = libro.getSheetAt(0); 
					XSSFRow fila1 =  hoja.getRow(0);
					String campo  = null;
					for(int i = 0; i<fila1.getPhysicalNumberOfCells(); i++) {
						if(fila1.getCell(i)!=null) {
							campo = "";
							campo = fila1.getCell(i).getStringCellValue();
							listadoColumnas.add(campo.trim());					
						}
		 
					}
				} catch (IOException e) {
					e.printStackTrace();
				}  

			} catch (IOException e) {

				e.printStackTrace();
			} 

		}

		return listadoColumnas;
	}


	private List<String> leerColumnasXls(MultipartFile archivoExcel, String formato) {
		LOG.info("ERA: XLS");
		List<String> listadoColumnas = new ArrayList<String>();
		if(formato.equals("xls")) {
			FileInputStream fis;
			try {

				fis = (FileInputStream) archivoExcel.getInputStream();
				ZipSecureFile.setMinInflateRatio(-1.0d);
				HSSFWorkbook libro;
				try {
					libro = new HSSFWorkbook(fis);
					HSSFSheet hoja=libro.getSheetAt(0);
					HSSFRow fila1 =  hoja.getRow(0);
					String campo  = null;
					for(int i = 0; i<fila1.getPhysicalNumberOfCells(); i++) {
						if(fila1.getCell(i)!=null) {
							campo = "";
							campo = fila1.getCell(i).getStringCellValue();
							listadoColumnas.add(campo.trim());					
						}
		 
					}
				} catch (IOException  e) {
					e.printStackTrace();
				}  

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return listadoColumnas;	
	}


	@SuppressWarnings("resource")
	@Override
	@Transactional
	public Map<String, String> leerImporteConCabeceras(MultipartFile archivoExcel) throws IOException {
		Map<String, String> valores = new HashMap<String, String>(); //Esto nos sirve un monton, por que sabemos que el nombre de la columna es unico, por lo que es una excelente Key y a la vez funcional
		XSSFWorkbook libro =  new XSSFWorkbook(archivoExcel.getInputStream());
		XSSFSheet  hoja  =   libro.getSheetAt(0);
		for(int i=1 ;  i<hoja.getPhysicalNumberOfRows(); i++) { 
			XSSFRow fila = hoja.getRow(i);
			String columna = fila.getCell(0) != null? fila.getCell(0).getStringCellValue():null;
			String tipificacion = fila.getCell(1) != null? fila.getCell(1).getStringCellValue():null;
			if(tipificacion != null && columna != null && !columna.isEmpty() && !tipificacion.isEmpty()) {
				valores.put(columna, tipificacion);
			}
		}
		return valores;
	}


	@Override
	@Transactional
	public Optional<Plantilla> actualizar(Plantilla plan) {
		return Optional.of(plantillaRepository.save(plan));
	}
		

}
