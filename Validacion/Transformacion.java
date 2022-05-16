/**
 * 
 */
package Validacion;

/**
 * @author valem y nathb
 *
 */
public class Transformacion {

	public static String tranformarResultados(String resultado) {

		String resultadoTransformado = "";

		resultadoTransformado=resultado.replaceAll("\n", "<br>");
		return "<html><body>" + resultadoTransformado + "</body></html>";
	}

}
