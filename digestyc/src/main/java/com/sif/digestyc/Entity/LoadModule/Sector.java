package com.sif.digestyc.Entity.LoadModule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 128)
    private String nombre;

    @NotNull
    @Column(length = 128)
    private String colorHex;

    @NotNull
    @Column(length = 128)
    private String ionIcon;

    @OneToMany(mappedBy = "sector")
    @JsonManagedReference
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

	public String getColorHex() {
		return colorHex;
	}

	public void setColorHex(String colorHex) {
		this.colorHex = colorHex;
	}

	public String getIonIcon() {
		return ionIcon;
	}

	public void setIonIcon(String ionIcon) {
		this.ionIcon = ionIcon;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public Sector(Long id, @NotNull String nombre, @NotNull String colorHex, @NotNull String ionIcon,
			List<Registro> registros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.colorHex = colorHex;
		this.ionIcon = ionIcon;
		this.registros = registros;
	}

	public Sector() {
		super();
	}
    
    

}