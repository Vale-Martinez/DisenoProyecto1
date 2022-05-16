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
 * Servlet implementation class Operacion
 */
@WebServlet("/Operacion")
public class OperacionS extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OperacionS() {
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
			String btnCambiarPin = request.getParameter("CambiarPin"),
					btnRetriroCambioMoneda = request.getParameter("RetriroCambioMoneda"),
					btnDepositoColones = request.getParameter("DepositoColones"),
					btnDepositoCambioMoneda = request.getParameter("DepositoCambioMoneda"),
					btnRetiroColones = request.getParameter("RetiroColones"),
					btnTransferencia = request.getParameter("Transferencia");
			if (btnCambiarPin != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/CambiarPin.jsp");
				rd.forward(request, response);
			} else if (btnDepositoColones != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/DepositoColones.jsp");
				rd.forward(request, response);
			}else if (btnDepositoCambioMoneda != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/DepositoCambioMoneda.jsp");
				rd.forward(request, response);
			}else if (btnRetiroColones != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/ValidarPinColones.jsp");
				rd.forward(request, response);
			}else if (btnRetriroCambioMoneda != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/ValidarPinCambioMoneda.jsp");
				rd.forward(request, response);
			}else if (btnTransferencia != null) {
				RequestDispatcher rd = request.getRequestDispatcher("Operacion/ValidarPinTransferencia.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("error" + e);
		}
	}

}
