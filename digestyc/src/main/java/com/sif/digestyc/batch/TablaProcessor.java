package com.sif.digestyc.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Service.Validation.ValidationImpl.ValidationServiceImpl;

public class TablaProcessor implements ItemProcessor<TablasDinamicas, ErrorTablaDinamica>{
	
	private static Logger LOG = LoggerFactory.getLogger(TablaProcessor.class);
	
	@Autowired
	ValidationServiceImpl validationService;
	
	@Override
	public ErrorTablaDinamica process(TablasDinamicas item) throws Exception {
		LOG.info("tabla id = "+item.getTablaId());
		return validationService.validate(item);
	}
	
	
	
}
