package com.sif.digestyc.Service.Security;

import java.util.List;

import com.sif.digestyc.Entity.Security.Role;

public interface RolService {

	public abstract Role findById(int id);

	public abstract List<Role> findRoleByUser(int id_usuario);

	public abstract List<Role> findAll();

	public abstract Role update(Role rol);

	public abstract void delete(Role rol);

	public abstract List<Role> findByRolName(String rol);
	
	public abstract Role save(Role rol);
	
	public abstract List<Role> findAllRoleLessId(int id);
	
	public abstract List<Role> findAllLessUser(int id);

}
