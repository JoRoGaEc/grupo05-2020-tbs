package com.sif.digestyc.Repository.Carga;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.HistorialEstadoEntrega;

@Repository("HentregaRepository")
public interface HistorialEntregaRepository extends JpaRepository<HistorialEstadoEntrega, Long> {
	
	@Query(value="select he.id, he.fecha_registro, he.comentario, he.actual, he.entrega_id, he.estado_entrega_id from historial_estado_entrega he inner join entrega e on he.entrega_id =e.id where he.actual=1 and e.registro_id=?1", nativeQuery = true)
	public List<HistorialEstadoEntrega> estado(Long registro_id);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value="exec sp_cambioEtapa ?1,?2,?3", nativeQuery = true)
	public void actualizarEstado(Long registro_id,Long periodo_id,String comentario);
	
	
	
}
