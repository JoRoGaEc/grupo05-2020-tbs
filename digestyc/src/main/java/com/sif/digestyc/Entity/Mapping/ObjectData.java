package com.sif.digestyc.Entity.Mapping;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.bytefish.jsqlserverbulkinsert.mapping.AbstractMapping;

public class ObjectData extends AbstractMapping<ObjectDataMapping>{

	public ObjectData(String tableName, List<String> columnas) {
		super(tableName);
		mapLong("id", true);
		mapLong("registro_id", ObjectDataMapping::getRegistro_id);
		mapLong("entrega_id", ObjectDataMapping::getEntrega_id);
		for(String val : columnas) {
			mapNvarchar(val, obj -> obj.getValue(val));
		}
	}

}
 