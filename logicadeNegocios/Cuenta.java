/**
 * 
 */
package logicadeNegocios;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author valem y nathb
 *
 */
public class Cuenta {

	private int idC;
	private Date fechaCreacion;
	private double saldo;
	private String estatus;
	private String pin;

	private Persona dueno;
	private ArrayList<Operacion> operaciones;

	/**
	 * @return the idC
	 */
	public int getIdC() {
		return idC;
	}

	/**
	 * @param idC the idC to set
	 */
	public void setIdC(int idC) {
		this.idC = idC;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the dueno
	 */
	public Persona getDueno() {
		return dueno;
	}

	/**
	 * @return the operaciones
	 */
	public ArrayList<Operacion> getOperaciones() {
		return operaciones;
	}

	/**
	 * @param idC
	 * @param fechaCreacion
	 * @param saldo
	 * @param estatus
	 * @param pin
	 */
	public Cuenta(int idC, double saldo, String estatus, String pin) {
		this.idC = idC;
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		this.fechaCreacion = date;
		this.saldo = saldo;
		this.estatus = estatus;
		this.pin = pin;
		this.operaciones = new ArrayList<Operacion>();
	}

	/**
	 * @param dueno the dueno to set
	 */
	public void asignarDueno(Persona dueno) {
		this.dueno = dueno;
	}

	public void agregarOperacion(String idOp, String tipo, boolean cargoComision, int monto) {
		Operacion op = new Operacion(idOp, tipo, cargoComision, monto);
		operaciones.add(op);
	}

	public String cambioPin(String pinA, String pinN) {

		if (this.pin.equals(pinA)) {

			this.pin = pinN;
		} else {
			return "Pin invalido";
		}
		return "PIN cambiado";
	}

	public double depositarEnColones(int monto) {
		this.saldo += monto;
		return this.saldo;
	}

	public double depositarCambioMoneda(int monto) {
		// cambia el saldo de dalores a colones
		// servicios.BCCR
		return depositarEnColones(monto);
	}

	public double retiroEnColones(int monto, String palabraSecreta, String pin) {
		String palabraSecretaEnviada = "";// se iguala a la funcion de envio de palabra por sms en servicios
		if (this.pin.equals(pin) && this.saldo > monto && palabraSecretaEnviada.equals(palabraSecreta)) {
			this.saldo -= monto;
			return this.saldo;
		}

		return 0;
	}

	public double retiroCambioMoneda(int monto, String palabraSecreta, String pin) {
		// cambia el saldo de dalores a colones
		// servicios.BCCR
		return retiroEnColones(monto, palabraSecreta, pin);
	}

	public String tranferencia(int monto, String palabraSecreta, String pin, String idCDistino) {
		String palabraSecretaEnviada = "";// se iguala a la funcion de envio de palabra por sms en servicios
		if (this.pin.equals(pin) && this.saldo > monto && palabraSecretaEnviada.equals(palabraSecreta)) {
			this.saldo -= monto;
			// se suma el monto en la cuenta de codigo ingresado en idCDestino
			return "tranferencia realizados";
		}

		return "Tanferencia incompleta ";
	}

	public double consultaTipoCambioCompra() {

		// retorna el valor del servicios.BCCR
		return 0;

	}

	public double consultaTipoCambioVenta() {

		// retorna el valor del servicios.BCCR
		return 0;

	}

	public double consultaSaldoActualColones(String pin) {

		if (this.pin.equals(pin)) {
			return this.saldo;
		} else {
			return 0;
		}
	}

	public double consultaSaldoActualCambioMoneda(String pin) {

		if (this.pin.equals(pin)) {
			return this.saldo; // pasar el this.saldo por el paremetro de servicios.BCCR
		} else {
			return 0;
		}
	}

	public String consultaEstadoCuentaColones(String pin) {
		if (this.pin.equals(pin)) {
			return "Cuenta [idC=" + idC + ", fechaCreacion=" + fechaCreacion + ", saldo=" + saldo + ", estatus="
					+ estatus + ", pin=" + pin + ", dueno=" + dueno + ", operaciones=" + operaciones + "]";
		} else {
			return "";
		}

	}

	public String consultaEstadoCuentaCambioMoneda(String pin) {
		if (this.pin.equals(pin)) {
			double dolares = 0; // pasar el this.saldo por el paremetro de servicios.BCCR
			return "Cuenta [idC=" + idC + ", fechaCreacion=" + fechaCreacion + ", saldo=" + dolares + ", estatus="
					+ estatus + ", pin=" + pin + ", dueno=" + dueno + ", operaciones=" + operaciones + "]";
		} else {
			return "";
		}
	}

	public String consultarEstatus() {
		return this.estatus;
	}

	public double consultaGananciasComisionesTotal() {
		double totalComisiones = 0;
		for (Operacion operacion : operaciones) {
			totalComisiones += operacion.getCantidadCobro();
		}
		return totalComisiones;

	}

	public double consultaGananciasComisionesTotalDeposito() {
		double comisionesDepositos = 0;
		for (Operacion operacion : operaciones) {
			if (operacion.getTipo().equals("Deposito")) {
				comisionesDepositos += operacion.getCantidadCobro();
			}
		}
		return comisionesDepositos;

	}

	public double consultaGananciasComisionesTotalRetiro() {
		double comisionesRetiro = 0;
		for (Operacion operacion : operaciones) {
			if (operacion.getTipo().equals("Retiro")) {
				comisionesRetiro += operacion.getCantidadCobro();
			}
		}
		return comisionesRetiro;

	}

	@Override
	public String toString() {
		return "Cuenta [idC=" + idC + ", fechaCreacion=" + fechaCreacion + ", saldo=" + saldo + ", estatus=" + estatus
				+ ", pin=" + pin + ", dueno=" + dueno + ", operaciones=" + operaciones + "]";
	}

}
