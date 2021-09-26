/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.util.filter;

import mx.gob.sat.siat.juridica.base.api.UsuarioFacade;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.UserProfileDTOTransformer;
import mx.gob.sat.siat.juridica.base.web.util.ApplicationContextHelper;
import mx.gob.sat.siat.juridica.rrl.util.constante.GeneralConstantes;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro que sirve para inicializar al usuario disponible como un
 * objeto de
 * 
 * @author Softtek
 * 
 */
public class JuridicaSessionFilter implements Filter {

    /**
     * Logger de la clase.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UsuarioFacade usuarioFacade;

    /**
     * Transformador para pasar del objeto {@link AccesoUsr} a
     * {@link UserProfileDTO}.
     */
    private UserProfileDTOTransformer userProfileDTOTransformer = new UserProfileDTOTransformer();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getRequestURL().toString();

        boolean esContribuyente = false;

        HttpSession session = (HttpSession) request.getSession(true);
        UserProfileDTO userProfileDTO = (UserProfileDTO) session.getAttribute("userProfile");

        session.setAttribute("tamanioApp", GeneralConstantes.TAMANIO_APP);
        session.setAttribute("tipoTamanioApp", GeneralConstantes.TIPO_TAMANIO_APP);
        session.setAttribute("tamanioBuzon", GeneralConstantes.TAMANIO_BUZON);
        session.setAttribute("tipoTamanioBuzon", GeneralConstantes.TIPO_TAMANIO_BUZON);
        session.setAttribute("fielMode", ApplicationContextHelper.getApplicationContext().getBean("appletMode"));
        if (null == userProfileDTO) {
            logger.debug("No hay userProfileDTO en sesion, intentando recuperarlo...");
            AccesoUsr accesoUsr = null;
            try {
                logger.debug("Obteniendo el valor en caso de ser contribuyente");
                accesoUsr = (AccesoUsr) session.getAttribute("acceso");
                if (null == accesoUsr) {
                    logger.debug("Obteniendo el valor en caso de ser funcionario");
                    accesoUsr = (AccesoUsr) session.getAttribute("accesoEF");
                }
                else {
                    esContribuyente = true;
                }
            }
            catch (Exception e) {
                logger.error("Error al obtener el accesoUsr.", e);
            }
            if (accesoUsr == null) {
                logger.error("No hay datos de usuario disponibles en sesion ");
                logger.debug("    url: {}", url);
                response.sendRedirect(request.getContextPath() + "/resources/pages/errorSesion.html");
                return;

            }
            else {

                userProfileDTO = userProfileDTOTransformer.transformarDTO(accesoUsr);
                session.setAttribute("userProfile", userProfileDTO);

                logger.debug("User profile cargado en la sesion: {}, {}", userProfileDTO.getUsuario(),
                        userProfileDTO.getRfc());

                if (esContribuyente) {
                    logger.debug("es contribuyente se va a guardar {}", this.usuarioFacade);
                    try {
                        this.usuarioFacade.generarContribuyente(userProfileDTO);
                    }
                    catch (Exception ex) {
                        logger.error("", ex);
                        logger.error("Error en la inicializacion del filtro: {}", ex.getMessage());
                    }

                }
            }
        }
        else {
            logger.debug("User profile existente: {}, {}", userProfileDTO.getUsuario(), userProfileDTO.getRfc());
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {
        // configuracion inicial de ser requerida.
        try {
            WebApplicationContext springContext =
                    WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
            logger.debug("springContext {}", springContext);
            this.usuarioFacade = (UsuarioFacade) springContext.getBean("usuarioFacade");
        }
        catch (Exception ex) {
            logger.error("", ex);
            logger.error("Error en la inicializacion del filtro: {}", ex.getMessage());
        }
    }

    public void destroy() {
        // liberar recursos.
    }

}
