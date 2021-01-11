package com.sif.digestyc.Entity.LoadModule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
//@EntityListeners(BitacoraListener.class)
@Table(name = "historial_plantilla_registro")

public class HistorialPlantillaRegistro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long registro_id;
	
	@NotNull
	private Long entrega_id;

	@NotNull
	private Long plantilla_id;

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

	public Long getPlantilla_id() {
		return plantilla_id;
	}

	public void setPlantilla_id(Long plantilla_id) {
		this.plantilla_id = plantilla_id;
	} 

	public HistorialPlantillaRegistro(Long id, @NotNull Long registro_id, @NotNull Long entrega_id, @NotNull Long plantilla_id) {
		super();
		this.id = id;
		this.registro_id = registro_id;
		this.entrega_id = entrega_id;
		this.plantilla_id = plantilla_id;
	}

	public Long getEntrega_id() {
		return entrega_id;
	}

	public void setEntrega_id(Long entrega_id) {
		this.entrega_id = entrega_id;
	}

	public HistorialPlantillaRegistro() {
		super();
	}
}
