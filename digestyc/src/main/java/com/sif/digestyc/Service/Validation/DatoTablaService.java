package com.sif.digestyc.Service.Validation;

import java.util.List;

import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;

public interface DatoTablaService {

	abstract public List<TablasDinamicas> getDataOf(String table, long idTable);
	abstract public List<TablasDinamicas> getDataOf(String table, int min, int max, long idTable);
	abstract public List<TablasDinamicas> getDataMapOf(String table,long idTable, long idregistro, long identrega);
	abstract public int updateData(String table, String columna, String value, Long fila);
	abstract public List<TablasDinamicas> getDataMapOf(String nombreTablaCorrespondiente, Long id);
	abstract public void deleteFromTabla(String tabla, Long id);
	

}
