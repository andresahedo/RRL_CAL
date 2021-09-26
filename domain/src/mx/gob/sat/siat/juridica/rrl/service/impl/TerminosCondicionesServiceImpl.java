package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;
import mx.gob.sat.siat.juridica.base.service.impl.BaseBusinessServices;
import mx.gob.sat.siat.juridica.rrl.dao.TerminosCondicionesDAO;
import mx.gob.sat.siat.juridica.rrl.service.TerminosCondicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminosCondicionesServiceImpl extends BaseBusinessServices implements TerminosCondicionesService {

    /**
     * 
     */
    private static final long serialVersionUID = 7463440971312788223L;

    @Autowired
    private TerminosCondicionesDAO terminosDao;

    @Override
    public TerminosCondiciones obtenerTerminos(int numPantalla) {

        return terminosDao.obtenerTerminosCondiciones(numPantalla);
    }

    /**
     * @return the terminosDao
     */
    public TerminosCondicionesDAO getTerminosDao() {
        return terminosDao;
    }

    /**
     * @param terminosDao
     *            the terminosDao to set
     */
    public void setTerminosDao(TerminosCondicionesDAO terminosDao) {
        this.terminosDao = terminosDao;
    }

}
