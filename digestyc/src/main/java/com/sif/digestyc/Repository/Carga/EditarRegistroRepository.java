package com.sif.digestyc.Repository.Carga;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.TipoRegistro;

@Repository("editarRegistroRepository")
public interface EditarRegistroRepository extends JpaRepository<Registro, Long>{
	
	
	@Query("select i from Registro i where i.id = ?1")
	public abstract Registro findByIdSql(Long id);


}
