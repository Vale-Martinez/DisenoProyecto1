package Controlador.Web.Consulta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EstadoCuentaDolares
 */
@WebServlet("/EstadoCuentaDolares")
public class EstadoCuentaDolares extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EstadoCuentaDolares() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String titulo = "Estado de Cuenta en Dolares";

		String numeroCuenta = request.getParameter("numeroCuenta");
		String pin = request.getParameter("Pin");

		String res = Controlador.Consola.Consultas.consultarEstadoCuentaDolaresWeb(numeroCuenta, pin);
		String transformado = Validacion.Transformacion.tranformarResultados(res);
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
		rd.include(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h4 class=\"display-6 fw-bold\">" + titulo + "<h4>");
		out.println("<p>" + transformado + "</p>");
		out.println("<table class=\"table table-hover\">");
		out.println("<tr>");
		out.println("<th>id Operacion</th>\r\n" + "<th>Numero Cuenta</th>\r\n" + "    <th>Fecha</th>\r\n"
				+ "<th>Tipo</th>\r\n" + "<th>Cargo Comision</th>\r\n" + "    <th>Monto cobrado por comision</th>\r\n"
				+ "<th>Monto</th>\r\n");
		out.println("</tr>");
		out.println("<tr>");
		Vector clientes = Controlador.Consola.Consultas
				.consultarDatosEstadoCuentaOperacionesDolares(Atenticacion.Encriptacion.CesarCifradoabc(numeroCuenta));
		for (int i = 0; i < clientes.size(); i++) {
			out.println("<tr>");
			for (int j = 0; j < ((Vector) clientes.get(i)).size(); j++) {
				Vector cliente = (Vector) clientes.get(i);
				out.println("<td>");
				String resultado = cliente.get(j).toString() + "\n";
				out.println(resultado + "</td>");

			}
			out.println("</tr>");
		}
		out.println("</tr>");
		out.println("</table>");
	}

}
