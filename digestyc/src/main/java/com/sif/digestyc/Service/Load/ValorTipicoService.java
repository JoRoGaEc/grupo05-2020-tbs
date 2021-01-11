package com.sif.digestyc.Service.Load;

import java.util.List;

import com.sif.digestyc.Entity.LoadModule.ValorTipico;

public interface ValorTipicoService {
	
	public abstract ValorTipico guardarValor(ValorTipico valorTipico);
	public abstract List<ValorTipico> buscarTodo();
	public abstract void eliminarValorTipico(ValorTipico valorTipico);

}
