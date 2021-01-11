USE digestyc
go

--========PROCEDIMIENTOS ALMACENADOS, FUNCIONES, TRIGGER, ETC=============
--Creador: Douglas Alfaro
--Comentario: SP para crear nueva entrega 
--Fecha: 20200804

use digestyc
go
create procedure sp_nuevaEntrega @registro_id int,@entrega_id int,@formato int,@nombreArchivo varchar(300)

as
	declare @entrega varchar(500),@archivo varchar(500),@ubicacion varchar(500),@idEntrega int,@idArchivo int

	--set @nombreArchivo=(select dbo.Fn_EntregaArchivoNombre(@registro_id,@periodo_id))
	set @idEntrega=(select id from entrega e where id=@entrega_id)
	PRINT @idEntrega
	
	set @entrega='update entrega set fecha_entrega=getdate() where id='+convert(varchar(20),@idEntrega)
	set @archivo='insert into dbo.archivo([codigo],[datos_cargados],[fecha_subido],[nombre],[ubicacion_archivo],[entrega_id],[formato_id]) values(convert(varchar(20),'+CHAR(39)+@nombreArchivo+CHAR(39)+'),1,getdate(),convert(varchar(20),'+CHAR(39)+@nombreArchivo+CHAR(39)+'),(select convert(varchar(20),max(id) ) from directorio),'+convert(varchar(20),@idEntrega)+','+convert(varchar(20),@formato)+')'

	exec(@entrega)
	exec(@archivo)
	set @idArchivo=0
	set @idArchivo=(SELECT @@IDENTITY AS [@@IDENTITY])
	--print cast(@idArchivo as varchar)
	set @entrega ='update entrega set archivo_id='+convert(varchar(20),@idArchivo)+' where id='+convert(varchar(20),@idEntrega)

	set @entrega='update entrega set archivo_id='+convert(varchar(20),@idArchivo)+' where id='+convert(varchar(20),@idEntrega)
	exec(@entrega)
		--print @nombreArchivo
		--return 
	GO

--exec sp_nuevaEntrega 1,2,1,'DIGESTYC_R1_P2'
--exec sp_nuevaEntrega 1,1,1

--=====================

--Creador: Douglas Alfaro
--Comentario: Funcion para crear el codigo del documento subido acorde a formato Institucion_idRegistro_idPeriodo
--Fecha: 20200804
go
create FUNCTION Fn_EntregaArchivoNombre(@registro_id int,@periodo_id int )
returns varchar(1000)
as begin
	declare @institucion varchar(300),@registro varchar(300),@periodo varchar(300),@nombreArchivo varchar(500)

	set @institucion=(select nombre from institucion where id=(Select institucion_id from registro where id=@registro_id))
	set @registro=(Select CONVERT(varchar(10),id) from registro WHERE ID=@registro_id)
	set @periodo=(select CONVERT(varchar(10),id) from periodo where id=@periodo_id)
	set @nombreArchivo=@institucion+'_R'+@registro+'_P'+@periodo

	return @nombreArchivo
END
go
--select dbo.Fn_EntregaArchivoNombre(1,1)

--=====================

--Creador: Douglas Alfaro
--Comentario: Funcion verificar si existe archivo cargado
--Fecha: 20200804
use digestyc
go
SET IDENTITY_INSERT [seguridad].permiso ON
go
create FUNCTION Fn_VerificarEntregaArchivo(@registro_id int,@periodo_id int)
returns INT
as begin
	declare @nombreArchivo varchar(500), @e int,@i int

	set @e =(select count(*) from entrega where periodo_id=@periodo_id and registro_id=@registro_id and archivo_id is not null and fecha_entrega is not null );
	set @i=1;
	if @e=0 -- NO EXISTE ARCHIVO CARGADO 
	begin
		set @i=0;
	end

	return  @i
END
go

--=====================

--Creador: Grupo05

USE digestyc
go

create procedure sp_cambioEtapa @registro_id int,@periodo_id int,@comentario varchar(500)
as
	declare @etapa int , @estadoEntrega int,@futuraEntrega varchar(500),@pasadaEntrega varchar(500),@idEntrega int,@idArchivo int

	set @idEntrega=(select id from entrega where periodo_id=@periodo_id and registro_id= @registro_id )
	
	set @estadoEntrega=(select top 1 estado_entrega_id from historial_estado_entrega where entrega_id=@idEntrega and actual=1)
	set @etapa=(select top 1 id from historial_estado_entrega where entrega_id=@idEntrega and actual=1)
	set @pasadaEntrega ='update historial_estado_entrega set actual=0 where id='+convert(varchar(50),@etapa)
	exec(@pasadaEntrega)
	set @estadoEntrega=@estadoEntrega+1
	if @estadoEntrega=2
	begin
		set @estadoEntrega=@estadoEntrega+1
	end
	set @futuraEntrega = 'insert into historial_estado_entrega(actual,comentario,fecha_registro,entrega_id,estado_entrega_id) values(1,'+CHAR(39)+@comentario+CHAR(39)+',getdate(),'+convert(varchar(50),@idEntrega)+','+convert(varchar(50),@estadoEntrega)+')'
	exec(@futuraEntrega)


--=============================================


--Creador: Douglas Alfaro
--Comentario: Funcion para crear codigo de registro segun el tipo de registro
--Fecha: 20200913
USE [Digestyc]
GO
/****** Object:  UserDefinedFunction [dbo].[Fn_CodigoRegistro]    Script Date: 19/11/2020 21:17:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER FUNCTION [dbo].[Fn_CodigoRegistro](@tipo int)-- 1 RA , 2 OE
returns varchar(250)
as begin
	declare @codigo varchar(500),@Mcodigo varchar(500),@McodigoNum int,@i int,@l varchar(2)
	if @tipo=1 begin set @l='RA' end
	if @tipo=2 begin set @l='OE' end

	if((select count(*) from registro where tipo_registro_id=@tipo)!=0)
	
		begin

			set @Mcodigo =(select codigo from registro where id=(select max(id) from registro where tipo_registro_id=@tipo )); --Se selecciona el codigo del max registro id
			set @Mcodigo=(REPLACE(@Mcodigo, @l, ''))
			set @McodigoNum=(select cast(@Mcodigo AS INT))
		end
	else
		begin
			if @tipo=1
				begin
					set @McodigoNum=0
				end
			if @tipo=2
				begin 

					set @McodigoNum=0
				end

		end
	aumentar:

	set @McodigoNum= @McodigoNum+1
	set @codigo=(@l+CONVERT(varchar(200),@McodigoNum))
	set @i=(select count(*) from registro where codigo= @codigo)
	if (@i=0)--Valida que el codigo no exista 
	begin
	return @codigo
	end

	goto aumentar

	return 0

END
--select [dbo].[Fn_CodigoRegistro](1)
--=======
	
--Creador: Grupo05
--Comentario: Actualizar el ID de la Version de la plantilla usada en determinada entrega
--Fecha: 2020-09-14	
	
use digestyc
go
create procedure sp_entregaVersionPlantillaUsada @entrega_id int, @version_plantilla_id int
as
	declare @entrega varchar(500),@idEntrega int

	--set @nombreArchivo=(select dbo.Fn_EntregaArchivoNombre(@registro_id,@periodo_id))
	set @idEntrega=(select id from entrega e where id=@entrega_id)
	PRINT @idEntrega
	set @entrega='update entrega set version_plantilla_id='+CONVERT(varchar(20),@version_plantilla_id) +' where id='+convert(varchar(20),@idEntrega)
	exec(@entrega) 
	GO


--select dbo.Fn_CodigoRegistro()
--=======
	
--Creador: Grupo05
--Comentario: Actualizar el ID de la Version de la plantilla usada en determinada entrega
--Fecha: 2020-09-14		
use digestyc
go
create procedure sp_entregaVersionPlantillaUsada @entrega_id int, @version_plantilla_id int
as
	declare @entrega varchar(500),@idEntrega int

	--set @nombreArchivo=(select dbo.Fn_EntregaArchivoNombre(@registro_id,@periodo_id))
	set @idEntrega=(select id from entrega e where id=@entrega_id)
	PRINT @idEntrega
	set @entrega='update entrega set version_plantilla_id='+CONVERT(varchar(20),@version_plantilla_id) +' where id='+convert(varchar(20),@idEntrega)
	exec(@entrega) 
	GO
	
	
	
--select dbo.Fn_CodigoRegistro()
--=======
	
--Creador: Grupo05
--Comentario: Actualizar el ID de la Version de la plantilla usada en determinada entrega
--Fecha: 2020-09-14		
use digestyc
go
create procedure sp_borrarDatosEntrega @registro_id int,@entrega_id int
as
	declare @codigo varchar(500),@consulta varchar(500)

	set @codigo =(Select codigo from registro where id=@registro_id)
	set @consulta='delete from '+@codigo+' where registro_id='+convert(varchar(250),@registro_id)+' and entrega_id='+convert(varchar(250),@entrega_id)

	exec(@consulta)

	GO

--===================================
	
--Creador: Grupo05
--Comentario: Funcion para crear codigo de version plantilla segun la plantilla.
--Fecha: 2020-09-14	
use digestyc
go
CREATE FUNCTION [dbo].[Fn_CodVersionPlantilla](@plantilla int)
returns varchar(250)
as begin
	declare @codigo varchar(500),@Mcodigo varchar(500),@McodigoNum int,@i int
	
		set @Mcodigo =(select codigo from version_plantilla where id=(select max(id) from version_plantilla where plantilla_id=@plantilla));
		if((select count(*) from version_plantilla where plantilla_id=@plantilla)!=0)
			begin
				set @Mcodigo='Version0'
		end
		set @Mcodigo=(REPLACE(@Mcodigo, 'Version', ''))
		set @McodigoNum=(select cast(@Mcodigo AS INT))
	 	aumentar:
		set @McodigoNum= @McodigoNum+1
		set @codigo=('Version'+CONVERT(varchar(200),@McodigoNum))
		set @i=(select count(*) from version_plantilla where codigo=@codigo and plantilla_id=@plantilla)
		if (@i=0)--Valida que el codigo no exista 
		begin
		return @codigo
		end

	goto aumentar
	return 0

END
