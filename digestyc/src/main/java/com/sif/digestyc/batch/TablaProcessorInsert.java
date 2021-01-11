package com.sif.digestyc.batch;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.InsertarDatosEnTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.TablaCorrespondenciaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.DatoTablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ValidationServiceImpl;

public class TablaProcessorInsert implements ItemProcessor<TablasDinamicas, TablasDinamicas>{
	
	private static Logger LOG = LoggerFactory.getLogger(TablaProcessor.class);
	
	@Autowired
	private InsertarDatosEnTablaDinamicaServiceImpl insertarDatosEnTablaDinamicaService;
	
	@Autowired
	private TablaCorrespondenciaServiceImpl tablaCorrespondenciaService;
	
	@Autowired
	private DatoTablaServiceImpl datoTablaService;
	
	@Autowired
	private TablaServiceImpl tablaService;
	
	@Autowired
	private ErrorTablaDinamicaServiceImpl errorService;
	
	
	@Override
	public TablasDinamicas process(TablasDinamicas item) throws Exception {
		//Aqui estaria toda la logica para insertar los datos en la otra tabla		
		Map<String, String> dataInserted = insertarDatosEnTablaDinamicaService.insertData(item);
		if(dataInserted.get("exito").equals("-1")) {
			LOG.error("Error, no se puede insertar id = "+item.getId()+" "+item.getDataMap());
			TablaCorrespondencia tabla = tablaCorrespondenciaService.buscarPorTabla(item.getTablaId());
			if(tabla!=null) {
				tabla.setError();
				tablaCorrespondenciaService.actualizar(tabla);
				ErrorTablaDinamica error = new ErrorTablaDinamica(item.getId(), dataInserted.get("error"), item.getDataMap(), "Unknow", tabla);
				errorService.actualizar(error);
			}
		}else {
			Optional<Tabla> t = tablaService.buscar(item.getTablaId());
			if(t.isPresent()) {
				datoTablaService.deleteFromTabla(t.get().getNombre(), item.getId());
			}
		}
		return null;
	}
	
	
	
}
