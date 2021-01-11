package com.sif.digestyc.Repository.Carga;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Registro;

@Repository("crearRegistroRepository")
public interface CrearRegistroRepository  extends JpaRepository<Registro, Long>{
	
	
	//esto es segun la notacion 
	public abstract Optional<Registro> findById(Long id);
	
	@Transactional
	@Query(value="select dbo.Fn_CodigoRegistro(?1)", nativeQuery = true)
	public String Fn_generarCodigoRegistro(Long id);

}

