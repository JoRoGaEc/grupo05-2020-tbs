package com.sif.digestyc.Service.Validation.ValidationImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Repository.Validacion.DatoTablaRepository;
import com.sif.digestyc.Service.Validation.DatoTablaService;

@Service("datoTablaServiceImpl")
public class DatoTablaServiceImpl implements DatoTablaService{
	
	@Autowired
	DatoTablaRepository datoTabla;

	@Override
	public List<TablasDinamicas> getDataOf(String table, long idTable) {
		return datoTabla.getDataOf(table, idTable);
	}

	@Override
	public List<TablasDinamicas> getDataOf(String table, int min, int max, long idTable) {
		return datoTabla.getDataOf(table, min, max, idTable);
	}

	@Override
	public List<TablasDinamicas> getDataMapOf(String table, long idTable,long idregistro, long identrega) {
		return datoTabla.getDataMapOf(table, idTable, idregistro, identrega);
	}

	@Override
	public int updateData(String table, String columna, String value, Long fila) {
		return datoTabla.updateData(table, columna, value, fila);
	}

	@Override
	public List<TablasDinamicas> getDataMapOf(String nombreTablaCorrespondiente, Long id) {
		return datoTabla.getDataMapOf(nombreTablaCorrespondiente, id);
	}

	@Override
	public void deleteFromTabla(String tabla, Long id) {
		datoTabla.deleteFromTabla(tabla, id);
	}
	
	
	

}
