/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.GenerarObservacionFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Bussines que implementan la l&oacute;gica de negocio para turnar
 * recurso de revocacion
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "generarObservacionBussines")
@NoneScoped
public class GenerarObservacionBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3688290193065023930L;

    /** Facade para turnar recurso revocacion */
    @ManagedProperty("#{generarObservacionFacade}")
    private GenerarObservacionFacade generarObservacionFacade;

    /**
     * M&eacute;todo para obtener las autorizades emisoras
     * 
     * @param observacionDTO
     * @throws SolicitudNoGuardadaException
     */
    public ObservacionDTO observar(ObservacionDTO observacionDTO, Long idTarea, String rfcUsuario)
            throws SolicitudNoGuardadaException {
        return generarObservacionFacade.observar(observacionDTO, idTarea, rfcUsuario);
    }

    /**
     * @return the generarObservacionFacade
     */
    public GenerarObservacionFacade getGenerarObservacionFacade() {
        return generarObservacionFacade;
    }

    /**
     * @param generarObservacionFacade
     *            the generarObservacionFacade to set
     */
    public void setGenerarObservacionFacade(GenerarObservacionFacade generarObservacionFacade) {
        this.generarObservacionFacade = generarObservacionFacade;
    }

    public TramiteDTO obtenerTramite(String numAsunto) {
        return generarObservacionFacade.obtenerTramite(numAsunto);
    }

}
