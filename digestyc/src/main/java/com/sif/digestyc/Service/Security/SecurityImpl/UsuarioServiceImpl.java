package com.sif.digestyc.Service.Security.SecurityImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.Security.Permiso;
import com.sif.digestyc.Entity.Security.Role;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Repository.Security.UsuarioRepository;
import com.sif.digestyc.Service.Security.UsuarioService;
@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	UsuarioRepository usuariorepository;

	@Override
	public Usuario findById(int id) {
		return usuariorepository.findById(id);
	}

	@Override
	public List<Usuario> findAll() {
		return usuariorepository.findAll();
	}

	@Override
	public Usuario update(Usuario usuario) {
		return usuariorepository.save(usuario);
	}

	@Override
	public boolean delete(Usuario usuario) {
		usuariorepository.delete(usuario);
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuariorepository.findByUsername(username);
		if (usuario == null) {
			logger.error("Error el login: no existe el usario " + username + " En el sistema");
			throw new UsernameNotFoundException("Error el login: no existe el usario " + username + " En el sistema");
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if(!usuario.getPrimeraSesion()) {
			for (Role rol : usuario.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(rol.getCodigo())); // Aqui iria el codigo del rol o el nombre
				for (Permiso permisos : rol.getPermisos()) {
					authorities.add(new SimpleGrantedAuthority(permisos.getUbicacion()));
				}
			}
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}
	
	

	@Override
	public List<Usuario> findAll(Boolean enable) {
		return usuariorepository.findAll(enable);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuariorepository.findByUsername(username);
	}

}