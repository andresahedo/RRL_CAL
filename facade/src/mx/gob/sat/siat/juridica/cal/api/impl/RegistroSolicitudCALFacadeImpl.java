/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALFacade;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALCommonServices;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade para atender el registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@Component("registroSolicitudCALFacade")
public class RegistroSolicitudCALFacadeImpl extends RegistroSolicitudCALCommonFacadeImpl implements
        RegistroSolicitudCALFacade {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1514140449969098245L;
    @Autowired
    private transient RegistroSolicitudCALServices registroSolicitudCALServices;

    public RegistroSolicitudCALCommonServices getRegistroSolicitudCALCommonServices() {
        return this.registroSolicitudCALServices;
    }

}
