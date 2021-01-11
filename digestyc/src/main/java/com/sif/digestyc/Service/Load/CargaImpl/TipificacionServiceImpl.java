package com.sif.digestyc.Service.Load.CargaImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Repository.Carga.TipificacionRepository;
import com.sif.digestyc.Service.Load.TipificacionService;

@Service("tipificacionServiceImpl")
public class TipificacionServiceImpl implements TipificacionService {
	
	@Autowired
	@Qualifier("tipificacionRepository")
	private TipificacionRepository tipificacionRepository;

	private int NUMERO = 1;
	private int VARCHAR = 2;
	private int FECHA = 3;

	@Override
	@Transactional
	public Tipificacion buscarPorId(int id) {
		return tipificacionRepository.findById(id);
	}

	@Override
	@Transactional
	public List<Tipificacion> buscarTodo() {
		return tipificacionRepository.findAll();
	}

	@Override
	@Transactional
	public Tipificacion actualizarTipificacion(Tipificacion tipificacion) {
		return tipificacionRepository.save(tipificacion);
	}


	@Override
	@Transactional
	public List<Tipificacion> buscarPorInstitucion(int id) {
		return tipificacionRepository.findByInstitucion(id);
	}
	
	@Override
	@Transactional
	public Optional<Tipificacion> buscarPorInstitucion(Long institucion, String nombre) {
		return tipificacionRepository.findByInstitucion(institucion, nombre);
	}

	@Override
	@Transactional
	public void eliminarTipificacion(Tipificacion tipificacion) {
		tipificacionRepository.delete(tipificacion);
	}

	@Override
	@Transactional
	public List<Tipificacion> buscarTodoMenos(int id) {
		return tipificacionRepository.findAllLessId(id);
	}

	@Override
	@Transactional
	public boolean esRangoValido(int rango, String valores, VariacionTipoDato dato) {
		boolean retorno = true;
		String string = "VARCHAR, TEXTO, NVARCHAR, STRING";
		if(valores.isEmpty() || dato.getTipoDato()==null || !string.contains(dato.getTipoDato().getTipoDatoJava().trim().toUpperCase()) || rango != VARCHAR) {
			retorno = false;
		}else {
			int l = dato.getLongitudCadena();
			String todos[] = valores.split(",");
			for(String v : todos) {
				if(v.length()>l) {
					retorno = false;
				}
			}
		}
		return retorno;
	}

	@Override
	@Transactional
	public boolean esRangoNumeroValido(int rango, String primerNumero, String segundoNumero, VariacionTipoDato dato) {
		String numeros = "INTEGER, REAL, FLOAT, DECIMAL, MONEY, DOUBLE";
		boolean retorno = false;
		if(rango == NUMERO && dato.getTipoDato() != null && numeros.contains(dato.getTipoDato().getTipoDatoJava().trim().toUpperCase()) && !primerNumero.isEmpty() && !segundoNumero.isEmpty()) {
			try {
				double numero1 = Double.parseDouble(primerNumero);
				double numero2 = Double.parseDouble(segundoNumero);
				if(numero2 > numero1) {
					retorno = true;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retorno;
	}

	@Override
	@Transactional
	public boolean esRangoFechaValido(int rango, String primeraFecha, String segundaFecha, VariacionTipoDato dato) {
		SimpleDateFormat formatos = new SimpleDateFormat("yyyy-MM-dd");
		boolean retorno = false;
		String fechas = "DATETIME, DATE, FECHA";
		if (rango == FECHA && dato.getTipoDato() != null && fechas.contains(dato.getTipoDato().getTipoDatoJava().trim().toUpperCase()) && !primeraFecha.isEmpty() && !segundaFecha.isEmpty()) {
			try {
				Date fecha_desde = formatos.parse(primeraFecha);
				Date fecha_hasta = formatos.parse(segundaFecha);
				if(fecha_desde.getTime() < fecha_hasta.getTime()) {
					retorno = true;
				}else {
					System.out.println("FECHA DESDE MAYOR A FECHA HASTA");
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("EXCEPCION");
			}
		}else {
			System.out.println("NO ENTRO AL TIPO DE DATO");
		}
		return retorno;
	}


}
