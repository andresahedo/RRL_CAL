package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.util.helper.ResolucionesImpugnadasHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ResolucionesImpHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -244595099916065323L;

    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private ResolucionesImpugnadasHelper resolucionesImpugnadasHelper;

    public boolean validarMedioDeDefensa(ResolucionImpugnadaDTO resolucion) {

        Tramite tramite =
                tramiteDao.obtenerTramitePorResolucion(resolucion.getResImpugnada(), resolucion.getIdTramite());
        if (tramite != null) {

            return resolucionesImpugnadasHelper.validacionEstadoTramiteResolucion(tramite);
        }
        else {
            return false;
        }

    }

}
