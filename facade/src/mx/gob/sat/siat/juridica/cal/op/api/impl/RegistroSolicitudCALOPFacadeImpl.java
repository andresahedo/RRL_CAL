package mx.gob.sat.siat.juridica.cal.op.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.cal.api.impl.RegistroSolicitudCALCommonFacadeImpl;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.op.api.RegistroSolicitudCALOPFacade;
import mx.gob.sat.siat.juridica.cal.op.service.RegistroSolicitudCALOPServices;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALCommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("registroSolicitudCALOPFacade")
public class RegistroSolicitudCALOPFacadeImpl extends RegistroSolicitudCALCommonFacadeImpl implements
        RegistroSolicitudCALOPFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient RegistroSolicitudCALOPServices registroSolicitudCALOPServices;

    @Override
    public void buscaUnidadAdminSolicitante(SolicitudCALDTO solicitud, String rfcSolicitante) {
        List<String> listaUnidad =
                registroSolicitudCALOPServices.obtenerUnidadesAdministrativas(rfcSolicitante,
                        TipoRol.OFICIAL_PARTES.getClave(), solicitud.getTipoTramite());
        solicitud.setClaveUnidadEmisora(listaUnidad.get(0));
    }

    @Override
    public RegistroSolicitudCALCommonServices getRegistroSolicitudCALCommonServices() {
        return this.registroSolicitudCALOPServices;
    }
}
