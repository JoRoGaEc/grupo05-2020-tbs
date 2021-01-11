package com.sif.digestyc.Service.Load.CargaImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Controller.Carga.IdentificacionController;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.VersionPlantillaRepository;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("versionPlantillaServiceImpl")
public class VersionPlantillaServiceImpl implements VersionPlantillaService {

	
	private static final Logger LOG = LoggerFactory.getLogger(VersionPlantillaServiceImpl.class);
	
	@Autowired
	private VersionPlantillaRepository versionPlantillaRepository;

	@Autowired
	@Qualifier("crearRegistroService")
	private CrearRegistroServiceImpl registroService;

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;

	@Autowired
	@Qualifier("plantillaColumnaServiceImpl")
	private PlantillaColumnaServiceImpl plantillaColumnaService;

	@Override
	@Transactional
	public Optional<VersionPlantilla> buscarVersionPlantilla(Long id) {
		return versionPlantillaRepository.findById(id);
	}

	@Override
	@Transactional
	public VersionPlantilla recuperarVersionPlantillaHabilitada(Long id) {
		return versionPlantillaRepository.buscarPlantillaHabilitada(id);
	}

	@Override
	public VersionPlantilla actualizar(VersionPlantilla versionPlantillaHabilitada) {
		return versionPlantillaRepository.save(versionPlantillaHabilitada);
	}

	@Override
	public void desabilitarDemasPlantillas(Long id, Long p_id) {
		versionPlantillaRepository.desabilitarDemasPlantillas(id, p_id);

	}

	@Override
	@Transactional
	public Map<String, Object> crearNuevaVersionPlantilla(Long id, List<String> columnas) {
		System.out.println("COLUMNAS DENTRO DEL SERVICE");
		Optional<Registro> registro = registroService.buscarPorId(id);
		Plantilla plantilla = new Plantilla();
		VersionPlantilla nuevaVersion = new VersionPlantilla();
		List<String> columnasDb = new ArrayList<String>();
		List<PlantillaColumna> columnasPlantilla = new ArrayList<PlantillaColumna>();
		
		if (registro.isPresent()) {
			plantilla = registro.get().getPlantilla();
			this.desabilitarTodasLasPlantillas(plantilla.getId());/*Desabilitar todas las versiones de las plantillas*/
			if (plantilla.getPlantillaColumnas() != null) {
				columnasPlantilla  = plantilla.getPlantillaColumnas();
				for (PlantillaColumna pc : plantilla.getPlantillaColumnas()) {
					LOG.info(pc.getCodigo());
					columnasDb.add(pc.getCodigo());
				}
			}
			String codigoPlantilla = Fn_CodVersionPlantilla(((long) plantilla.getId()));
			nuevaVersion.setCodigo(codigoPlantilla);
			nuevaVersion.setHabilitada(true);
			nuevaVersion.setNombre(codigoPlantilla);
			/*VersionPlantilla - Plantilla*/
			nuevaVersion.setPlantilla(plantilla);
			for (String c : columnas) {
				if (columnasDb.contains(c)) {
					/* Significa que la columna ya existe, solo hay que asignarlo */
					PlantillaColumna plantillaColumna = plantillaColumnaService.recuperarColumnaDePlantilla(plantilla.getId(), c);
					ColumnaVersionPlantilla colVerPla = new ColumnaVersionPlantilla();
					colVerPla.setPlantillaColumna(plantillaColumna);
					colVerPla.setVersionPlantilla(nuevaVersion);

					nuevaVersion.agregarVersionColumna(colVerPla);
					LOG.info("Asignando plantilla"+plantillaColumna.getCodigo());
					
				} else {
					/* Hay que crear la columna y asignarlo a la version */
					PlantillaColumna nuevaColumna = new PlantillaColumna();
					nuevaColumna.setActivo(true);
					nuevaColumna.setCodigo(c.replace(" ", "").trim().toUpperCase());
					nuevaColumna.setNombre(c.replace(" ", "").trim().toUpperCase());
					nuevaColumna.setOrden(columnasDb.size());
					/*Columna - Plantilla*/
					nuevaColumna.setPlantilla(plantilla);
					columnasPlantilla.add(nuevaColumna);
					columnasDb.add(c);
					
					/*ColumnaVersionPlantilla - (PlantillaColumna, VersionPlantilla)*/
					ColumnaVersionPlantilla colVerPla = new ColumnaVersionPlantilla();
					colVerPla.setPlantillaColumna(nuevaColumna);
					colVerPla.setVersionPlantilla(nuevaVersion);
					
					
					
					nuevaVersion.agregarVersionColumna(colVerPla);

				}
				
			}
			/*Plantilla - columnas*/
			plantilla.setPlantillaColumnas(columnasPlantilla);
			versionPlantillaServiceImpl.actualizar(nuevaVersion);		
		}

		return null;
	}

	@Override
	public int desabilitarTodasLasPlantillas(Long idPlantilla) {
		System.out.println("ID DE LA PLANTILLA " + idPlantilla);
		return versionPlantillaRepository.desabilitarTodasLasPlantillas(idPlantilla);
	}

	@Override
	public int habilitarVersionPlantilla(Long idVersion, Long idPlantilla) {
		return versionPlantillaRepository.habilitarVersionPlantilla(idVersion, idPlantilla);
	}

	@Override
	public boolean puedeEditar(Long id) {
		return versionPlantillaRepository.canEdit(id) == 0;
	}

	@Override
	public String Fn_CodVersionPlantilla(Long plantilla) {
		return versionPlantillaRepository.Fn_CodVersionPlantilla(plantilla);
	}

}
