package com.sif.digestyc.Entity.LoadModule;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name = "valor_tipico")
public class ValorTipico {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="tipificacion_id", nullable = false)
    private Tipificacion tipificacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "valorTipico")
	private List<Correspondencia> correspondencias =  new ArrayList<>();

    @Column(length = 300)
    private String valor;

    @Column(name = "existe_rango")
    private Integer existeRango =  0;
    
    @Column(name = "inicio_rango")
    private float inicioRango;
    
    @Column(name = "fin_rango")
    private float finRango;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "inicio_fecha")
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fin_fecha")
    private Date fechaFin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tipificacion getTipificacion() {
		return tipificacion;
	}

	public void setTipificacion(Tipificacion tipificacion) {
		this.tipificacion = tipificacion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getExisteRango() {
		return existeRango;
	}

	public void setExisteRango(Integer existeRango) {
		this.existeRango = existeRango;
	}

	public float getInicioRango() {
		return inicioRango;
	}

	public void setInicioRango(float inicioRango) {
		this.inicioRango = inicioRango;
	}

	public float getFinRango() {
		return finRango;
	}

	public void setFinRango(float finRango) {
		this.finRango = finRango;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ValorTipico(Long id, Tipificacion tipificacion, String valor, Integer existeRango, float inicioRango,
			float finRango, Date fechaInicio, Date fechaFin) {
		super();
		this.id = id;
		this.tipificacion = tipificacion;
		this.valor = valor;
		this.existeRango = existeRango;
		this.inicioRango = inicioRango;
		this.finRango = finRango;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public ValorTipico() {
		super();
	}

	public ValorTipico(Tipificacion tipificacion, Integer existeRango, String valor) {
		super();
		this.tipificacion = tipificacion;
		this.valor = valor;
		this.existeRango = existeRango;
	}

	public ValorTipico(Tipificacion tipificacion, Integer existeRango, float inicioRango, float finRango) {
		super();
		this.tipificacion = tipificacion;
		this.existeRango = existeRango;
		this.inicioRango = inicioRango;
		this.finRango = finRango;
	}

	public ValorTipico(Tipificacion tipificacion, Integer existeRango, Date fechaInicio, Date fechaFin) {
		super();
		this.tipificacion = tipificacion;
		this.existeRango = existeRango;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public List<Correspondencia> getCorrespondencias() {
		return correspondencias;
	}

	public void setCorrespondencias(List<Correspondencia> correspondencias) {
		this.correspondencias = correspondencias;
	}
    
	
	
    
    
}
