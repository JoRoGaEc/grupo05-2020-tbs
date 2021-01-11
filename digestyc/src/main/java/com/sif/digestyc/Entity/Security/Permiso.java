package com.sif.digestyc.Entity.Security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Listener.BitacoraListener;

@Entity
@EntityListeners(BitacoraListener.class)
@Table(name = "permiso", schema="seguridad")
public class Permiso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty (message="El nombre es obligatorio")
    @NotNull (message = "El nombre no puede ser nulo")
    @Length(max = 128, message = "Nombre debe tener máximo 128 caracteres")
	@Column(unique = true)
	private String nombre;

	@NotEmpty (message="La ubicacion es obligatorio")
    @NotNull (message = "La ubicacion no puede ser nulo")
    @Length(max = 300, message = "Nombre debe tener máximo 300 caracteres")
	private String ubicacion;
	// Dejarlo como not null?

	@ManyToMany(mappedBy = "permisos")
	private List<Role> roles;
	
	public Permiso() {
		super();
	}

	public Permiso(Long id) {
		super();
		this.id = id;
	}

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

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Permiso [id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", roles=" + roles + "]";
	}

	
	
}
