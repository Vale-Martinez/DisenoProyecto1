package Controlador.Web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controlador.Consola.Consultas;

/**
 * Servlet implementation class Consulta
 */
@WebServlet("/Consulta")
public class ConsultaS extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultaS() {
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
		try {
			String btnListaClientes = request.getParameter("ListaClientes"),
					btnListaCuentas = request.getParameter("ListaCuentas"),
					btnTipoCambioCompra = request.getParameter("TipoCambioCompra"),
					btnTipoCambioVenta = request.getParameter("TipoCambioVenta"),
					btnSaldoActualColones = request.getParameter("SaldoActualColones"),
					btnSaldoActualDivisaExtranjera = request.getParameter("SaldoActualDivisaExtranjera"),
					btnEstadoCuentaColones = request.getParameter("EstadoCuentaColones"),
					btnEstadoCuentaDolar = request.getParameter("EstadoCuentaDolar"),
					btnEstatusCuenta = request.getParameter("EstatusCuenta"),
					btnGananciasComisionesTotal = request.getParameter("GananciasComisionesTotal"),
					btnGananciasComisionesCuenta = request.getParameter("GananciasComisionesCuenta");
			if (btnListaClientes != null) {
				cargarClientes(request, response);
			} else if (btnListaCuentas != null) {
				cargarCuentas(request, response);
			} else if (btnTipoCambioCompra != null) {
				cargaTipoCambioCompra(request, response);
			} else if (btnTipoCambioVenta != null) {
				cargaTipoCambioVenta(request, response);
			} else if (btnSaldoActualColones != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/SaldoActualColones.jsp");
				rd.forward(request, response);
			} else if (btnSaldoActualDivisaExtranjera != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/SaldoActualDolares.jsp");
				rd.forward(request, response);
			} else if (btnEstadoCuentaColones != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/EstadoCuentaColones.jsp");
				rd.forward(request, response);
			} else if (btnEstadoCuentaDolar != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/EstadoCuentaDolares.jsp");
				rd.forward(request, response);
			} else if (btnEstatusCuenta != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/EstatusCuenta.jsp");
				rd.forward(request, response);
			} else if (btnGananciasComisionesTotal != null) {
				cargaGananciasTotal(request, response);
			} else if (btnGananciasComisionesCuenta != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Consulta/GananciasComisionCuentaEspecifico.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("error" + e);
		}
	}
	
	private void cargarClientes (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titulo = "Listado Clientes";
		String res = "String largo";
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
		rd.include(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h4 class=\"display-6 fw-bold\">" + titulo + "<h4>");
		out.println("<table class=\"table table-hover\">");
		out.println("<tr>");
		out.println("<th>Primero Apelido</th>\r\n" + "<th>Segundo Apellido</th>\r\n" + "    <th>Nombre</th>\r\n"
				+ "<th>Identificacion</th>\r\n");
		out.println("</tr>");
		out.println("<tr>");
		Vector clientes = Controlador.Consola.Consultas.listarDatosClientes();
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
		
		out.println("<br><br>");
		out.println("<form action=\"ConsultarClienteEspecifico\" method=\"post\">\r\n"
				+ "				<div class=\"form-group\">\r\n"
				+ "					<input type=\"text\" class=\"form-control\" placeholder=\"Ingrese el identificador del cliente a cosultar\"\r\n"
				+ "						name=\"identificacion\"> \r\n"
				+ "				</div>\r\n"
				+ "				<br>\r\n"
				+ "				<button type=\"submit\"\r\n"
				+ "					class=\"btn btn-outline-dark align-items-center\" name=\"Consultar\">Consultar</button>\r\n"
				+ "			</form>");
	}

	private void cargarCuentas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String titulo = "Listado Cuentas";
		String res = "String largo";
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
		rd.include(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h4 class=\"display-6 fw-bold\">" + titulo + "<h4>");
		out.println("<table class=\"table table-hover\">");
		out.println("<tr>");
		out.println("<th>Numero de Cuenta</th>\r\n" + "<th>Estatus</th>\r\n" + "    <th>Saldo</th>\r\n"
				+ "<th>Identificacion</th>\r\n"+ "    <th>Nombre</th>\r\n"+"<th>Primero Apelido</th>\r\n" + "<th>Segundo Apellido</th>\r\n" );
		out.println("</tr>");
		out.println("<tr>");
		Vector clientes = Controlador.Consola.Consultas.listarDatosCuentas();
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
		
		out.println("<br><br>");
		out.println("<form action=\"ConsultarCuentaEspecifico\" method=\"post\">\r\n"
				+ "				<div class=\"form-group\">\r\n"
				+ "					<input type=\"text\" class=\"form-control\" placeholder=\"Ingrese el numero de cuenta a cosultar\"\r\n"
				+ "						name=\"numeroCuenta\"> \r\n"
				+ "				</div>\r\n"
				+ "				<br>\r\n"
				+ "				<button type=\"submit\"\r\n"
				+ "					class=\"btn btn-outline-dark align-items-center\" name=\"Consultar\">Consultar</button>\r\n"
				+ "			</form>");
	}
	
	private void cargaTipoCambioCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titulo="Tipo de Cambio Compra";
		String res =Controlador.Consola.Consultas.consultaTipoCambioCompra();
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
        rd.include(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter(); 
        out.println("<h4 class=\"display-6 fw-bold\">"+titulo+"<h4>");
        out.println("<br>");
        out.println("<p>"+res+"</p>");
	}
	private void cargaTipoCambioVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titulo="Tipo de Cambio Venta";
		String res =Controlador.Consola.Consultas.consultaTipoCambioVenta();
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
        rd.include(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter(); 
        out.println("<h4 class=\"display-6 fw-bold\">"+titulo+"<h4>");
        out.println("<br>");
        out.println("<p>"+res+"</p>");
	}
	private void cargaGananciasTotal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titulo="Consultar ganancias del banco producto del cobro de comisiones Totalizado";
		String res = Consultas.consultaGananciasPorComisionesTotal();
		String resDeposito = Consultas.consultaGananciasPorComisionesTotalDeposito();
		String resRetiro = Consultas.consultaGananciasPorComisionesTotalRetiro();
		RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
        rd.include(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter(); 
        out.println("<h4 class=\"display-6 fw-bold\">"+titulo+"<h4>");
        out.println("<br>");
        out.println("<p>"+res+"</p>");
        out.println("<br>");
        out.println("<p>"+resDeposito+"</p>");
        out.println("<br>");
        out.println("<p>"+resRetiro+"</p>");
	}
}
