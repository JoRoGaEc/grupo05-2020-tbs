package com.sif.digestyc.Entity.LoadModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name = "archivo")
public class Archivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @NotNull
    @Column(length = 50)
    private String codigo;
    
    @NotEmpty
    @NotNull
    @Column(length = 100)
    private String nombre;

    @NotNull
    private Date fechaSubido;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrega_id", referencedColumnName = "id")
    private Entrega entrega;
    
    @Column(name="datos_cargados")
    private Boolean datosCargados;
    
    @ManyToOne
    @JoinColumn(name="formato_id", nullable = false)
    private Formato formato;
    
    @OneToMany(mappedBy = "archivo", fetch = FetchType.LAZY)
    private List<Tabla> tablas =  new ArrayList<Tabla>();
    
    @Column(name = "ubicacion_archivo")
    private String ubicacionArchivo;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFechaSubido() {
		return fechaSubido;
	}

	public void setFechaSubido(Date fechaSubido) {
		this.fechaSubido = fechaSubido;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public List<Tabla> getTablas() {
		return tablas;
	}

	public void setTablas(List<Tabla> tablas) {
		this.tablas = tablas;
	}


    
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarTabla(Tabla tabla) {
		this.getTablas().add(tabla);
		tabla.setArchivo(this);
	}
	
	public void eliminarTabla(Tabla tabla) {
		this.getTablas().remove(tabla);
	}

	public String getUbicacionArchivo() {
		return ubicacionArchivo;
	}

	public void setUbicacionArchivo(String ubicacionArchivo) {
		this.ubicacionArchivo = ubicacionArchivo;
	}
	
	public Boolean getDatosCargados() {
		return datosCargados;
	}

	public void setDatosCargados(Boolean datosCargados) {
		this.datosCargados = datosCargados;
	}

	public Archivo(Long id, @NotEmpty @NotNull String codigo, @NotEmpty @NotNull String nombre,
			@NotNull Date fechaSubido, Entrega entrega, Formato formato, List<Tabla> tablas) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechaSubido = fechaSubido;
		this.entrega = entrega;
		this.formato = formato;
		this.tablas = tablas;
	}

	public Archivo() {}
    
    
}