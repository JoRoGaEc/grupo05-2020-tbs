package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Map;

public interface InsertarEnTablaService {
	
	public abstract boolean insertarConBulk(long registro_id, long entrega_id, List<Map<String, String>> datos, List<String> columnas, String tabla);
	
}
