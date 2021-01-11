package com.sif.digestyc.Entity.LoadModule;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name = "plantilla")
public class Plantilla {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name="habilitado")
    private boolean habilitado;
    
    @OneToMany(mappedBy = "plantilla", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Registro> registro;
    
    //Relacion con las versiones de la plantilla
    @OneToMany(mappedBy="plantilla" , fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VersionPlantilla> versionesPlantilla = new ArrayList<VersionPlantilla>();
    
    @OneToMany(mappedBy = "plantilla", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<PlantillaColumna> plantillaColumnas;
    
    @NotEmpty
    @NotNull
    @Column(length = 100)
    private String nombre;
    
    public void agregarPlantilla(VersionPlantilla versionPlantilla) {
    	this.versionesPlantilla.add(versionPlantilla);
    }
    
	public Plantilla() {
		super();
		this.registro = new ArrayList<Registro>();
	}

	public List<Registro> getRegistro() {
		return registro;
	}
	
	public void addColumna(PlantillaColumna pCol) {
		plantillaColumnas.add(pCol);
	}

	public void setRegistros(List<Registro> registro) {
		this.registro = registro;
	}
	
	public void setRegistro(Registro registro) {
		this.registro.add(registro);
	}

	public List<PlantillaColumna> getPlantillaColumnas() {
		return plantillaColumnas;
	}

	public void setPlantillaColumnas(List<PlantillaColumna> plantillaColumnas) {
		this.plantillaColumnas = plantillaColumnas;
	}
	
	

	public List<VersionPlantilla> getVersionesPlantilla() {
		return versionesPlantilla;
	}

	public void setVersionesPlantilla(List<VersionPlantilla> versionesPlantilla) {
		this.versionesPlantilla = versionesPlantilla;
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
	
	 public boolean isHabilitado() {
			return habilitado;
		}

		public void setHabilitado(boolean habilitado) {
			this.habilitado = habilitado;
		}

    
    
    
}
