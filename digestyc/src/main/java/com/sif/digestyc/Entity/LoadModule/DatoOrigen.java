package com.sif.digestyc.Entity.LoadModule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "dato_origen")
public class DatoOrigen {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="fila_origen_id", nullable = false)
    private FilaOrigen filaOrigen;

    @ManyToOne
    @JoinColumn(name="columna_origen_id", nullable = false)
    private ColumnaOrigen columnaOrigen;

   	@NotNull
	@Column(length = 1024)
	private String dato;
	
	
	public Long getIdFilaOrigen() {
		return this.filaOrigen != null?filaOrigen.getId():null;
	}
	
	public Long getIdColumnaOrigen() {
		return this.columnaOrigen != null?columnaOrigen.getId():null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FilaOrigen getFilaOrigen() {
		return filaOrigen;
	}

	public void setFilaOrigen(FilaOrigen filaOrigen) {
		this.filaOrigen = filaOrigen;
	}

	public ColumnaOrigen getColumnaOrigen() {
		return columnaOrigen;
	}

	public void setColumnaOrigen(ColumnaOrigen columnaOrigen) {
		this.columnaOrigen = columnaOrigen;
	}

	public String getDato() {
		return dato.trim();
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public DatoOrigen() {
		super();
	}

	public DatoOrigen(Long id, FilaOrigen filaOrigen, ColumnaOrigen columnaOrigen, List<Error> errores,
			@NotNull String dato) {
		super();
		this.id = id;
		this.filaOrigen = filaOrigen;
		this.columnaOrigen = columnaOrigen;
		this.dato = dato;
	}
	
	
	
	
	
}
