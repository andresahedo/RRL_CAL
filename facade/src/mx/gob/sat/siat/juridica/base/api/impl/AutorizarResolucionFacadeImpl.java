package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.AutorizarResolucionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarResolucionRecursoRevocacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("autorizarResolucionFacade")
public class AutorizarResolucionFacadeImpl extends BaseFacadeImpl implements AutorizarResolucionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -3780123513792415969L;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient GenerarResolucionCALService generarResolucionCALService;

    @Autowired
    private transient AutorizarResolucionRecursoRevocacionServices autorizarResolucionRecursoRevocacionServices;

    /**
     * Genera la cadena original de la autorizaci&oacute;n de la
     * resoluci&oacute;n
     * 
     * @return La cadena original
     */
    @Override
    public String generaCadenaOriginalAutorizarResolucion(Long idSolicitud, Date fechaFirma, String rfcFuncionario) {
        String cadenaOriginal = null;
        Tramite tramite = tramiteServices.obtenerTramitePorIdSolicitud(idSolicitud);
        if (tramite != null && tramite.getSolicitud().getDiscriminatorValue() != null) {
            if (tramite.getSolicitud().getDiscriminatorValue()
                    .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                cadenaOriginal =
                        generarResolucionCALService.generaCadenaOriginal(idSolicitud, fechaFirma, rfcFuncionario);
            }
            else if (tramite.getSolicitud().getDiscriminatorValue()
                    .startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                cadenaOriginal =
                        autorizarResolucionRecursoRevocacionServices.generaCadenaOriginal(idSolicitud, fechaFirma,
                                rfcFuncionario);
            }
        }

        return cadenaOriginal;
    }
}
