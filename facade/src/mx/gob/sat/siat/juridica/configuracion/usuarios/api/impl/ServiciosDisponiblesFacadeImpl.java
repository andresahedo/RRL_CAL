package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ServiciosDisponiblesFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.ServiciosModel;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.ServiciosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.RoleTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.ServiciosDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.RolesServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ServiciosDisponiblesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("serviciosDisponiblesFacade")
public class ServiciosDisponiblesFacadeImpl implements ServiciosDisponiblesFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 6263957007702694399L;

    @Autowired
    private transient ServiciosDisponiblesService serviciosDisponiblesService;

    @Autowired
    private ServiciosDTOTransformer serviciosDTOTransformer;

    @Autowired
    private RolesServices rolesServices;

    @Autowired
    private RoleTransformer roleTransformer;

    @Override
    public List<ServiciosDTO> obtenerServiciosRol(String rol) {
        List<ServiciosDTO> servsDTO = new ArrayList<ServiciosDTO>();
        List<ServiciosModel> servs = getServiciosDisponiblesService().obtenerServiciosRol(rol);
        for (ServiciosModel ser : servs) {
            ServiciosDTO servDTO = serviciosDTOTransformer.transformarDTO(ser);
            servsDTO.add(servDTO);
        }
        return servsDTO;

    }

    @Override
    public List<PermisosAUDTO> obtenerPermisos() {
        List<Role> listaRoles = rolesServices.obtenerRoles();
        List<PermisosAUDTO> listaPermisos = new ArrayList<PermisosAUDTO>();
        for (Role rol : listaRoles) {
            listaPermisos.add(roleTransformer.transformarDTO(rol));
        }
        return listaPermisos;
    }

    public ServiciosDisponiblesService getServiciosDisponiblesService() {
        return serviciosDisponiblesService;
    }

    public void setServiciosDisponiblesService(ServiciosDisponiblesService serviciosDisponiblesService) {
        this.serviciosDisponiblesService = serviciosDisponiblesService;
    }

}
