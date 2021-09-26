package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.BuscarEmpleadoFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.PersonaInternaDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("buscarEmpleadoFacade")
public class BuscarEmpleadoFacadeImpl extends BaseFacadeImpl implements BuscarEmpleadoFacade {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient EmpleadoService empleadoService;

    @Autowired
    private PersonaInternaDTOTransformer personaTransformer;

    /**
     * Metodo para obtener informacion de un empleado por numero de
     * empleado
     * 
     * @param numEmpleado
     * @return PersonaInternaDTO
     */
    @Override
    public PersonaInternaDTO obtenerInformacionEmpleado(Long numEmpleado) {
        PersonaInterna personaInterna = empleadoService.buscarEmpleado(numEmpleado);
        if (personaInterna != null) {
            return personaTransformer.transformarDTO(personaInterna);
        }
        else {
            return null;
        }
    }

}
