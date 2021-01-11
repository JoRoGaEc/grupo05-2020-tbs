SET IDENTITY_INSERT [seguridad].permiso ON

insert into [seguridad].permiso (id, nombre, ubicacion) values (41, 'LISTADO DE TIPO DE DATO','/tipoDato/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (42, 'AGREGAR TIPO DE DATO','/tipoDato/addTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (43, 'EDITAR TIPO DE DATO','/tipoDato/editTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (44, 'ELIMINAR TIPO DE DATO','/tipoDato/removeTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (45, 'INSTITUCIONES','/tipoDato/TipoDatoform');

SET IDENTITY_INSERT [seguridad].permiso OFF

insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 41);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 42);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 43);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 44);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 45);

insert into institucion(fecha_registro,nombre) values(getdate(),'DIGESTYC')
insert into periodicidad values (1,1,1,'Mensual')
insert into tipo_registro values('TR','Registro administrativo','Registro administrativo','RA')

insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(1,1,'descripcion',5,'20200505','20200501','Registro administrativo de MUERTES',5,1,1,1)

insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(0,1,'descripcion',5,'20200505','20200501','Registro administrativo de PNC',5,1,1,1)

insert into registro(activo,editable,descripcion,duracion_plazo_entrega,fecha_fin_entrega,fecha_inicio_entrega,nombre,prioridad,institucion_id,periodicidad_id,tipo_registro_id)
values(1,1,'descripcion',5,'20200505','20200501','Registro administrativo de BLOOM',5,1,1,1)

insert into formato values('Excel','.xls','Excel')
insert into formato values('Documento de texto','.txt','Documento de texto')
insert into formato values('Valores separados por comas','.csv','Csv')


insert into formato values('Excel','.xls','Excel')
insert into formato values('Documento de texto','.txt','Documento de texto')
insert into formato values('Valores separados por comas','.csv','Csv')
insert into formato values('Access','.mdb','Access')
insert into formato values('SPSS','.sav','SPSS')
insert into formato values('DBF','.dbf','DBF')

