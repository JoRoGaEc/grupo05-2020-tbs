package com.sif.digestyc.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.Mapping.TablaDinamicaMapper;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;

@Configuration
@EnableBatchProcessing
public class BatchJob {
	
	private static Logger LOG = LoggerFactory.getLogger(BatchJob.class);	
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    JobRepository jobRepository;
	
	@Autowired
	TablaServiceImpl tablaService;
	
	/* BATCH PARA VALIDAR DATOS */
	
	@Bean
	public TablaProcessor processor() {
		LOG.info("Processing");
		return new TablaProcessor();
	}
	
	
	//Leer datos de la tabla base y retornar una TablaDinamica
	public JdbcCursorItemReader<TablasDinamicas> reader(String t, long tablaId ,long entrega_id) {
		JdbcCursorItemReader<TablasDinamicas> reader =new JdbcCursorItemReaderBuilder<TablasDinamicas>()
		.name("TablaReader")
		.rowMapper(new TablaDinamicaMapper(tablaId))
		.dataSource(jdbcTemplate.getDataSource())
		.sql("select * from "+t +" where entrega_id = "+entrega_id)
		.build();
		return reader;
	}
	
	
	//Actualizamos de ser necesario la entidad ErrorTablaDinamica
	@Bean
	public JdbcBatchItemWriter<ErrorTablaDinamica> writter(){
		LOG.info("Writing");
		return new JdbcBatchItemWriterBuilder<ErrorTablaDinamica>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.dataSource(jdbcTemplate.getDataSource())
				.sql("update error_tabla_dinamica set fila = :fila, columna = :columna where id = :id")
				.build();
	}
	
	
	// Validamos los datos, este es el metodo que llamamos desde el controlador
	public Job validarTablJob(JobListener listener, String t, long idTabla, long entrega_id) {
		return jobBuilderFactory.get("validarTablJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1(writter(), t, idTabla, entrega_id))
				.end()
				.build();
	}
	
	/* 
	 * Aqui se realiza la logica de todo, primero seteamos que leeremos 100 filas de la tabla, 
	 * enviamos los datos necesarios para leer, luego colocamos el procesador y le decimos donde va a escribir los datos
	 * */
	public Step step1(JdbcBatchItemWriter<ErrorTablaDinamica> writer, String t, long idTabla, long entrega_id) {
		return stepBuilderFactory.get("step1")
				.<TablasDinamicas, ErrorTablaDinamica> chunk(100)
				.reader(reader(t,idTabla, entrega_id))
				.processor(processor())
				.writer(writer)
				.build();
	}

	
	/* BATCH PARA INSERTAR DATOS DE UNA TABLA A OTRA */	
	
	@Bean
	public TablaProcessorInsert processorTabla() {
		LOG.info("Processing");
		return new TablaProcessorInsert();
	}
	
	
	/* Actualizamos la tabla generada para que tenga el id de la tabla que proviene */
	@Bean
	public JdbcBatchItemWriter<TablasDinamicas> writterTabla(){
		LOG.info("Writing");
		return new JdbcBatchItemWriterBuilder<TablasDinamicas>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.dataSource(jdbcTemplate.getDataSource())
				.sql("insert into error_tabla_dinamica (columna, fila, tabla_id, tipo_dato) values ('NONE', :registro_id, :tablaId, 'NONE')")
				.build();
	}
	

	
	/* Step para insertar datos de una tabla a otra */
	public Step step2(JdbcBatchItemWriter<TablasDinamicas> writterTabla, String t, long idTabla, long entrega_id) {
		return stepBuilderFactory.get("step2")
				.<TablasDinamicas, TablasDinamicas> chunk(100)
				.reader(reader(t,idTabla, entrega_id))
				.processor(processorTabla())
				.writer(writterTabla)
				.build();
	}
	

	public Job insertarEnTabla(JobListenerInsertar listener, String t, long idTabla, long entrega_id) {
		return jobBuilderFactory.get("insertarEnTabla")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step2(writterTabla(),t, idTabla, entrega_id))
				.end()
				.build();
	}
	
	
	
	/* 
	 * Necesitamos que el batch se ejecute asincronicamente 
	 */

	@Bean(name = "jobAsincrono")
	public JobLauncher simpleJobLauncher() throws Exception {
	    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	    jobLauncher.setJobRepository(jobRepository);
	    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
	    jobLauncher.afterPropertiesSet();
	    return jobLauncher;/*solid - liskov*/
	}
	

}
