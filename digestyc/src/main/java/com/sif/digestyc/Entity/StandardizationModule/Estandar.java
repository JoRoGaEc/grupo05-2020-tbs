package com.sif.digestyc.Entity.StandardizationModule;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name="estandar")
public class Estandar implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre no debe estar vacío")
    @NotNull(message = "El nombre es obligatorio")
    @Length(min=2, max=100, message = "El Nombre debe tener entre 2 y 100 caracteres")
	@Column(name="nombre", length = 100)
	private String nombre;
		
	@Column(name="tipoDato")
	private String tipoDato;
	
	@Column(name="grupo_datos")
	private String grupoDatos;
	
	@Column(name="longitud")	
	private Integer longitudN;
	
	@NotEmpty(message = "La descripción no debe estar vacía")
    @NotNull(message = "La descripción es obligatorio")
    @Length(min=5, max=500, message = "La descripción debe tener entre 5 y 500 caracteres")
 	@Column(name="descripcion",length = 500)
	private String descripcion;
	
	@Column(name="permite_vacio")
	private Boolean esVacio;
	
	private Integer precision;
	
	private Integer escala; 
		
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estandar", cascade =  CascadeType.ALL)
	private List<ValorTipicoEstandar> valoresTipicosEstandar;
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estandar", cascade =  CascadeType.ALL)
	private List<ColumnaCorrespondencia> columnaCorrespondencia;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public List<ValorTipicoEstandar> getValoresTipicosEstandar() {
		return valoresTipicosEstandar;
	}


	public void setValoresTipicosEstandar(List<ValorTipicoEstandar> valoresTipicosEstandar) {
		this.valoresTipicosEstandar = valoresTipicosEstandar;
	}


	public List<ColumnaCorrespondencia> getColumnaCorrespondencia() {
		return columnaCorrespondencia;
	}


	public void setColumnaCorrespondencia(List<ColumnaCorrespondencia> columnaCorrespondencia) {
		this.columnaCorrespondencia = columnaCorrespondencia;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipoDato() {
		return tipoDato;
	}


	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}



	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getEsVacio() {
		return esVacio==null?false:esVacio;
	}


	public void setEsVacio(Boolean esVacio) {
		this.esVacio = esVacio;
	}


	public Integer getLongitudN() {
		return longitudN;
	}


	public void setLongitudN(Integer longitudN) {
		this.longitudN = longitudN;
	}


	public Integer getPrecision() {
		return precision;
	}


	public void setPrecision(Integer precision) {
		this.precision = precision;
	}


	public Integer getEscala() {
		return escala;
	}


	public void setEscala(Integer escala) {
		this.escala = escala;
	}


	public String getGrupoDatos() {
		return grupoDatos;
	}


	public void setGrupoDatos(String grupoDatos) {
		this.grupoDatos = grupoDatos;
	}



}
