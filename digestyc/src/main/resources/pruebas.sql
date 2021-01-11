USE digestyc
go

-- DATOS DE PRUEBA
insert into tipo_error (codigo, nombre) values('prueba','prueba');
insert into tipo_error (codigo, nombre) values('prueba 1','prueba 1');
insert into tipo_error (codigo, nombre) values('prueba 2','prueba 2');

-- REGISTROS
insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(1,1,'descripcion',5,'20200505','20200501','Registro administrativo de MUERTES',5,1,1,1)
insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(0,1,'descripcion',5,'20200505','20200501','Registro administrativo de PNC',5,1,1,1)
insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(1,1,'descripcion',5,'20200505','20200501','Registro administrativo de BLOOM',5,1,1,1)

-- INSTITUCIONES
insert into institucion(fecha_registro,nombre) values(getdate(),'DIGESTYC')
insert into institucion (fecha_registro, nombre) values('2020-08-02','Ministerio de hacienda');
insert into institucion (fecha_registro, nombre) values('2020-08-02','Ministerio de Educacion');


-- PERIODOS
insert into periodo values (1,'20200501','20200401','Mensual 04 2020',1)
insert into periodo values (1,'20200601','20200501','Mensual 05 2020',1)

--- PERIODICIAD
insert into periodicidad values (1,1,1,'Mensual')
insert into periodicidad (anios_a_sumar,dias_a_sumar,meses_a_sumar, nombre)values(1,1,1,'Mensual');
insert into periodicidad  (anios_a_sumar,dias_a_sumar,meses_a_sumar, nombre)values(1,1,1,'Bimestral');
insert into periodicidad  (anios_a_sumar,dias_a_sumar,meses_a_sumar, nombre)values(1,1,1,'Trimestral');


-- ENTREGAS
insert into entrega values(null,'20200802','20200702',null,1,1,1);
insert into entrega values(null,'20200902','20200802',null, 2,1,1);
