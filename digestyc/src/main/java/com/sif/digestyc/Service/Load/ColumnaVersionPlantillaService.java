package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;

public interface ColumnaVersionPlantillaService {

	public abstract Optional<ColumnaVersionPlantilla> buscarVersionColumna(Long id);
	
	public abstract void actualizar( ColumnaVersionPlantilla colVerPlantilla);
	
	public abstract void eliminarColumnaVersion(Long id);
	
	public abstract List<ColumnaVersionPlantilla> buscarColumnasRegistro(Long id);

	public abstract List<ColumnaVersionPlantilla> buscarTodas();
	
}
