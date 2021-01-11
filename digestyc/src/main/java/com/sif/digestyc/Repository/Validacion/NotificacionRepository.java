package com.sif.digestyc.Repository.Validacion;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.ValidationModule.Notificacion;

@Repository("notificacionRepository")
public interface NotificacionRepository extends JpaRepository<Notificacion, Serializable>{
	
	
	public List<Notificacion> findByEstado(boolean estado);

	@Query(value = "select top 5 * from notificacion where estado = 0 order by id desc", nativeQuery = true)
	public List<Notificacion> findFirst(int i);
	
	@Query(value="select count(*) from notificacion where estado = 0", nativeQuery=true)
	public Integer countNotification();

	@Query(value = "select * from notificacion where estado = 0", nativeQuery = true)
	public Object findActivate();

}
