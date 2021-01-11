package com.sif.digestyc.Service.Load;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Entity.LoadModule.Plantilla;

public interface PlantillaService {

	public abstract List<String> leerCampos(MultipartFile archivoExcel);

	public abstract Map<String, String> leerImporteConCabeceras(MultipartFile archivoExcel) throws IOException;

	public abstract Optional<Plantilla> buscarPorId(Long id);

	public abstract Optional<Plantilla> actualizar(Plantilla plan);

	
}
