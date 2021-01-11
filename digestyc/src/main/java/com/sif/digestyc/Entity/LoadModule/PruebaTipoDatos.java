package com.sif.digestyc.Entity.LoadModule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prueba_tipo_datos")
public class PruebaTipoDatos {
	
	@Id
	Long id;
	
	@Column(name="tipoFloat")
	Float tipoFloat;
	
	@Column(name="tipoDouble")
	Double tipoDouble;
	
	@Column(name="tipoBoolean")
	Boolean tipoBoolean;
	
	@Column(length = 60)
	String tipoString;

	
	
}
