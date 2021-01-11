package com.sif.digestyc.ServiceTest;

import com.sif.digestyc.Service.Load.ManageArchivoService;
import com.sif.digestyc.Service.Load.LoadImpl.*;

public class DBFtest {

	public static void main(String[] args) {
		
		ManageArchivoService manageDbf = new ManageDBFServiceImpl();
		manageDbf.leerArchivo("ruta");

	}

}
