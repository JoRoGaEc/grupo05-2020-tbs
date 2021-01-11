package com.sif.digestyc.Repository.Carga;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sif.digestyc.Entity.LoadModule.FilaOrigen;

@Repository
public interface FilaRepository extends JpaRepository<FilaOrigen, Serializable>{

}
