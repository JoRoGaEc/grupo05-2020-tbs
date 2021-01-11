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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;


@Entity
@Table(name = "tabla")
public class Tabla {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(length = 60)
    private String nombre;
    
    @NotNull
    @Column(name="estado", columnDefinition = "integer default 0")
    private int estado = 0; // 0||null:=no validado, 1:=errores encontrados, 2:=errores solucionados, 4:datos empiezan a insertarse en otra tabla, 5: datos finalmente insertados
    
    @ManyToOne
    @JoinColumn(name="archivo_id", nullable = true)
    private Archivo archivo;
    
    @OneToMany(mappedBy = "tabla", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<FilaOrigen> filas;
    
    @OneToMany(mappedBy = "tabla", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<ColumnaOrigen> columnas;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabla_correspondencia_id", referencedColumnName = "id")
    private TablaCorrespondencia tablaCorrespondencia;
    

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

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public List<FilaOrigen> getFilas() {
		return filas;
	}

	public void setFilas(List<FilaOrigen> filas) {
		this.filas = filas;
	}

	public List<ColumnaOrigen> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<ColumnaOrigen> columnas) {
		this.columnas = columnas;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

    
	public boolean isNotValid() {
		return estado == 0;
	}
	
	public boolean isValidating() {
		return estado == 1;
	}
    
	public boolean isValidated() {
		return estado == 2;
	}
	
	public boolean isDeActive() {
		return estado == -1;
	}
	
	public void setNoValidating() {
		this.estado = 0;
	}
	
	public void setValidating() {
		this.estado = 1;
	}
    
	public void setValidated() {
		this.estado = 2;
	}
	
	public void setDeAcitive() {
		this.estado = -1;
	}

	public TablaCorrespondencia getTablaCorrespondencia() {
		return tablaCorrespondencia;
	}

	public void setTablaCorrespondencia(TablaCorrespondencia tablaCorrespondencia) {
		this.tablaCorrespondencia = tablaCorrespondencia;
	}

	public void setPassingData() {
		this.estado = 4;
	}
	
	
	public boolean isPassingData() {
		return this.estado == 4;
	}
	
	
	public void setDataInserted() {
		this.estado = 5;
	}
	
	public boolean isDataInserted() {
		return this.estado == 5;
	}
	
    
}
