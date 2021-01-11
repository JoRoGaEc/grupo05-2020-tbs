package com.sif.digestyc.Service.Validation.ValidationImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Repository.Carga.EntregaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Repository.Carga.VersionPlantillaRepository;
import com.sif.digestyc.Repository.Validacion.ErrorTablaDinamicaRepository;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationService;

@Service("validationServiceImpl")
public class ValidationServiceImpl implements ValidationService{

	private static Logger LOG = LoggerFactory.getLogger(ValidationServiceImpl.class);

	private static final String cadenas = "string varchar char character";
	private static final String enteros = "integer int long";
	private static final String flotante = "float double numeric decimal";
	private static final String fecha = " datetime calendar date ";
	
	@Autowired
	ErrorTablaDinamicaRepository errorRepository;
	
	@Autowired
	EntregaRepository entregaRepository;
	
	@Autowired
	TablaServiceImpl tablaService;
	
	@Autowired
	VersionPlantillaRepository versionPlanilla;
	
	@Override
	public ErrorTablaDinamica validate(TablasDinamicas tabla) {
		ErrorTablaDinamica error = null;
		Entrega entrega = entregaRepository.buscarEntrega(tabla.getEntrega_id());
		if(entrega != null && entrega.getVersionPlantilla() != null && entrega.getRegistro() !=null) {
			
			Optional<Tabla> t = tablaService.buscar(tabla.getTablaId());
			LOG.info("Buscando la tala por el id = "+tabla.getTablaId());
			Map<String, String> columnasMalas = validar(tabla, entrega.getVersionPlantilla().getVersionesColumna());
			if(!columnasMalas.isEmpty() && t.isPresent()) {
				Iterator<String> iterador = columnasMalas.keySet().iterator();
				while(iterador.hasNext()) {
					String key = iterador.next(); // Columna
					String dato = columnasMalas.get(key); //Tipo de dato				
					error = new ErrorTablaDinamica(t.get(), tabla.getId(), key, tabla.getDataMap(), dato);
					error = errorRepository.save(error);
				}
			}
		}
		return error;
	}
 
	private Map<String, String> validar(TablasDinamicas tabla, List<ColumnaVersionPlantilla> versionesColumna) {
		Map<String, String> col = new HashMap<String, String>();

		boolean retorno = true;
		Iterator<String> iterador = tabla.getIterator();
		
		try{
		while(iterador.hasNext()) {
			
			String key = iterador.next(); // Columna
			String dato = tabla.getValue(key); //valor de esa columna
			//System.out.println("dato: "+dato);
			Optional<ColumnaVersionPlantilla> columnaOptional = versionesColumna.stream().filter(c -> c.getPlantillaColumna().getCodigo().equalsIgnoreCase(key)).findFirst(); //obtengo la columna con ese nombre
			/* AQUI VALIDAR ESTA PARTE PARA QUE NO VAYA A DAR UNA EXCEPCION POR FA*/
			
			if(columnaOptional.isPresent()) {
				ColumnaVersionPlantilla columna = columnaOptional.get();
				if(columna.getTipificacion().getVariacionTipoDato().getTipoDato().getTipoDatoJava() != null) {
					String tDJava = columna.getTipificacion().getVariacionTipoDato().getTipoDato().getTipoDatoJava();
					List<ValorTipico> valorTipico = columna.getTipificacion().getValorTipico();
					float inicio=0;
					float fin=0;
					Date inicioFecha=null;
					Date finFecha = null;
					int vacio=0;

					int largoCadena=0;
					String valores = "#";
					
					 largoCadena=columna.getTipificacion().getVariacionTipoDato().getLongitudCadena();

					String v;

					//String valores = "";

					if(valorTipico.isEmpty())
					{
						 vacio=1;
					}

					if(vacio==0)
					{
					ValorTipico vt=valorTipico.get(0);
					int rango=vt.getExisteRango();
					
					if(rango==1) // rango 
					{
						inicio=vt.getInicioRango();
						fin=vt.getFinRango();
					}
					else
					{
						if(rango==2) // valores especificos
						{
							
							//System.out.println("--ENTRA VALORES ESPECIFICOS---");
							
							
							for(ValorTipico val: valorTipico)
							{
								
								v=val.getValor().toUpperCase();
								System.out.println("VAL: "+v);
								valores=valores+",";
								valores=valores+v;
								valores=valores+",";
								System.out.println("VALORES: "+valores);
							}
							

						}
						else // por defecto 3 , Fecha
						{
							inicioFecha=vt.getFechaInicio();
							finFecha=vt.getFechaFin();
						}
					}
					}
					
					

					if(tDJava != null) {
						if(cadenas.contains(tDJava.toLowerCase())) {
							//es una cadena
							System.out.println("-----");
							System.out.println("cadena:  "+dato);
							retorno = isString(dato,valores, largoCadena,columna.getTipificacion().isEsNulo());
							
						}else {
							if(enteros.contains(tDJava.toLowerCase())) {
								//Es entero
								System.out.println("-----");
								System.out.println("entero: "+dato);
								retorno = isInteger(dato,inicio,fin,columna.getTipificacion().isEsNulo());
							}else {
								if(flotante.contains(tDJava.toLowerCase())) {
									//Es flotante
									System.out.println("-----");
									System.out.println("flotante: "+dato);
									retorno = isFloatOrDouble(dato,inicio,fin, columna.getTipificacion().isEsNulo());
								}else {
									if(fecha.contains(tDJava.toLowerCase())) {
										//Caso contrario es fecha	
										System.out.println("-----");
										System.out.println("fecha: "+dato);
										retorno = isDate(dato,inicioFecha,finFecha,columna.getTipificacion().getVariacionTipoDato().getFormatoFecha(), columna.getTipificacion().isEsNulo());
									}
								}
							}
						}
						if(!retorno) {
							String mensaje="";
							for(ValorTipico vt :columna.getTipificacion().getValorTipico()) {
								mensaje+= vt.getInicioRango()< vt.getFinRango() ? "Valor entre "+vt.getInicioRango() + "-" + vt.getFinRango():
									"";
								if(vt.getFechaInicio()!=null && vt.getFechaFin()!=null && columna.getTipificacion().getVariacionTipoDato()!=null) {
									try {
										SimpleDateFormat formatter = new SimpleDateFormat(columna.getTipificacion().getVariacionTipoDato().getFormatoFecha()!=null && columna.getTipificacion().getVariacionTipoDato().getFormatoFecha().isEmpty() ? columna.getTipificacion().getVariacionTipoDato().getFormatoFecha():"dd-MM-yyyy");
									    mensaje+= "Fecha entre :\n"+ formatter.format(vt.getFechaInicio()) + " - " + formatter.format(vt.getFechaFin())+"\n";
										
									}catch(Exception e){
										mensaje+="Formato o fecha inválida";
									}
								}
								
								mensaje+=vt.getValor()!=null && !vt.getValor().isEmpty()? vt.getValor()+ "|\n":"";
								
							}

							if(cadenas.contains(tDJava.toLowerCase()) && columna.getTipificacion().getVariacionTipoDato()!=null) {
								try {
									mensaje+= "Longitud menor de :\n"+ largoCadena+"\n";								
								}catch(Exception e){
									mensaje+="Longitud de caracteres inválida";
								}
							}
							
							if(fecha.contains(tDJava.toLowerCase()) && columna.getTipificacion().getVariacionTipoDato().getFormatoFecha()!=null) {
								try {
									mensaje+= "Con formato:\n"+ columna.getTipificacion().getVariacionTipoDato().getFormatoFecha()+"\n";								
								}catch(Exception e){
									mensaje+="Formato o fecha inválida";
								}
							}
							col.put(columna.getPlantillaColumna().getCodigo(), tDJava+"\n"+ mensaje);
							retorno = true; //para seguir contando las columnas
						}
						
					}
				}
			}//-----------
			
			
		}
		}
		catch(Exception e){
			System.out.println("Something went wrong." + e);
		}

		
		return col;
	}
	
	
	
	
	public boolean isInteger(String value,float inicio,float fin, boolean nulo) {
		System.out.println("value int |"+value+"|" );
		boolean b =(value==null||value=="") ;
		System.out.println(b);
		boolean retorno = false; 
		if(b==false) {
		if(value.matches("^[-]{1}[0-9]+|[0-9]{1,}")) {
			
		/*	if(value.isEmpty()==false) 
			{*/
				
				if(fin!=0) {
					if(Integer.parseInt(value) >= inicio && Integer.parseInt(value)<= fin)
					{
						retorno = true;
						
					}
					else
					{
						retorno = false;
						
					}
				}
				else
				{
					retorno = true;
				}//}
				
		}	
		}else {
			System.out.println("entra false" );
			System.out.println(nulo&&value==null );
			if(nulo&&(value==null||value==""))
			{
				System.out.println("entra true" );
				return true;
			}
			else
			{	System.out.println("entra false x2" );
				return false;
			}

		}
		System.out.println("salto" );
		return retorno;
	}
	
	public boolean isFloatOrDouble(String value, float inicio,float fin,boolean nulo) {
		boolean retorno = false; 
		boolean b =(value==null||value=="") ;
		if(b==false) {
		if(value.matches("^([-]{1}[0-9]+|[0-9]{1,})[.]{0,1}[0-9]*")) {
			
			/*if(value.isEmpty()==false) 
			{*/
				float val=Float.parseFloat(value);
				if(inicio!=fin) {
					boolean be=val>=inicio &&val<=fin;
					System.out.println("mayor" + be);
					
					
				if(be)
				{
				retorno = true;
				}
				else
				{
					retorno = false;
				}
				}
				
				else
				{
					retorno = true;
				}
			}
			
			else
			{	
				if(value==null&&nulo)
				{
					retorno = true;
				}
				else
				{
					retorno = false;
				}
				
				
			}
		}
		
	else {
			
			if(nulo&&(value==null||value==""))
			{
				retorno = true;
			}
			else
			{
				retorno = false;
			}
			
		}
		return retorno;
	}
	
	public boolean isDate(String date, Date inicio,Date fin,String format, boolean nulo) {
		boolean retorno = false;
		SimpleDateFormat  formato;

		if(date!=null&&date!="") {
			
			format=format.toLowerCase();
			
			if(format.contains("m")) {
				format.replace("y", "M");
				
			}
			
			if(format.contains(":MM:")) {
				format.replace(":MM:", ":mm:");
				
			}
			
			if(format.contains("h")) {
				format.replace("h", "H");
				
			}
			
			System.out.println("DATE FORMATO "+ format);
			SimpleDateFormat formatos = null;
			if( format.isEmpty()==false && format.length()>6) {
				  formato = new SimpleDateFormat(format);
				  System.out.println("1");
			}else {
				  formato = new SimpleDateFormat("ddMMyyyy");
				  System.out.println("2");
			}
			try {
				Date f=formato.parse(date);
				System.out.println("f "+f);
				String fecha = formato.format(f);
				
				
				System.out.println("date "+ date +" fecha " + fecha);
				if (date.contains(fecha) ) // se comparan en STRING para comprobar los formatos
				{
					System.out.println("FORMATOS IGUALES");
					
					if (inicio != null && fin != null) {
						if ((f.after(inicio) && f.before(fin)) || date.contains( formato.format(inicio)) || date.contains( formato.format(fin))||nulo) {

							retorno = true;// Si tiene el formato y rango correcto

						}
						else
						{
							retorno = false; // no esta en el rango de fecha
						}
					} 
					else {
						retorno = true; // Si tiene el formato correcto y no hay rango definido 
					}
				} else {
					retorno = false; // No tiene el formato correcto
				}

				
			}
			catch (Exception e) {
				//System.out.println(e.getMessage());
				retorno = false;
			}
			
		}else {
			if(nulo)
			{
				retorno = true;
			}
			else
			{
				retorno = false;
			}
			retorno = nulo;
		}
		return retorno;
	}
	

	public boolean isString(String cadena, String valores,int largoCadena, boolean nulo) {
		System.out.println("--isString---");
		boolean retorno = false;
		//System.out.println("1");
		if(cadena != null&&cadena !="") {
			//System.out.println("2");
			retorno = cadena.length()>0; //&& cadena.matches("([._]*[a-zA-Z0-9]*[._+*]*)*"); //por si queremos que concuerde con algun patron
			//System.out.println("3");
			System.out.println(cadena.isEmpty());
			if(cadena.isEmpty()==false) 
				{
				System.out.println("4 valores: |"+valores+"|");
				//System.out.println("ADENTRO DE FUNCION");
				//System.out.println("valores:"+valores);
			
					if(valores=="#")
					{
						
						if(cadena.length()<=largoCadena)
						{System.out.println("si");

							retorno = true;	
						}
						else
						{
							retorno = false;	
						}
						
					}
					else
					{
						String cadena2=",";
						
						cadena2=cadena2+cadena.toUpperCase();
						cadena2=cadena2+",";
						//System.out.println("cadena2: "+cadena2);
						
						if(valores.contains(cadena2)&&cadena.length()<=largoCadena)
						{
							retorno = true;		// Si esta en los valores especificos 
						}
						else
						{
							retorno = false;	// No esta en los valores especificos 
						}
					}
				
					//retorno = true;	

					
				}
		}else {
			//System.out.println("5");
			retorno = nulo;
		}
		//System.out.println("6");
		return retorno;
	}
	
	public Date getDate(String date, String format) {
		Date retorno = null;
		SimpleDateFormat formatos = null;
		if(format!=null && !format.isEmpty() && format.length()>6) {
			formatos = new SimpleDateFormat(format);
		}else {
			formatos = new SimpleDateFormat("yyyyMMdd");
		}
		
		try {
			retorno = formatos.parse(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	
}
