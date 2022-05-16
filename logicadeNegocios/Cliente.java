package logicadeNegocios;

import java.sql.Date;

public class Cliente extends Persona {

	private String codigo;

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param fechaNacimiento
	 * @param identificacion
	 * @param numTelefonico
	 * @param email
	 * @param codigo
	 */
	public Cliente(String nombre, String apellido1, String apellido2, Date fechaNacimiento, int identificacion,
			int numTelefonico, String email, String codigo) {
		super(nombre, apellido1, apellido2, fechaNacimiento, identificacion, numTelefonico, email);
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "Cliente [codigo=" + codigo + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2="
				+ apellido2 + ", fechaNacimiento=" + fechaNacimiento + ", identificacion=" + identificacion
				+ ", numTelefonico=" + numTelefonico + ", email=" + email + "]";
	}

	
	

}
