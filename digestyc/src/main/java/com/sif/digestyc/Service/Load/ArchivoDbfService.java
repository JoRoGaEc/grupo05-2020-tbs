package com.sif.digestyc.Service.Load;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
public interface ArchivoDbfService {

	public abstract ArrayList<Map<String, String>> leerDatosDbfParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro);
	
	public abstract boolean validarColumnasDbf(String ubicacion, Registro registro);
	
	public List<String> leerColumnasDbfDelServidor(String ubicacion);
	
	public List<String> getColumnasFromDbf(InputStream is);
	
}
