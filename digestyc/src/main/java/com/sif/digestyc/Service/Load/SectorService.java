package com.sif.digestyc.Service.Load;

import java.util.List;

import com.sif.digestyc.Entity.LoadModule.Sector;

public interface SectorService {

	public abstract List<Sector> listarSectores();
	public abstract Sector buscarSectorPorId(Long id);
	
}
