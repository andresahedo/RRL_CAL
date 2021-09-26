package mx.gob.sat.siat.juridica.base.web.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ConsultasBussines;

/**
 * Servlet implementation class DecifrarDocumentoServlet
 */

@WebServlet("/document.pdf")
public class DecifrarDocumentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(DecifrarDocumentoServlet.class);

    ConsultasBussines cb = new ConsultasBussines();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DecifrarDocumentoServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @SuppressWarnings("resource")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("Inicial la generación del PDF ...");
        byte[] content = (byte[]) request.getSession().getAttribute("documento");
        response.setContentType("application/pdf");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
