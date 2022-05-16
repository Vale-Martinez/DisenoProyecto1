/**
 * 
 */
package Controlador.Consola;

import java.sql.Date;

import Persistencia.*;
import Servicios.BCCR;
import logicadeNegocios.*;

/**
 * @author valem y nathb
 *
 */
public class Operaciones {

	Persona persona;
	Cuenta cuenta;
	Operacion operacion;
	static int operacionesGratis = 0, contadorErroresPalabraSecreta = 2, contadorErroresPin = 2;
	static float cobroComision = 0, montoRebajadoComision = 0;
	static boolean cargoComison = false;
	static long miliseconds = System.currentTimeMillis();
	static Date date = new Date(miliseconds);
	static String palabraSecretaEnviada = "abc12";
	static Servicios.BCCR bccr = new BCCR();

	public static String cambiarPin(String numeroCuenta, String pinA, String pinN) {
		String pinBD, NCEncriptada = "", pin;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pin = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!Validacion.ExpresionRegular.validarPin(pinN)) {
			return "Ingreso un pin con formato valido. Debe contener una mayuscula, una minuscula, un numero y un caracter especial (!#$%*.)";
		}
		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}

		if (pin.equals(pinA)) {
			pinBD = Atenticacion.Encriptacion.CesarCifradoabc(pinN);
		} else {
			contadorErroresPin -= 1;
			validarIntentos(contadorErroresPin, NCEncriptada);
			return "Pin Incorrecto. Un (1) intento restante";
		}

		String consulta2 = "update Cuenta set pin ='" + pinBD + "' Where Cuenta.idC='" + NCEncriptada + "';";
		try {
			Conexion.agregar(consulta2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String resultado = "Estimado usuario, se ha cambiado satisfactoriamente el PIN de su cuenta: " + numeroCuenta;

		return resultado;
	}

	public static String depositoEnColones(String numeroCuenta, int monto) {
		String saldoBD, NCEncriptada = "", saldoN;
		float saldo = 0;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		saldoBD = DAO.buscarCuentaporNumeroSaldo(NCEncriptada);
		saldo = Float.parseFloat(Atenticacion.Encriptacion.CesarDescifradoabc(saldoBD));

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}

		operacionesGratis = DAO.buscarCuentaporNumeroCantOperaciones(NCEncriptada);
		if (operacionesGratis > 3) {
			cobroComision = (float) (monto * 0.02);
			montoRebajadoComision = monto - cobroComision;
			cargoComison = true;
		} else {
			cobroComision = 0;
			montoRebajadoComision = monto;
			cargoComison = false;
		}

		// ingresar operacion en bd
		Creaciones.registrarOperacion(NCEncriptada, "Deposito", cargoComison, cobroComision, monto);

		// aumentar saldo en bd
		saldoN = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(montoRebajadoComision + saldo));
		operacionesGratis += 1;
		DAO.actualizarsaldoCuenta(NCEncriptada, saldoN, operacionesGratis);

		String resultado = "Estimado usuario, se han depositado correctamente " + monto + " colones \n"
				+ "[El monto real depositado a su cuenta " + numeroCuenta + " es de " + montoRebajadoComision
				+ "colones]\n" + "[El monto cobrado por concepto de comisión fue de " + cobroComision
				+ " colones, que fueron rebajados automáticamente de su saldo actual]";

		return resultado;
	}

	public static String depositoCambioMoneda(String numeroCuenta, int monto) {

		int equivalenteColones = (int) (Float.parseFloat(bccr.getCompra())) * monto;

		depositoEnColones(numeroCuenta, equivalenteColones);

		String resultado = "Estimado usuario, se han depositado correctamente " + monto + " dolares \n"
				+ "[Según el BCCR, el tipo de cambio de compra del dólar de " + date + " es:"+bccr.getVenta()+"]\n"
				+ "[El monto equivalente en colones es " + equivalenteColones + "]\n"
				+ "[El monto real depositado a su cuenta " + numeroCuenta + " es de " + montoRebajadoComision
				+ "colones]\n" + "[El monto cobrado por concepto de comisión fue de " + cobroComision
				+ " colones, que fueron rebajados automáticamente de su saldo actual]";

		operacionesGratis = 0;
		montoRebajadoComision = 0;
		cobroComision = 0;
		return resultado;
	}

	public static String validaPinRetiro(String numeroCuenta, String pin) {
		String pinBD, NCEncriptada = "", pinDesencriptado;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		pinBD = DAO.buscarCuentaporNumeroPin(NCEncriptada);
		pinDesencriptado = Atenticacion.Encriptacion.CesarDescifradoabc(pinBD);

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}

		if (pinDesencriptado.equals(pin)) {
			int numTelefono = DAO.buscarCuentaporNumeroTelefono(NCEncriptada);
			// mandar mensaje de texto y que la funcion retorne el codigo enviado a la
			// persona
			// palabraSecretaEnviada=Servicios.MensajesSMS.envioSMS("+506"+String.valueOf(numTelefono));
			palabraSecretaEnviada = "abc12";
			//System.out.println(numTelefono);

		} else {
			return "\"Pin Incorrecto. Un (1) intento restante";
		}

		String resultado = "Estimado usuario se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digitar la palabra enviada.";
		return resultado;
	}

	public static boolean validaPalabraSecreta(String palabraSecreta, String numeroCuenta) {
		if (palabraSecretaEnviada.equals(palabraSecreta)) {
			return true;
		} else {
			contadorErroresPalabraSecreta -= 1;
			if (validarIntentos(contadorErroresPalabraSecreta,
					Atenticacion.Encriptacion.CesarCifradoabc(numeroCuenta))) {
				return false;
			}
			return false;
		}
	}

	private static boolean validarIntentos(int contador, String numeroCuenta) { // numero Cuenta llega encriptado
		if (contador <= 0) {
			DAO.actualizarEstatusCuenta(numeroCuenta);
			;
			Servicios.EnvioCorreo.EnviarCorreo(DAO.buscarCuentaporNumeroEmail(numeroCuenta),
					Atenticacion.Encriptacion.CesarDescifradoabc(numeroCuenta));
			contador = 0;
			return false;
		}
		return true;
	}

	public static String retiroEnColones(String numeroCuenta, String pin, boolean palabraSecreta, int monto) {
		String saldoBD, NCEncriptada = "", saldoN;
		float saldo = 0;
		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}

		if (!palabraSecreta) {
			contadorErroresPalabraSecreta -= 1;
			if (validarIntentos(contadorErroresPalabraSecreta, NCEncriptada))
				return "Cuenta Inactivdad. Revise su correo para mas informacion";
		}

		saldoBD = DAO.buscarCuentaporNumeroSaldo(NCEncriptada);
		saldo = Float.parseFloat(Atenticacion.Encriptacion.CesarDescifradoabc(saldoBD));
		operacionesGratis = DAO.buscarCuentaporNumeroCantOperaciones(NCEncriptada);
		
		if (operacionesGratis > 3) {
			cobroComision = (float) (monto * 0.02);
			montoRebajadoComision = monto + cobroComision;
			cargoComison = true;
		} else {
			cobroComision = 0;
			montoRebajadoComision = monto;
			cargoComison = false;
		}

		if (saldo < montoRebajadoComision) {
			return "Fondos Insuficientes. Ingrese una cantidad adecueada.";
		}
		
		// ingresar operacion en bd
		Creaciones.registrarOperacion(NCEncriptada, "Retiro", cargoComison, cobroComision, monto);

		// aumentar saldo en bd
		saldoN = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(saldo - montoRebajadoComision));
		operacionesGratis += 1;
		DAO.actualizarsaldoCuenta(NCEncriptada, saldoN, operacionesGratis);

		String res = "Estimado usuario, el monto de este retiro es " + monto + " colones \n"
				+ "[El monto cobrado por concepto de comisión fue de " + cobroComision
				+ " colones, que fueron rebajados automáticamente de su saldo actual]";
		return res;
	}

	public static String retiroCambioMoneda(String numeroCuenta, String pin, boolean palabraSecreta, int monto) {
		int equivalenteColones = (int) (Float.parseFloat(bccr.getVenta()) * monto); // =cambios de dolres a colones

		retiroEnColones(numeroCuenta, pin, palabraSecreta, equivalenteColones);

		String resultado = "Estimado usuario, el monto de este retiro es " + monto + " dolares. \n"
				+ "[Según el BCCR, el tipo de cambio de compra del dólar de " + date + " es: " + bccr.getCompra()
				+ "]\n" + "[El monto equivalente en colones es " + equivalenteColones + "]\n"
				+ "[El monto real retirado a su cuenta " + numeroCuenta + " es de " + montoRebajadoComision
				+ " colones]\n" + "[El monto cobrado por concepto de comisión fue de " + cobroComision
				+ " colones, que fueron rebajados automáticamente de su saldo actual] ";

		operacionesGratis = 0;
		montoRebajadoComision = 0;
		cobroComision = 0;
		return resultado;

	}

	public static String tranferencia(String numeroCuentaDes, int monto) {
		depositoTransferencia(numeroCuentaDes, monto);

		String resultado = "Estimado usuario, la transferencia de fondos se ejecutó satisfactoriamente.\n"
				+ "El monto retirado de la cuenta origen y depositado en la cuenta destino es " + monto + "colones.\n"
				+ "[El monto cobrado por concepto de comisión a la cuenta origen fue de" + cobroComision + "\n"
				+ "colones, que fueron rebajados automáticamente de su saldo actual]";

		operacionesGratis = 0;
		montoRebajadoComision = 0;
		cobroComision = 0;
		return resultado;
	}

	public static String depositoTransferencia(String numeroCuenta, int monto) {
		String saldoBD, NCEncriptada = "", saldoN;
		float saldo = 0;

		NCEncriptada = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(numeroCuenta));
		saldoBD = DAO.buscarCuentaporNumeroSaldo(NCEncriptada);
		saldo = Float.parseFloat(Atenticacion.Encriptacion.CesarDescifradoabc(saldoBD));

		if (!DAO.buscarCuentaporNumeroEstatus(NCEncriptada)) {
			return "Esta cuenta se encuentra Inactiva.";
		}

		// ingresar operacion en bd
		Creaciones.registrarOperacion(NCEncriptada, "Tranferencia", false, 0, monto);

		// aumentar saldo en bd
		saldoN = Atenticacion.Encriptacion.CesarCifradoabc(String.valueOf(monto + saldo));
		operacionesGratis += 1;
		DAO.actualizarsaldoCuenta(NCEncriptada, saldoN, operacionesGratis);

		String resultado = "Estimado usuario, se han depositado correctamente " + monto + " colones \n"
				+ "[El monto real depositado a su cuenta " + numeroCuenta + " es de " + montoRebajadoComision
				+ "colones]\n" + "[El monto cobrado por concepto de comisión fue de " + cobroComision
				+ " colones, que fueron rebajados automáticamente de su saldo actual]";

		return resultado;
	}
}
