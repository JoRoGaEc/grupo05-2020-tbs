package com.sif.digestyc.Entity.StandardizationModule;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;

@Entity
@Table(name="columna_correspondencia")
public class ColumnaCorrespondencia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estandar_id")
	private Estandar estandar;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columna_version_plantilla_id", referencedColumnName = "id", nullable=true)
    private ColumnaVersionPlantilla columnaVersionPlantilla;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estandar getEstandar() {
		return estandar;
	}

	public void setEstandar(Estandar estandar) {
		this.estandar = estandar;
	}

	public ColumnaVersionPlantilla getColumnaVersionPlantilla() {
		return columnaVersionPlantilla;
	}

	public void setColumnaVersionPlantilla(ColumnaVersionPlantilla columnaVersionPlantilla) {
		this.columnaVersionPlantilla = columnaVersionPlantilla;
	}
	
	

}
