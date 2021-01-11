package com.sif.digestyc.Test;

import java.sql.SQLException;

import com.sif.digestyc.Service.BulkInsertion.TestHuman;

public class TestHumanInsert {

	public static void main(String[] args) {
		TestHuman human =  new TestHuman();
		human.abrirConexion();
		try {
			human.bulkInsertPersonDataTest();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		human.cerrarConexion();
	}

}
