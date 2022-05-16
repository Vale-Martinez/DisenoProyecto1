package Controlador.Web.Operacion;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controlador.Consola.Operaciones;

/**
 * Servlet implementation class CambiarPin
 */
@WebServlet("/CambiarPin")
public class CambiarPin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CambiarPin() {
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
			String titulo = "Confirmacion cambiar PIN";
			String numeroCuenta = request.getParameter("numeroCuenta");
			String pinA = request.getParameter("PinA");
			String pinN = request.getParameter("PinN");

			String res = Controlador.Consola.Operaciones.cambiarPin(numeroCuenta, pinA, pinN);
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
