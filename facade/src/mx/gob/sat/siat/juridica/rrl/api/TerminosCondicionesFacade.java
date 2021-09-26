package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.TerminosCondicionesDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

public interface TerminosCondicionesFacade extends BaseFacade {

    TerminosCondicionesDTO obtenerTerminos(int numPantalla);

}
