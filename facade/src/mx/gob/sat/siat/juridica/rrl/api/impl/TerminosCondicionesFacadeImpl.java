package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;
import mx.gob.sat.siat.juridica.base.dto.TerminosCondicionesDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.TerminosCondicionesDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.rrl.api.TerminosCondicionesFacade;
import mx.gob.sat.siat.juridica.rrl.service.TerminosCondicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("terminosCondicionesFacade")
public class TerminosCondicionesFacadeImpl extends BaseFacadeImpl implements TerminosCondicionesFacade {

    @Autowired
    private transient TerminosCondicionesService terminosService;

    @Autowired
    private transient TerminosCondicionesDTOTransformer terminosTransformer;

    /**
     * 
     */
    private static final long serialVersionUID = 7078546899657680775L;

    @Override
    public TerminosCondicionesDTO obtenerTerminos(int numPantalla) {

        TerminosCondiciones terminosModel = terminosService.obtenerTerminos(numPantalla);

        return terminosTransformer.transformarModel(terminosModel);
    }

}
