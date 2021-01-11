package com.sif.digestyc.Service.Validation.ValidationImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Repository.Validacion.ErrorTablaDinamicaRepository;
import com.sif.digestyc.Service.Validation.ErrorTablaDinamicaService;

@Service("errorTablaDinamicaServiceImpl")
public class ErrorTablaDinamicaServiceImpl implements ErrorTablaDinamicaService{

	@Autowired
	private ErrorTablaDinamicaRepository errorRepository;
	

	@Override
	public void delete(List<ErrorTablaDinamica> errores) {
		errorRepository.deleteAll(errores);
	}
	
	@Override
	public List<ErrorTablaDinamica> obtenerErroresDeTabla(Long id, int i, int j) {
		return errorRepository.findByTabla(id, i, j);
	}
	
	@Override
	public long contarErrores(long id) {
		return errorRepository.countError(id);
	}

	@Override
	public ErrorTablaDinamica actualizar(ErrorTablaDinamica error) {
		return errorRepository.save(error);
	}

}
