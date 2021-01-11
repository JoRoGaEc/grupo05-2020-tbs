package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Repository.Carga.TablaRepository;
import com.sif.digestyc.Service.Load.TablaService;

@Service("tablaServiceImpl")
public class TablaServiceImpl implements TablaService{
	
	@Autowired
	private TablaRepository tablaRepository;

	@Override
	@Transactional
	public Tabla actualizar(Tabla tabla) {
		Tabla tablaActualizada = tablaRepository.save(tabla);
	   return tablaActualizada;
	}

	@Override
	@Transactional
	public List<Tabla> getTablas() {
		return tablaRepository.findAll();
	}

	@Override
	@Transactional
	public void eliminar(Tabla tabla) {
		tablaRepository.delete(tabla);
	}

	@Override
	@Transactional
	public Optional<Tabla> buscar(Long id) {
		return tablaRepository.findById(id);
	}

	@Override
	public List<String> obtenerColumnas(String tabla) {
		return tablaRepository.getColumn(tabla);
	}

	@Override
	public Optional<Tabla> buscar(String nombre, long archivo_id, long id) {
		return tablaRepository.findByName(nombre,archivo_id, id);
	}

	@Override
	public List<Tabla> getTablasValidas() {
		return tablaRepository.findTablaValida();
	}

	@Override
	public boolean existeTabla(String table) {
		return tablaRepository.existsTable(table).isEmpty();
	}

	@Override
	@Transactional(readOnly =false)
	public void colocarArchivoId(Long idTabla, Long idArchivo) {
		tablaRepository.colocarArchivoId(idTabla, idArchivo);
		
	}

	@Override
	public List<ArrayList<String>> getDescripcionTabla(String tabla) {
		return tablaRepository.getDescriptionTable(tabla);
	}
	
	@Override
	public String getTablaDestino(long idTabla) {
		return tablaRepository.getTableToInsert(idTabla);
	}

	@Override
	public boolean tieneTipificacion(Long id) {
		long tipificaciones = tablaRepository.haveTipificacion(id);
		long columna_version = tablaRepository.haveColumnasVersion(id);
		return tipificaciones>0 && tipificaciones==columna_version;
	}

	

}
