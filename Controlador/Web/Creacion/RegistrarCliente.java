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

import Controlador.Consola.Creaciones;

/**
 * Servlet implementation class RegistrarCliente
 */
@WebServlet("/RegistrarCliente")
public class RegistrarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrarCliente() {
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
			String apellido1, apellido2, nombre, email, temp;
			int identificacion, telefono;
			java.sql.Date fechaNacimiento;
			String titulo = "Confirmacion Registrar Cliente";
			apellido1 = request.getParameter("Apellido1");
			apellido2 = request.getParameter("Apellido2");
			nombre = request.getParameter("Nombre");
			email = request.getParameter("Email");
			temp = request.getParameter("FechaNacimiento");
			fechaNacimiento = (java.sql.Date) Date.valueOf(temp);
			identificacion = Integer.parseInt(request.getParameter("Identificacion"));
			telefono = Integer.parseInt(request.getParameter("Telefono"));

			String res = Controlador.Consola.Creaciones.crearCliente(nombre, apellido1, apellido2, fechaNacimiento,
					identificacion, telefono, email);
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
