package com.sif.digestyc.Service.Load;

import java.util.List;

import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.HistorialEstadoEntrega;
import com.sif.digestyc.Entity.LoadModule.Registro;

public interface HistorialEntregaService {
	
	public abstract List<HistorialEstadoEntrega> findAll();
	public List<HistorialEstadoEntrega> estado(Long registro_id);
	public void actualizarEstado(Long registro_id,Long periodo_id,String comentario);

}
