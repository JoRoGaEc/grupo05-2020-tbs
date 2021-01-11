package com.sif.digestyc.Entity.LoadModule;

import java.util.ArrayList;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "columna_origen")
public class ColumnaOrigen {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="tabla_id", nullable = false)
    private Tabla tabla;
    
    
    @OneToMany(mappedBy = "columnaOrigen", fetch = FetchType.LAZY)
    private List<DatoOrigen> datosOrigen;
    
    @NotEmpty
	@NotNull
	@Column(length = 20)
	private String codigo;
	
	@NotNull
	@Column(name = "orden")
	private Integer orden;
	
    @NotEmpty
	@NotNull
	@Column(length = 60)
	private String nombre;

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

	public List<DatoOrigen> getDatosOrigen() {
		return datosOrigen;
	}

	public void setDatosOrigen(List<DatoOrigen> datosOrigen) {
		this.datosOrigen = datosOrigen;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ColumnaOrigen(Long id, Tabla tabla, List<DatoOrigen> datosOrigen, @NotEmpty @NotNull String codigo,
			@NotNull Integer orden, @NotEmpty @NotNull String nombre) {
		super();
		this.id = id;
		this.tabla = tabla;
		this.datosOrigen = datosOrigen;
		this.codigo = codigo;
		this.orden = orden;
		this.nombre = nombre;
	}

	public ColumnaOrigen() {
		super();
		this.datosOrigen = new ArrayList<DatoOrigen>();
	}

	public ColumnaOrigen(Tabla tabla, @NotEmpty @NotNull String codigo, @NotNull Integer orden,
			@NotEmpty @NotNull String nombre) {
		super();
		this.tabla = tabla;
		this.codigo = codigo;
		this.orden = orden;
		this.nombre = nombre;
	}

	public void setDatoOrigen(DatoOrigen dato) {
		this.datosOrigen.add(dato);	
	}
    
    
    
	
}
