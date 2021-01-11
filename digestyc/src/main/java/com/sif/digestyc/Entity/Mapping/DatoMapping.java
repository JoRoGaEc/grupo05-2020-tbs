package com.sif.digestyc.Entity.Mapping;

import com.sif.digestyc.Entity.LoadModule.DatoOrigen;

import de.bytefish.jsqlserverbulkinsert.mapping.AbstractMapping;

public class DatoMapping extends AbstractMapping<DatoOrigen>{

	public DatoMapping() {
		super("dato_origen");
		mapLong("id", true);
		mapNvarchar("dato",DatoOrigen::getDato);
		mapLong("columna_origen_id", DatoOrigen::getIdColumnaOrigen);
		mapLong("fila_origen_id", DatoOrigen::getIdFilaOrigen);
	}
	
	

}
