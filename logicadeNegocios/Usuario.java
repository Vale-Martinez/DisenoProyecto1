/**
 * 
 */
package logicadeNegocios;

import java.sql.Date;

/**
 * @author valem y nathb
 *
 */
public class Usuario extends Persona {

	/**
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param fechaNacimiento
	 * @param identificacion
	 * @param numTelefonico
	 * @param email
	 */
	public Usuario(String nombre, String apellido1, String apellido2, Date fechaNacimiento, int identificacion,
			int numTelefonico, String email) {
		super(nombre, apellido1, apellido2, fechaNacimiento, identificacion, numTelefonico, email);
		
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", fechaNacimiento=" + fechaNacimiento + ", identificacion=" + identificacion + ", numTelefonico="
				+ numTelefonico + ", email=" + email + "]";
	}

	
	
}
