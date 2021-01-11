USE digestyc
--create schema seguridad;

SET IDENTITY_INSERT [seguridad].usuario ON
insert into [seguridad].usuario (id, apellido, email, enabled, intentos_restantes, nombre, password, username, institucion_id, primera_sesion) values (1, 'administrador', 'administrador@gmail.com', 1, 3, 'administrador', '$2a$10$/bbJ8DAytRwo587p0olBX.DibKxExU6pINHFb2uCYplJ1IDv9PQq2','administrador', null, 0);
SET IDENTITY_INSERT [seguridad].usuario OFF

SET IDENTITY_INSERT [seguridad].permiso ON
insert into [seguridad].permiso (id, nombre, ubicacion) values (1, 'LISTADO DE PERMISO','/permisos/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (2, 'AGREGAR PERMISO','/permisos/addpermiso');
insert into [seguridad].permiso (id, nombre, ubicacion) values (3, 'ELIMINAR PERMISO','/permisos/removepermiso');
insert into [seguridad].permiso (id, nombre, ubicacion) values (4, 'EDITAR PERMISO','/permisos/editpermiso');
insert into [seguridad].permiso (id, nombre, ubicacion) values (5, 'PERMISOS','/permisos/permisoform');
insert into [seguridad].permiso (id, nombre, ubicacion) values (6, 'LISTADO DE ROLES','/roles/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (7, 'AGREGAR ROL','/roles/addrol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (8, 'ELIMINAR ROL','/roles/removerol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (9, 'EDITAR ROL','/roles/editarrol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (10, 'ROLES','/roles/rolform');
insert into [seguridad].permiso (id, nombre, ubicacion) values (11, 'ASIGNAR PERMISO','/roles/permisos/agregarpermiso/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (12, 'ELIMINAR PERMISO ASIGNADO','/roles/eliminarpermiso');
insert into [seguridad].permiso (id, nombre, ubicacion) values (13, 'GUARDAR PERMISO','/roles/guardarpermiso');
insert into [seguridad].permiso (id, nombre, ubicacion) values (14, 'LISTADO DE USUARIOS','/usuarios/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (15, 'LISTADO DE USUARIOS DESACTIVADOS','/usuarios/desactivados');
insert into [seguridad].permiso (id, nombre, ubicacion) values (16, 'LISTADO DE COMPLETA DE USUARIOS','/usuarios/listaCompleta');
insert into [seguridad].permiso (id, nombre, ubicacion) values (17, 'AGREGAR USUARIO','/usuarios/agregarusuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (18, 'GUARDAR USUARIO','/usuarios/guardarusuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (19, 'EDITAR USUARIO','/usuarios/editarusuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (20, 'EDITAR MI PERFIL','/usuarios/miPerfil');
insert into [seguridad].permiso (id, nombre, ubicacion) values (21, 'ACTUALIZAR USUARIO','/usuarios/actualizarUsuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (22, 'ELIMINAR USUARIO','/usuarios/removerusuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (23, 'ASIGNAR ROL','/usuarios/agregarrol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (24, 'ELIMINAR ROL ASIGNADO','/usuarios/eliminarrol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (25, 'GUARDAR ROL','/usuarios/guardarrol');
insert into [seguridad].permiso (id, nombre, ubicacion) values (26, 'PERMITIR EL CAMBIO DE CONTRASEÑA DE OTROS USUARIOS','/usuarios/cambiarPasswordUsuario');
insert into [seguridad].permiso (id, nombre, ubicacion) values (27, 'PERMITIR LA ACTUALIZACION DEL PROPIO PERFIL','/usuarios/actualizarPerfil');
insert into [seguridad].permiso (id, nombre, ubicacion) values (28, 'PERMITIR LA ACTUALIZACION DE LA CONTRASEÑA DEL PROPIO PERFIL','/usuarios/cambiarPasswordPerfil');
insert into [seguridad].permiso (id, nombre, ubicacion) values (29, 'LISTADO TIPO DE ERROR','/tipoerror/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (30, 'AGREGAR TIPO DE ERROR','/tipoerror/addtipoerror');
insert into [seguridad].permiso (id, nombre, ubicacion) values (31, 'EDITAR TIPO DE ERROR','/tipoerror/edittipoerror');
insert into [seguridad].permiso (id, nombre, ubicacion) values (32, 'ELIMINAR TIPO DE ERROR','/tipoerror/removetipoerror');
insert into [seguridad].permiso (id, nombre, ubicacion) values (33, 'TIPO DE ERROR','/tipoerror/tipoerrorform');
insert into [seguridad].permiso (id, nombre, ubicacion) values (34, 'LISTADO REGISTROS','/registro/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (35, 'EDITAR REGISTRO','/registros/editarregistro');
insert into [seguridad].permiso (id, nombre, ubicacion) values (36, 'ACTUALIZAR REGISTRO DE INSTITUCION','/registros/actualizarRegistro');
insert into [seguridad].permiso (id, nombre, ubicacion) values (37, 'LISTADO DE TIPO DE DATO','/tipoDato/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (38, 'AGREGAR TIPO DE DATO','/tipoDato/addTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (39, 'EDITAR TIPO DE DATO','/tipoDato/editTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (40, 'ELIMINAR TIPO DE DATO','/tipoDato/removeTipoDato');
insert into [seguridad].permiso (id, nombre, ubicacion) values (41, 'TIPO DE DATO','/tipoDato/TipoDatoform');
insert into [seguridad].permiso (id, nombre, ubicacion) values (42, 'AGREGAR INSTITUCION','/instituciones/addinstitucion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (43, 'EDITAR INSTITUCION','/instituciones/editinstitucion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (44, 'ELIMINAR INSTITUCION','/instituciones/removeinstitucion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (45, 'INSTITUCIONES','/instituciones/institucioneform');
insert into [seguridad].permiso (id, nombre, ubicacion) values (46, 'LISTADO DE INSTITUCIONES','/instituciones/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (47, 'CREAR REGISTRO','/registros/crear');
insert into [seguridad].permiso (id, nombre, ubicacion) values (48, 'GUARDAR REGISTRO DE INSTITUCION','/registros/guardarregistro');
insert into [seguridad].permiso (id, nombre, ubicacion) values (49, 'LISTADO DE TIPO DE TIPIFICACION','/tipificacion/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (50, 'CREAR TIPIFICACION','/tipificacion/nuevo');
insert into [seguridad].permiso (id, nombre, ubicacion) values (51, 'GUARDAR TIPIFICACION METODO POST','/tipificacion/agregar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (52, 'ELIMINAR TIPIFICACION','/tipificacion/remover');
insert into [seguridad].permiso (id, nombre, ubicacion) values (53, 'AGREGAR TIPIFICACIONES A INSTITUCIONES','/tipificacion/agregarTipificaciones');
insert into [seguridad].permiso (id, nombre, ubicacion) values (54, 'ELIMINAR TIPIFICACIONES DE INSTITUCION ESPECIFICA','/eliminarTipificacion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (55, 'GUARDAR TIPIFICACIONES DE INSTITUCION ESPECIFICA','/guardarTipificacion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (56, 'EDTIAR TIPIFICACIONES A INSTITUCIONES','/tipificacion/editar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (57, 'ACTUALIZAR TIPIFICACIONES DE INSTITUCION ESPECIFICA','/tipificacion/actualizar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (58, 'ELIMINAR RANGO DE TIPIFICACIONES DE INSTITUCION ESPECIFICA','/tipificacion/eliminarRango');
insert into [seguridad].permiso (id, nombre, ubicacion) values (59, 'HISTORIAL DE ENTREGA DE REGISTROS','/HistorialEntrega/listar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (60, 'FILTRAR HISTORIAL DE ENTREGA DE REGISTROS','/HistorialEntrega/Filtro');

insert into [seguridad].permiso (id, nombre, ubicacion) values (61, 'ASIGNAR TIPOS DE DATOS A TIPIFICACIONES','/identificacion/columnas');
insert into [seguridad].permiso (id, nombre, ubicacion) values (62, 'GUARDAR TIPOS DE DATOS A TIPIFICACIONES','/identificacion/guardar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (63, 'NUEVA COLUMNA CON TIPO DE DATO','/identificacion/nuevo');
insert into [seguridad].permiso (id, nombre, ubicacion) values (64, 'ELIMINAR COLUMNA','/identificacion/remover');
insert into [seguridad].permiso (id, nombre, ubicacion) values (65, 'DESCARGAR PLANTILLA PARA IMPORTE MASIVO','/identificacion/importeMasivo');
insert into [seguridad].permiso (id, nombre, ubicacion) values (66, 'IMPORTAR PLANTILLA PARA IMPORTE MASIVO','/identificacion/importar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (67, 'BITACORA, VER ACTIVIDADES','/bitacora/lista');
insert into [seguridad].permiso (id, nombre, ubicacion) values (68, 'LISTADO DE DIRECTORIOS','/directorio/crear');
insert into [seguridad].permiso (id, nombre, ubicacion) values (69, 'GUARDAR DIRECTORIOS','/directorio/guardar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (70, 'GUARDAR TIPIFICACIONES','/tipificacion/guardarTipificacion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (71, 'ELIMINAR TIPIFICACIONES','/tipificacion/eliminarTipificacion');
insert into [seguridad].permiso (id, nombre, ubicacion) values (72, 'AGREGAR TIPIFICACIONES','/tipificacion/desdeTipificaciones');

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
insert into [seguridad].permiso (id, nombre, ubicacion) values (107, 'ELIMINAR ESTANDAR',               '/estandares/remover');
insert into [seguridad].permiso (id, nombre, ubicacion) values (108, 'ASIGNAR VALORES A ESTANDAR',      '/estandares/asignar/valores-tipicos/{id}/{idEstandar}/{IdTabla}/editar/');
insert into [seguridad].permiso (id, nombre, ubicacion) values (109, 'GUARDAR VALORES A ESTANDAR',      '/emparejar-valores/guardar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (110, 'EDITAR ESTANDAR',                 '/estandares/editar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (111, 'ACTUALIZAR ESTANDAR',                 '/estandares/actualizar');
insert into [seguridad].permiso (id, nombre, ubicacion) values (112, 'ELIMINAR RANGOS DE VALORES DE ESTANDAR',                 '/estandares/eliminarRango');
insert into [seguridad].permiso (id, nombre, ubicacion) values (113, 'FILTRO LISTAR ENTREGAS',                 '/registro/Filtro');

SET IDENTITY_INSERT [seguridad].permiso OFF

SET IDENTITY_INSERT [seguridad].rol ON
insert into [seguridad].rol (id, codigo, descripcion, nombre) values (1, 'ROLE_ADMIN', 'Rol para el administrador', 'ROL ADMINISTRADOR');
SET IDENTITY_INSERT [seguridad].rol OFF




insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 1);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 2);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 3);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 4);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 5);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 6);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 7);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 8);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 9);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 10);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 11);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 12);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 13);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 14);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 15);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 16);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 17);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 18);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 19);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 20);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 21);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 22);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 23);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 24);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 25);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 26);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 27);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 28);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 29);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 30);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 31);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 32);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 33);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 34);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 35);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 36);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 37);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 38);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 39);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 40);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 41);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 42);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 43);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 44);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 45);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 46);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 47);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 48);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 49);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 50);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 51);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 52);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 53);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 54);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 55);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 56);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 57);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 58);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 59);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 60);

insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 61);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 62);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 63);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 64);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 65);

insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 66);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 67);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 68);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 69);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 70);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 71);
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
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 107);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 108);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 109);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 110);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 111);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 112);
insert into [seguridad].role_permiso (role_id, permiso_id) values(1, 113);



insert into [seguridad].usuario_role (usuario_id, role_id) values (1,1);



SET IDENTITY_INSERT tipo_dato ON
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(1,'String','varchar', 1);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(2, 'Integer','integer',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(3,'Date','date',1);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(4, 'Double','Double',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(5,'Double','Float',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(6,'Boolean','Boolean',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(7,'Decimal','Decimal',1);
SET IDENTITY_INSERT tipo_dato OFF

SET IDENTITY_INSERT variacion_tipo_dato ON
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(1,0,'Entero','',0,0, 2)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(2,0,'Double','',0,0, 4)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(3,0,'Float','',0,0, 5)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(4,0,'Boolean','',1,0, 6)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(5,0,'Varchar','',0,150, 1)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(6,0,'Fecha','dd-MM-yyyy',0,0, 3)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(6,0,'Fecha','dd/MM/yyyy',0,0, 3)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(7,2,'Decimal','',0,0, 7)
SET IDENTITY_INSERT variacion_tipo_dato OFF


insert into formato values('Excel','.xls','Excel');
insert into formato values('Documento de texto','.txt','Documento de texto');
insert into formato values('Valores separados por comas','.csv','Csv');
insert into formato values('Access','.mdb','Access');
insert into formato values('SPSS','.sav','SPSS');
insert into formato values('DBF','.dbf','DBF');
insert into formato values('Excel','.xlsx','Excel');

-- TIPO DE REGISTROS
insert into tipo_registro (codigo, descripcion, nombre, nombre_corto) values('RA-01','Registros administratrivos','Registros administratrivos','RA');
insert into tipo_registro (codigo, descripcion, nombre, nombre_corto) values('OE-02','Operaciones estadisticas','Operaciones estadisticas','OE');

--set identity_insert institucion on
insert into institucion(fecha_registro, nombre) values('2020-05-28', 'MINED');
insert into institucion(fecha_registro, nombre) values('2020-05-28', 'MINSAL');
insert into institucion(fecha_registro, nombre) values('2020-05-28', 'Alcaldía de apopa');
--set identity_insert institucion off 

insert into periodicidad(anios_a_sumar,dias_a_sumar,meses_a_sumar,nombre) values(0,0,1,'mensual');
insert into periodicidad(anios_a_sumar,dias_a_sumar,meses_a_sumar,nombre) values(0,0,2,'Bimensual');
insert into periodicidad(anios_a_sumar,dias_a_sumar,meses_a_sumar,nombre) values(0,0,3,'Trimestral');
insert into periodicidad(anios_a_sumar,dias_a_sumar,meses_a_sumar,nombre) values(0,0,6,'Semestral');


INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Pendiente','PE','Ningun Archivo ha sido entregado hasta este momento', 1, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('No enviado','NE','El archivo no fue presentado dentro del plazo establecido', 2, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Enviado','EN','El archivo ha sido recibido pero no se encuentra en proceso de trabajo aun', 3, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Validacion','VA','El archivo se encuentra en proceso de validacion de los datos que contiene', 4, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Estandarizacion','ES','Los datos del archivo se están estandarizando', 5, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Depuracion','DE','Se están depurando aquellos datos innecesarios', 6, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Vinculacion','VI','Se están creando los enlaces entre los datos provistos con los registros de DIGESTYC', 7, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Anonimizacion','AN','Se está removiendo cualquier vinculacion que pueda relacionar los datos con personales naturales o juridicas', 8, 'tc');
INSERT INTO "estado_entrega" (nombre, nombre_corto, descripcion, ordinal, codigo) VALUES('Terminado','TE','El proceso de integración de datos ha sido finalizado correctamente', 9, 'tc');


INSERT INTO sector (nombre, color_hex, ion_icon) VALUES ('Educacion', '#43b839', 'school');
INSERT INTO sector (nombre, color_hex, ion_icon) VALUES ('Salud', '#6de6e8', 'fitness');
INSERT INTO sector (nombre, color_hex, ion_icon) VALUES ('Seguridad', '#0b2385', 'shield');
INSERT INTO sector (nombre, color_hex, ion_icon) VALUES ('Vivienda', '#9c3710', 'home');



--insert into periodo values (1,'20200501','20200401','Mensual 04 2020',1)
--insert into periodo values (1,'20200601','20200501','Mensual 05 2020',1)
--insert into entrega values(null, '20200802','20200702',null,null,1,1,1,1)
--insert into entrega values(null, '20200902','20200802',null,null,2,1,1,1)



