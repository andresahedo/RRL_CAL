package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

public interface RegistroFolioOPFacade extends BaseFacade {

    Tramite obtenerTramitePorId(String numeroAsunto);

}
