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

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@Table(name = "version_plantilla")
@Data
@EntityListeners(BitacoraListener.class)
public class VersionPlantilla {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="habilitada", nullable = true)
	private Boolean habilitada;
	
	@ManyToOne
	@JoinColumn(name = "plantilla_id", nullable = true)
	private Plantilla plantilla;
	
	@OneToMany(mappedBy = "versionPlantilla", fetch  = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Entrega> entregas =  new ArrayList<Entrega>();
	
	@OneToMany(mappedBy = "versionPlantilla", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
	private List<ColumnaVersionPlantilla> versionesColumna = new ArrayList<ColumnaVersionPlantilla>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void agregarVersionColumna(ColumnaVersionPlantilla versionPlantillaColumna) {
		this.versionesColumna.add(versionPlantillaColumna);
	}
	
	public void removerVersionColumna(ColumnaVersionPlantilla versionPlantillaColumna) {
		this.versionesColumna.remove(versionPlantillaColumna);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Plantilla getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public List<ColumnaVersionPlantilla> getVersionesColumna() {
		return versionesColumna;
	}

	public void setVersionesColumna(List<ColumnaVersionPlantilla> versionesColumna) {
		this.versionesColumna = versionesColumna;
	}

	public Boolean getHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

	public List<Entrega> getEntregas() {
		return entregas;
	}

	public void setEntregas(List<Entrega> entregas) {
		this.entregas = entregas;
	}
	
	
	
	
	
}
