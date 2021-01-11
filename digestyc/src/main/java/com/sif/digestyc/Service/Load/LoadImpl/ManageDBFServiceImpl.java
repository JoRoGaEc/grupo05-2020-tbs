package com.sif.digestyc.Service.Load.LoadImpl;

import java.io.*;

import org.springframework.stereotype.Service;

import com.linuxense.javadbf.*;
import com.sif.digestyc.Service.Load.*;

@Service("dbfServiceImpl")
public class ManageDBFServiceImpl implements ManageArchivoService {

	@Override
	public void leerArchivo(String rutaArchivo) {
		DBFReader reader = null;
		try {
			String rutaArchivoExcel = "C://books.dbf";
			// create a DBFReader object
			reader = new DBFReader(new FileInputStream(rutaArchivoExcel));

			// get the field count if you want for some reasons like the following

			int numberOfFields = reader.getFieldCount();

			// use this count to fetch all field information
			// if required

			for (int i = 0; i < numberOfFields; i++) {

				DBFField field = reader.getField(i);

				// do something with it if you want
				// refer the JavaDoc API reference for more details
				//
				System.out.println(field.getName());
			}

			// Now, lets us start reading the rows

			Object[] rowObjects;

			while ((rowObjects = reader.nextRecord()) != null) {

				for (int i = 0; i < rowObjects.length; i++) {
					System.out.println(rowObjects[i]);
				}
			}

			// By now, we have iterated through all of the rows

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DBFUtils.close(reader);
		}

	}

}
