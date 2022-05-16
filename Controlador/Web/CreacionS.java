package Controlador.Web;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Creacion
 */
@WebServlet("/Creacion")
public class CreacionS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreacionS() {
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
		try {
			String btnRegistrarClientes = request.getParameter("RegistrarCliente"),
					btnRegistrarCuentas = request.getParameter("RegistrarCuenta");
			if (btnRegistrarClientes != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Creacion/RegistrarCliente.jsp");
				rd.forward(request, response);
	        } else if (btnRegistrarCuentas != null) {
	        	RequestDispatcher rd = request.getRequestDispatcher("Creacion/RegistrarCuenta.jsp");
	            rd.forward(request, response);
	        }
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("error" +e);
		}
	}

}
