package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;

public interface VersionPlantillaService {
	
	public abstract Optional<VersionPlantilla> buscarVersionPlantilla(Long id);
	
	public abstract VersionPlantilla recuperarVersionPlantillaHabilitada(Long id);

	public abstract VersionPlantilla actualizar(VersionPlantilla versionPlantillaHabilitada);
	
	public void desabilitarDemasPlantillas(Long id, Long p_id);	
	
	public int desabilitarTodasLasPlantillas(Long idPlantilla);
	
	public Map<String, Object> crearNuevaVersionPlantilla(Long id, List<String> columnas);
	
	public int habilitarVersionPlantilla(Long idVersion, Long idPlantilla);

	public abstract boolean puedeEditar(Long id);
	
	public abstract String Fn_CodVersionPlantilla(Long plantilla);
	
}
