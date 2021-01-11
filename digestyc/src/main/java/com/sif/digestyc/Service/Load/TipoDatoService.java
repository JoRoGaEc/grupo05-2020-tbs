package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import com.sif.digestyc.Entity.LoadModule.TipoDato;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;


public interface TipoDatoService {

	//CRUD
	public abstract Optional<TipoDato> buscarTipoDatoPorId(TipoDato tipoDato); //Read
	public abstract List<TipoDato> listarTipoDato();    //Read
	public abstract TipoDato agregarTipoDato(TipoDato tipoDato); //Create
	public abstract void eliminarTipoDato (Long id);  //Delete
	public abstract TipoDato actualizadTipoDato(TipoDato tipoDato); //Actualizar
	
	public abstract TipoDato  buscarTipoDatoPorId(Long id);	
	//OTROS METODOS
}
