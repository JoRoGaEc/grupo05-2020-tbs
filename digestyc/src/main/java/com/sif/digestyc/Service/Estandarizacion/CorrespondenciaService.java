package com.sif.digestyc.Service.Estandarizacion;
import java.util.List;
import java.util.Map;

import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
public interface CorrespondenciaService {

	public Map<String,Object> guardarCorrespondencias(List<Map<String, Object>> datos);

	public List<Correspondencia> findAllByEstandar(Long id);

}
