/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.ca.api.ConsultasAutorizacionesFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Bean que implementan la l&oacute;gica de negocio para la consulta
 * de autorizaciones.
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean
public class ConsultasAutorizacionesBusiness extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -372283349945727025L;
    /**
     * Facade para manejar las consultas de autorizaciones.
     */
    @ManagedProperty("#{consultasAutorizacionesFacade}")
    private ConsultasAutorizacionesFacade consultasAutorizacionesFacade;

    /**
     * Metodo para obtener datos de una solicitud por el idSolicitud.
     * 
     * @param idSolicitud
     * @return
     */
    public SolicitudConsultaAutorizacionDTO obtenerDatos(Long idSolicitud) {
        return consultasAutorizacionesFacade.obtenerDatosSolicitud(idSolicitud);
    }

    /**
     * Metodo que realiza una busqueda de solicitante por el
     * idSolicitud.
     * 
     * @param idSolicitud
     * @return
     */
    public DatosSolicitudDTO buscarSolicitante(Long idSolicitud) {
        return consultasAutorizacionesFacade.obtenerDatosSolicitante(idSolicitud);
    }

    /**
     * 
     * @return consultasAutorizacionFacade
     */
    public ConsultasAutorizacionesFacade getConsultasAutorizacionesFacade() {
        return consultasAutorizacionesFacade;
    }

    /**
     * 
     * @param consultasAutorizacionesFacade
     *            la consultasAutorizacionesFacade a fijar.
     */
    public void setConsultasAutorizacionesFacade(ConsultasAutorizacionesFacade consultasAutorizacionesFacade) {
        this.consultasAutorizacionesFacade = consultasAutorizacionesFacade;
    }

}
