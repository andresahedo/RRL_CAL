/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.SolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.rrl.api.RegistroSolicitudRRLFacade;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Softtek
 * 
 */
@Component
public class RegistroSolicitudRRLFacadeImpl extends BaseFacadeImpl implements RegistroSolicitudRRLFacade {

    private static final long serialVersionUID = -5078091740955733984L;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient SolicitudDTOTransformer transformer;

    /*
     * Implementaci&oacute;n que obtiene unicamente los campos....
     */
    @Override
    public SolicitudDTO obtenerSolicitudPorId(Long idSolicitud) {
        getLogger().info("requiriendo solicitud por id {} a los servicios", idSolicitud);
        return transformer.transformarDTO(solicitudService.obtenerSolicitudporId(idSolicitud));
    }

}
