package com.sif.digestyc.Repository.Catalogos;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;


@Repository
public interface ValorTipicoRepository extends JpaRepository<ValorTipico, Serializable>{
	
}
