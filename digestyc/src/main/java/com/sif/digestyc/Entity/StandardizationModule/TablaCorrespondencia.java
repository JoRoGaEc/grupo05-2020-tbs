package com.sif.digestyc.Entity.StandardizationModule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;

@Entity
@Table(name="tabla_correspondencia")
public class TablaCorrespondencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private Boolean error = false;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabla_id", referencedColumnName = "id", nullable=true)
    private Tabla tabla;
	
	@Column(name="nombre_tabla_correspondiente", nullable=false)
	private String nombreTablaCorrespondiente;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "tabla_correspondencia_columna_version_plantilla", 
			uniqueConstraints = { @UniqueConstraint(columnNames = { "tabla_correspondencia_id", "columna_version_plantilla_id" }) }, 
			joinColumns = @JoinColumn(name = "tabla_correspondencia_id"), 
			inverseJoinColumns = @JoinColumn(name = "columna_version_plantilla_id")
	)
	private List<ColumnaVersionPlantilla> columnaVersionPlantillas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ColumnaVersionPlantilla> getColumnaVersionPlantillas() {
		return columnaVersionPlantillas;
	}

	public void setColumnaVersionPlantillas(List<ColumnaVersionPlantilla> columnaVersionPlantillas) {
		this.columnaVersionPlantillas = new ArrayList<ColumnaVersionPlantilla>();
		this.columnaVersionPlantillas.addAll(columnaVersionPlantillas);
	}

	public Tabla getTabla() {
		return tabla;
	}

	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}

	public String getNombreTablaCorrespondiente() {
		return nombreTablaCorrespondiente;
	}

	public void setNombreTablaCorrespondiente(String nombreTablaCorrespondiente) {
		this.nombreTablaCorrespondiente = nombreTablaCorrespondiente;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	
	public void setError() {
		this.error = true;
	}

	
}
