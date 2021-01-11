package com.sif.digestyc.Entity.LoadModule;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data

public class Periodicidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(length = 128)
    private String nombre;


    @NotNull
    @Column(name = "dias_a_sumar", columnDefinition = "integer default 0")
    // Spring no convierte apropiadamente el nombre del campo.
    private Integer diasASumar;


    @NotNull
    @Column(name = "meses_a_sumar", columnDefinition = "integer default 0")
    private Integer mesesASumar;


    @NotNull
    @Column(name = "anios_a_sumar", columnDefinition = "integer default 0")
    private Integer aniosASumar;

    @OneToMany(mappedBy = "periodicidad")
    private List<Periodo> periodos;

    @OneToMany(mappedBy = "periodicidad")
    private List<Registro> registros;

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

	public Integer getDiasASumar() {
		return diasASumar;
	}

	public void setDiasASumar(Integer diasASumar) {
		this.diasASumar = diasASumar;
	}

	public Integer getMesesASumar() {
		return mesesASumar;
	}

	public void setMesesASumar(Integer mesesASumar) {
		this.mesesASumar = mesesASumar;
	}

	public Integer getAniosASumar() {
		return aniosASumar;
	}

	public void setAniosASumar(Integer aniosASumar) {
		this.aniosASumar = aniosASumar;
	}

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public Periodicidad(Long id, @NotEmpty @NotNull String nombre, @NotNull Integer diasASumar,
			@NotNull Integer mesesASumar, @NotNull Integer aniosASumar, List<Periodo> periodos,
			List<Registro> registros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.diasASumar = diasASumar;
		this.mesesASumar = mesesASumar;
		this.aniosASumar = aniosASumar;
		this.periodos = periodos;
		this.registros = registros;
	}
	public Periodicidad() {}
    
    

    
}