package com.sif.digestyc.Entity.LoadModule;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sif.digestyc.Listener.BitacoraListener;

import lombok.Data;

@Entity
@EntityListeners(BitacoraListener.class)
@Data
public class TipoRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(length = 128)
    private String nombre;
    
    @NotEmpty
    @NotNull
    @Column(length = 3)
    private String nombreCorto;

    @NotEmpty
    @Column(length = 1024)
    private String descripcion;

    @NotEmpty
    @Column(length = 25)
    private String codigo;

    @OneToMany(mappedBy = "tipoRegistro")
    private List<Registro> registros;

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

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
	public TipoRegistro(Long id, @NotEmpty @NotNull String nombre, @NotEmpty @NotNull String nombreCorto,
			@NotEmpty String descripcion, @NotEmpty String codigo, List<Registro> registros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.registros = registros;
	}
	public TipoRegistro() {}
	

    
}