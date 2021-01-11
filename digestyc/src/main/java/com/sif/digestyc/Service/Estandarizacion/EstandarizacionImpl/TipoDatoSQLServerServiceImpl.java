package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.StandardizationModule.TipoDatoEstandar;
import com.sif.digestyc.Service.Estandarizacion.TipoDatoSQLServerService;

@Service("tipoDatoSQLServerImpl")
public class TipoDatoSQLServerServiceImpl implements TipoDatoSQLServerService{

	@Override
	public List<TipoDatoEstandar> listarTipoDeDatosEstandar() {
		return this.getTiposDatos();
	}


	public List<TipoDatoEstandar> getTiposDatos(){
		List<TipoDatoEstandar> listado =  new ArrayList<>();
		
		/*Enteros*/
		TipoDatoEstandar tBigInteger = new TipoDatoEstandar();
		tBigInteger.setNombre("bigint");
		tBigInteger.setNombreEtiqueta("bigint");
		tBigInteger.setGrupoDatos("enteros");
		listado.add(tBigInteger);
		
		TipoDatoEstandar tInteger = new TipoDatoEstandar();
		tInteger.setNombre("int");
		tInteger.setNombreEtiqueta("int");
		tInteger.setGrupoDatos("enteros");
		listado.add(tInteger);
		
		TipoDatoEstandar tSmallint = new TipoDatoEstandar();
		tSmallint.setNombre("smallint");
		tSmallint.setNombreEtiqueta("smallint");
		tSmallint.setGrupoDatos("enteros");	
		listado.add(tSmallint);

		TipoDatoEstandar tBit = new TipoDatoEstandar();
		tBit.setNombre("bit");
		tBit.setNombreEtiqueta("bit");
		tBit.setGrupoDatos("boleano");
		listado.add(tBit);

		TipoDatoEstandar tReal = new TipoDatoEstandar();
		tReal.setNombre("real");
		tReal.setNombreEtiqueta("real");
		tReal.setGrupoDatos("real");
		listado.add(tReal);
		
		TipoDatoEstandar tMoney = new TipoDatoEstandar();
		tMoney.setNombre("money");
		tMoney.setNombreEtiqueta("money");
		tMoney.setGrupoDatos("money");
		listado.add(tMoney);
		
		TipoDatoEstandar tSmallmoney = new TipoDatoEstandar();
		tSmallmoney.setNombre("smallmoney");
		tSmallmoney.setNombreEtiqueta("smallmoney");
		tSmallmoney.setGrupoDatos("money");
		listado.add(tSmallmoney);
		/*fin Enteros*/
		
		/*Con parametros*/
		
		TipoDatoEstandar tDecimal = new TipoDatoEstandar(); /*parametro (p,s) -- s: digitos despues del punto*/
		tDecimal.setNombre("decimal");
		tDecimal.setNombreEtiqueta("decimal(p,s)");
		tDecimal.setGrupoDatos("decimales");
		listado.add(tDecimal);

		TipoDatoEstandar tNumeric = new TipoDatoEstandar(); /*parametro (p,s)*/
		tNumeric.setNombre("numeric");
		tNumeric.setNombreEtiqueta("numeric(p,s)");
		tNumeric.setGrupoDatos("decimales");
		listado.add(tNumeric);

		TipoDatoEstandar tFloat = new TipoDatoEstandar(); /*parametro (n) -- default(53)*/
		tFloat.setNombre("float");
		tFloat.setNombreEtiqueta("float(n)");
		tFloat.setGrupoDatos("decimales");	
		listado.add(tFloat);
		/*Fin con parametros*/
		
		/*Fechas*/
		TipoDatoEstandar tDatetime = new TipoDatoEstandar(); 
		tDatetime.setNombre("datetime");
		tDatetime.setNombreEtiqueta("datetime");
		tDatetime.setGrupoDatos("fechas");	
		listado.add(tDatetime);

		TipoDatoEstandar tDatetime2 = new TipoDatoEstandar(); 
		tDatetime2.setNombre("datetime2");
		tDatetime2.setNombreEtiqueta("datetime2");
		tDatetime2.setGrupoDatos("fechas");
		listado.add(tDatetime2);

		TipoDatoEstandar tDate = new TipoDatoEstandar(); 
		tDate.setNombre("date");
		tDate.setNombreEtiqueta("date");
		tDate.setGrupoDatos("fechas");
		listado.add(tDate);

		TipoDatoEstandar tTime = new TipoDatoEstandar(); 
		tTime.setNombre("time");
		tTime.setNombreEtiqueta("time");
		tTime.setGrupoDatos("fechas");
		listado.add(tTime);
		
		TipoDatoEstandar tTimestamp = new TipoDatoEstandar(); 
		tTimestamp.setNombre("timestamp");
		tTimestamp.setNombreEtiqueta("timestamp");
		tTimestamp.setGrupoDatos("fechas");
		listado.add(tTimestamp);

		TipoDatoEstandar tSmalldatetime = new TipoDatoEstandar(); 
		tSmalldatetime.setNombre("smalldatetime");
		tSmalldatetime.setNombreEtiqueta("smalldatetime");
		tSmalldatetime.setGrupoDatos("fechas");
		listado.add(tSmalldatetime);
		
		/*Cadenas*/
		TipoDatoEstandar tChar = new TipoDatoEstandar(); /*parametro (n) -- longitud*/
		tChar.setNombre("char");
		tChar.setNombreEtiqueta("char(n)");
		tChar.setGrupoDatos("cadenas");
		listado.add(tChar);

		TipoDatoEstandar tVarchar = new TipoDatoEstandar(); /*parametro (n) -- longitud*/
		tVarchar.setNombre("varchar");
		tVarchar.setNombreEtiqueta("varchar(n)");
		tVarchar.setGrupoDatos("cadenas");
		listado.add(tVarchar);

		
		TipoDatoEstandar tText = new TipoDatoEstandar(); 
		tText.setNombre("text");
		tText.setNombreEtiqueta("text");
		tText.setGrupoDatos("cadenas");
		listado.add(tText);

		TipoDatoEstandar nChar = new TipoDatoEstandar(); 
		nChar.setNombre("nchar");
		nChar.setNombreEtiqueta("nchar(n)");
		nChar.setGrupoDatos("cadenas");
		listado.add(nChar);

		
		TipoDatoEstandar nVarchar = new TipoDatoEstandar(); 
		nVarchar.setNombre("nvarchar");
		nVarchar.setNombreEtiqueta("nvarchar(n)");
		nVarchar.setGrupoDatos("cadenas");
		listado.add(nVarchar);
		/*Fin Cadenas*/
		
		TipoDatoEstandar nBinary = new TipoDatoEstandar(); /*parametro (n) -- longitud*/
		nBinary.setNombre("binary");
		nBinary.setNombreEtiqueta("binary");
		nBinary.setGrupoDatos("binario");
		listado.add(nBinary);
		
		return listado;
	};
	
}
