package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;

public interface TipificacionService {
	
	public abstract Tipificacion buscarPorId(int id);
	public abstract List<Tipificacion> buscarTodo();
	public abstract Tipificacion actualizarTipificacion(Tipificacion tipificacion);
	public abstract void eliminarTipificacion(Tipificacion tipificacion);
	public abstract List<Tipificacion> buscarPorInstitucion(int id);
	public abstract List<Tipificacion> buscarTodoMenos(int id);
	public abstract boolean esRangoValido(int rango, String valores, VariacionTipoDato dato);
	public abstract boolean esRangoNumeroValido(int rango, String primerNumero, String segundoNumero, VariacionTipoDato dato);
	public abstract boolean esRangoFechaValido(int rango, String primeraFecha, String segundaFecha, VariacionTipoDato dato);
	public abstract Optional<Tipificacion> buscarPorInstitucion(Long institucion, String nombre);
		

}
