/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.validador;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.util.validator.BaseValidator;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import org.springframework.stereotype.Component;

/**
 * 
 * @author softtek
 */
@Component
public class DetallePromocionConsultaAutorizacionValidator extends BaseValidator {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6063955466750450583L;

    /**
     * Metodo para validar un estado procesal
     * 
     * @param cve1
     * @param cve2
     * @throws SolicitudNoGuardadaException
     */
    public void validarEstadoProcesal(String cve1, String cve2) throws SolicitudNoGuardadaException {
        if (cve1.equals(EstadoTramite.RESUELTO.getClave()) && cve2.equals(EstadoTramite.EN_ESTUDIO.getClave())
                || cve1.equals(EstadoTramite.RESUELTO.getClave()) && cve2.equals(EstadoTramite.REQUERIDO.getClave())) {
            return;
        }
        else {
            if (cve1.equals(EstadoTramite.REQUERIDO.getClave()) && cve2.equals(EstadoTramite.EN_ESTUDIO.getClave())) {
                return;
            }
            else {
                if (cve1.equals(EstadoTramite.REMITIDO.getClave()) && cve2.equals(EstadoTramite.EN_ESTUDIO.getClave())) {
                    return;
                }
                else {
                    if (cve1.equals(EstadoTramite.RESUELTO_NOTIFICADO.getClave())
                            && cve2.equals(EstadoTramite.RESUELTO.getClave())) {
                        return;
                    }
                    else {
                        if (cve1.equals(EstadoTramite.EN_ESTUDIO.getClave())
                                && cve2.equals(EstadoTramite.REMITIDO.getClave())) {
                            return;
                        }
                        else {
                            throw new SolicitudNoGuardadaException(RegistroSolicitudConstants.SOLICITUD_NO_GUARDADA_EP);
                        }
                    }
                }
            }
        }

    }

}
