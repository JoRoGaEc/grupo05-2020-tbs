package com.sif.digestyc.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.Human;

@Repository
public interface HumanRepository  extends CrudRepository<Human, Long>{

	
}
