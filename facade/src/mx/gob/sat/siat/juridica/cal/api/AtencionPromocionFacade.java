/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

/**
 * @author Softtek
 *
 */
public interface AtencionPromocionFacade extends BaseFacade {

    void registraAtencionPromocion();

    TramiteDTO obtenerTramite(String numAsunto);

    void actualizaDatosBP(int idInstancia, String numeroAsunto, String usuarioAsignado);

    String getIdeTareaOrigen();

    void actualizarAsunto(String numeroAsunto, int idInstancia, String usuario);

    String obtenerObservacionTurnado(String numeroAsunto);

}
