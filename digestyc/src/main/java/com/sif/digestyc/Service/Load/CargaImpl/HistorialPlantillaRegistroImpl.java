package com.sif.digestyc.Service.Load.CargaImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.sif.digestyc.Repository.Carga.HistorialPlantillaRegistroRepository;
import com.sif.digestyc.Service.Load.HistorialPlantillaRegistroService;


@Service("historialplantillaregistroServiceImpl")
public class HistorialPlantillaRegistroImpl implements HistorialPlantillaRegistroService{
	
	@Autowired
	@Qualifier("historialplantillaregistroRepository")
	private HistorialPlantillaRegistroRepository historialplantillaregistroRepository;

	@Override
	public void PlantillaRegistro(Long registro_id,Long plantilla_id) {
		historialplantillaregistroRepository.PlantillaRegistro(registro_id,plantilla_id);
	}

}
