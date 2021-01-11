package com.sif.digestyc.Service.Validation;

import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;

public interface ValidationService {

	public abstract ErrorTablaDinamica validate(TablasDinamicas tabla);
	
}
