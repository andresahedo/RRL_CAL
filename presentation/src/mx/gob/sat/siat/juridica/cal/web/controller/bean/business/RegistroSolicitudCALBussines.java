/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALCommonFacade;
import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Bean para conectar con el backend, atiende todas las peticiones del
 * registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "registroSolicitudCALBussines")
@NoneScoped
public class RegistroSolicitudCALBussines extends RegistroSolicitudCALCommonBussines {

    /**
     * 
     */
    private static final long serialVersionUID = -1231013432247486261L;
    /** Facade para atender el registro de la solicitud. */
    @ManagedProperty("#{registroSolicitudCALFacade}")
    private RegistroSolicitudCALFacade registroSolicitudCALFacade;

    /**
     * @return the registroSolicitudCALFacade
     */
    public RegistroSolicitudCALFacade getRegistroSolicitudCALFacade() {
        return registroSolicitudCALFacade;
    }

    /**
     * @param registroSolicitudCALFacade
     *            the registroSolicitudCALFacade to set
     */
    public void setRegistroSolicitudCALFacade(RegistroSolicitudCALFacade registroSolicitudCALFacade) {
        this.registroSolicitudCALFacade = registroSolicitudCALFacade;
    }

    public RegistroSolicitudCALCommonFacade getRegistroSolicitudCALCommonFacade() {
        return registroSolicitudCALFacade;
    }
}
