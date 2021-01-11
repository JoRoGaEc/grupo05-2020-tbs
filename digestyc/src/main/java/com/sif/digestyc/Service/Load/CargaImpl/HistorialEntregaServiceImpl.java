package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.HistorialEstadoEntrega;
import com.sif.digestyc.Repository.Carga.HistorialEntregaRepository;
import com.sif.digestyc.Service.Load.HistorialEntregaService;

@Service("HentregaServiceImpl")
public class HistorialEntregaServiceImpl implements HistorialEntregaService{
	
	@Autowired
	@Qualifier("HentregaRepository")
	private HistorialEntregaRepository HentregaRepository;

	
	@Override
	@Transactional
	public List<HistorialEstadoEntrega> findAll() {
		return HentregaRepository.findAll();
	}

	@Override
	@Transactional
	public List<HistorialEstadoEntrega> estado(Long registro_id) {
		// TODO Auto-generated method stub
		return HentregaRepository.estado(registro_id);
	}
	
	@Override
	@Transactional
	public void actualizarEstado(Long registro_id,Long periodo_id,String comentario) {
		HentregaRepository.actualizarEstado(registro_id, periodo_id, comentario);
	}
	
}
