package com.sif.digestyc.Service.Especial;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("ficherosServiceImpl")
public class ArchivoServiceImpl implements FicherosService{

	@Override
	public String recuperarExtensionArchivo(MultipartFile file) {		
		String archivo = file.getOriginalFilename();
		String[] partes = archivo.split("\\.");		
		return partes[1].toLowerCase().trim();
	}

}
