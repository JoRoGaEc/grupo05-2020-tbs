package com.sif.digestyc.Repository.Validacion;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;

@Repository("errorTablaDinamicaRepository")
public interface ErrorTablaDinamicaRepository extends JpaRepository<ErrorTablaDinamica, Serializable>{


	@Query(value="SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as row FROM  error_tabla_dinamica where tabla_id = ? ) a WHERE a.row > ? and a.row <= ?", nativeQuery=true)
	List<ErrorTablaDinamica> findByTabla(Long id, int i, int j);

	@Query(value = "select count(*) from error_tabla_dinamica where tabla_id = ?", nativeQuery = true)
	long countError(long id);
	
}
