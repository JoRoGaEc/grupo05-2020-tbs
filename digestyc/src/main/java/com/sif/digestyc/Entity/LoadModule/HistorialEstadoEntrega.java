package com.sif.digestyc.Entity.LoadModule;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class HistorialEstadoEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    private Date fechaRegistro;

    
    @Column(length = 1024)
    private String comentario;


    @Column(columnDefinition = "bit default 0")
    private Boolean actual;

    @ManyToOne
    @JoinColumn(name="estado_entrega_id", nullable = false)
    private EstadoEntrega estadoEntrega;

    @ManyToOne
    @JoinColumn(name="entrega_id", nullable = false)
    private Entrega entrega;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getActual() {
		return actual;
	}

	public void setActual(Boolean actual) {
		this.actual = actual;
	}

	public EstadoEntrega getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(EstadoEntrega estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}


	
	public HistorialEstadoEntrega(Long id, @NotNull Date fechaRegistro, String comentario, Boolean actual,
			EstadoEntrega estadoEntrega, Entrega entrega) {
		super();
		this.id = id;
		this.fechaRegistro = fechaRegistro;
		this.comentario = comentario;
		this.actual = actual;
		this.estadoEntrega = estadoEntrega;
		this.entrega = entrega;
	}

	public HistorialEstadoEntrega(){}
    

}