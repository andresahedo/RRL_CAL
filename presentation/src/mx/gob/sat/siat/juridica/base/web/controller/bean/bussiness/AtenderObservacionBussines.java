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
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.AtenderObservacionFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.Date;

/**
 * Bean que implementan la l&oacute;gica de negocio para atender
 * requerimiento
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean(name = "atenderObservacionBussines")
public class AtenderObservacionBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5147855292746059873L;

    /**
     * Facade que publica los metodos que implementan la logica de
     * negocio para atender un requerimiento.
     */

    /** Facade para turnar recurso revocacion */
    @ManagedProperty("#{atenderObservacionFacade}")
    private AtenderObservacionFacade atenderObservacionFacade;

    public String generaCadenaOriginal(Long idSolicitud, Date fechaFirma) {
        return "|cadena|observacion|";
    }

    /**
     * Metodo para atender la firma del requerimiento.
     * 
     * @param datosBandeja
     * @param firma
     * @param idSolicitud
     * @param idRequerimiento
     * @throws SolicitudNoGuardadaException
     */
    public void firmaAtender(String numeroAsunto, Long idObservacion, String rfc, FirmaDTO firmaDto, String idTarea)
            throws SolicitudNoGuardadaException {
        getAtenderObservacionFacade().firmaAtender(numeroAsunto, idObservacion, rfc, firmaDto, idTarea);

    }

    public ObservacionDTO obternerObservacion(String numeroAsunto) {

        return getAtenderObservacionFacade().obternerObservacion(numeroAsunto);
    }

    public PersonaSolicitudDTO obtenerSolicitante(Long idSolicitud) {

        return getAtenderObservacionFacade().obtenerSolicitante(idSolicitud);
    }

    /**
     * @return the atenderObservacionFacade
     */
    public AtenderObservacionFacade getAtenderObservacionFacade() {
        return atenderObservacionFacade;
    }

    /**
     * @param atenderObservacionFacade
     *            the atenderObservacionFacade to set
     */
    public void setAtenderObservacionFacade(AtenderObservacionFacade atenderObservacionFacade) {
        this.atenderObservacionFacade = atenderObservacionFacade;
    }

}
