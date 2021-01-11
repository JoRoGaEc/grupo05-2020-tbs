package com.sif.digestyc.Entity.Security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Listener.BitacoraListener;

import javax.persistence.JoinColumn;

@Entity
@EntityListeners(BitacoraListener.class)
@Table(name = "rol", schema="seguridad")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty (message="El nombre es obligatorio")
    @NotNull (message = "El nombre no puede ser nulo")
    @Length(max = 50, message = "Nombre debe tener máximo 50 caracteres")
	@Column(unique = true)
	private String nombre;

	
	@NotEmpty (message="La descripcion es obligatorio")
    @NotNull (message = "La descripcion no puede ser nulo")
    @Length(max = 1024, message = "Descripcion debe tener máximo 1024 caracteres")
	private String descripcion;

	@Length(max = 20, message = "Código debe tener máximo 20 caracteres")
	private String codigo;

	@ManyToMany(mappedBy = "roles")
	private List<Usuario> usuarios;

	@ManyToMany()
	@JoinTable(
			name = "role_permiso", 
			schema="seguridad",
			uniqueConstraints = { @UniqueConstraint(columnNames = { "role_id",
			"permiso_id" }) }, joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permiso_id"))
	private List<Permiso> permisos;
	
	public boolean setPermiso(Permiso permiso) {
		return permisos.add(permiso);
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.trim();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	
	
	

}
