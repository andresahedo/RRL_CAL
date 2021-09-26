/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service.impl;

import mx.gob.sat.siat.juridica.base.service.impl.TramiteServicesImpl;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.service.DetallePromocionServices;
import mx.gob.sat.siat.juridica.ca.util.validador.DetallePromocionConsultaAutorizacionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service()
public class DetallePromocionServicesImpl extends TramiteServicesImpl implements DetallePromocionServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado
     * "detallePromocionConsultaAutorizacionValidator" tipo
     * DetallePromocionConsultaAutorizacionValidator
     */
    @Autowired
    private DetallePromocionConsultaAutorizacionValidator detallePromocionConsultaAutorizacionValidator;

    /**
     * Metodo para validar un estado procesal
     */
    @Override
    public void validaEstadoProcesal(String cveEstado, String cveEstadoTramite) throws SolicitudNoGuardadaException {
        detallePromocionConsultaAutorizacionValidator.validarEstadoProcesal(cveEstado, cveEstadoTramite);
    }

}
