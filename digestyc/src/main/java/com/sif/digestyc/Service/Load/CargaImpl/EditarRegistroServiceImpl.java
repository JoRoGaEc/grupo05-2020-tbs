package com.sif.digestyc.Service.Load.CargaImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Repository.Carga.EditarRegistroRepository;
import com.sif.digestyc.Service.Load.EditarRegistroService;

@Service("editarRegistroService")
public class EditarRegistroServiceImpl implements EditarRegistroService{
	
	@Autowired
	@Qualifier("editarRegistroRepository")
	private EditarRegistroRepository editarRegistroRepository;
	
	
	@Override
	@Transactional
	public Registro findById(Long id) {
		return editarRegistroRepository.findByIdSql(id);
	}

	@Override
	@Transactional
	public void updateRegistro(Registro registro) {
		editarRegistroRepository.save(registro);
		
	}
	

}
