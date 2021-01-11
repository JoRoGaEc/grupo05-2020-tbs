package com.sif.digestyc.Service.Load.CargaImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Service.Load.RegistroService;

@Service("registroServiceImpl")
public class RegistroServiceImpl implements RegistroService{
	
	@Autowired
	@Qualifier("registroRepository")
	private RegistroRepository registroRepository;


	@Override
	@Transactional
	public List<Registro> findAll() {
		return (List<Registro>)registroRepository.findAll();
	}
	
	@Override
	@Transactional
	public Registro buscarRegistroPorId(Long id) {
		return registroRepository.buscarRegistroPorId(id);
		
	}
	@Override
	@Transactional
	public void uploadFile(MultipartFile file) throws Exception  {
		//file.transferTo(new File("C:\\Users\\dougl\\OneDrive\\Escritorio\\TESIS\\p4\\digestyc\\uploads\\"+file.getOriginalFilename()));
		String folder ="/tesis/";
		byte[] bytes = file.getBytes();
		java.nio.file.Path path = Paths.get(folder + file.getOriginalFilename());
		Files.write(path, bytes);
	}

	@Override
	@Transactional
	public void uploadFile2(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public Registro buscarRegistroConColumnas(Long id) {
		return registroRepository.buscarRegistroAndColumnas(id);
	}

	
	@Override
	@Transactional
	public String codigoRegistro(Long registro_id) {
		return  registroRepository.codigoRegistro(registro_id);

	}

	@Override
	public Registro buscarRegistroPorIdJpa(Long id) {
		return registroRepository.buscarRegistroPorIdJpa(id);
	}
	
	

}
