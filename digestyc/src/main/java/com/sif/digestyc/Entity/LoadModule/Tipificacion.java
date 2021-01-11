package com.sif.digestyc.Entity.LoadModule;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name = "tipificacion")
public class Tipificacion {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotEmpty(message = "El nombre no debe estar vacío")
    @NotNull(message = "El nombre es obligatorio")
    @Length(min=2, max=100, message = "El Nombre debe tener entre 2 y 100 caracteres")
    @Column(length = 100)
    private String nombre;

    @NotEmpty(message = "La descripción no debe estar vacía")
    @NotNull(message = "La descripción es obligatorio")
    @Length(min=5, max=500, message = "La descripción debe tener entre 5 y 500 caracteres")
    @Column(length = 500)
    private String descripcion;
    
    @NotNull
    @Column(name = "es_nulo")
    private boolean esNulo;


    @OneToMany(mappedBy = "tipificacion", fetch = FetchType.LAZY, cascade  = CascadeType.ALL)
    private List<ColumnaVersionPlantilla> versionesColumna;
    

    @OneToMany(mappedBy = "tipificacion", fetch = FetchType.LAZY)
    private List<ValorTipico> valorTipico;
    
    @ManyToOne
    @JoinColumn(name="variacion_tipo_id", nullable = false)
    @JsonManagedReference
    private VariacionTipoDato variacionTipoDato;
    
	@ManyToMany(mappedBy = "tipificaciones")
	private List<Institucion> instituciones;
	
	

	public Tipificacion() {
		super();
	}

	public Tipificacion(Long id,
			@NotEmpty(message = "El nombre no debe estar vacio") @NotNull(message = "El nombre es obligatorio") @Min(value = 5, message = "debe tener al menos 5 caracteres") @Max(value = 100, message = "debe tener menos de 100 caracteres") String nombre,
			@NotEmpty(message = "La descripcion no debe estar vacio") @NotNull(message = "La descripcion es obligatorio") @Min(value = 5, message = "debe tener al menos 5 caracteres") @Max(value = 499, message = "debe tener menos de 500 caracteres") String descripcion,
			@NotNull boolean esNulo, List<ValorTipico> valorTipico,
			VariacionTipoDato tipoDato, List<Institucion> instituciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.esNulo = esNulo;
		this.valorTipico = valorTipico;
		this.variacionTipoDato = tipoDato;
		this.instituciones = instituciones;
	}





	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEsNulo() {
		return esNulo;
	}

	public void setEsNulo(boolean esNulo) {
		this.esNulo = esNulo;
	}

	public List<ValorTipico> getValorTipico() {
		return valorTipico;
	}

	public void setValorTipico(List<ValorTipico> valorTipico) {
		this.valorTipico = valorTipico;
	}

	public VariacionTipoDato getVariacionTipoDato() {
		return variacionTipoDato;
	}

	public void setVariacionTipoDato(VariacionTipoDato variacionTipoDato) {
		this.variacionTipoDato = variacionTipoDato;
	}

	public List<Institucion> getInstituciones() {
		return instituciones;
	}

	public void setInstituciones(List<Institucion> instituciones) {
		this.instituciones = instituciones;
	}
	
	public void setInstituciones(Institucion institucion) {
		this.instituciones.add(institucion);
	}

	public List<ColumnaVersionPlantilla> getVersionesColumna() {
		return versionesColumna;
	}

	public void setVersionesColumna(List<ColumnaVersionPlantilla> versionesColumna) {
		this.versionesColumna = versionesColumna;
	}

}
