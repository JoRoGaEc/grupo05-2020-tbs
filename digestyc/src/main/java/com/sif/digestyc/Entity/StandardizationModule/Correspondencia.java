package com.sif.digestyc.Entity.StandardizationModule;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Entity.LoadModule.ValorTipico;

@Entity
@Table(name="correspondencia")
public class Correspondencia implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="valor_tipico_id")
	@NotNull(message="debe seleccionar un valor tipico")
	private ValorTipico valorTipico;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="valor_tipico_estandar_id")
	@NotNull(message="debe seleccionar un valor tipico")
	private ValorTipicoEstandar valorTipicoEstandar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ValorTipico getValorTipico() {
		return valorTipico;
	}

	public void setValorTipico(ValorTipico valorTipico) {
		this.valorTipico = valorTipico;
	}

	public ValorTipicoEstandar getValorTipicoEstandar() {
		return valorTipicoEstandar;
	}

	public void setValorTipicoEstandar(ValorTipicoEstandar valorTipicoEstandar) {
		this.valorTipicoEstandar = valorTipicoEstandar;
	}
	
	
	
	
	

}
