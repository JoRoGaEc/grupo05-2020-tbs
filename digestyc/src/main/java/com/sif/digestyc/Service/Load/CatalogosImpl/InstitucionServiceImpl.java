package com.sif.digestyc.Service.Load.CatalogosImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Repository.Catalogos.InstitucionRepository;
import com.sif.digestyc.Service.Load.InstitucionService;

@Service("institucionServiceImpl")
public class InstitucionServiceImpl implements InstitucionService{

	@Autowired
	@Qualifier("institucionRepository")
	private InstitucionRepository institucionRepository;

	@Override
	public Optional<Institucion> buscarInstitucionPorId(Institucion institucion) {
		return (Optional<Institucion>) institucionRepository.findById(institucion.getId());
	}

	@Override
	public List<Institucion> listarInstituciones() {
		return (List<Institucion>)institucionRepository.findAll(); //devuelve un Iterable por eso lo casteamos
	}

	@Override
	public Institucion agregarInstitucion(Institucion institucion) {
		return institucionRepository.save(institucion);
		
	}

	@Override
	public void eliminarInstitucion(Long id) {
		institucionRepository.deleteById(id);
		
	}

	@Override
	public Institucion actualizadInstitucion(Institucion institucion) {
		return institucionRepository.save(institucion);
	}

	@Override
	public Institucion buscarInstitucionPorId(Long id) {		
		return institucionRepository.findByIdSql(id);
	}

	@Override
	public List<Registro> registroAdministraticoByInstitucion(Long id, Long idTipo) {	
		return (List<Registro>) institucionRepository.registroAdministraticoByInstitucion(id, idTipo);
	}
	


}
