package com.sif.digestyc.Service.Estandarizacion;

import java.util.Map;

public interface TablaConTipoDatoDinamicaService {
	
	public abstract boolean crearTabla(String tabla, Map<String, String> columnas);
	public abstract boolean addCoumnaTabla(String tabla, Map<String, String> columnas);
	
}
