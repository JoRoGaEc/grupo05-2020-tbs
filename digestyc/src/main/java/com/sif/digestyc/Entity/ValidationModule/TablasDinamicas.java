package com.sif.digestyc.Entity.ValidationModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;



@Entity
@Data
public class TablasDinamicas {
	
	@Id
	private Long id;
	
	@Column
	private Long registro_id;
	
	@Column
	private Long entrega_id;
	
	@Column
	private long tablaId;
	
	
	@ElementCollection
	private Map<String, String> dataMap;
			
	public TablasDinamicas(Long id, Long registro_id, Long entrega_id, long tablaId) {
		super();
		this.id = id;
		this.registro_id = registro_id;
		this.entrega_id = entrega_id;
		this.dataMap = new HashMap<>();
		this.tablaId = tablaId;
	}




	public Map<String, String> getDataMap() {
		return dataMap;
	}


	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	
	public void addDataMap(String key, String value) {
		this.dataMap.put(key, value);
	}
	
	public Set<String> getKeySet(){
		return dataMap.keySet();
	}
	
	public Iterator<String> getIterator(){
		return dataMap.keySet().iterator();
	}
	
	public String getValue(String key) {
		return dataMap.get(key);

	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegistro_id() {
		return registro_id;
	}
	public void setRegistro_id(Long registro_id) {
		this.registro_id = registro_id;
	}
	public Long getEntrega_id() {
		return entrega_id;
	}
	public void setEntrega_id(Long entrega_id) {
		this.entrega_id = entrega_id;
	}

	public long getTablaId() {
		return tablaId;
	}

}
