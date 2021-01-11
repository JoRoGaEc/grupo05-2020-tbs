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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(	name = "plantilla_columna", 
		uniqueConstraints= { 
				@UniqueConstraint(columnNames = { "codigo","plantilla_id" })
			}
		)
public class PlantillaColumna {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="plantilla_id", nullable = false)
    private Plantilla plantilla;

    /*@ManyToOne
    @JoinColumn(name="plantilla_tipificacion_id", nullable = true)
    private Tipificacion tipificacion;*/
    @OneToMany(mappedBy = "plantillaColumna", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ColumnaVersionPlantilla> versionesColumna;
    
    //@NotEmpty(message="El nombre no puede estar vacío")
    //@NotNull
    //@Length(min=1, max=60, message="El Nombre necesita mínimo 1 caracter y máximo 60 caracteres")
    @Column(length = 60)
    private String nombre;
    
    @NotNull
    @Column(length = 60)
    private String codigo;
    
    @NotNull
    @Column(name = "orden")
    private Integer orden;
    
    @NotNull
    @Column(name="activo")
    private boolean activo;
    
    

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		this.codigo = nombre.trim().replace(" ", "_");
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

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
	
	
	
	public List<ColumnaVersionPlantilla> getVersionesColumna() {
		return versionesColumna;
	}

	public void setVersionesColumna(List<ColumnaVersionPlantilla> versionesColumna) {
		this.versionesColumna = versionesColumna;
	}

	public PlantillaColumna(Long id, Plantilla plantilla, @NotEmpty @NotNull String nombre,
			@NotNull Integer orden, @NotNull boolean activo) {
		super();
		this.id = id;
		this.plantilla = plantilla;

		this.nombre = nombre;
		this.orden = orden;
		this.activo = activo;
		this.codigo = nombre.trim().replace(" ", "_");
	}

	public PlantillaColumna() {
		super();
	}
	
	
	
    

}
