package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Repository.Estandarizacion.TablaCorrespondenciaRepository;
import com.sif.digestyc.Service.Estandarizacion.TablaCorrespondenciaService;

@Service("tablaCorrelacionServiceImpl")
public class TablaCorrespondenciaServiceImpl implements TablaCorrespondenciaService {
	
	@Autowired
	private TablaCorrespondenciaRepository tablaCorrespondenciaRepository;

	@Override
	public TablaCorrespondencia actualizar(TablaCorrespondencia tabla) {
		return tablaCorrespondenciaRepository.save(tabla);
	}

	@Override
	public Optional<TablaCorrespondencia> encontrarPorId(Long id) {
		return tablaCorrespondenciaRepository.findById(id);
	}

	@Override
	public List<TablaCorrespondencia> buscarTodo() {
		return tablaCorrespondenciaRepository.findAll();
	}

	@Override
	public TablaCorrespondencia buscarPorTabla(long tablaId) {
		return tablaCorrespondenciaRepository.findByTabla(tablaId);
	}

}
