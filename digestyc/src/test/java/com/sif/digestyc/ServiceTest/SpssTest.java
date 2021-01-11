package com.sif.digestyc.ServiceTest;

import org.springframework.stereotype.Service;

import com.sif.digestyc.Service.Load.ManageArchivoService;
import com.sif.digestyc.Service.Load.LoadImpl.*;


public class SpssTest {
   public static void main(String args[]) {
	   ManageArchivoService spssService  =  new ManageSpssServiceImpl();
	   
	    spssService.leerArchivo("ruta");
   }
}
