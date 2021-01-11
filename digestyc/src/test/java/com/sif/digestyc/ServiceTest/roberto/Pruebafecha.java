package com.sif.digestyc.ServiceTest.roberto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pruebafecha {

	public static void main(String[] args) {
		String formato  ="dd-MM-yyyy";
		String dato = "1-1-20";
		formatoFecha(dato, formato);

	}

	
	public static void formatoFecha(String dato, String formato) {
   	    SimpleDateFormat formatter1=new SimpleDateFormat(formato);
   	    String fecha ="";
   	    try {
		   fecha  = formatter1.parse(dato).toString();
		} catch (ParseException e) {

		}

   	   System.out.print("Fecha" + fecha);
	}
}
