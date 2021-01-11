package com.sif.digestyc.Entity.LoadModule;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Listener.BitacoraListener;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name="firstProcedure",procedureName="sp_nuevaEntrega", 
parameters={
		@StoredProcedureParameter(mode=ParameterMode.IN,name="registro_id",type=Integer.class),
		@StoredProcedureParameter(mode=ParameterMode.IN,name="periodo_id",type=Integer.class),
		@StoredProcedureParameter(mode=ParameterMode.IN,name="formato",type=Integer.class)
})  })
@NamedEntityGraph(
    name = "detail-entrega",
    attributeNodes = {
        @NamedAttributeNode(value = "registro"),
        @NamedAttributeNode("usuario"),
        @NamedAttributeNode("archivo"),
        @NamedAttributeNode(value = "periodo", subgraph = "sub-g-periodo")
    },
    subgraphs = {
        @NamedSubgraph(name = "sub-g-registro", attributeNodes = {
            @NamedAttributeNode("tipoRegistro"),
            @NamedAttributeNode("institucion"),
            @NamedAttributeNode("periodicidad")
        }),
        @NamedSubgraph(name = "sub-g-periodo", attributeNodes = {
            @NamedAttributeNode("periodicidad")
        }),
    }
)
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private Date fechaEntrega;
    
    @NotNull
    private Date fechaInicioEntrega;


    @NotNull
    private Date fechaFinEntrega;

    @ManyToOne
    @JoinColumn(name="periodo_id", nullable = true)
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name="registro_id", nullable = false)
    private Registro registro;

    @OneToMany(mappedBy = "entrega")
    private List<HistorialEstadoEntrega> historialEstados;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="version_plantilla_id", nullable =true)
    private VersionPlantilla versionPlantilla;
    
    @Column(name="ubicacion")
    private String ubicacion;
    

  //  @OneToOne(mappedBy = "entrega", fetch = FetchType.EAGER)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archivo_id", referencedColumnName = "id")
    private Archivo archivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<HistorialEstadoEntrega> getHistorialEstados() {
		return historialEstados;
	}

	public void setHistorialEstados(List<HistorialEstadoEntrega> historialEstados) {
		this.historialEstados = historialEstados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	
    
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	
	
	


	public VersionPlantilla getVersionPlantilla() {
		return versionPlantilla;
	}

	public void setVersionPlantilla(VersionPlantilla versionPlantilla) {
		this.versionPlantilla = versionPlantilla;
	}

	public Entrega(Long id, Date fechaEntrega, @NotNull Date fechaInicioEntrega, @NotNull Date fechaFinEntrega,
			Periodo periodo, Registro registro, List<HistorialEstadoEntrega> historialEstados, Usuario usuario,
			Archivo archivo) {
		super();
		this.id = id;
		this.fechaEntrega = fechaEntrega;
		this.fechaInicioEntrega = fechaInicioEntrega;
		this.fechaFinEntrega = fechaFinEntrega;
		this.periodo = periodo;
		this.registro = registro;
		this.historialEstados = historialEstados;
		this.usuario = usuario;
		this.archivo = archivo;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	
	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Entrega() {}
    
    
}