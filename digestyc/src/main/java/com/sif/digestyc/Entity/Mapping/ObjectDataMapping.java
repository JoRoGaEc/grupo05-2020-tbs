package com.sif.digestyc.Entity.Mapping;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ObjectDataMapping {
	
	private Map<String, String> mapa;
	private Long registro_id;
	private Long entrega_id;
	
	public ObjectDataMapping(Map<String, String> mapa, Long registro_id, Long entrega_id) {
		super();
		this.mapa = mapa;
		this.registro_id = registro_id;
		this.entrega_id = entrega_id;
	}

	public Map<String, String> getMapa() {
		return mapa;
	}

	public void setMapa(Map<String, String> mapa) {
		this.mapa = mapa;
	}
	
	public Set<String> getKeySet(){
		return mapa.keySet();
	}
	
	public Iterator<String> getIterator(){
		return mapa.keySet().iterator();
	}
	
	public String getValue(String key) {
		String value = mapa.get(key);
		return value;
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
	
	
	

}
