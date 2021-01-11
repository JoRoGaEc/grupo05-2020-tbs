--ASIGNACIONES NUEVAS PERO YA ESTAN EN EL IMPORT
insert into [seguridad].permiso (id, nombre, ubicacion) values (73, 'VALIDACION DE ERRORES',			'/{id}/tabla/{skip}/{inf}/{pre}');
insert into [seguridad].permiso (id, nombre, ubicacion) values (74, 'VER DATOS DE TABLAS DINAMICAS',	'/{id}/datos_completos/{skip}/{inf}/{tablaCorrelativa}');
insert into [seguridad].permiso (id, nombre, ubicacion) values (75, 'EDITAR DATOS DE TABLAS DINAMICAS',	'/reparar/guardar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (76, 'VER NOTIFICACIONES',				'/reparar/notificaciones');
insert into [seguridad].permiso (id, nombre, ubicacion) values (77, 'VER TABLAS A EJECUTAR JOB',		'/validacion/start_job');
insert into [seguridad].permiso (id, nombre, ubicacion) values (78, 'EJECUTAR JOB DE VALIDACION',		'/validacion/start_job/post');
insert into [seguridad].permiso (id, nombre, ubicacion) values (79, 'CARGAR NOTIFICACIONES',			'/notificacion_rest/notificaciones');
insert into [seguridad].permiso (id, nombre, ubicacion) values (80, 'CONTAR NOTIFICACIONES',			'/notificacion_rest/countNotification');
insert into [seguridad].permiso (id, nombre, ubicacion) values (81, 'CONECTAR SOCKET',					'CONECTAR_SOCKET');
insert into [seguridad].permiso (id, nombre, ubicacion) values (82, 'VER TABLAS PARA ASIGNAR ESTANDAR',	'/asignarestandar/index');
insert into [seguridad].permiso (id, nombre, ubicacion) values (83, 'ASIGNAR ESTANDAR',					'/asignarestandar/guardar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (84, 'VER ESTRUCTURA DE TABLAS',			'/asignarestandar/id/estructura');
insert into [seguridad].permiso (id, nombre, ubicacion) values (85, 'EDITAR ESTANDAR POR COLUMNA',		'/asignarestandar/{id}/editar/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (86, 'VER TABLAS A ASIGNAR ESTANDAR',	'/estandarizar/start_job');
insert into [seguridad].permiso (id, nombre, ubicacion) values (87, 'EJECUTAR JOB DE ESTANDAR',			'/estandarizar/start_job/post');
insert into [seguridad].permiso (id, nombre, ubicacion) values (88, 'CREAR ESTRUCTURA DE TABLA',		'/estandarizar/create_table');
insert into [seguridad].permiso (id, nombre, ubicacion) values (89, 'PERMITIR REGRESAR A VALIDACION',	'/estandarizar/preparar/');



insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 72);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 73);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 74);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 75);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 76);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 77);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 78);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 79);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 80);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 81);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 82);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 83);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 84);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 85);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 86);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 87);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 88);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 89);



