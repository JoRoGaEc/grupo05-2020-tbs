package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.TablaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Repository.Validacion.DatoTablaRepository;
import com.sif.digestyc.Service.Estandarizacion.InsertarDatosEnTablaDinamicaService;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;

@Service("insertarDatosEnTablaDinamicaServiceImpl")
public class InsertarDatosEnTablaDinamicaServiceImpl implements InsertarDatosEnTablaDinamicaService{
	
	private static Logger LOG = LoggerFactory.getLogger(InsertarDatosEnTablaDinamicaServiceImpl.class);
	@Autowired
	private DatoTablaRepository datoTablaRepository;
		
	@Autowired
	private TablaCorrespondenciaServiceImpl tablaCorrespondenciaService;
	
	@Autowired
	private ParsearDataServiceImpl parsearDataService;
	
	@Override
	public Map<String, String> insertData(TablasDinamicas tablaDinamica) {
		return primeraForma(tablaDinamica);
	}	
	
	private Map<String, String> primeraForma(TablasDinamicas tablaDinamica) {
		TablaCorrespondencia t = tablaCorrespondenciaService.buscarPorTabla(tablaDinamica.getTablaId());
		List<ColumnaVersionPlantilla> columnas = t.getColumnaVersionPlantillas();
		int total = columnas.size()-1;
		StringBuilder start_insert = new StringBuilder("(tabla_id, entrega_id,");
		StringBuilder end_insert = new StringBuilder("("+tablaDinamica.getTablaId()+", "+tablaDinamica.getEntrega_id()+",");
		for(int i=0; i<total; i++) {
			start_insert.append(columnas.get(i).getPlantillaColumna().getCodigo()+", ");
			end_insert.append(parsearDataService.getDato(tablaDinamica.getValue(columnas.get(i).getPlantillaColumna().getCodigo()), columnas.get(i)) + ",");
		}
		start_insert.append(columnas.get(total).getPlantillaColumna().getCodigo()+ ") values ");;
		end_insert.append(parsearDataService.getDato(tablaDinamica.getValue(columnas.get(total).getPlantillaColumna().getCodigo()), columnas.get(total))+" );");
		return datoTablaRepository.insertData("insert into "+t.getNombreTablaCorrespondiente()+" "+start_insert.toString()+end_insert.toString());
	}

}







