package Controlador.Web.Consulta;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controlador.Consola.Consultas;

/**
 * Servlet implementation class ConsultarClienteEspecifico
 */
@WebServlet("/ConsultarCuentaEspecifico")
public class ConsultarCuentaEspecifico extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarCuentaEspecifico() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//llamar a los metodos en los controlador consola
				String titulo="Cuenta Consultada:";
				String numeroCuenta = request.getParameter("numeroCuenta");

				String res = Controlador.Consola.Consultas.datosCuentaEspecificas(numeroCuenta);
				String transformado = Validacion.Transformacion.tranformarResultados(res);
				RequestDispatcher rd = request.getRequestDispatcher("Confirmacion.jsp");
		        rd.include(request, response);
		        response.setContentType("text/html");
		        PrintWriter out = response.getWriter(); 
		        out.println("<h4 class=\"display-6 fw-bold\">"+titulo+"<h4>");
		        out.println("<p>"+transformado+"</p>");
	}

}
