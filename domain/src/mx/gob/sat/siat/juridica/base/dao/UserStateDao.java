/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface UserStateDao extends Serializable {
    /**
     * Metodo para obtener un usuario por rfc
     * 
     * @param idPersona
     * @return Persona
     */
    UserState obtenerUsuario(String rfc);

    /**
     * Metodo para insertar un usuario
     * 
     * @param usuario
     * @return void
     */
    UserState insertarUsuario(UserState usuario);

    /**
     * Metodo para modificar un usuario usado por administracion de
     * usuarios
     * 
     * @param usuario
     * 
     */
    void modificarUsuario(UserState usuario);
    
    List<UserState> obtenerUsuariosActivos();

}
