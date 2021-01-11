--ASIGNACIONES NUEVAS PERO YA ESTAN EN EL IMPORT
SET IDENTITY_INSERT [seguridad].permiso ON
--VERSION PLANTILLA
insert into [seguridad].permiso (id, nombre, ubicacion) values (90, 'PLANTILLAS POR INSTITUCIONES',			'/plantillas/instituciones-con-ra-u-oe');
insert into [seguridad].permiso (id, nombre, ubicacion) values (91, 'PLANTILLAS DE INSTITUCIONES DE RA',			'/plantillas/ra/{id}/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (92, 'PLANTILLAS DE INSTITUCIONES DE OE',			'/plantillas/oe/{id}/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (93, 'GESTIONAR VERSIONES DE PLANTILLAS',			'/plantillas/gestionar-plantillas/{id}/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (94, 'HABILITAR - DESHABILITAR VERSIONES DE PLANTILLAS',			'/plantillas/gestionar-plantillas/habilitar/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (95, 'VER COLUMNAS DE LAS VERSIONES DE PLANTILLAS',			'/plantillas/columnasversionplantilla/{id}/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (96, 'CRER NUEVA VERSION PLANTILLA',			'/identificacion/nuevaPlantilla/{id}');
insert into [seguridad].permiso (id, nombre, ubicacion) values (97, 'GUARDAR NUEVA VERSION PLANTILLA',			'/identificacion/guardar/nuevaPlantilla');
insert into [seguridad].permiso (id, nombre, ubicacion) values (98, 'ELIMINAR COLUMNAS EN VERSION PLANTILLA',			'/identificacion/nueva-plantilla/{columna}/eliminar/');
 
--ENTREGAS REGISTROS
insert into [seguridad].permiso (id, nombre, ubicacion) values (99, 'ESTADO ENTREGA DE REGISTRO',			'/registro/ListarEstado');
insert into [seguridad].permiso (id, nombre, ubicacion) values (100, 'ANEXAR ARCHIVO DE UNA ENTREGA',			'/registro/upload');
insert into [seguridad].permiso (id, nombre, ubicacion) values (101, 'SUSTITUIR ARCHIVO DE UNA ENTREGA',			'/registro/sustituir-entrega/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (102, 'CARGAR DATOS DE UN ARCHIVO',			'/registro/upload/carga-datos/{id}/{idRegistro}/{idPeriodo}/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (103, 'BORRAR DATOS DE UN ARCHIVO',			'/registro/upload/eliminar-datos/{id}/{idRegistro}/{idPeriodo}/');

--ESTANDARES
insert into [seguridad].permiso (id, nombre, ubicacion) values (104, 'LISTADO DE ESTANDARES',			'/estandares/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (105, 'CREAR NUEVO ESTANDAR',			'/estandares/form');
insert into [seguridad].permiso (id, nombre, ubicacion) values (106, 'GUARDAR NUEVO ESTANDAR',			'/estandares/nuevo/guardar');



SET IDENTITY_INSERT [seguridad].permiso OFF
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 90);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 91);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 92);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 93);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 94);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 95);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 96);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 97);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 98);

insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 99);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 100);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 101);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 102);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 103);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 104);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 105);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 106);





