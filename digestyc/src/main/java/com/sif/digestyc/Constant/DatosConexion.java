package com.sif.digestyc.Constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


//para inyectarlo, es mas eficiente que constantes
@Component
public class DatosConexion {
	/*public static final String JDBC_URL = "jdbc:sqlserver://localhost\\\\DESKTOP-V2N606P\\INSTANCIA01:1433;databaseName=digestyc";
	public static final String JDBC_USER = "sa";
	public static final String JDBC_PASS = "0000";*/
	
	
	// 	public static final String JDBC_URL = "jdbc:sqlserver://localhost\\\\HP\\SQLEXPRESS01:1433;databaseName=digestyc";
	//public static final String JDBC_URL = "jdbc:sqlserver://localhost;databaseName=digestyc";
	//public static final String JDBC_USER = "walter";
	//public static final String JDBC_PASS = "root";
	 
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
