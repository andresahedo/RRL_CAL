package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.ServiciosDisponiblesDAO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ServiciosDisponiblesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosDisponiblesServiceImpl extends BaseSerializableBusinessServices implements
        ServiciosDisponiblesService {

    /**
     * 
     */
    private static final long serialVersionUID = -1646191419859929518L;

    @Autowired
    private ServiciosDisponiblesDAO serviciosDisponiblesDAO;

    @Override
    public List<ServiciosModel> obtenerServiciosRol(String rol) {
        return getServiciosDisponiblesDAO().obtenerServiciosRol(rol);
    }

    public ServiciosDisponiblesDAO getServiciosDisponiblesDAO() {
        return serviciosDisponiblesDAO;
    }

    public void setServiciosDisponiblesDAO(ServiciosDisponiblesDAO serviciosDisponiblesDAO) {
        this.serviciosDisponiblesDAO = serviciosDisponiblesDAO;
    }

}
