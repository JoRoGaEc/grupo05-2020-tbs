package com.sif.digestyc.Entity.LoadModule;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name="columna_version_plantilla")
public class ColumnaVersionPlantilla {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "columna_id", nullable=true)
	private PlantillaColumna plantillaColumna; 
	
	@ManyToOne
	@JoinColumn(name = "version_plantilla_id", nullable=true)
	private VersionPlantilla versionPlantilla;
	
	@ManyToOne
    @JoinColumn(name="tipificacion_id", nullable = true)
    private Tipificacion tipificacion;
	
    @OneToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name = "columna_correspondencia_id", referencedColumnName = "id", nullable=true)
    private ColumnaCorrespondencia columnaCorrespondencia;
	
	@ManyToMany(mappedBy = "columnaVersionPlantillas", fetch = FetchType.LAZY)
	private List<TablaCorrespondencia> tablaCorrespondencias;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public PlantillaColumna getPlantillaColumna() {
		return plantillaColumna;
	}

	public void setPlantillaColumna(PlantillaColumna plantillaColumna) {
		this.plantillaColumna = plantillaColumna;
	}

	public VersionPlantilla getVersionPlantilla() {
		return versionPlantilla;
	}

	public void setVersionPlantilla(VersionPlantilla versionPlantilla) {
		this.versionPlantilla = versionPlantilla;
	}

	public Tipificacion getTipificacion() {
		return tipificacion;
	}

	public void setTipificacion(Tipificacion tipificacion) {
		this.tipificacion = tipificacion;
	}

	public ColumnaCorrespondencia getColumnaCorrespondencia() {
		return columnaCorrespondencia;
	}

	public void setColumnaCorrespondencia(ColumnaCorrespondencia columnaCorrespondencia) {
		this.columnaCorrespondencia = columnaCorrespondencia;
	}

	public List<TablaCorrespondencia> getTablaCorrespondencias() {
		return tablaCorrespondencias;
	}

	public void setTablaCorrespondencias(List<TablaCorrespondencia> tablaCorrespondencias) {
		this.tablaCorrespondencias = tablaCorrespondencias;
	}
	
	

	
}
