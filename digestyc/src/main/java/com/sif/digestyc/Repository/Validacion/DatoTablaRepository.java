package com.sif.digestyc.Repository.Validacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Mapping.TablaDinamicaMapper;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;

@Repository
public class DatoTablaRepository {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<TablasDinamicas> getDataOf(String table, long idTable){
		return jdbcTemplate.query("select * from "+table, new TablaDinamicaMapper(idTable));
	}
	
	
	public List<TablasDinamicas> getDataOf(String table, int min, int max, long idTable){
		return jdbcTemplate.query("SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as row FROM  "+table+") a WHERE a.row > "+min+" and a.row <= "+max, new TablaDinamicaMapper(idTable));
	}
	
	public List<TablasDinamicas> getDataMapOf(String table, long idTable,long idregistro, long identrega){
		return jdbcTemplate.query("select * from "+table+ " where registro_id = "+idregistro+" and entrega_id = "+identrega, new TablaDinamicaMapper(idTable));
	}


	public int updateData(String table, String columna, String value, Long fila) {
		return jdbcTemplate.update("update "+table+" set "+ columna + " = "+ value + " where id = "+fila);
	}
	
	public Map<String, String> insertData(String data) {
		Map<String, String> map = new HashMap<>();
		try {
			long dataInserted = jdbcTemplate.update(data);
			map.put("exito", ""+dataInserted);
		}catch (Exception e) {
			map.put("exito", "-1");
			map.put("error", e.getCause().getMessage());
		}
		return map;
	}


	public List<TablasDinamicas> getDataMapOf(String nombreTablaCorrespondiente, Long id) {
		return jdbcTemplate.query("select * from "+nombreTablaCorrespondiente+ " where tabla_id = "+id, new TablaDinamicaMapper(id));
	}


	public void deleteFromTabla(String tabla, Long id) {
		try {
			jdbcTemplate.update("delete from "+tabla+ " where id = "+id);
		}catch (Exception e) {}
	}
	
	

}
