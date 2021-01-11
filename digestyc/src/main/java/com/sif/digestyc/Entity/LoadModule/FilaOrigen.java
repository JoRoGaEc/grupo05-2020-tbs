package com.sif.digestyc.Entity.LoadModule;

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
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "fila_origen")
public class FilaOrigen {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="tabla_id", nullable = false)
    private Tabla tabla;
    
    @OneToMany(mappedBy = "filaOrigen", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<DatoOrigen> datosOrigenes;
    
    public void setDatoOrigene(DatoOrigen datosOrigen) {
		this.datosOrigenes.add(datosOrigen);
	}
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tabla getTabla() {
		return tabla;
	}

	public void setTabla(Tabla tabla) {
		this.tabla = tabla;
	}

	public List<DatoOrigen> getDatosOrigenes() {
		return datosOrigenes;
	}

	public void setDatosOrigenes(List<DatoOrigen> datosOrigenes) {
		this.datosOrigenes = datosOrigenes;
	}

	public FilaOrigen(Tabla tabla, List<DatoOrigen> datosOrigenes) {
		super();
		this.tabla = tabla;
		this.datosOrigenes = datosOrigenes;
	}

	public FilaOrigen() {
		super();
	}
    
    
    

}
