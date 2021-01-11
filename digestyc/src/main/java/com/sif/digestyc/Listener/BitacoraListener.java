package com.sif.digestyc.Listener;

import java.util.List;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.sif.digestyc.Entity.Security.Bitacora;
import com.sif.digestyc.Repository.Security.BitacoraRepository;
import com.sif.digestyc.Service.Security.BitacoraService;
import com.sif.digestyc.Service.Security.SecurityImpl.BitacoraServiceImpl;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

public class BitacoraListener {
		
	private Logger logger = LoggerFactory.getLogger(BitacoraListener.class);
	
	@Autowired
	BitacoraRepository bitacoraRepository;
	
	/*
	 * Este metodo se llama despues de crear una nueva entidad, durante el commit
	 */
	@PostPersist
	public void onPostPersist(Object object) {
		guardarBitacora("INSERT", "TABLE: ", "SE INGRESO LA ENTIDAD: ", object);
	}
	
	/*
	 * Este metodo se llama despues de actualizar una entidad
	 */
	@PostUpdate
	public void onPostUpdate(Object object) {
		guardarBitacora("UPDATE", "TABLE: ", "SE ACTUALIZO LA ENTIDAD: ",object);
	}
	
	/*
	 * Este metodo se llama despues de eliminar una entidad
	 */
	@PostRemove
	public void onPostRemove(Object object) {
		guardarBitacora("delete", "TABLE: ", "SE ELIMINO LA ENTIDAD: ", object);
	}
	
	
	private void guardarBitacora(String accion, String vista, String descripcion, Object object) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
			String tabla = object.getClass().getSimpleName();
			logger.info(tabla);
			bitacoraRepository.save(new Bitacora(accion, user.getUsername(), vista + tabla, descripcion + tabla));
		}catch (Exception e) {
			logger.error("Error al crear bitacora"+e.getMessage());
			//e.printStackTrace();
		}
	}

}
