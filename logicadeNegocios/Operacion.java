/**
 * 
 */
package logicadeNegocios;

import java.sql.Date;

/**
 * @author valem y nathb
 *
 */
public class Operacion {

	private String idOp;
	private Date fecha;
	private String tipo;
	private boolean cargoComision;
	private int monto;
	private double cantidadCobro;

	/**
	 * @return the idOp
	 */
	public String getIdOp() {
		return idOp;
	}

	/**
	 * @param idOp the idOp to set
	 */
	public void setIdOp(String idOp) {
		this.idOp = idOp;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fechaOp the fechaOp to set
	 */
	public void setFecha(Date fechaOp) {
		this.fecha = fechaOp;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipoOp) {
		this.tipo = tipoOp;
	}

	/**
	 * @return the cargoComision
	 */
	public boolean isCargoComision() {
		return cargoComision;
	}

	/**
	 * @param cargoComision the cargoComision to set
	 */
	public void setCargoComision(boolean cargoComision) {
		this.cargoComision = cargoComision;
	}

	/**
	 * @return the monto
	 */
	public int getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(int monto) {
		this.monto = monto;
	}

	
	/**
	 * @return the cantidadCobro
	 */
	public double getCantidadCobro() {
		return cantidadCobro;
	}

	/**
	 * @param cantidadCobro the cantidadCobro to set
	 */
	public void setCantidadCobro(double cantidadCobro) {
		this.cantidadCobro = cantidadCobro;
	}

	/**
	 * @param idOp
	 * @param tipo
	 * @param cargoComision
	 * @param monto
	 */
	public Operacion(String idOp, String tipo, boolean cargoComision, int monto) {
		this.idOp = idOp;
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		this.fecha = date;
		this.tipo = tipo;
		this.cargoComision = cargoComision;
		this.monto = monto;
	}

	@Override
	public String toString() {
		return "Operacion [idOp=" + idOp + ", fecha=" + fecha + ", tipo=" + tipo + ", cargoComision=" + cargoComision
				+ ", monto=" + monto + ", cantidadCobro=" + cantidadCobro + "]";
	}

	

}
