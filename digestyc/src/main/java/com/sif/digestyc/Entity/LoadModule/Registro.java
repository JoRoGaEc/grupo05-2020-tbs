package com.sif.digestyc.Entity.LoadModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data

@NamedEntityGraph(
    name = "detail-registro",
    attributeNodes = {
        @NamedAttributeNode("tipoRegistro"),
        @NamedAttributeNode("periodicidad"),
        @NamedAttributeNode("institucion")
    }
)
public class Registro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty (message="El nombre es obligatorio")
    @NotNull (message = "El nombre no puede ser nulo")
    @Length(min=1, max = 128, message = "El Nombre debe tener mínimo 1 caracter y máximo 1280 caracteres")
    private String nombre;

    @NotNull (message = "Fecha Inicio Entrega no puede ser nulo")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioEntrega;


    @NotNull (message = "Fecha Fin Entrega no puede ser nulo")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinEntrega;

    @Length(max = 1024, message = "La Descripción debe tener máximo 1024 caracteres")
    private String descripcion;

    @NotNull (message = "La Prioridad no puede ser nulo")
    private Integer prioridad;
    
    @NotNull (message = "Activo no puede ser nulo")
    private Boolean activo;
    
    @NotNull (message = "Editable no puede ser nulo")
    private Boolean editable;

    @NotNull (message = "Duración Plazo Entrega no puede ser nulo")
    private Integer duracionPlazoEntrega;

    @ManyToOne
    // Default Fetch type para ManyToOne es Eager
    @JoinColumn(name="institucion_id", nullable = false)
    private Institucion institucion;

    @ManyToMany(mappedBy = "noNotificarRegistros")
    // Default Fetch type para ManyToMany es Lazy
    private List<Usuario> noNotificarUsuarios;

    @ManyToOne
    @JoinColumn(name="periodicidad_id", nullable = false)
    private Periodicidad periodicidad;

    @ManyToOne
    @JoinColumn(name="tipo_registro_id", nullable = false)
    private TipoRegistro tipoRegistro;
    
    @ManyToOne
    @JoinColumn(name="sector_id", nullable = false)
    private Sector sector;
    
    @OneToMany(mappedBy = "registro")
    private List<Entrega> entregas;
    
    @ManyToOne
    @JoinColumn(name="plantilla_id", nullable = false)
    private Plantilla plantilla;
    
    @ManyToOne
    @JoinColumn(name="directorio_id", nullable = false)
    private Directorio directorio;
    
    @Column(name = "ubicacion")
    private String ubicacion;
    
    @Column(name = "codigo")
    private String codigo;
    
    @PrePersist
    public void prePersist() {
    	this.activo =false;   
    }

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


	public Date getFechaInicioEntrega() {
		return fechaInicioEntrega;
	}


	public void setFechaInicioEntrega(Date fechaInicioEntrega) {
		this.fechaInicioEntrega = fechaInicioEntrega;
	}


	public Date getFechaFinEntrega() {
		return fechaFinEntrega;
	}


	public void setFechaFinEntrega(Date fechaFinEntrega) {
		this.fechaFinEntrega = fechaFinEntrega;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getPrioridad() {
		return prioridad;
	}


	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}


	public Integer getDuracionPlazoEntrega() {
		return duracionPlazoEntrega;
	}


	public void setDuracionPlazoEntrega(Integer duracionPlazoEntrega) {
		this.duracionPlazoEntrega = duracionPlazoEntrega;
	}


	public Institucion getInstitucion() {
		return institucion;
	}


	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}


	public List<Usuario> getNoNotificarUsuarios() {
		return noNotificarUsuarios;
	}


	public void setNoNotificarUsuarios(List<Usuario> noNotificarUsuarios) {
		this.noNotificarUsuarios = noNotificarUsuarios;
	}


	public Periodicidad getPeriodicidad() {
		return periodicidad;
	}


	public void setPeriodicidad(Periodicidad periodicidad) {
		this.periodicidad = periodicidad;
	}


	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}


	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}


	public List<Entrega> getEntregas() {
		return entregas;
	}


	public void setEntregas(List<Entrega> entregas) {
		this.entregas = entregas;
	}


	public Plantilla getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public Boolean getActivo() {
		return activo;
	}


	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


	public Boolean getEditable() {
		return editable;
	}


	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Registro(Long id, @NotEmpty @NotNull String nombre, @NotEmpty @NotNull Date fechaInicioEntrega,
			@NotEmpty @NotNull Date fechaFinEntrega, String descripcion, @NotNull Integer prioridad,
			@NotNull Boolean activo, @NotNull Boolean editable, @NotNull Integer duracionPlazoEntrega,
			Institucion institucion, List<Usuario> noNotificarUsuarios, Periodicidad periodicidad,
			TipoRegistro tipoRegistro,Sector sector, List<Entrega> entregas, Plantilla plantilla, String codigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.fechaInicioEntrega = fechaInicioEntrega;
		this.fechaFinEntrega = fechaFinEntrega;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.activo = activo;
		this.editable = editable;
		this.duracionPlazoEntrega = duracionPlazoEntrega;
		this.institucion = institucion;
		this.noNotificarUsuarios = noNotificarUsuarios;
		this.periodicidad = periodicidad;
		this.tipoRegistro = tipoRegistro;
		this.sector = sector;
		this.entregas = entregas;
		this.plantilla = plantilla;
	}
	
	public Registro() {
		super();
	}

	public Directorio getDirectorio() {
		return directorio;
	}

	public void setDirectorio(Directorio directorio) {
		this.directorio = directorio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	
	
    
    
}