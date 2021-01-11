package com.sif.digestyc.Service.Especial;

import org.springframework.web.multipart.MultipartFile;

public interface FicherosService {
	
	public String recuperarExtensionArchivo(MultipartFile file);

}
