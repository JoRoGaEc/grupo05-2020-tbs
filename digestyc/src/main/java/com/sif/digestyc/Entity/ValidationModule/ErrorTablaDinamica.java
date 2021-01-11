package com.sif.digestyc.Entity.ValidationModule;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;

@Entity
public class ErrorTablaDinamica {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name="tabla_id", nullable = true)
    private Tabla tabla;
    
    @ManyToOne
    @JoinColumn(name="tabla_correspondencia_id", nullable = true)
    private TablaCorrespondencia tablaCorrespondencia;
	
	private Long fila;
	
	private String columna;
	
	private String tipoDato;
	
	@ElementCollection
	private Map<String, String> data;

	public ErrorTablaDinamica(Tabla tabla, Long fila, String columna, Map<String, String> data, String tipoDato) {
		super();
		this.tabla = tabla;
		this.fila = fila;
		this.columna = columna;
		this.data = data;
		this.tipoDato = tipoDato;
	}
	
	public ErrorTablaDinamica(Long fila, String columna, Map<String, String> data, String tipoDato, TablaCorrespondencia tablaCorrespondencia) {
		super();
		this.fila = fila;
		this.columna = columna;
		this.data = data;
		this.tipoDato = tipoDato;
		this.tablaCorrespondencia = tablaCorrespondencia;
	}


	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}


	public ErrorTablaDinamica() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tabla getTabla() {
		return tabla;
	}

	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}

	public Long getFila() {
		return fila;
	}

	public void setFila(Long fila) {
		this.fila = fila;
	}


	public String getTipoDato() {
		return tipoDato;
	}


	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}


	public TablaCorrespondencia getTablaCorrespondencia() {
		return tablaCorrespondencia;
	}


	public void setTablaCorrespondencia(TablaCorrespondencia tablaCorrespondencia) {
		this.tablaCorrespondencia = tablaCorrespondencia;
	}
	
	
	
	
	
}
