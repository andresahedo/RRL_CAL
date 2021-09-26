/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.service.TurnarService;
import mx.gob.sat.siat.juridica.cal.api.AtencionPromocionFacade;
import mx.gob.sat.siat.juridica.cal.service.AtencionPromocionCALServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("atencionPromocionFacade")
public class AtencionPromocionFacadeImpl extends BaseFacadeImpl implements AtencionPromocionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 7586873417132257611L;
    @Autowired
    private transient TramiteServices tramiteServices;
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;
    @Autowired
    private transient AtencionPromocionCALServices atencionPromocionCALServices;

    @Autowired
    private TurnarService turnarService;

    /*
     * . (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.ca.api.AtencionPromocionFacade#
     * registraAtencionPromocion()
     */
    @Override
    public void registraAtencionPromocion() {

    }

    @Override
    public TramiteDTO obtenerTramite(String numAsunto) {
        TramiteDTO tramiteDto = null;
        if (numAsunto != null) {
            Tramite tramite = tramiteServices.buscarTramite(numAsunto, null);
                                                                              // no
                                                                              // se
                                                                              // envia
                                                                              // id
                                                                              // de
                                                                              // solicitud
                                                                              // al
                                                                              // servicio
                                                                              // para
                                                                              // que
                                                                              // solo
                                                                              // busque
                                                                              // por
                                                                              // num.
                                                                              // de
                                                                              // asunto
            if (tramite != null) {
                tramiteDto = tramiteDTOTransformer.transformarDTO(tramite);
            }
        }
        return tramiteDto;
    }

    @Override
    public void actualizaDatosBP(int idInstancia, String numeroAsunto, String usuarioAsignado) {
        tramiteServices.actualizaDatosBP(idInstancia, numeroAsunto, usuarioAsignado);
    }

    @Override
    public String getIdeTareaOrigen() {
        return atencionPromocionCALServices.getIdeTareaOrigen();
    }

    @Override
    public void actualizarAsunto(String numeroAsunto, int idInstancia, String usuario) {

        tramiteServices.actualizaDatosBP(idInstancia, numeroAsunto, usuario);
    }

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado por numero de asunto
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    @Override
    public String obtenerObservacionTurnado(String numeroAsunto) {
        return turnarService.obtenerObservacionTurnado(numeroAsunto);
    }

}
