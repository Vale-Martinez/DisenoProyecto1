/**
 * 
 */
package logicadeNegocios;

import java.sql.Date;

/**
 * @author valem y nathb
 *
 */
public class Persona {
	protected String nombre;
	protected String apellido1;
	protected String apellido2;
	protected Date fechaNacimiento;
	protected int identificacion;
	protected int numTelefonico;
	protected String email;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the identificacion
	 */
	public int getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the numTelefonico
	 */
	public int getNumTelefonico() {
		return numTelefonico;
	}

	/**
	 * @param numTelefonico the numTelefonico to set
	 */
	public void setNumTelefonico(int numTelefonico) {
		this.numTelefonico = numTelefonico;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param fechaNacimiento
	 * @param identificacion
	 * @param numTelefonico
	 * @param email
	 */
	public Persona(String nombre, String apellido1, String apellido2, Date fechaNacimiento, int identificacion,
			int numTelefonico, String email) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fechaNacimiento = fechaNacimiento;
		this.identificacion = identificacion;
		this.numTelefonico = numTelefonico;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", fechaNacimiento=" + fechaNacimiento + ", identificacion=" + identificacion + ", numTelefonico="
				+ numTelefonico + ", email=" + email + "]";
	}

	
	
}
