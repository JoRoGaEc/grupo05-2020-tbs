package com.sif.digestyc.Entity.Security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "bitacora", schema="seguridad")
public class Bitacora {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Debe contener una acción, INSERT, DELETE, UPDATE")
	@NotNull
	@Length(min = 4, max=40, message = "La longitud debe estar entre 4 y 40 caracteres")
	@Column(name="accion", length=40)
	private String accion;
	
	@NotBlank(message = "Debe tener un usuario que realiza la acción")
	@NotNull
	@Length(min = 6, max =75, message = "El usuario debe tener mínimo 6 caracteres y máximo de 75 caracteres")
	@Column(name="usuario", length=75)
	private String usuario;

	@NotBlank(message = "Debe tener una vista")
	@NotNull
	@Length(min = 3, max =50, message = "La vista debe tener entre 3 y 50 caracteres")
	@Column(name="tabla", length=50)
	private String tabla;

	@NotBlank(message = "Debe tener una breve descripcion")
	@NotNull
	@Length(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
	@Column(name="descripcion", length=100)
	private String descripcion;
	
    @Column(name = "fecha")
    private Date fecha;
    
    
    public Bitacora(Long id,
			@NotBlank(message = "Debe contener una accion, INSERT, DELETE, UPDATE") @NotNull @Length(min = 4, max = 40, message = "La longitud debe estar entre 4 y 40 caracteres") String accion,
			@NotBlank(message = "Debe tener un usuario que realiza la accion") @NotNull @Length(min = 6, max = 75, message = "El usuario debe tener al menos 6 caracteres y menos de 75 caracteres") String usuario,
			@NotBlank(message = "Debe tener una vista") @NotNull @Length(min = 3, max = 50, message = "La vista debe tener entre 3 y 50 caracteres") String tabla,
			@NotBlank(message = "Debe tener una breve descripcion") @NotNull @Length(min = 3, max = 100, message = "La descripcion debe tener entre 3 y 100 caracteres") String descripcion) {
		super();
		this.id = id;
		this.accion = accion;
		this.usuario = usuario;
		this.tabla = tabla;
		this.descripcion = descripcion;
		this.fecha = new Date();
	}

    public Bitacora(
			@NotBlank(message = "Debe contener una accion, INSERT, DELETE, UPDATE") @NotNull @Length(min = 4, max = 40, message = "La longitud debe estar entre 4 y 40 caracteres") String accion,
			@NotBlank(message = "Debe tener un usuario que realiza la accion") @NotNull @Length(min = 6, max = 75, message = "El usuario debe tener al menos 6 caracteres y menos de 75 caracteres") String usuario,
			@NotBlank(message = "Debe tener una vista") @NotNull @Length(min = 3, max = 50, message = "La vista debe tener entre 3 y 50 caracteres") String tabla,
			@NotBlank(message = "Debe tener una breve descripcion") @NotNull @Length(min = 3, max = 100, message = "La descripcion debe tener entre 3 y 100 caracteres") String descripcion) {
		super();
		this.accion = accion;
		this.usuario = usuario;
		this.tabla = tabla;
		this.descripcion = descripcion;
		this.fecha = new Date();
	}
	
	public Bitacora() {
		super();
		this.fecha = new Date();
	}



	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVista() {
		return tabla;
	}

	public void setVista(String tabla) {
		this.tabla = tabla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
