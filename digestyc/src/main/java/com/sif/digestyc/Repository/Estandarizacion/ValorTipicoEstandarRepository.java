package com.sif.digestyc.Repository.Estandarizacion;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.Correspondencia;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;

@Repository
public interface ValorTipicoEstandarRepository extends JpaRepository<ValorTipicoEstandar, Serializable>{
	
}
