package com.sif.digestyc.Entity.Mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;

public class TablaDinamicaMapper implements RowMapper<TablasDinamicas>{

	private long idTabla;

	public TablaDinamicaMapper(long idTabla) {
		super();
		this.idTabla = idTabla;
	}



	@Override
	public TablasDinamicas mapRow(ResultSet result, int row) throws SQLException {	
		TablasDinamicas tabla = new TablasDinamicas(result.getLong(1), result.getLong(2), result.getLong(3), idTabla);		
		for(int i=4; i<=result.getMetaData().getColumnCount(); i++) {
			String col = result.getMetaData().getColumnName(i);
			tabla.addDataMap(col, result.getString(col) !=null?result.getString(col):"");
		}
		return tabla;
	}
	
}
