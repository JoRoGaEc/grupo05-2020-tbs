package com.sif.digestyc.Service.Load;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Registro;

public interface InstitucionService {
	
	//CRUD
	public abstract Optional<Institucion> buscarInstitucionPorId(Institucion institucion); //Read
	public abstract List<Institucion> listarInstituciones();    //Read
	public abstract Institucion agregarInstitucion(Institucion institucion); //Create
	public abstract void eliminarInstitucion (Long id);  //Delete
	public abstract Institucion actualizadInstitucion(Institucion institucion); //Actualizar
	
	public abstract Institucion buscarInstitucionPorId(Long id);	
	//OTROS METODOS
	public List<Registro> registroAdministraticoByInstitucion(Long id, Long idTipo);
	
}
