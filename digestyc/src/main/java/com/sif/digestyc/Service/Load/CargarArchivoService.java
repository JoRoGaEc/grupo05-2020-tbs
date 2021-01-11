package com.sif.digestyc.Service.Load;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;

public interface CargarArchivoService {

	public abstract boolean existeColumna(List<PlantillaColumna> plantillaColumnas, String columna);
	public abstract boolean insertarConBulk(Tabla tabla, ArrayList<Map<String, String>> datos) throws SQLException;
	public abstract void actualizarColumnas(List<String> columnas, String tabla);
	
}
