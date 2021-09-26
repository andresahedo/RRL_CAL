/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;

/**
 * Clase Business para la busqueda de empleados
 * 
 * @author Softtek - EQG
 * @since 1/11/2014
 */
public interface BuscarEmpleadoFacade extends BaseFacade {

    /**
     * Metodo para obtener informacion de un empleado por numero de
     * empleado
     * 
     * @param numEmpleado
     * @return PersonaInternaDTO
     */
    PersonaInternaDTO obtenerInformacionEmpleado(Long numEmpleado);

}
