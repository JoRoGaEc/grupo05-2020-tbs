package com.sif.digestyc.Entity.StandardizationModule;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
@Table(name="valor_tipico_estandar")
public class ValorTipicoEstandar implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="estandar_id")
	private Estandar estandar;
	
	@Column(name="valor_tipico")
	private String valorTipico;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "valorTipicoEstandar")
	private List<Correspondencia> correspondencias = new ArrayList<>();

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

	public List<Correspondencia> getCorrespondencias() {
		return correspondencias;
	}

	public void setCorrespondencias(List<Correspondencia> correspondencias) {
		this.correspondencias = correspondencias;
	}

	public String getValorTipico() {
		return valorTipico;
	}

	public void setValorTipico(String valorTipico) {
		this.valorTipico = valorTipico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

}
