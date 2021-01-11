package com.sif.digestyc.Service.Security.SecurityImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.Security.Permiso;
import com.sif.digestyc.Repository.Security.PermisoRepository;
import com.sif.digestyc.Service.Security.PermisoService;

@Service("permisoServiceImpl")
public class PermisoServiceImpl implements PermisoService {

	@Autowired
	PermisoRepository permisoRepository;

	@Override
	public Permiso findById(int id) {
		return permisoRepository.findPermisoById(id);
	}

	@Override
	public List<Permiso> findByRole(int id_role) {
		return permisoRepository.findByRole(id_role);
	}

	@Override
	public List<Permiso> findAll() {
		return permisoRepository.findAll();
	}

	@Override
	public Permiso update(Permiso permiso) {
		return permisoRepository.save(permiso);
	}

	@Override
	public void delete(Permiso permiso) {
		permisoRepository.delete(permiso);
	}

	@Override
	public List<Permiso> findByName(String name) {
		return permisoRepository.findByName(name);
	}

	@Override
	public Permiso save(Permiso permiso) {
		return  permisoRepository.save(permiso);
		
	}

	@Override
	public List<Permiso> findAllLessRole(int id_role) {
		return permisoRepository.findAllLessRole(id_role);
	}

}
