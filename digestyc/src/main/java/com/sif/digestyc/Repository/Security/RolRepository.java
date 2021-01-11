package com.sif.digestyc.Repository.Security;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Security.Role;

@Repository
public interface RolRepository extends JpaRepository<Role, Serializable> {

	@Query(value = "select * from [seguridad].rol where id = ?1", nativeQuery = true)
	public Role findRoleById(int id);

	@Query(value = "select r.id, r.codigo from [seguridad].rol as r join usuarios_roles as ur on ur.usuarios_id = ?", nativeQuery = true)
	public List<Role> findRoleByUser(int id);

	@Query(value = "select * from [seguridad].rol where nombre = ?", nativeQuery = true)
	public List<Role> findByRoleName(String rol);
	
	@Query(value = "select * from [seguridad].rol where id != ?1", nativeQuery = true)
	public List<Role> findAllRoleLessId(int id);
	
	@Query(value = "select * from [seguridad].rol " + 
			"except " + 
			"select r.* from  [seguridad].rol as r inner join [seguridad].usuario_role as u on u.role_id = r.id and u.usuario_id = ?1", nativeQuery = true)
	public List<Role> findAllLessUser(int id);
	
	
}
