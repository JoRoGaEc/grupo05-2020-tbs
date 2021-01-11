package com.sif.digestyc.Entity.LoadModule;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class EstadoEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 128)
    private String nombre;

    @NotNull
    @Column(length = 2, unique = true)
    private String nombreCorto;

    @NotNull
    @Column(length = 2)
    private String codigo;

    @Column(length = 1024)
    private String descripcion;

    @NotNull
    private Integer ordinal;

    @OneToMany(mappedBy = "estadoEntrega")
    private List<HistorialEstadoEntrega> historialEntregas;

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

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public List<HistorialEstadoEntrega> getHistorialEntregas() {
		return historialEntregas;
	}

	public void setHistorialEntregas(List<HistorialEstadoEntrega> historialEntregas) {
		this.historialEntregas = historialEntregas;
	}

	public EstadoEntrega(Long id, @NotNull String nombre, @NotNull String nombreCorto, @NotNull String codigo,
			String descripcion, @NotNull Integer ordinal, List<HistorialEstadoEntrega> historialEntregas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.ordinal = ordinal;
		this.historialEntregas = historialEntregas;
	}
	public EstadoEntrega() {}
    
}