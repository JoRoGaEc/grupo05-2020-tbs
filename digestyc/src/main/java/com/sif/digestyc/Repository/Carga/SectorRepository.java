package com.sif.digestyc.Repository.Carga;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Sector;

@Repository("sectorRepository")
public interface SectorRepository extends CrudRepository<Sector, Long>{

	@Query("select i from Sector i where i.id = ?1")
	public abstract Sector findByIdSql(Long id);
}
