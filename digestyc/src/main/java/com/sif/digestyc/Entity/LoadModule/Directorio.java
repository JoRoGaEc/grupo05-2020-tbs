package com.sif.digestyc.Entity.LoadModule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Listener.BitacoraListener;

@Entity
@EntityListeners(BitacoraListener.class)
@Table(name = "directorio")
public class Directorio {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="Error, la dirección no puede estar vacía")
    @NotNull(message="Erro, no puede ser nulo")
    @Length(min=2, max=299, message="La longitud del directorio debe estar entre 2 y 300 caracteres")
    @Column(length = 300)
    private String ubicacion;

    //Atributo para identificar si un directorio esta activo o no, solo puede estar activo un directorio no mas
    @Column(name="activo", nullable = true)
    private Boolean activo = true;
    
    @OneToMany(mappedBy = "directorio", fetch = FetchType.LAZY)
    private List<Registro> registros;

	public Directorio() {
		super();
	}

	public Directorio(Long id,
			@NotEmpty(message = "Error, la direccion no puede estar vacia") @NotNull(message = "Erro, no puede ser nulo") @Length(min = 2, max = 299, message = "La longitud del directorio debe estar entre 2 y 300 caracteres") String ubicacion,
			Boolean activo, List<Registro> registros) {
		super();
		this.id = id;
		this.ubicacion = ubicacion;
		this.activo = activo;
		this.registros = registros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

    
    
    
    
	
}
