package com.sif.digestyc.Controller.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManipularDate {
	
	public static Date sumarDias(Date fecha, int sumarDias) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(fecha); //https://mkyong.com/java/java-how-to-add-days-to-current-date/
    	c.add(Calendar.DAY_OF_MONTH, sumarDias); //https://beginnersbook.com/2017/10/java-add-days-to-date/
    	Date fechaNueva  =c.getTime();
     return fechaNueva;
	}
	
	public static Date sumarDias(String fecha, int sumarDias) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd"); //aca hay insconsistencia ya que en la vista tiene barras
		Calendar c =  Calendar.getInstance();
		Date fechaRetorno=null;
		try {
			c.setTime(formateador.parse(fecha));
			c.add(Calendar.DAY_OF_MONTH, sumarDias);
			fechaRetorno= c.getTime();
		} catch (ParseException e) {

			e.printStackTrace(System.out);
		}
		return fechaRetorno;		
	}
	
	public static Date formatearFecha(String fecha) {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd"); //aca hay insconsistencia ya que en la vista tiene barras
		Calendar c =  Calendar.getInstance();
		Date fechaRetorno=null;
		try {
			c.setTime(formateador.parse(fecha));
			fechaRetorno= c.getTime();
		} catch (ParseException e) {

			e.printStackTrace(System.out);
		}
		return fechaRetorno;		
	}

}
