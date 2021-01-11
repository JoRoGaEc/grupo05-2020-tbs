package com.sif.digestyc.Entity.LoadModule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tipo_dato")
public class TipoDato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="tipo_dato_sql_server")
	private String tipoDatoSqlServer;
	
	@Column(name ="tipo_dato_java")
	private String tipoDatoJava;
	
	@Column(name="has_variacion")
	private Boolean hasVariacion;
	
	@OneToMany(mappedBy = "tipoDato", fetch = FetchType.LAZY)
    @JsonBackReference
	private List<VariacionTipoDato> variacionesTipoDato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDatoSqlServer() {
		return tipoDatoSqlServer;
	}

	public void setTipoDatoSqlServer(String tipoDatoSqlServer) {
		this.tipoDatoSqlServer = tipoDatoSqlServer;
	}

	public String getTipoDatoJava() {
		return tipoDatoJava;
	}

	public void setTipoDatoJava(String tipoDatoJava) {
		this.tipoDatoJava = tipoDatoJava;
	}
	
	public List<VariacionTipoDato> getVariacionesTipoDato() {
		return variacionesTipoDato;
	}

	public void setVariacionesTipoDato(List<VariacionTipoDato> variacionesTipoDato) {
		this.variacionesTipoDato = variacionesTipoDato;
	}

	public Boolean getHasVariacion() {
		return hasVariacion;
	}

	public void setHasVariacion(Boolean hasVariacion) {
		this.hasVariacion = hasVariacion;
	}
	
	public void addVariacionTipo(VariacionTipoDato varTipoDato) {
		this.variacionesTipoDato.add(varTipoDato);
	}
	

}
