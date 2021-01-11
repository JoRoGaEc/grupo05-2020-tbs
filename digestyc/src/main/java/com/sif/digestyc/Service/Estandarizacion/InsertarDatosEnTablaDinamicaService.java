package com.sif.digestyc.Service.Estandarizacion;

import java.util.Map;

import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;

public interface InsertarDatosEnTablaDinamicaService {
	
	public abstract Map<String, String> insertData(TablasDinamicas tablaDinamica);

}
