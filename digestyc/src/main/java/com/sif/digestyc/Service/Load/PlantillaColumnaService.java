package com.sif.digestyc.Service.Load;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;

public interface PlantillaColumnaService {
	
	public abstract PlantillaColumna buscarPorId(int id);
	
	public abstract List<PlantillaColumna> PlanillaColumnasTodas();
	
	public abstract PlantillaColumna actualizar(PlantillaColumna plantillaColumna);
	
	public abstract boolean eliminar(Long id);

	public abstract ByteArrayInputStream paraImporteMasivo(Optional<Registro> registro) throws IOException;

	public abstract ArrayList<PlantillaColumna> PlanillaColumnasPorInstitucion(Long id);
	
	
	public List<PlantillaColumna>  getColumnasDePlantilla(Long id);
	
	
	public abstract PlantillaColumna recuperarColumnaDePlantilla(Long id, String codigo);
	
}
