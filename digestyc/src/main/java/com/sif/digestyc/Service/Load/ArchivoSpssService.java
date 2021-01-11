package com.sif.digestyc.Service.Load;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
public interface ArchivoSpssService {

	public abstract ArrayList<Map<String, String>> leerDatosSpssParaInsercion(List<PlantillaColumna> pColumnas,String ubicacion, Registro registro);
	
	public abstract boolean validarColumnasSpss(String ubicacion, Registro registro);
	
	public List<String> leerColumnasSpssDelServidor(String ubicacion);
	
	public List<String> getColumnasFromSpss(InputStream fis);
	
}
