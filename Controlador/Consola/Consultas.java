/**
 * 
 */
package Controlador.Consola;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Persistencia.Conexion;
import Persistencia.DAO;
import Servicios.BCCR;

/**
 * @author valem y nathb
 *
 */
public class Consultas {

	static long miliseconds = System.currentTimeMillis();
	static Date date = new Date(miliseconds);
	static Servicios.BCCR bccr = new BCCR();
	static final String compraDolar = bccr.getCompra();
	static final String ventaDolar = bccr.getVenta();
	static float saldo = 0;

	public static String consultaTipoCambioCompra() {

		String resultado = "Segun el BCCR la compra del dolar para el " + date + " se encuentra en: " + compraDolar;
		return resultado;
	}

	public static String consultaTipoCambioVenta() {
		String resultado = "Segun el BCCR la venta del dolar para el " + date + " se encuentra en: " + ventaDolar;
		return resultado;
	}

	public static String consultaSaldoActualColones(String numeroCuenta, String pin) {
		String saldoBD, NCEncriptada = "";
		String pinBD, pinDesencriptado;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			saldoBD = DAO.buscarCuentaporNumeroSaldo(NCEncriptada);
			saldo = Float.parseFloat(Atenticacion.Encriptacion.CesarDescifradoabc(saldoBD));

		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}

		String resultado = "Estimado usuario el saldo actual de su cuenta es " + saldo + " colones.";
		return resultado;
	}

	public static String consultaSaldoActualCambioMoneda(String numCuenta, String pin) {

		consultaSaldoActualColones(numCuenta, pin);
		float saldoDolar = saldo / Float.parseFloat(ventaDolar);

		String resultado = "Estimado usuario el saldo actual de su cuenta es " + saldoDolar + " dólares.\n"
				+ "Para esta conversión se utilizó el tipo de cambio del dólar, precio de venta.\n"
				+ "[Según el BCCR, el tipo de cambio de venta del dólar de hoy es: " + ventaDolar + "]";
		return resultado;
	}

	public static String listarClientes() {
		String resultado = "";
		Vector titulo = new Vector();
		titulo.add("Primero Apelido");
		titulo.add("Segundo Apellido");
		titulo.add("Nombre");
		titulo.add("Identificacion");
		resultado += titulo + "\n";
		Vector clientes = listarDatosClientes();
		for (int i = 0; i < clientes.size(); i++) {
			resultado += clientes.get(i).toString() + "\n";
		}
		return resultado;
	}

	public static void cargarClientes() {
		DefaultTableModel modelo = (DefaultTableModel) Vista.GUI.ConsultarClientes.jTable1.getModel();
		modelo.setRowCount(0);
		Vector list = listarDatosClientes();
		for (int i = 0; i < list.size(); i++) {
			modelo.addRow((Vector<?>) list.get(i));
		}
	}

	public static Vector listarDatosClientes() {
		ResultSet list = DAO.listadoClientes();
		Vector clientes = new Vector();
		try {
			while (list.next()) {
				Vector v = new Vector();
				v.add(list.getString(1));
				v.add(list.getString(2));
				v.add(list.getString(3));
				v.add(list.getInt(4));
				clientes.add(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientes;
	}

	public static String datosClienteEspecificos(int identificacion) {

		String resultado = "Cliente solicitado:" + "\n";
		ResultSet list = DAO.listadoUnClienteEspecifico(identificacion);
		try {
			while (list.next()) {
				resultado += "Identificacion : " + list.getInt(1) + "\n";
				resultado += "Nombre : " + list.getString(2) + "\n";
				resultado += "Primer Apellido : " + list.getString(3) + "\n";
				resultado += "Segundo Apellido : " + list.getString(4) + "\n";
				resultado += "Fecha Nacimiento : " + list.getDate(5) + "\n";
				resultado += "Numero de Telefono : " + list.getInt(6) + "\n";
				resultado += "Email : " + list.getString(7) + "\n";
				resultado += "Codigo Cliente : " + list.getString(8) + "\n";
				resultado += "Numero de Cuenta : " + Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(9))
						+ "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String listarCuentas() {
		String resultado = "";
		Vector titulo = new Vector();
		titulo.add("Numero de cuenta");
		titulo.add("Estatus");
		titulo.add("Saldo");
		titulo.add("Identificacion");
		titulo.add("Nombre");
		titulo.add("Primer Apelido");
		titulo.add("Segundo Apellido");
		resultado += titulo + "\n";
		Vector cuentas = listarDatosCuentas();
		for (int i = 0; i < cuentas.size(); i++) {
			resultado += cuentas.get(i).toString() + "\n";
		}
		return resultado;

	}
	public static void cargarCuentas() {
		DefaultTableModel modelo = (DefaultTableModel) Vista.GUI.ConsultarCuentas.jTable1.getModel();
		modelo.setRowCount(0);
		Vector list = listarDatosCuentas();
		for (int i = 0; i < list.size(); i++) {
			modelo.addRow((Vector<?>) list.get(i));
		}
	}
	
	public static Vector listarDatosCuentas() {
		ResultSet list = DAO.listadoCuentas();
		Vector cuentas = new Vector();
		try {
			while (list.next()) {
				Vector v = new Vector();
				v.add(Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(1)));
				v.add(list.getString(2));
				v.add(Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(3)));
				v.add(list.getInt(4));
				v.add(list.getString(5));
				v.add(list.getString(6));
				v.add(list.getString(7));
				cuentas.add(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cuentas;
	}

	public static String datosCuentaEspecificas(String numeroCuenta) {

		String resultado = "Cuenta solicitada: " + "\n";
		ResultSet list = DAO.listadoUnaCuentaEspecifica(Atenticacion.Encriptacion.CesarCifradoabc(numeroCuenta));
		try {
			while (list.next()) {
				resultado += "Numero de Cuenta : " + Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(1))
						+ "\n";
				resultado += "Dueño : " + list.getString(2) + "\n";
				resultado += "Fecha creacion : " + list.getDate(3) + "\n";
				resultado += "Saldo Actual : " + Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(4)) + "\n";
				resultado += "Estatus: " + list.getString(5) + "\n";
				resultado += "Cantidad de operaciones realizadas : " + list.getInt(6) + "\n";
				resultado += "Identificacion del Dueño : " + list.getInt(7) + "\n";
				resultado += "Nombre : " + list.getString(8) + "\n";
				resultado += "Primer Apellido : " + list.getString(9) + "\n";
				resultado += "Segundo Apellido : " + list.getString(10) + "\n";
			}
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String consultarEstadoCuentaColones(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuenta(NCEncriptada) + "\n";
			Vector titulo = new Vector();
			titulo.add("Id Operacion");
			titulo.add("Numero cuentaAsociado");
			titulo.add("Fecha");
			titulo.add("Tipo");
			titulo.add("Cargo Comision");
			titulo.add("Cantidad cobrada por comision");
			titulo.add("Monto");
			resultado += titulo.toString() + "\n";
			Vector operaciones = consultarDatosEstadoCuentaOperaciones(NCEncriptada);
			for (int i = 0; i < operaciones.size(); i++) {
				resultado += operaciones.get(i).toString() + "\n";
			}
			return resultado;

		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}
	public static String consultarEstadoCuentaColonesInterfaz(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuenta(NCEncriptada) + "\n";
			cargarOperaciones(NCEncriptada);
			return resultado;
		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}

	public static void cargarOperaciones(String NCEncriptada) {
		DefaultTableModel modelo = (DefaultTableModel) Vista.GUI.ConfirmacionEstadoCuentaCol.jtable.getModel();
		modelo.setRowCount(0);
		Vector list = consultarDatosEstadoCuentaOperaciones(NCEncriptada);
		for (int i = 0; i < list.size(); i++) {
			modelo.addRow((Vector<?>) list.get(i));
		}
	}
	
	public static String consultarEstadoCuentaColonesWeb(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuenta(NCEncriptada) + "\n";
			return resultado;
		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}
	
	public static Vector consultarDatosEstadoCuentaOperaciones(String NCEncriptada) {
		ResultSet list = DAO.infoEstadoCuentaOperaciones(NCEncriptada);
		Vector operaciones = new Vector();
		try {
			while (list.next()) {
				Vector v = new Vector();
				v.add(list.getString(1));
				v.add(Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(2)));
				v.add(list.getDate(3));
				v.add(list.getString(4));
				v.add(list.getString(5));
				v.add(list.getInt(6));
				v.add(list.getInt(7));
				operaciones.add(v);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return operaciones;
	}

	public static String consultarDatosEstadoCuenta(String NCEncriptada) {
		String resultado = "";
		ResultSet list = DAO.infoEstadoCuenta(NCEncriptada);
		try {
			while (list.next()) {
				resultado += "Numero de cuenta: " + Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(1))
						+ "\n";
				resultado += "Codigo del dueño: " + list.getString(2) + "\n";
				resultado += "Fecha de creacion: " + list.getDate(3) + "\n";
				resultado += "Saldo Actual en colones: "
						+ Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(4)) + "\n";
				resultado += "PIN : " + list.getString(5) + "\n";
				resultado += "Estatus : " + list.getString(6) + "\n";
				resultado += "Cantidad de operaciones realizadas sobre la cuenta : " + list.getInt(7) + "\n";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String consultarEstadoCuentaDolares(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuentaDolares(NCEncriptada) + "\n";
			Vector titulo = new Vector();
			titulo.add("Id Operacion");
			titulo.add("Numero cuentaAsociado");
			titulo.add("Fecha Creaciones");
			titulo.add("Tipo");
			titulo.add("Cargo Comision");
			titulo.add("Cantidad cobrada por comision");
			titulo.add("Monto");
			resultado += titulo.toString() + "\n";
			Vector operaciones = consultarDatosEstadoCuentaOperacionesDolares(NCEncriptada);
			for (int i = 0; i < operaciones.size(); i++) {
				resultado += operaciones.get(i).toString() + "\n";
			}
			return resultado;

		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}

	public static Vector consultarDatosEstadoCuentaOperacionesDolares(String NCEncriptada) {
		ResultSet list = DAO.infoEstadoCuentaOperaciones(NCEncriptada);
		Vector operaciones = new Vector();
		try {
			while (list.next()) {
				Vector v = new Vector();
				v.add(list.getString(1));
				v.add(Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(2)));
				v.add(list.getDate(3));
				v.add(list.getString(4));
				v.add(list.getString(5));
				v.add(list.getInt(6));
				v.add(list.getInt(7) / Float.parseFloat(ventaDolar));
				operaciones.add(v);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return operaciones;
	}

	public static String consultarDatosEstadoCuentaDolares(String NCEncriptada) {
		String resultado = "";
		ResultSet list = DAO.infoEstadoCuenta(NCEncriptada);
		try {
			while (list.next()) {
				resultado += "Numero de cuenta: " + Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(1))
						+ "\n";
				resultado += "Codigo del dueño: " + list.getString(2) + "\n";
				resultado += "Fecha de creacion: " + list.getDate(3) + "\n";
				resultado += "Saldo Actual en colones: "
						+ Float.parseFloat(Atenticacion.Encriptacion.CesarDescifradoabc(list.getString(4)))
								/ Float.parseFloat(ventaDolar)
						+ "\n";
				resultado += "PIN : " + list.getString(5) + "\n";
				resultado += "Estatus : " + list.getString(6) + "\n";
				resultado += "Cantidad de operaciones realizadas sobre la cuenta : " + list.getInt(7) + "\n";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static String consultarEstadoCuentaDolaresInterfaz(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuentaDolares(NCEncriptada) + "\n";
			cargarOperacionesDolares(NCEncriptada);
			return resultado;
		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}
	public static String consultarEstadoCuentaDolaresWeb(String numeroCuenta, String pin) {
		String NCEncriptada = "";
		String pinBD, pinDesencriptado;
		String resultado = "";

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}
		if (pinDesencriptado.equals(pin)) {
			resultado += consultarDatosEstadoCuentaDolares(NCEncriptada) + "\n";
			return resultado;
		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}
	}
	
	public static void cargarOperacionesDolares(String NCEncriptada) {
		DefaultTableModel modelo = (DefaultTableModel) Vista.GUI.ConfirmacionEstadoCuentaDol.jtable.getModel();
		modelo.setRowCount(0);
		Vector list = consultarDatosEstadoCuentaOperacionesDolares(NCEncriptada);
		for (int i = 0; i < list.size(); i++) {
			modelo.addRow((Vector<?>) list.get(i));
		}
	}

	public static String consultaEstatusActual(String numeroCuenta) {
		String NCEncriptada = "";
		String estatus;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		estatus = DAO.buscarCuentaporNumeroEstatusString(NCEncriptada);

		String resultado = "La cuenta número " + numeroCuenta + " tiene estatus de " + estatus + ".";
		return resultado;
	}

	public static String consultaGananciasPorComisionesTotal() {

		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones es de: "
				+ DAO.cantidadComisionTodasCuentas()+ " colones";
		return resultado;
	}

	public static String consultaGananciasPorComisionesTotalDeposito() {

		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones en Depositos es de: "
				+ DAO.cantidadComisionTodasCuentasDeposito()+ " colones";
		return resultado;
	}

	public static String consultaGananciasPorComisionesTotalRetiro() {

		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones en Retiros es de: "
				+ DAO.cantidadComisionTodasCuentasRetiro()+ " colones";
		return resultado;
	}

	public static String consultaGananciasPorComisionesEspecifica(String numeroCuenta) {
		String NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones es de: "
				+ DAO.cantidadComisionCuentasEspecifica(NCEncriptada)+ " colones";
		return resultado;
	}

	public static String consultaGananciasPorComisionesEspecificaDeposito(String numeroCuenta) {
		String NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones en Depositos es de: "
				+ DAO.cantidadComisionCuentaEspecificaDeposito(NCEncriptada)+ " colones";
		return resultado;
	}

	public static String consultaGananciasPorComisionesEspecificaRetiro(String numeroCuenta) {
		String NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		String resultado = "El total de las ganancias recuperadas por el banco por medio del cobro de comisiones en Retiros es de: "
				+ DAO.cantidadComisionCuentaEspecificaRetiro(NCEncriptada)+ " colones";
		return resultado;
	}
}
