package com.sif.digestyc.Service.Load;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;

public interface ArchivoTxtService {
	
	public abstract ArrayList<Map<String, String>> leerDatosTxtParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro,int formato);
	
	public abstract boolean validarColumnasTxt(String ubicacion, Registro registro,int formato);
	
	public List<String> leerColumnasTxtDelServidor(String ubicacion);
	
	public List<String> leerColumnasCsvDelServidor(String ubicacion);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getColumnaFromTxt(InputStream is);
	public List<String> getColumnaFromCsv(InputStream is);
	
}
