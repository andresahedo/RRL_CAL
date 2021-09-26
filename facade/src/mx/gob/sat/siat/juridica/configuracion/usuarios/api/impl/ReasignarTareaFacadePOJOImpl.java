package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;


import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ReasignarTareaFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EmpleadoService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ReasignarTareaService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.constants.TareasRolEnum;
import mx.gob.sat.siat.juridica.rrl.dto.DatosPersonaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.TareaUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("reasignarTareaFacade")
public class ReasignarTareaFacadePOJOImpl implements ReasignarTareaFacade {



    @Autowired
    private transient PersonaServices personaServices;

    @Autowired
    private transient ReasignarTareaService reasignarTareaService;

    @Autowired
    private transient EmpleadoService empleadoService;

    @Override
    public List<CatalogoDTO> getTiposDeTramite() {
        List<CatalogoDTO> tiposDeTramite = new ArrayList<CatalogoDTO>();

        List<TipoTramite> listaDeTramites = reasignarTareaService.obtenerTiposDeTramite();

        for (TipoTramite tramite : listaDeTramites) {
            CatalogoDTO tramiteDTO = new CatalogoDTO();
            tramiteDTO.setClave(tramite.getServicio());
            tramiteDTO.setDescripcion(tramite.getDescripcionServicio());

            tiposDeTramite.add(tramiteDTO);
        }

        return tiposDeTramite;
    }

    @Override
    public List<CatalogoDTO> getModalidadByTipoTramite(String idServicio) {
        List<CatalogoDTO> tiposDeModalidad = new ArrayList<CatalogoDTO>();

        List<TipoTramite> listaDeModalidades = reasignarTareaService.obtenerTiposDeModalidadByTipoTramite(idServicio);

        for (TipoTramite modalidad : listaDeModalidades) {
            CatalogoDTO modalidadDTO = new CatalogoDTO();
            modalidadDTO.setClave(modalidad.getIdTipoTramite().toString());
            modalidadDTO.setDescripcion(modalidad.getDescripcionModalidad());

            tiposDeModalidad.add(modalidadDTO);
        }

        return tiposDeModalidad;
    }

    public PersonaInterna obtenerEmpleado(Long numeroEmpleado) {
        return empleadoService.buscarEmpleado(numeroEmpleado);
    }

    public List<CatalogoDTO> getRoles(List<String> roles) {
        List<Role> rols = reasignarTareaService.obtenerRolesByIdRol(roles);
        List<CatalogoDTO> tiposDeRoles = new ArrayList<CatalogoDTO>();
        for (Role role : rols) {
            CatalogoDTO rolesDto = new CatalogoDTO();
            rolesDto.setClave(role.getName());
            rolesDto.setDescripcion(role.getDescription());
            tiposDeRoles.add(rolesDto);
        }
        return tiposDeRoles;
    }

  

    public void reasignar(List<DatosPersonaTareaDTO> idAsuntos, String permiso, String rfcAsignar, String reasignador) {
        List<String> asuntosOwner = new ArrayList<String>();
        List<String> asuntosNotOwner = new ArrayList<String>();
        List<Long> instanciasNotOwner = new ArrayList<Long>();
        List<Long> instanciasOwner = new ArrayList<Long>();
        List<String> tareasOwner = new ArrayList<String>();
        List<String> tareasNotOwner = new ArrayList<String>();

        Persona personaAsignar = personaServices.obtenerPersonaPorRFC(rfcAsignar);

        for (DatosPersonaTareaDTO personasTarea : idAsuntos) {
            for (TareaUsuarioDTO asunto : personasTarea.getTareas()) {
                // Persona personaAsignada =
                // personaServices.obtenerPersonaPorRFCCorto(rfc)
                if (permiso.equals("abogado")) {

                    if ((asunto.getRfcUsuarioAsignado().equals(asunto.getAbogado()))
                            && (TareasRolEnum.obtenerRolPorTarea(asunto.getNombreTarea()).getRol().equals("abogado"))) {
                        asuntosOwner.add(asunto.getNumeroAsunto());
                        tareasOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasOwner.add(asunto.getIdInstancia());
                    }
                    else {
                        asuntosNotOwner.add(asunto.getNumeroAsunto());
                        tareasNotOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasNotOwner.add(asunto.getIdInstancia());
                    }
                }
                if (permiso.equals("administrador")) {

                    if ((asunto.getRfcUsuarioAsignado().equals(asunto.getAdministrador()))
                            && (TareasRolEnum.obtenerRolPorTarea(asunto.getNombreTarea()).getRol()
                                    .equals("administrador"))) {
                        asuntosOwner.add(asunto.getNumeroAsunto());
                        tareasOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasOwner.add(asunto.getIdInstancia());
                    }
                    else {
                        asuntosNotOwner.add(asunto.getNumeroAsunto());
                        tareasNotOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasNotOwner.add(asunto.getIdInstancia());
                    }
                }
                if (permiso.equals("OficialDePartes")) {

                    if ((asunto.getRfcUsuarioAsignado().equals(asunto.getOficial()))
                            && (TareasRolEnum.obtenerRolPorTarea(asunto.getNombreTarea()).getRol()
                                    .equals("OficialDePartes"))) {
                        asuntosOwner.add(asunto.getNumeroAsunto());
                        tareasOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasOwner.add(asunto.getIdInstancia());
                    }
                    else {
                        asuntosNotOwner.add(asunto.getNumeroAsunto());
                        tareasNotOwner.add(asunto.getIdTareaUsuario().toString());
                        instanciasNotOwner.add(asunto.getIdInstancia());
                    }
                }
            }
        }
        if (!asuntosOwner.isEmpty() && !tareasOwner.isEmpty()) {

            reasignarTareaService.reasignarTareas(asuntosOwner, tareasOwner, permiso, personaAsignar.getRfc(),
                    reasignador);
            reasignarTareaService.cambiaVariableRol(asuntosOwner, instanciasOwner, permiso, rfcAsignar);
        }

        if (!asuntosNotOwner.isEmpty() && !tareasNotOwner.isEmpty()) {
            reasignarTareaService.cambiaVariableRol(asuntosNotOwner, instanciasNotOwner, permiso, rfcAsignar);

        }
    }
}
