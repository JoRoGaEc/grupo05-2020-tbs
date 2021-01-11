package com.sif.digestyc.ServiceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sif.digestyc.Service.Load.ManageArchivoService;
import com.sif.digestyc.Service.Load.LoadImpl.*;;

public class ExcelTest {

	public static void main(String[] args) {
	
		ManageArchivoService excelService = new ManageExcelServiceImpl();
		excelService.leerArchivo("ruta");

	}

}
