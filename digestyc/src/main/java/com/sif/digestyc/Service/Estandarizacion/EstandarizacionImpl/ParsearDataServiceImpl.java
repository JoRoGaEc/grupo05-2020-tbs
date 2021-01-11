package com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Repository.Carga.ColumnaVersionPlantillaRepository;
import com.sif.digestyc.Service.Estandarizacion.ParsearDataService;

@Service("parsearDataServiceImpl")
public class ParsearDataServiceImpl implements ParsearDataService {
	private static Logger LOG = LoggerFactory.getLogger(ParsearDataServiceImpl.class);
	private static final String cadenas = "String";
	private static final String enteros = "Integer";
	private static final String numericos = "Double";
	private static final String decimales = "Decimal";
	private static final String fechas = "Date";
	private static final String boleanos = "Boolean";
	/* SQL server */
	private static final String cadenasSQLFijas = "char, nchar"; // De longitud fija
	private static final String cadenasSQLVar = "nvarchar, varchar"; // De longitud fija
	private static final String enterosSQL = "bigint, int, smallint";
	private static final String realSQL = "real";
	private static final String decimalesSQL = "decimal, numeric,float";
	private static final String boleanoSQL = "bit";
	private static final String moneySQL = "money, smallmoney";
	private static final String textSQL = "text";
	private static final String fechasSQL = "datetime, datetime2, date, time, timestamp, smalldatetime";
	// private static final String binarioSQL = "binary";

	@Autowired
	private ColumnaVersionPlantillaRepository columnaVersionRepository;
	@Autowired
	private CorrespondenciaServiceImpl correspondenciaService;
	/*
	 * tipos de datos en SQL Server a manejar: (enteros) bigint, int, smallint
	 * (boleano) bit (real) real (money) money, smallmoney (decimales) decimal,
	 * numeric,float (fechas) datetime, datetime2,date, time,timestamp,smalldatetime
	 * (cadenas) char(n), varchar(n), text, nchar(n), nvarchar(n) (binario) binary
	 */

	/*
	 * DATE - format YYYY-MM-DD DATETIME - format: YYYY-MM-DD HH:MI:SS SMALLDATETIME
	 * - format: YYYY-MM-DD HH:MI:SS TIMESTAMP - format: a unique number
	 */
	@Override
	@Transactional
	public String getDato(String dato, ColumnaVersionPlantilla columnaVersion) {
		Map<String, Object> mapInfoEstandares = new HashMap<>();
		Map<String, Object> mapInfoTransformaciones = new HashMap<>();
		Tipificacion tipificacion = new Tipificacion();
		String valor = "";
		String tDJava = columnaVersion.getTipificacion().getVariacionTipoDato().getTipoDato().getTipoDatoJava();
		mapInfoEstandares = recuperarInfoEstandares(dato, columnaVersion);
		Estandar estandar = new Estandar();
		ValorTipico t = null;
		tipificacion = columnaVersion.getTipificacion();
		Correspondencia correspondencia = null;
		estandar = (Estandar) mapInfoEstandares.get("estandar");
		if (mapInfoEstandares.containsKey("correspondencia")) {
			correspondencia = (Correspondencia) mapInfoEstandares.get("correspondencia");
		}
		if (mapInfoEstandares.containsKey("valorTipico")) {
			t = (ValorTipico) mapInfoEstandares.get("valorTipico");
		}
		String tipoDatoEstandar = estandar.getTipoDato().toLowerCase();
		if (tDJava != null) {
			if (cadenas.toLowerCase().contains(tDJava.toLowerCase())) {
				if (t != null) {
					if (correspondencia != null) {
						mapInfoTransformaciones = transformarSegunTipoDatoEstandar(tipoDatoEstandar, dato, estandar,
								correspondencia, t, null);
						if (mapInfoTransformaciones.containsKey("valor")) {
							valor = mapInfoTransformaciones.get("valor").toString();
						}
					}
				} else {
					valor = "'" + dato + "' ";
				}

			} else {
				if (enteros.toLowerCase().contains(tDJava.toLowerCase())
						|| numericos.toLowerCase().contains(tDJava.toLowerCase())
						|| decimales.toLowerCase().contains(tDJava.toLowerCase())
						|| boleanos.toLowerCase().contains(tDJava.toLowerCase())) {
					if (t != null) {
						mapInfoTransformaciones = transformarSegunTipoDatoEstandar(tipoDatoEstandar, dato, estandar,
								correspondencia, t, null);
						if (mapInfoTransformaciones.containsKey("valor")) {
							valor = mapInfoTransformaciones.get("valor").toString();
						}
					} else {
						valor = dato;
					}
				} else {
					if (fechas.toLowerCase().contains(tDJava.toLowerCase())) {
						if (t != null) {
							mapInfoTransformaciones = transformarSegunTipoDatoEstandar(tipoDatoEstandar, dato, estandar,
									correspondencia, t, tipificacion);
							if (mapInfoTransformaciones.containsKey("valor")) {
								valor = mapInfoTransformaciones.get("valor").toString();
							}
						} else {
							String datoParseado = convertirFechaParaSqlServer(dato, tipificacion, estandar);
							valor = "'" + datoParseado + "' ";
						}

					}
				}
			}

		}
		return valor;
	}

	public Map<String, Object> transformarSegunTipoDatoEstandar(String tipoDatoEstandar, String dato, Estandar estandar,
			Correspondencia correspondencia, ValorTipico t, Tipificacion tipificacion) {
		Map<String, Object> mapInfoTransformaciones = new HashMap<>();
		String nuevoValorEstandar = null;
		String valor = "";
		if (cadenasSQLFijas.toLowerCase().contains(tipoDatoEstandar)) {
			if (estandar.getLongitudN() != null) {
				Integer longitudN = estandar.getLongitudN();
				if (longitudN > dato.length()) {
					if (correspondencia.getValorTipicoEstandar().getValorTipico() != null) {
						nuevoValorEstandar = correspondencia.getValorTipicoEstandar().getValorTipico();
					}
					valor = t == null ? "'" + dato + "' "
							: (nuevoValorEstandar == null ? dato : "'" + nuevoValorEstandar + "' ");
					LOG.info("Pasando dato de " + (t != null ? t.getValor() : dato) + " a " + nuevoValorEstandar);
				} else {
					/* error ya que nchar y char son de tamaño fijo */
				}
			}
		} else if (cadenasSQLVar.toLowerCase().contains(tipoDatoEstandar)
				|| textSQL.toLowerCase().contains(tipoDatoEstandar)) {
			if (correspondencia.getValorTipicoEstandar().getValorTipico() != null) {
				nuevoValorEstandar = correspondencia.getValorTipicoEstandar().getValorTipico();
			}
			valor = t == null ? "'" + dato + "' "
					: (nuevoValorEstandar == null ? dato : "'" + nuevoValorEstandar + "' ");
		} else if (enterosSQL.contains(tipoDatoEstandar) || realSQL.contains(tipoDatoEstandar)
				|| decimalesSQL.contains(tipoDatoEstandar) || boleanoSQL.contains(tipoDatoEstandar)) {
			if (correspondencia.getValorTipicoEstandar().getValorTipico() != null) {
				nuevoValorEstandar = correspondencia.getValorTipicoEstandar().getValorTipico();
			}
			valor = t == null ? dato : (nuevoValorEstandar == null ? dato : nuevoValorEstandar);
		} else if (fechasSQL.toLowerCase().contains(tipoDatoEstandar)) {
			/* Funcion que transforme la fecha en un formato que se tenga en el estandar */
			valor = convertirFechaParaSqlServer(dato, tipificacion, estandar);

		}
		mapInfoTransformaciones.put("valor", valor);
		return mapInfoTransformaciones;
	}

	// public String convertirFechaParaSqlServer(String datoFecha, String ff, String
	// xx) {
	public String convertirFechaParaSqlServer(String datoFecha, Tipificacion tipificacion, Estandar estandar) {
		String datoFinal = "";
		if (datoFecha != null) {
			if (datoFecha.split("-").length == 3) {
				datoFinal = tipoFormatoFechaGuion(datoFecha, tipificacion, estandar);
			} else if (datoFecha.split("/").length == 3) {
				datoFinal = tipoFormatoFechaBarra(datoFecha, tipificacion, estandar);
			} else if (datoFecha.split("-").length == 1 && datoFecha.split("/").length == 1) {
				datoFinal = tipoFormatoSoloNumeros(datoFecha, tipificacion, estandar);
			}
		}
		return datoFinal;

	}

	public String tipoFormatoFechaGuion(String datoFecha, Tipificacion tipificacion, Estandar estandar) {
		String formatoFecha = tipificacion.getVariacionTipoDato().getFormatoFecha();

		String formatoEstandar = estandar.getTipoDato().toLowerCase();
		Integer inicioDia = formatoFecha.indexOf("d");
		Integer inicioMes = formatoFecha.indexOf("M");
		Integer inicioAnio = formatoFecha.indexOf("y");

		Integer posicionDia = 2;
		Integer posicionMes = 2;
		Integer posicionAnio = 2;

		if (inicioDia > inicioMes) {
			posicionMes--;
		} else {
			posicionDia--;
		}
		if (inicioDia > inicioAnio) {
			posicionAnio--;
		} else {
			posicionDia--;
		}
		if (posicionMes > posicionAnio) {
			posicionAnio--;
		} else {
			posicionMes--;
		}
		String[] partesFecha = datoFecha.trim().split("-");
		String dia = partesFecha[posicionDia];
		String mes = partesFecha[posicionMes];
		String anio = partesFecha[posicionAnio];

		String valorFecha = "";
		if (formatoFecha != null) {
			switch (formatoEstandar) {
			case "date":
				valorFecha = anio + "-" + mes + "-" + dia;
				break;
			case "datetime":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg();
				break;
			case "datetime2":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg() + "." + "0000000";
				break;
			case "smalldatetime":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg();
				break;
			case "time":
				/* No aplica */
				break;
			case "timestamp":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg() + "." + "0000000";
				break;
			default:
				break;
			}

		}
		return valorFecha;
	}

	public String tipoFormatoFechaBarra(String datoFecha, Tipificacion tipificacion, Estandar estandar) {
		String formatoFecha = tipificacion.getVariacionTipoDato().getFormatoFecha();

		String formatoEstandar = estandar.getTipoDato().toLowerCase();
		Integer inicioDia = formatoFecha.indexOf("d");
		Integer inicioMes = formatoFecha.indexOf("M");
		Integer inicioAnio = formatoFecha.indexOf("y");

		Integer posicionDia = 2;
		Integer posicionMes = 2;
		Integer posicionAnio = 2;

		if (inicioDia > inicioMes) {
			posicionMes--;
		} else {
			posicionDia--;
		}
		if (inicioDia > inicioAnio) {
			posicionAnio--;
		} else {
			posicionDia--;
		}
		if (posicionMes > posicionAnio) {
			posicionAnio--;
		} else {
			posicionMes--;
		}
		String[] partesFecha = datoFecha.trim().split("/");
		String dia = partesFecha[posicionDia];
		String mes = partesFecha[posicionMes];
		String anio = partesFecha[posicionAnio];

		String valorFecha = "";
		
		Date date = new GregorianCalendar(Integer.valueOf(anio), Integer.valueOf(mes) - 1, Integer.valueOf(dia)).getTime();
		DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFinal  = targetFormat.format(date);
		if (formatoFecha != null) {
			switch (formatoEstandar) {
			case "date":
				valorFecha = fechaFinal;
				break;
			case "datetime":
				valorFecha = fechaFinal + " " + getHorasMinSeg();
				break;
			case "datetime2":
				valorFecha = fechaFinal + " " + getHorasMinSeg() + "." + "0000000";
				break;
			case "smalldatetime":
				valorFecha = fechaFinal + " " + getHorasMinSeg();
				break;
			case "time":
				/* No aplica */
				break;
			case "timestamp":
				valorFecha = fechaFinal + " " + getHorasMinSeg() + "." + "0000000";
				break;
			default:
				break;
			}

		}
		return valorFecha;
	}

	public String tipoFormatoSoloNumeros(String datoFecha, Tipificacion tipificacion, Estandar estandar) {
		String formatoFecha = tipificacion.getVariacionTipoDato().getFormatoFecha();
		String formatoEstandar = estandar.getTipoDato().toLowerCase();
		Integer inicioDia = formatoFecha.indexOf("d");
		Integer inicioMes = formatoFecha.indexOf("M");
		Integer inicioAnio = formatoFecha.indexOf("y");

		String dia = datoFecha.substring(inicioDia, inicioDia + 1);
		String mes = datoFecha.substring(inicioMes, inicioMes + 1);
		String anio = datoFecha.substring(inicioAnio, inicioAnio + 3);

		String valorFecha = "";
		if (formatoFecha != null) {
			switch (formatoEstandar) {
			case "date":
				valorFecha = anio + "-" + mes + "-" + dia;
				break;
			case "datetime":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg();
				break;
			case "datetime2":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg() + "." + "0000000";
				break;
			case "smalldatetime":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg();
				break;
			case "time":
				/* No aplica */
				break;
			case "timestamp":
				valorFecha = anio + "-" + mes + "-" + dia + " " + getHorasMinSeg() + "." + "0000000";
				break;
			default:
				break;
			}

		}
		return valorFecha;
	}

	public String getHorasMinSeg() {
		Format f = new SimpleDateFormat("HH:mm:ss");
		String strResult = f.format(new Date());
		return strResult;
	}

	public Map<String, Object> recuperarInfoEstandares(String dato, ColumnaVersionPlantilla columnaVersion) {
		Map<String, Object> resultados = new HashMap<>();
		String tDJava = columnaVersion.getTipificacion().getVariacionTipoDato().getTipoDato().getTipoDatoJava()
				.toLowerCase();
		if (tDJava != null) {
			Estandar estandar = null;
			if (columnaVersion.getColumnaCorrespondencia() != null) {
				estandar = columnaVersion.getColumnaCorrespondencia().getEstandar();
				resultados.put("estandar", estandar);
				List<ValorTipico> valoresTipicos = columnaVersion.getTipificacion().getValorTipico();
				List<Correspondencia> correspondencias = new ArrayList<>();
				if (valoresTipicos != null) {
					if (estandar != null && !valoresTipicos.isEmpty()) {
						Optional<ValorTipico> v = valoresTipicos.stream().filter(
								value -> value.getValor() != null ? value.getValor().equalsIgnoreCase(dato) : false)
								.findFirst();
						if (v.isPresent()) {
							ValorTipico t = v.get();
							resultados.put("valorTipico", t);
							correspondencias = correspondenciaService.findAllByEstandar(estandar.getId());
							Optional<Correspondencia> correspondencia = correspondencias.stream()
									.filter(cor -> cor.getValorTipico().getValor().equals(t.getValor())).findFirst();
							if (correspondencia.isPresent()) {
								resultados.put("correspondencia", correspondencia.get());
							}
						}

					} else {
						resultados.put("sinEstandar", true);
					}
				} else {/* El dato no tiene valores tipicos */
					resultados.put("sinValoresTipicos", true);
				}
			}
		}

		return resultados;

	}

}
