package com.sif.digestyc.Service.Security.SecurityImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.Security.Role;
import com.sif.digestyc.Repository.Security.RolRepository;
import com.sif.digestyc.Service.Security.RolService;

@Service("rolServiceImpl")
public class RolServiceImpl implements RolService {

	@Autowired
	RolRepository rolRepository;

	@Override
	public Role findById(int id) {
		return rolRepository.findRoleById(id);
	}

	@Override
	public List<Role> findRoleByUser(int id_usuario) {
		return rolRepository.findRoleByUser(id_usuario);
	}

	@Override
	public List<Role> findAll() {
		return rolRepository.findAll();
	}

	@Override
	public Role update(Role rol) {
		return rolRepository.save(rol);
	}

	@Override
	public void delete(Role rol) {
		 rolRepository.delete(rol);
	}

	@Override
	public List<Role> findByRolName(String rol) {
		return rolRepository.findByRoleName(rol);
	}
	
	
	@Override
	public Role save(Role rol) {
		return rolRepository.save(rol);
	}

	@Override
	public List<Role> findAllRoleLessId(int id) {
		return rolRepository.findAllRoleLessId(id);
	}

	@Override
	public List<Role> findAllLessUser(int id) {
		return rolRepository.findAllLessUser(id);
	}

}
