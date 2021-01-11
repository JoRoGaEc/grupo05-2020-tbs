package com.sif.digestyc.Repository.Security;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Security.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Serializable> {

	@Query(value = "select * from [seguridad].permiso where id = ?", nativeQuery = true)
	public Permiso findPermisoById(int id);

	@Query(value = "select p.id, p.name from [seguridad].permiso as p join [seguridad].role_permisos as rp on rp.rolp_id = ?", nativeQuery = true)
	public List<Permiso> findByRole(int id);

	@Query(value = "select * from [seguridad].permiso where name = ?", nativeQuery = true)
	public List<Permiso> findByName(String name);

	//Esta busca todos los permisos menos los que ya tiene asignados
	@Query(value = "select * from [seguridad].permiso \n" + 
			"except \n" + 
			"select p.id, p.nombre, p.ubicacion from [seguridad].permiso as p inner join [seguridad].role_permiso as rp on rp.permiso_id = p.id and rp.role_id=?1", nativeQuery = true)
	public List<Permiso> findAllLessRole(int id_role);
	
}
