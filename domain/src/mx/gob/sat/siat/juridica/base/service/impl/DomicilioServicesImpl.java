package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.DomicilioDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.service.DomicilioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("domicilioServices")
public class DomicilioServicesImpl implements DomicilioServices {

    /**
     * 
     */
    private static final long serialVersionUID = -3381356970948066118L;

    @Autowired
    private transient DomicilioDAO domicilioDAO;

    @Override
    public EntidadFederativa buscarEntidadByClave(String clave) {
        return domicilioDAO.buscarEntidadByClave(clave);
    }

    @Override
    public Colonia buscarColoniaByClave(String clave) {
        return domicilioDAO.buscarColoniaByClave(clave);
    }

    @Override
    public Pais buscarPaisByClave(String clave) {
        return domicilioDAO.buscarPaisByClave(clave);
    }

    @Override
    public DelegacionMunicipio buscarDelegacionByClave(String clave) {
        return domicilioDAO.buscarDelegacionByClave(clave);
    }

    @Override
    public Localidad buscarLocalidadByClave(String clave) {
        return domicilioDAO.buscarLocalidadByClave(clave);
    }

}
