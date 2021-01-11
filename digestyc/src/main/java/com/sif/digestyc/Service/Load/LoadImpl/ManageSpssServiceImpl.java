package com.sif.digestyc.Service.Load.LoadImpl;

import org.springframework.stereotype.Service;

import com.bedatadriven.spss.SpssDataFileReader;
import com.bedatadriven.spss.SpssVariable;
import com.sif.digestyc.Service.Load.*;
@Service("spssServiceImpl")
public class ManageSpssServiceImpl implements ManageArchivoService {

	@Override
	public void leerArchivo(String rutaArchivo) {
		String ruta = "C://PRUEBA2018.sav";

		try {
			SpssDataFileReader reader = new SpssDataFileReader(ruta);
			System.out.println("Este es el numero de filas");
			System.out.println(reader.getNumCases());

			for (SpssVariable var : reader.getVariables()) {
				// con esto recupera los nombres de las columnas
				System.out.println(var.getVariableName());
			}

			System.out.println("Este es el numero de columnas ");
			System.out.println(reader.getVariables().size());

			// Read the cases
			System.out.println("Este es la lectura del arhivo ");
			/*
			 * while (reader.readNextCase()) { // para verificar los datos puede utilizar el
			 * programa PSPP (Es gratuito) String var0 =
			 * String.valueOf(reader.getDoubleValue(0)); // Si no se ocupa bien el tipo
			 * imprime Null String var1 = reader.getStringValue(1); // Si no se ocupa bien
			 * el tipo imprime Null double var2 = reader.getDoubleValue(2); // Si no se
			 * ocupa bien el tipo imprime Null System.out.println("" + var0 + " , " + var1 +
			 * " , " + var2); }
			 */
			int numeroColumnas = reader.getVariables().size();
			while (reader.readNextCase()) {
				for (int j = 0; j < numeroColumnas; j++) {
					String dato = null;
					if (reader.getStringValue(j) != null) {
						dato = reader.getStringValue(j);
					} else {
						dato = String.valueOf(reader.getDoubleValue(j));
					}
					System.out.println(dato + " , " + j);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
