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
 * Servlet implementation class RetiroColones
 */
@WebServlet("/RetiroColones")
public class RetiroColones extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static String  numeroCuenta = "";
	static String pin = "";
	static String palabraSecreta = "";
	static int monto = 0;
	static boolean validaPalabra = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RetiroColones() {
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
			String btnValidar = request.getParameter("Validar"),
					btnValidarPalabraSecreta = request.getParameter("ValidarPalabraSecreta"),
					btnRetirar = request.getParameter("Retirar");
			
			if (btnValidar != null) {
				numeroCuenta = request.getParameter("numeroCuenta");
				pin = request.getParameter("Pin");
				String resValidacion = Operaciones.validaPinRetiro(numeroCuenta, pin);
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/PalabraSecretaColones.jsp");
				rd.forward(request, response);

			} else if (btnValidarPalabraSecreta != null) {
				palabraSecreta = request.getParameter("palabrasecreta");
				validaPalabra = Operaciones.validaPalabraSecreta(palabraSecreta, numeroCuenta);
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/RetiroColones.jsp");
				rd.forward(request, response);
				
			} else if (btnRetirar != null) {
				monto = Integer.parseInt(request.getParameter("monto"));
				String titulo = "Confirmacion Retiro Colones";
				String res = Operaciones.retiroEnColones(numeroCuenta, pin, validaPalabra, monto);
				String transformado = Validacion.Transformacion.tranformarResultados(res);
				RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
				rd.include(request, response);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<h4 class=\"display-6 fw-bold\">" + titulo + "<h4>");
				out.println("<p>" + transformado + "</p>");
			}
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
