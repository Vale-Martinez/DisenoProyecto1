package Controlador.Web.Creacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrarCliente
 */
@WebServlet("/RegistrarCuenta")
public class RegistrarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrarCuenta() {
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
			String codigoCliente, pin;
			int monto;
			String titulo = "Confirmacion Registrar Cuenta";
			codigoCliente = request.getParameter("CodigoCliente");
			pin = request.getParameter("Pin");
			monto = Integer.parseInt(request.getParameter("Monto"));

			String res = Controlador.Consola.Creaciones.crearCuenta(codigoCliente, monto, codigoCliente, pin);
			String transformado = Validacion.Transformacion.tranformarResultados(res);
			RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
			rd.include(request, response);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4 class=\"display-6 fw-bold\">" + titulo + "<h4>");
			out.println("<p>" + transformado + "</p>");
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
			rd.include(request, response);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h4 class=\"display-6 fw-bold\">Error, ingrese los datos de forma correcta.<h4>");
			out.println("<p>" + e + "</p>");
		}

	}

}
