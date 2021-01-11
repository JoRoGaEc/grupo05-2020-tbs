package com.sif.digestyc.Repository.Estandarizacion;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;

@Repository
public interface CorrespondenciaRepository extends JpaRepository<Correspondencia, Serializable>{

	@Query(value = "select * from correspondencia where valor_tipico_estandar_id in (select id from valor_tipico_estandar where estandar_id = ?);", nativeQuery = true)
	public List<Correspondencia> findAllByEstandar(Long id);

}
