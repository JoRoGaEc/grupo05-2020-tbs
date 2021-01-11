package com.sif.digestyc.Entity.ValidationModule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sif.digestyc.Entity.LoadModule.Tabla;

@Entity(name = "notificacion")
public class Notificacion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String mensaje;
	
	@Column
	private String titulo;
	
	@Column
	private long cantidadDatos;
	
	@Column
	private long cantidadErrores;
	
	@Column
	private String tabla;
	
	@Column 
	private long tablaId;
	
	@Column
	private String url;

	@Column
	private boolean estado;
	
	
	public Notificacion() {
		super();
	}


	public Notificacion(String mensaje, String titulo, long cantidadDatos, long cantidadErrores, String tabla, long tablaId, 
			boolean estado, String url) {
		super();
		this.mensaje = mensaje;
		this.titulo = titulo;
		this.cantidadDatos = cantidadDatos;
		this.cantidadErrores = cantidadErrores;
		this.tabla = tabla;
		this.tablaId = tablaId;
		this.estado = estado;
		this.url = url;
	}



	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getCantidadDatos() {
		return cantidadDatos;
	}

	public void setCantidadDatos(long cantidadDatos) {
		this.cantidadDatos = cantidadDatos;
	}

	public long getCantidadErrores() {
		return cantidadErrores;
	}

	public void setCantidadErrores(long cantidadErrores) {
		this.cantidadErrores = cantidadErrores;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}



	public long getTablaId() {
		return tablaId;
	}


	public void setTablaId(long tablaId) {
		this.tablaId = tablaId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	
}
