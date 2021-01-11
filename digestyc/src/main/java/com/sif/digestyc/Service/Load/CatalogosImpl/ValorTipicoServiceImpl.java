package com.sif.digestyc.Service.Load.CatalogosImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Repository.Catalogos.ValorTipicoRepository;
import com.sif.digestyc.Service.Load.ValorTipicoService;

@Service("valorTipicoServiceImpl")
public class ValorTipicoServiceImpl implements ValorTipicoService{
	
	@Autowired
	private ValorTipicoRepository valorTipicoRepository;

	@Override
	public ValorTipico guardarValor(ValorTipico valorTipico) {
		return valorTipicoRepository.save(valorTipico);
	}

	@Override
	public List<ValorTipico> buscarTodo() {
		return valorTipicoRepository.findAll();
	}

	@Override
	public void eliminarValorTipico(ValorTipico valorTipico) {
		valorTipicoRepository.delete(valorTipico);
	}

}
