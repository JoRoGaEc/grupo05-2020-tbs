package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sif.digestyc.Service.Estandarizacion.TablaConTipoDatoDinamicaService;
import com.sif.digestyc.utilities.ConexionSqlServer;

@Service("tablaConTipoDatoDinamicaServiceImpl")
public class TablaConTipoDatoDinamicaServiceImpl extends ConexionSqlServer implements TablaConTipoDatoDinamicaService{

	@Override
	public boolean crearTabla(String tabla, Map<String, String> columnas) {
		return this.crearTablaSiNoExiste(tabla, columnas);
	}

	@Override
	public boolean addCoumnaTabla(String tabla, Map<String, String> columnas) {
		return this.addColumn(tabla, columnas);
	}

}
