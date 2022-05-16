/**
 * 
 */
package Servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author valem y nathb
 *
 */
public class BCCR {
//atributos
	private int indicador = 0;
	private long miliseconds = System.currentTimeMillis();
	private Date dateHoy = new Date(miliseconds);
	private String Fecha = new SimpleDateFormat("dd/MM/yyyy").format(dateHoy);
	private final String HOST = "https://gee.bccr.fi.cr/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML?";
	private String url;

	// funcion que devuelve el html
	protected static String getHTML(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	// forma el url para el el tipo de cambio
	private String getValue() {
		try {
			setUrl(); // Set del Url con los Parámetros

			// Obtiene y Parsea el XML
			String data = getHTML(url);

			return parserXML(data);

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error al obtener el valor del BCCR.");
			return "0";
		}
	}

	private void setUrl() {
		this.url = HOST + "Indicador=" + indicador + "&FechaInicio=" + Fecha + "&FechaFinal=" + Fecha
				+ "&Nombre=TEC&SubNiveles=N&CorreoElectronico=nathalyabarrerat@estudiantec.cr&Token=TRLAA4B2C4";

	}

	private String parserXML(String data) {

		String regex = "&lt;NUM_VALOR&gt;(.*?)&lt;/NUM_VALOR&gt;";

		String resultado = "";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);

		while (matcher.find()) {
			resultado += matcher.group();
		}

		String Tag1 = "&lt;NUM_VALOR&gt;";
		String Tag2 = "&lt;/NUM_VALOR&gt;";

		String resultadoLimpio=resultado.replaceAll(Tag1, "").replaceAll(Tag2, "");
		return (resultadoLimpio);
	}

	public String getCompra() {
		this.indicador = 317;
		String valor = getValue();
		return valor;
	}

	public String getVenta() {
		this.indicador = 318;
		String valor = getValue();
		return valor;
	}

}
