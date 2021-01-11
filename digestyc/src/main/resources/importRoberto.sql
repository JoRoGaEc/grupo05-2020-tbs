use digestyc 
go
SET IDENTITY_INSERT tipo_dato ON
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(1,'String','varchar', 1);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(2, 'Integer','Entero',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(3,'Date','Fecha',1);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(4, 'Double','Double',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(5,'Double','Float',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(6,'Boolean','Boolean',0);
insert into tipo_dato (id,tipo_dato_java, tipo_dato_sql_server,has_variacion) values(7,'Decimal','Decimal',1);
SET IDENTITY_INSERT tipo_dato OFF
go

SET IDENTITY_INSERT variacion_tipo_dato ON
go
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(1,0,'Entero','',0,0, 2)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(2,0,'Double','',0,0, 4)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(3,0,'Float','',0,0, 5)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(4,0,'Boolean','',1,0, 6)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(5,0,'Varchar','',0,150, 1)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(6,0,'Fecha','dd-MM-yyyy',0,0, 3)
insert into variacion_tipo_dato (id,decimales,descripcion,formato_fecha,is_boolean,longitud_cadena,tipo_dato_id) values(7,2,'Decimal','',0,0, 7)
SET IDENTITY_INSERT variacion_tipo_dato OFF
go

