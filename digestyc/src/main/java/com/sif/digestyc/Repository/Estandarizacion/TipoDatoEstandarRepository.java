package com.sif.digestyc.Repository.Estandarizacion;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.TipoDatoEstandar;

@Repository("tipoDatoEstandarRepository")
public interface TipoDatoEstandarRepository extends JpaRepository<TipoDatoEstandar,Serializable>{

	
}
