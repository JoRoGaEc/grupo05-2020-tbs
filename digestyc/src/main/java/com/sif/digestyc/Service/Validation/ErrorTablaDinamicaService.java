package com.sif.digestyc.Service.Validation;

import java.util.List;

import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;

public interface ErrorTablaDinamicaService {
	

	abstract void delete(List<ErrorTablaDinamica> errorsIdDelete);

	abstract List<ErrorTablaDinamica> obtenerErroresDeTabla(Long id, int i, int j);

	abstract long contarErrores(long id);

	public abstract ErrorTablaDinamica actualizar(ErrorTablaDinamica error);
	
}
