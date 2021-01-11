package com.sif.digestyc.Service.Load.CargaImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.PlantillaColumnaRepository;
import com.sif.digestyc.Service.Load.PlantillaColumnaService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("plantillaColumnaServiceImpl")
public class PlantillaColumnaServiceImpl implements PlantillaColumnaService{
	
	@Autowired
	PlantillaColumnaRepository plantillaColumnaRepository;

	@Override
	@Transactional
	public PlantillaColumna buscarPorId(int id) {
		return plantillaColumnaRepository.findById(id);
	}

	@Override
	@Transactional
	public List<PlantillaColumna> PlanillaColumnasTodas() {
		return plantillaColumnaRepository.findAll();
	}
	
	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;

	@Override
	@Transactional
	public PlantillaColumna actualizar(PlantillaColumna plantillaColumna) {
		return plantillaColumnaRepository.save(plantillaColumna);
	}

	@Override
	@Transactional
	public boolean eliminar(Long id) {
		boolean retornar = true;
		try {
			PlantillaColumna pColumna = new PlantillaColumna();
			pColumna.setId(id);
			plantillaColumnaRepository.delete(pColumna);
		}catch (Exception e) {
			retornar = false;
		}
		return retornar;
	}
	
	
	@Override
	@Transactional
	public ByteArrayInputStream paraImporteMasivo(Optional<Registro> registro) throws IOException {
		String[] columnas = {"COLUMNA","TIPIFICACION"};
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Sheet hoja = workbook.createSheet("importeMasivo");
		Row fila = hoja.createRow(0);
		for(int i=0; i<columnas.length; i++) {
			Cell celda = fila.createCell(i);
			celda.setCellValue(columnas[i]);
		}
		int i = 1;
		Optional<VersionPlantilla> versionPlantilla = registro.isPresent()? Optional.of(versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada(registro.get().getId())) : Optional.empty();
		if(versionPlantilla.isPresent()) {
			for(ColumnaVersionPlantilla verPlantillaCol : versionPlantilla.get().getVersionesColumna()) {
				fila = hoja.createRow(i);
				fila.createCell(0).setCellValue(verPlantillaCol.getPlantillaColumna().getNombre());
				if(verPlantillaCol.getTipificacion()!=null) {
					fila.createCell(1).setCellValue(verPlantillaCol.getTipificacion().getNombre());
				}
				i++;
			}
		}
		
		workbook.write(stream);
		workbook.close();
		return new ByteArrayInputStream(stream.toByteArray());
	}

	@Override
	public ArrayList<PlantillaColumna> PlanillaColumnasPorInstitucion(Long id) {
		return plantillaColumnaRepository.findbyInstitucion(id);
	}

	@Override
	public List<PlantillaColumna> getColumnasDePlantilla(Long id) {
		
		return plantillaColumnaRepository.getColumnasDePlantilla(id);
	}

	@Override
	@Transactional
	public PlantillaColumna recuperarColumnaDePlantilla(Long id, String codigo) {
		return plantillaColumnaRepository.recuperarColumnaDePlantilla(id, codigo);
	}

}
