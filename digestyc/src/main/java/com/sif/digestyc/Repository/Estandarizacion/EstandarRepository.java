package com.sif.digestyc.Repository.Estandarizacion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.StandardizationModule.Estandar;

@Repository
public interface EstandarRepository extends CrudRepository<Estandar, Long>{

}
