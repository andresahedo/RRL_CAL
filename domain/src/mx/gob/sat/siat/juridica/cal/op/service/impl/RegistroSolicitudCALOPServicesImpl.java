package mx.gob.sat.siat.juridica.cal.op.service.impl;

import mx.gob.sat.siat.juridica.cal.op.service.RegistroSolicitudCALOPServices;
import mx.gob.sat.siat.juridica.cal.service.impl.RegistroSolicitudCALCommonServicesImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("registroSolicitudCALOPServices")
public class RegistroSolicitudCALOPServicesImpl extends RegistroSolicitudCALCommonServicesImpl implements
        RegistroSolicitudCALOPServices {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol, String tipoTramite) {
        return getPersonaDao().obtenerUnidadesAdministrativas(rfc, cveRol, tipoTramite);
    }

}
