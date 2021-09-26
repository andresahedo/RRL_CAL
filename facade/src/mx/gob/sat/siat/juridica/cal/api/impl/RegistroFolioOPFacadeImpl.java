package mx.gob.sat.siat.juridica.cal.api.impl;

import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.cal.api.RegistroFolioOPFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("registroFolioOPFacade")
public class RegistroFolioOPFacadeImpl implements RegistroFolioOPFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 6903948192852442814L;

    @Autowired
    private transient TramiteDao tramitedao;

    @Override
    public Tramite obtenerTramitePorId(String numeroAsunto) {
        return tramitedao.obtenerTramitePorId(numeroAsunto);
    }

}
