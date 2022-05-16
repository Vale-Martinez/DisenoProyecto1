package Controlador.Consola;



import java.sql.Date;
import java.sql.ResultSet;

import Persistencia.Conexion;
import logicadeNegocios.*;

public class Creaciones {

	Persona persona;
	Cuenta cuenta;
	static long miliseconds = System.currentTimeMillis();
	static Date date = new Date(miliseconds);

	public static String crearCliente(String nombre, String apellido1, String apellido2, Date fechaNacimiento,
			int identificacion, int numTelefonico, String email) {
		Persona personaTemp = new Persona(nombre, apellido1, apellido2, fechaNacimiento, identificacion, numTelefonico,
				email);
		int cont = 0;

		if (!Validacion.ExpresionRegular.validarEmail(email)
				|| !Validacion.ExpresionRegular.validarTelefono(numTelefonico)) {
			return "Ingreso los datos correctamente.";
		}
		String consulta = "insert into Persona (identificacion,nombre,apellido1,apellido2,fechaNacimiento,numeroTel,email) VALUES ("
				+ identificacion + ",'" + nombre + "','" + apellido1 + "','" + apellido2 + "','" + fechaNacimiento
				+ "'," + numTelefonico + ",'" + email + "');";
		Conexion.agregar(consulta);

		String consulta1 = "select count(identificacion)  from Cliente";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			while (res.next()) {
				cont = res.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String codigo = "CIF_" + (cont + 1);
		String consulta2 = "insert into Cliente(identificacion,codigo) VALUES (" + identificacion + ",'" + codigo
				+ "')";
		Conexion.agregar(consulta2);

		String resultado = "Se ha creado un nuevo cliente en el sistema, los datos que fueron almacenados son:\n"
				+ "Codigo:" + codigo + "\n" + "Nombre:" + nombre + "\n" + "Identificacion:" + identificacion + "\n"
				+ "Fecha de Nacimiento:" + fechaNacimiento + "\n" + "Numero Telefonico:" + numTelefonico + "\n";

		return resultado;
	}

	public static String crearCuenta(String codigoCliente, double saldo, String estatus, String pin) {
		String nombre = "", email = "";
		int numTel = 0;
		String idC = "";
		if (!Validacion.ExpresionRegular.validarPin(pin)) {
			return "Ingreso un pin valido.";
		}

		String consulta1 = "Select Persona.nombre,Persona.numeroTel,Persona.email from Cliente join Persona on Persona.identificacion = Cliente.identificacion Where Cliente.codigo='"
				+ codigoCliente + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				nombre = res.getString(1);
				numTel = res.getInt(2);
				email = res.getString(3);
			} else {
				return "Ingrese un codigo de cliente valido.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int cont = 0;
		String consulta2 = "select count(idC)  from Cuenta";
		try {
			ResultSet res = Conexion.consulta(consulta2);
			while (res.next()) {
				cont = res.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		idC = "CN_" + (cont + 1);

		String saldoEncriptado = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(saldo));
		String pinEncriptado = Atenticacion.Encriptacion.CesarCifradoabc(pin);
		String idCEncriptado = Atenticacion.Encriptacion.CesarCifradoabc(idC);
		String consulta = "insert into Cuenta (idC,dueno,fechaCreacion,saldo,pin,estatus) VALUES ('" + idCEncriptado
				+ "'," + "'" + codigoCliente + "','" + date + "','" + saldoEncriptado + "','" + pinEncriptado
				+ "','Activa');";
		Conexion.agregar(consulta);

		String resultado = "Se ha creado una nueva cuenta en el sistema, los datos de la cuenta son:\n"
				+ "Numero de cuenta: " + idC + "\n" + "Estatus de cuenta: Activa \n" + "Saldo Actual de cuenta: "
				+ saldo + "\n" + "Nombre del dueno: " + nombre + "\n" + "Numero de telefono asociado a la cuenta: "
				+ numTel + "\n" + "Direccion de correo electronico asociado a la cuenta: " + email + "\n";
		return resultado;
	}

	public static void registrarOperacion(String numeroCuenta, String tipo, boolean cargoComision, float cobroComision,
			int monto) {
		int cont = 0;
		String consulta2 = "select count(idOp)  from Operacion";
		try {
			ResultSet res = Conexion.consulta(consulta2);
			while (res.next()) {
				cont = res.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String idOp = "OP_" + (cont + 1);

		double cantidadCobro= cobroComision;
		String consulta = "insert into Operacion (idOp,cuentaAsociada,fecha,tipo,cargoComision,cantidadCobro,monto) VALUES('"
				+ idOp + "','" + numeroCuenta + "','" + date + "','" + tipo + "','" + String.valueOf(cargoComision)
				+ "'," +cantidadCobro  + ","+monto+");";
		Conexion.agregar(consulta);
		System.out.println("ingreso op");
	}
}
