package com.sif.digestyc.Service.BulkInsertion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sif.digestyc.Entity.Human;
import com.sif.digestyc.Entity.Mapping.PersonMapping;
import com.sif.digestyc.utilities.ConexionSqlServer;
import com.sif.digestyc.utilities.rendimiento.MeasurementUtils;

import de.bytefish.jsqlserverbulkinsert.SqlServerBulkInsert;

public class TestHuman extends ConexionSqlServer {


	public void bulkInsertPersonDataTest() throws SQLException {
		// Numero de Registros a insertar
		int numEntities = 1000000;
		// Crear la Lista de Humanos
		List<Human> persons = getPersonList(numEntities);
		// Instanciar el Mapeo
		PersonMapping mapping = new PersonMapping();		
		// Crear un insertador masivo
		SqlServerBulkInsert<Human> bulkInsert = new SqlServerBulkInsert<>(mapping);//Le mapeamos los campos que vamos a insertar
		
		// Medir el tiempo de la insercion e insertar al  tiempo
		MeasurementUtils.MeasureElapsedTime("Datos Ingresados", () -> {
			// Now save all entities of a given stream:
			bulkInsert.saveAll(conexion, persons.stream());
		});
		// Para confirmar que se han ingresado los datos, para contar
		// Assert.assertEquals(numEntities, getRowCount());
	}

	private List<Human> getPersonList(int numPersons) {
		List<Human> persons = new ArrayList<>();

		for (int pos = 0; pos < numPersons; pos++) {
			Human p = new Human();
			p.setFirstName("Jose Roberto");
			p.setLastName("Garcia");

			persons.add(p);
			// System.out.println(p);
		}

		return persons;
	}

	/*private int getRowCount() throws SQLException {

		Statement s = conexion.createStatement();

		ResultSet r = s.executeQuery("SELECT COUNT(*) AS total FROM [dbo].[human];");
		r.next();
		int count = r.getInt("total");
		r.close();

		return count;
	}*/
}
