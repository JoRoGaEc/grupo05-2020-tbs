package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Repository.Carga.ColumnaVersionPlantillaRepository;
import com.sif.digestyc.Service.Load.ColumnaVersionPlantillaService;

@Service("columnaVersionPlantillaServiceImpl")
public class ColumnaVersionPlantillaServiceImpl implements ColumnaVersionPlantillaService{

	@Autowired
	private ColumnaVersionPlantillaRepository columnaVersionPlantillaRepository;
	
	@Override
	@Transactional
	public Optional<ColumnaVersionPlantilla> buscarVersionColumna(Long id) {
		return columnaVersionPlantillaRepository.findById(id);
	}

	@Override
	@Transactional
	public void actualizar(ColumnaVersionPlantilla colVerPlantilla) {
		columnaVersionPlantillaRepository.save(colVerPlantilla);
		
	}

	@Override
	@Transactional
	public void eliminarColumnaVersion(Long id) {
		columnaVersionPlantillaRepository.deleteById(id);
		
	}

	@Override
	public List<ColumnaVersionPlantilla> buscarColumnasRegistro(Long id) {
		return columnaVersionPlantillaRepository.findVersionColumnByIdRegister(id);
	}

	@Override
	public List<ColumnaVersionPlantilla> buscarTodas() {
		return IterableUtils.toList(columnaVersionPlantillaRepository.findAll());
	}

}
