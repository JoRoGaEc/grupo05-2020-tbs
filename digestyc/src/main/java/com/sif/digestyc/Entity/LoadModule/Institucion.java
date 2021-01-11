package com.sif.digestyc.Entity.LoadModule;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
public class Institucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty (message="El nombre es obligatorio")
    @NotNull (message = "El nombre no puede ser nulo")
    @Length(max = 128, message = "Nombre debe tener máximo 128 caracteres")
    @Column(unique = true)
    private String nombre;


    private Date fechaRegistro;

    // Default Fetch para One to Many es lazy
    @OneToMany(mappedBy = "institucion")
    private List<Usuario> usuarios;

    // Default Fetch para One to Many es lazy
    @OneToMany(mappedBy = "institucion")
    private List<Registro> registros;
    
	@ManyToMany()
	@JoinTable(
			name = "institucion_tipificacion", 
			uniqueConstraints = { @UniqueConstraint(columnNames = {"institucion_id", "tipificacion_id" }) }, 
			joinColumns = @JoinColumn(name = "institucion_id"), 
			inverseJoinColumns = @JoinColumn(name = "tipificacion_id")
	)
	private List<Tipificacion> tipificaciones; 

	@PrePersist
	public void prePersist() {
		fechaRegistro = new Date(); //Se va ejecutar a la hora de crear un Profesor
	}
	
	
	
	public Institucion() {
		
	}
	
	public Institucion(Long id) {
		this.id =  id;
	}
	
	
	
	public List<Tipificacion> getTipificaciones() {
		return tipificaciones;
	}



	public void setTipificaciones(List<Tipificacion> tipificaciones) {
		this.tipificaciones = tipificaciones;
	}

	public void setTipificaciones(Tipificacion tipificacion) {
		this.tipificaciones.add(tipificacion);
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
    
	public void agregarTipificacion(Tipificacion tipificacion) {
		this.tipificaciones.add(tipificacion);
	}
    
    
}