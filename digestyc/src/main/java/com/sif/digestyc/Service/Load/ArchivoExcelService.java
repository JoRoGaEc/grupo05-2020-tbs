package com.sif.digestyc.Service.Load;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
public interface ArchivoExcelService {

	public abstract ArrayList<Map<String, String>> leerDatosExcelParaInsercion(List<PlantillaColumna> pColumnas, String ubicacion, 
																			Registro registro, String formato);
	
	public abstract boolean validarColumnasExcel(String ubicacion, Registro registro, String formato);
	
	public List<String> leerColumnasExcelDelServidor(String ubicacion, String formato);
	
}
