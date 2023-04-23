package prog.ud09.relacion02.ejercicio01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar la base de datos empresa
 * 
 * @author alumnado
 *
 */
public class ServicioAlmacenamientoEmpresa {

	private static final String CENTRO_NUMCE = "numce";
	private static final String CENTRO_NOMCE = "nomce";

	private static final String DEP_NUMDE = "numde";
	private static final String DEP_DIREC = "direc";
	private static final String DEP_TIDIR = "tidir";
	private static final String DEP_PRESU = "presu";
	private static final String DEP_DEPDE = "depde";
	private static final String DEP_NOMDE = "nomde";

	private static final String ERROR_CERRAR_CONEXION = "Ocurrió un error al cerrar la conexión";

	private String rutaDb;

	public ServicioAlmacenamientoEmpresa(String rutaDb) {
		setRutaDb(rutaDb);
	}

	public String getRutaDb() {
		return rutaDb;
	}

	public void setRutaDb(String rutaDb) {
		this.rutaDb = rutaDb;
	}

	/**
	 * Busca centro haciendo una consulta y filtrando por codigo dado
	 * 
	 * @param codigo del centro
	 * @return objeto Centro
	 */
	public Centro buscaCentroPorCodigo(int codigo) {
		Centro centro = null;
		Connection conexion = null;
		try {
			// Conexion a la base de datos
			conexion = DriverManager.getConnection(rutaDb);
			String sqlSelect = "SELECT * FROM centro WHERE " + CENTRO_NUMCE + " = ?";
			// Obtener sentencia
			PreparedStatement sentencia = conexion.prepareStatement(sqlSelect);
			sentencia.setInt(1, codigo);
			// Ejecutar sentencia
			ResultSet resultado = sentencia.executeQuery();
			// Recorremos la consulta
			while (resultado.next()) {
				// Recuperamos resultados de la consulta
				int numceEncontrado = resultado.getInt(CENTRO_NUMCE);
				String nomceEncontrado = resultado.getString(CENTRO_NOMCE);
				// Se los asignamos a un objeto creado tipo Centro
				centro = new Centro(numceEncontrado, nomceEncontrado);
			}
			resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cerrar conexión siempre
				conexion.close();
			} catch (SQLException e) {
				System.err.println(ERROR_CERRAR_CONEXION + e);
			}
		}
		return centro;
	}

	/**
	 * Selecciona todos los centros y los devuelve en forma de lista
	 * 
	 * @param ordenar de forma ascendente la select en caso de true
	 * @return lista de centros, resultado de la select
	 */
	public List<Centro> buscaTodos(boolean ordenar) {
		List<Centro> listaCentros = new ArrayList<>();

		Connection conexion = null;
		try {
			// Conexion a la base de datos
			conexion = DriverManager.getConnection(rutaDb);
			String sqlSelect = "SELECT * FROM centro";
			// Si elige ordenar
			if (ordenar) {
				sqlSelect = sqlSelect + " ORDER BY " + CENTRO_NUMCE + " ASC";
			}
			// Obtener sentencia
			Statement sentencia = conexion.createStatement();
			// Ejecutar sentencia
			ResultSet resultado = sentencia.executeQuery(sqlSelect);
			// Recorremos la consulta
			while (resultado.next()) {
				Centro centro = new Centro(resultado.getInt(CENTRO_NUMCE), resultado.getString(CENTRO_NOMCE));
				listaCentros.add(centro);
			}
			resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cerrar conexión siempre
				conexion.close();
			} catch (SQLException e) {
				System.err.println(ERROR_CERRAR_CONEXION + e);
			}
		}
		return listaCentros;
	}

	/**
	 * Inserta un centro en la tabla centro
	 * 
	 * @param centro
	 * @throws ServicioAlmacenamientoException si ya existe el centro
	 */
	public void insertaCentro(Centro centro) throws ServicioAlmacenamientoException {
		Connection conexion = null;
		try {
			// Conexion a la base de datos
			conexion = DriverManager.getConnection(rutaDb);
			// Comprobar que no existe
			Statement sentenciaComprobar = conexion.createStatement();
			// Ejecutar select para comprobar
			int resultadoComprobacion = sentenciaComprobar
					.executeUpdate("SELECT * FROM centro WHERE " + CENTRO_NUMCE + " = " + centro.getNumce());
			// Si da 0 (no hay resultados)
			if (resultadoComprobacion == 0) {
				String sqlInsert = "INSERT INTO centro (" + CENTRO_NUMCE + ", " + CENTRO_NOMCE + ") VALUES ("
						+ centro.getNumce() + ",'" + centro.getNomce() + "')";
				// Crear sentencia
				Statement sentencia = conexion.createStatement();
				// Ejecutar sentencia
				sentencia.executeUpdate(sqlInsert);
				System.out.println("Inserción realizada correctamente.");
			} else {
				throw new ServicioAlmacenamientoException("Ya existe un centro con el numce dado ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cerrar conexión siempre
				conexion.close();
			} catch (SQLException e) {
				System.err.println(ERROR_CERRAR_CONEXION + e);
			}
		}
	}

	public void actualizaCentro(Centro centro) throws ServicioAlmacenamientoException {
		Connection conexion = null;
		try {
			// Conexion a la base de datos
			conexion = DriverManager.getConnection(rutaDb);
			// Comprobar si existe el centro
			Statement sentenciaComprobar = conexion.createStatement();
			// Ejecutar select para comprobar
			int resultadoComprobacion = sentenciaComprobar
					.executeUpdate("SELECT * FROM centro WHERE " + CENTRO_NUMCE + " = " + centro.getNumce());
			// Si no da 0 (hay resultados)
			if (resultadoComprobacion != 0) {
				Statement sentenciaUpdate = conexion.createStatement();

				System.out.println("Actualización realizada correctamente.");
			} else {
				throw new ServicioAlmacenamientoException("Ya existe un centro con el numce dado ");
			}
			// Sentencia
			String sqlUpdate = "UPDATE pueblos SET ";
			PreparedStatement sentencia = conexion.prepareStatement(sqlUpdate);
			sentencia.executeUpdate();
			System.out.println("Modificación realizada con éxito.");
		} catch (SQLException e) {
			System.err.println("Ocurrió un error modificando la población " + e);
		} finally {
			try {
				// Cerrar conexión siempre
				conexion.close();
			} catch (SQLException e) {
				System.err.println(ERROR_CERRAR_CONEXION + e);
			}
		}
	}

	/**
	 * Elimina el centro de la tabla centro
	 * 
	 * @param codigo del centro a eliminar
	 */
	public void eliminaCentroPorCodigo(int codigo) {
		Connection conexion = null;
		try {
			// Conexion a la base de datos
			conexion = DriverManager.getConnection(rutaDb);
			// Sentencia
			String sqlUpdate = "DELETE FROM centro WHERE " + CENTRO_NUMCE + " = ?";
			PreparedStatement sentencia = conexion.prepareStatement(sqlUpdate);
			sentencia.setInt(1, codigo);
			// Ejecuta sentenc
			int resultado = sentencia.executeUpdate();
			if (resultado == 1) {
				System.out.println("Eliminación realizada con éxito.");
			} else {
				System.out.println("No existe centro con el código dado.");
			}
		} catch (SQLException e) {
			System.err.println("Ocurrió un error eliminando el centro " + e);
		} finally {
			try {
				// Cerrar conexión siempre
				conexion.close();
			} catch (SQLException e) {
				System.err.println(ERROR_CERRAR_CONEXION + e);
			}
		}
	}

	/**
	 * Haze select en la tabla departamento
	 * 
	 * @param codigo como condicion en la select
	 * @return Departamento encontrado
	 */
	public Departamento buscaDepartamentoPorCodigo(int codigo) {
		Connection conexion = null;
		Departamento dep = null;

		try {
			// Conexion
			conexion = DriverManager.getConnection(rutaDb);
			// Sentencia sql
			String sqlSelect = "SELECT * FROM departamento WHERE " + DEP_NUMDE + " = ?";
			// Creacion sentencia
			PreparedStatement sentencia = conexion.prepareStatement(sqlSelect);
			sentencia.setInt(1, codigo);
			ResultSet resultado = sentencia.executeQuery();

			while (resultado.next()) {
				dep = new Departamento(resultado.getInt(DEP_NUMDE), resultado.getInt(CENTRO_NUMCE),
						resultado.getString(DEP_DIREC), resultado.getInt(DEP_TIDIR), resultado.getInt(DEP_PRESU),
						resultado.getInt(DEP_DEPDE), resultado.getString(DEP_NOMDE));
			}
			resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dep;

	}

	/**
	 * Busca todos los departamentos de la tabla departamento
	 * 
	 * @param ordenar por nombre ascendente o no
	 * @return lista de departamentos
	 */
	public List<Departamento> buscaTodosDepartamentos(boolean ordenar) {
		List<Departamento> listaDepartamentos = new ArrayList<>();

		Connection conexion = null;
		Departamento dep = null;

		try {
			// Conexion
			conexion = DriverManager.getConnection(rutaDb);
			// Sentencia sql
			Statement sentencia = conexion.createStatement();
			String sqlSelect = "SELECT * FROM departamento";

			if (ordenar) {
				sqlSelect = sqlSelect + " ORDER BY " + DEP_NOMDE + " ASC";
			}

			ResultSet resultado = sentencia.executeQuery(sqlSelect);

			while (resultado.next()) {
				dep = new Departamento(resultado.getInt(DEP_NUMDE), resultado.getInt(CENTRO_NUMCE),
						resultado.getString(DEP_DIREC), resultado.getInt(DEP_TIDIR), resultado.getInt(DEP_PRESU),
						resultado.getInt(DEP_DEPDE), resultado.getString(DEP_NOMDE));
				listaDepartamentos.add(dep);
			}
			resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaDepartamentos;
	}

	/**
	 * Inserta un departamento a la tabla departamento
	 * 
	 * @param dep a insertar
	 * @throws ServicioAlmacenamientoException si ya existe el departamento
	 */
	public void insertaDepartamento(Departamento dep) throws ServicioAlmacenamientoException {
		Connection conexion = null;

		try {
			// Conexion
			conexion = DriverManager.getConnection(rutaDb);
			// Comprobación
			Statement sentenciaComprobacion = conexion.createStatement();
			String sqlComprobacion = "SELECT * FROM departamento WHERE numde = " + dep.getNumde();
			ResultSet resultado = sentenciaComprobacion.executeQuery(sqlComprobacion);

			// Si la consulta esta vacía (no existe el centro)
			if (!resultado.next()) {
				// Sentencia sql
				Statement sentencia = conexion.createStatement();
				String sqlInsert = "INSERT INTO departamento (numde, numce, direc, tidir, presu, depde, nomde) VALUES ("
						+ dep.getNumde() + ", " + dep.getNumce() + ", '" + dep.getDirec() + "', " + dep.getTidir() + ", "
						+ dep.getPresu() + ", " + dep.getDepde() + ", '" + dep.getNomde() + "')";
				sentencia.executeUpdate(sqlInsert);
			} else {
				throw new ServicioAlmacenamientoException("Ya existe un departamento con ese munde");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Actualiza un departamento de la tabla departamento
	 * 
	 * @param codigo del departamento a actualizar
	 * @param nombre para cambiar del departamento
	 */
	public void actualizaDepartamento(int codigo, String nombre) {
		Connection conexion = null;

		try {
			// Conexion
			conexion = DriverManager.getConnection(rutaDb);
			// Comprobación
			Statement sentenciaComprobacion = conexion.createStatement();
			String sqlUpdate = "UPDATE departamento SET " + DEP_NOMDE + " = '" + nombre + "' WHERE numde = " + codigo;
			int resultado = sentenciaComprobacion.executeUpdate(sqlUpdate);

			if (resultado != 1) {
				System.err.println("No se ha encontrado departamento con el código " + codigo);
			} else {
				System.out.println("Actualizacion realizada con exito.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Elimina un departamento de la tabla departamento
	 * 
	 * @param codigo campo numde de la tabla departamento
	 * @throws ServicioAlmacenamientoException si no existe ningun departamento con
	 *                                         el codigo dado
	 */
	public void eliminaDepartamentoPorCodigo(int codigo) throws ServicioAlmacenamientoException {
		Connection conexion = null;

		String sqlDelete = "DELETE FROM departamento WHERE " + DEP_NUMDE + " = ?";

		try {
			conexion = DriverManager.getConnection(rutaDb);
			PreparedStatement sentencia = conexion.prepareStatement(sqlDelete);
			sentencia.setInt(1, codigo);

			int resultado = sentencia.executeUpdate();

			if (resultado == 1) {
				System.out.println("Eliminación realizada con éxito");
			} else {
				throw new ServicioAlmacenamientoException("No se ha encontrado departamento con el código " + codigo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
