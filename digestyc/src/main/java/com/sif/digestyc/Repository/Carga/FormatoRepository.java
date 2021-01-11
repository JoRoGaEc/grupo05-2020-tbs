package com.sif.digestyc.Repository.Carga;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Formato;

@Repository
public interface FormatoRepository  extends CrudRepository<Formato, Long>{
	
	@Query("select f from Formato  f where f.extension = ?1")
	public abstract Formato findByExtension(String extension);
	
	
}
