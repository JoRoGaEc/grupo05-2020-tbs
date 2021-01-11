package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;

@Repository
public interface ColumnaOrigenRepository extends JpaRepository<ColumnaOrigen, Serializable>{

	@Query(value = "select * from columna_origen where tabla_id = ?", nativeQuery = true)
	public List<ColumnaOrigen> buscarColumnaPorTabla(Long tabla_id);
	
}
