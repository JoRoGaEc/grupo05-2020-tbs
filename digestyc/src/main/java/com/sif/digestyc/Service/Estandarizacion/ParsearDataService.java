package com.sif.digestyc.Service.Estandarizacion;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;

public interface ParsearDataService {
	
	public abstract String getDato(String codigo, ColumnaVersionPlantilla columnaVersion);
	
}
