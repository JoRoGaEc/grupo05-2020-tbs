package com.sif.digestyc.Service.Load.CatalogosImpl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Repository.Catalogos.DirectorioRepository;
import com.sif.digestyc.Service.Load.DirectorioService;

@Service("directorioServiceImpl")
public class DirectorioServiceImpl implements DirectorioService{
	
	@Autowired
	private DirectorioRepository directorioRepository;

	@Override
	public List<Directorio> obtenerDirectorios() {
		return directorioRepository.findAll();
	}

	@Override
	public Optional<Directorio> obtenerDirectorioActivo(boolean activo) {
		return directorioRepository.obtenerDirectoryActivo(activo);
	}
	
	/*
	 * Funcion actualizar, desactiva todos los directorios pues solo debe tener uno activo, luego verifica que la direccion exista 
	 * y si no existe crea la direccion y las subcarpetas
	 */
	@Override
	public Directorio actualizar(Directorio directorio, boolean root) {
		Directorio nuevoDirectorio = null;
		File direccion = null;
		if(!root && (directorio.getUbicacion().contains("\\") || directorio.getUbicacion().contains("/"))) {
			direccion = new File(directorio.getUbicacion());
		}else {
			if(!(directorio.getUbicacion().contains("\\") || directorio.getUbicacion().contains("/"))) {
				directorio.setUbicacion(directorio.getUbicacion().trim().toLowerCase());
				String nombre_sio = System.getProperty("os.name");
				System.out.println("es: "+nombre_sio);
				if (nombre_sio.toUpperCase().contains("WINDOWS")) {
					direccion = new File("C:\\"+directorio.getUbicacion());
				}else {
					direccion = new File("/home/"+directorio.getUbicacion());
				}
				directorio.setUbicacion(direccion.getAbsolutePath());
			}
		}
		
		if(direccion!=null && !direccion.exists()) {
			directorioRepository.desactivarDirectorios();
			direccion.mkdirs();
			nuevoDirectorio = directorioRepository.save(directorio);
		}
		return nuevoDirectorio;
	}

	@Override
	public void desactivarDirectorios() {
		directorioRepository.desactivarDirectorios();
	}

	@Override
	public void eliminar(Directorio directorio) {
		directorioRepository.delete(directorio);
	}

	@Override   
	public boolean crearDirectorio(String ubicacion) {
		Optional<Directorio> dir = obtenerDirectorioActivo(true);
		if(dir.isPresent()) {
			String direccion = dir.get().getUbicacion();
			direccion += "\\"+ubicacion;
			File file = new File(direccion);
			if(file!=null && !file.exists()) {
				return file.mkdirs();
			}
		}
		return false;
	}
	
	
	

}
