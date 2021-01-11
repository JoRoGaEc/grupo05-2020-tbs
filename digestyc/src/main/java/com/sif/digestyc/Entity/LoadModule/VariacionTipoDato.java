package com.sif.digestyc.Entity.LoadModule;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sif.digestyc.Listener.BitacoraListener;

@Entity
@EntityListeners(BitacoraListener.class)
@Table(name = "variacion_tipo_dato")
public class VariacionTipoDato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "longitud_cadena")
	private Integer longitudCadena=0 ; //En caso de que sea String
	
	@Column(name = "decimales")
	private Integer decimales=0; //En caso de ser numerico
	
	
	private String descripcion ;
	
	@Column(name  ="formatoFecha")
	private String  formatoFecha=""; //En caso e que sea tipo fecha
	
	@Column(name="is_boolean")
	private Boolean isBoolean=false;
	
    @ManyToOne
    @JoinColumn(name="tipo_dato_id", nullable = false)
    private TipoDato tipoDato;	
	
    @JsonBackReference
	@OneToMany(mappedBy = "variacionTipoDato", fetch = FetchType.LAZY)
	private List<Tipificacion> tipificacion;
	
	

	public VariacionTipoDato() {
		super();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLongitudCadena() {
		return longitudCadena;
	}

	public void setLongitudCadena(int longitudCadena) {
		this.longitudCadena = longitudCadena;
	}

	public int getDecimales() {
		return decimales;
	}

	public void setDecimales(int decimales) {
		this.decimales = decimales;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFormatoFecha() {
		return formatoFecha;
	}

	public void setFormatoFecha(String formatoFecha) {
		this.formatoFecha = formatoFecha;
	}

	public Boolean getIsBoolean() {
		return isBoolean;
	}

	public void setIsBoolean(Boolean isBoolean) {
		this.isBoolean = isBoolean;
	}


	public TipoDato getTipoDato() {
		return tipoDato;
	}


	public void setTipoDato(TipoDato tipoDato) {
		this.tipoDato = tipoDato;
	}


	public List<Tipificacion> getTipificacion() {
		return tipificacion;
	}


	public void setTipificacion(List<Tipificacion> tipificacion) {
		this.tipificacion = tipificacion;
	}

   

	
}
