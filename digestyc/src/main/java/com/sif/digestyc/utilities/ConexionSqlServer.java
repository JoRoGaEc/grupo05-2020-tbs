package com.sif.digestyc.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sif.digestyc.Constant.DatosConexion;
import com.sif.digestyc.Controller.Carga.CargaExcelController;

public abstract class ConexionSqlServer implements IAdministradorConexiones{
	protected Connection conexion;
	
	private static final Logger LOG = LoggerFactory.getLogger(ConexionSqlServer.class);
	@Autowired
	DatosConexion datosConexion;

	@Override
	public void abrirConexion() {
		try {
			conexion = DriverManager.getConnection(datosConexion.getUrl(), datosConexion.getUsername(), datosConexion.getPassword());
			conexion.setAutoCommit(true); // Start the Transaction:
		} catch (SQLException e) {
			this.conexion = null;
			e.printStackTrace();
		}
	}

	@Override
	public void cerrarConexion() {
		try {
			this.conexion.close();
		} catch (SQLException e) {
			this.conexion = null;
		}
		this.conexion = null;
	}

	public Connection getCon() {
		return conexion;
	}

	public void setCon(Connection con) {
		this.conexion = con;
	}
	
	
	public boolean crearTablaSiNoExiste(String tabla, List<String> columnas){
		try {
			String sqlStatement = "if not exists (select * from sysobjects where name='"+tabla+"' and xtype='U') CREATE TABLE "+tabla+"(id int identity(1,1) primary key, registro_id bigint, entrega_id bigint, ";
			int total = columnas.size()-1;
			for(int i=0;i<total;i++) {
				sqlStatement += columnas.get(i).replace(" ", "_")+" NVARCHAR(255),";
			}
			sqlStatement += columnas.get(total)+" NVARCHAR(255));";
	        Statement statement = this.conexion.createStatement();
	        System.out.println("STATEMENT  : "+ statement);
	        System.out.println("SQl Smtm  : "+ sqlStatement);
	        return statement.execute(sqlStatement);
		}catch (Exception e) {
			System.out.println("Mensaje dentro de crear tabla" + e.getMessage());
			return false;
		}		
	}
	
	
	
	public boolean addColumn(String tabla, String col){
		this.abrirConexion();
		try {
			String sqlStatement = "IF COL_LENGTH('"+tabla +"', "+"'"+col.toUpperCase().replace(" ", "_") +"') IS NULL"+
								  " BEGIN" +
								  " ALTER TABLE "+ tabla + 
								  " ADD " + col.toUpperCase().replace(" ", "_")+ " NVARCHAR(255)"+
								  " END";
			//String sqlStatement = "alter table "+tabla+" add "+col.toUpperCase().replace(" ", "_")+" NVARCHAR(255)";
	        Statement statement = this.conexion.createStatement();
	        return statement.execute(sqlStatement);
		}catch (Exception e) {
			return false;
		}finally {
			this.cerrarConexion();
		}
	}
	
	
	//map<String, String> key = columna, value = tipodato (NVARCHAR(122)), ETC
	public boolean crearTablaSiNoExiste(String tabla, Map<String, String> columnas){
		this.abrirConexion();
		boolean resultado = false;
		try {
			String sqlStatement = "if not exists (select * from sysobjects where name='"+tabla+"' and xtype='U') CREATE TABLE "+tabla+"(id int identity(1,1) primary key, tabla_id bigint, entrega_id bigint, ";
			Iterator<String> iterador = columnas.keySet().iterator();
			while(iterador.hasNext()) {
				String key = iterador.next().replace(" ", "_"); //tenemos el nombre de la columna
				String valor = columnas.get(key); //tenemos el tipo de dato y la descripcion
				sqlStatement += key+" "+valor+",";
			}
			sqlStatement += ");";
	        Statement statement = this.conexion.createStatement();
	        resultado = statement.execute(sqlStatement);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.cerrarConexion();
		}
		return resultado;
	}
	
	
	
	public boolean addColumn(String tabla, Map<String, String> columnas){
		this.abrirConexion();
		boolean resultado = false;
		try {
			Iterator<String> iterador = columnas.keySet().iterator();
			while(iterador.hasNext()) {
				String key = iterador.next().replace(" ", "_"); //tenemos el nombre de la columna
				String valor = columnas.get(key); //tenemos el tipo de dato y la descripcion
				String sqlStatement = "alter table "+tabla+" add "+key+" "+valor;
		        Statement statement = this.conexion.createStatement();
		        statement.execute(sqlStatement);
			}
			resultado = true;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			this.cerrarConexion();
		}
		return resultado;
	}
	
	
	
}
