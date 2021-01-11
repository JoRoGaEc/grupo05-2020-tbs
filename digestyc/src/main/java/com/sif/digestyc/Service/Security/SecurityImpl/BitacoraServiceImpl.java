package com.sif.digestyc.Service.Security.SecurityImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sif.digestyc.Entity.Security.Bitacora;
import com.sif.digestyc.Repository.Security.BitacoraRepository;
import com.sif.digestyc.Service.Security.BitacoraService;

@Service("bitacoraServiceImpl")
public class BitacoraServiceImpl implements BitacoraService{
	
	@Autowired
	private BitacoraRepository bitacoraRepository;

	@Override
	public List<Bitacora> buscarBitacoras(int inf, int sup) {
		return bitacoraRepository.findBitacorasSinceUntil(inf, sup);
	}

	@Override
	public Bitacora update(Bitacora bitacora) {
		return bitacoraRepository.save(bitacora);
	}

	@Override
	public List<Bitacora> buscarTodo() {
		return bitacoraRepository.findAll();
	}

	@Override
	public void eliminar(Bitacora bitacora) {
		bitacoraRepository.delete(bitacora);
	}

	@Override
	public Optional<Bitacora> buscarPotId(Long id) {
		return bitacoraRepository.findById(id);
	}

	@Override
	public int getCantidad(String buscar) {
		int valor = 0;
		if(buscar != null && !buscar.isEmpty()) {
			valor = bitacoraRepository.getCantidad(buscar, buscar, buscar, buscar);
		}else {
			valor = bitacoraRepository.getCantidad();
		}
		return valor;
	}

	@Override
	public List<Bitacora> buscarBitacoras(int inf, int sup, String valor) {
		return bitacoraRepository.findBitacorasSinceUntil(valor, valor, valor, valor, inf, sup);
	}
	
}
