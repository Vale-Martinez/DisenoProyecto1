/**
 * 
 */
package Persistencia;

import java.sql.ResultSet;

/**
 * @author valem y nathb
 *
 */
public class DAO {
	public static String buscarCuentaporNumeroPin(String numeroCuenta) {
		String pin = "";
		String consulta1 = "Select Cuenta.pin from Cuenta join Cliente on Cuenta.dueno = Cliente.codigo Where Cuenta.idC='"
				+ numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				pin = res.getString(1);
				return pin;
			} else {
				return "Ingrese un numero de cuenta valido.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pin;
	}

	public static boolean buscarCuentaporNumeroEstatus(String numeroCuenta) {
		String estatus = "";
		String consulta1 = "Select Cuenta.estatus from Cuenta Where Cuenta.idC='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				estatus = res.getString(1);
				if (estatus.equals("Activa")) {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String buscarCuentaporNumeroEstatusString(String numeroCuenta) {
		String estatus = "";
		String consulta1 = "Select Cuenta.estatus from Cuenta Where Cuenta.idC='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				estatus = res.getString(1);
				return estatus;
			} else {
				return "Cuenta invalida";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String buscarCuentaporNumeroEmail(String numeroCuenta) {
		String email = "";
		String consulta1 = "select Persona.email from Cliente join Persona on Cliente.identificacion = Persona.identificacion join Cuenta on Cuenta.dueno =Cliente.codigo Where Cuenta.idC='"
				+ numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				email = res.getString(1);
				return email;
			} else {
				return "Cuenta invalida";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String buscarCuentaporNumeroSaldo(String numeroCuenta) {
		String saldo = "";
		String consulta1 = "Select Cuenta.saldo from Cuenta Where Cuenta.idC='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				saldo = res.getString(1);
				return saldo;
			} else {
				return "Ingrese un numero de cuenta valido.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saldo;
	}

	public static int buscarCuentaporNumeroCantOperaciones(String numeroCuenta) {
		int cantOperaciones = 0;
		String consulta1 = "Select Cuenta.cantOperaciones from Cuenta Where Cuenta.idC='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cantOperaciones = res.getInt(1);
				return cantOperaciones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cantOperaciones;
	}

	public static void actualizarEstatusCuenta(String NCEncriptada) {
		String consulta2 = "update Cuenta set estatus ='Inactiva' Where Cuenta.idC='" + NCEncriptada + "';";
		try {
			Conexion.agregar(consulta2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void actualizarsaldoCuenta(String NCEncriptada, String saldo, int operacionesGratis) {
		String consulta2 = "update Cuenta set saldo ='" + saldo + "', cantOperaciones = " + operacionesGratis
				+ " Where Cuenta.idC='" + NCEncriptada + "';";
		try {
			Conexion.agregar(consulta2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int buscarCuentaporNumeroTelefono(String NCEncriptada) {
		String consulta1 = "Select Persona.numeroTel from Cuenta join Cliente on Cuenta.dueno = Cliente.codigo join Persona on Cliente.identificacion=Persona.identificacion Where Cuenta.idC='"
				+ NCEncriptada + "';";
		int numTel = 0;
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				numTel = res.getInt(1);
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numTel;
	}

	public static ResultSet infoEstadoCuenta(String numeroCuenta) {
		String consulta1 = "select * from Cuenta Where idC='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet infoEstadoCuentaOperaciones(String numeroCuenta) {
		String consulta1 = "select * from Operacion Where cuentaAsociada='" + numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet listadoClientes() {
		String consulta1 = "Select Persona.apellido1,Persona.apellido2,Persona.nombre,Persona.identificacion from Cliente join Persona on Cliente.identificacion = Persona.identificacion order by Persona.apellido1;";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet listadoUnClienteEspecifico(int identificacion) {
		String consulta1 = "Select Persona.identificacion,Persona.nombre,Persona.apellido1,Persona.apellido2,Persona.fechaNacimiento,Persona.numeroTel,Persona.email,Cliente.codigo,Cuenta.idC from Cliente join Persona on Cliente.identificacion = Persona.identificacion join Cuenta on Cuenta.dueno =Cliente.codigo Where Persona.identificacion= "
				+ identificacion + ";";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet listadoCuentas() {
		String consulta1 = "Select Cuenta.idC,Cuenta.estatus,Cuenta.saldo, Persona.identificacion, Persona.nombre,Persona.apellido1,Persona.apellido2 from Cliente join Persona on Cliente.identificacion = Persona.identificacion join Cuenta on Cuenta.dueno =Cliente.codigo order by Cuenta.saldo ;";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet listadoUnaCuentaEspecifica(String numeroCuenta) {
		String consulta1 = "Select Cuenta.idC,Cuenta.dueno,Cuenta.fechaCreacion,Cuenta.saldo,Cuenta.estatus, Cuenta.cantOperaciones,Persona.identificacion, Persona.nombre,Persona.apellido1,Persona.apellido2 from Cliente join Persona on Cliente.identificacion = Persona.identificacion join Cuenta on Cuenta.dueno =Cliente.codigo Where Cuenta.idc='"
				+ numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int cantidadComisionTodasCuentas() {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}

	public static int cantidadComisionTodasCuentasDeposito() {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion Where Operacion.tipo='Deposito';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}

	public static int cantidadComisionTodasCuentasRetiro() {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion Where Operacion.tipo='Retiro';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}

	public static int cantidadComisionCuentasEspecifica(String numeroCuenta) {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion Where Operacion.cuentaAsociada='"
				+ numeroCuenta + "'; ";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}

	public static int cantidadComisionCuentaEspecificaDeposito(String numeroCuenta) {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion Where Operacion.tipo='Deposito' AND Operacion.cuentaAsociada='"
				+ numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}

	public static int cantidadComisionCuentaEspecificaRetiro(String numeroCuenta) {
		int cobroComisiones = 0;
		String consulta1 = "Select Sum (Operacion.cantidadCobro) from Operacion Where Operacion.tipo='Retiro' AND Operacion.cuentaAsociada='"
				+ numeroCuenta + "';";
		try {
			ResultSet res = Conexion.consulta(consulta1);
			if (res.next()) {
				cobroComisiones = res.getInt(1);
				return cobroComisiones;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cobroComisiones;
	}
}
