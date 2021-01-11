package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;
import com.sif.digestyc.Repository.Estandarizacion.ColumnaCorrespondenciaRepository;
import com.sif.digestyc.Service.Estandarizacion.ColumnaCorrelacionService;

@Service("columnaCorrelacionServiceImpl")
public class ColumnaCorrelacionServiceImpl implements ColumnaCorrelacionService{
	
	@Autowired
	private ColumnaCorrespondenciaRepository columnaCorrespondenciaRepository;

	@Override
	public ColumnaCorrespondencia actualizar(ColumnaCorrespondencia columnaCorrespondencia) {
		return columnaCorrespondenciaRepository.save(columnaCorrespondencia);
	}

	@Override
	public List<ColumnaCorrespondencia> buscarTodas() {
		return columnaCorrespondenciaRepository.findAll();
	}

	@Override
	public ColumnaCorrespondencia buscarPorVersionColumna(long versionColumnaId) {
		return columnaCorrespondenciaRepository.findByVersionColumn(versionColumnaId);
	}

	@Override
	public Optional<ColumnaCorrespondencia> buscarPorId(long id) {
		return columnaCorrespondenciaRepository.findById(id);
	}
	
	
}
