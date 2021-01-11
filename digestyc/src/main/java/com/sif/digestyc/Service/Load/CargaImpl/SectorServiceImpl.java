package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Sector;
import com.sif.digestyc.Repository.Carga.SectorRepository;
import com.sif.digestyc.Service.Load.SectorService;

@Service("sectorService")
public class SectorServiceImpl implements SectorService{

	@Autowired
	@Qualifier("sectorRepository")
	private SectorRepository sectorRepository;
	
	@Override
	@Transactional
	public List<Sector> listarSectores() {
		return (List<Sector>) sectorRepository.findAll();
	}

	@Override
	@Transactional
	public Sector buscarSectorPorId(Long id) {
		return sectorRepository.findByIdSql(id);
	}

}
