package com.sif.digestyc.Entity.LoadModule;


import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@NamedEntityGraph(
    name = "detail-periodo",
    attributeNodes = {
        @NamedAttributeNode("periodicidad"),
        
    }
)
public class Periodo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(length = 128)
    private String nombre;


    @NotNull
    private Date fechaInicio;


    @NotNull
    private Date fechaFin;

    
    @NotNull
    private Integer correlativo;

    @ManyToOne
    @JoinColumn(name="periodicidad_id", nullable = false)
    private Periodicidad periodicidad;
    
    @OneToMany(mappedBy = "periodo")
    private List<Entrega> entregas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	public Periodicidad getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(Periodicidad periodicidad) {
		this.periodicidad = periodicidad;
	}

	public List<Entrega> getEntregas() {
		return entregas;
	}

	public void setEntregas(List<Entrega> entregas) {
		this.entregas = entregas;
	}

	public Periodo(Long id, @NotEmpty @NotNull String nombre, @NotNull Date fechaInicio, @NotNull Date fechaFin,
			@NotNull Integer correlativo, Periodicidad periodicidad, List<Entrega> entregas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.correlativo = correlativo;
		this.periodicidad = periodicidad;
		this.entregas = entregas;
	}
    
	public Periodo() {}
    
}
