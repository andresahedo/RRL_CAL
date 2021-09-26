/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface UserStateServices extends Serializable {

    /**
     * Metodo para buscar un tramite por numero de asunto y id de
     * solicitud
     * 
     * @param rfc
     * @return UserState
     */
    UserState buscarUsuario(String rfc);

    /**
     * Metodo para insertar un usuario
     * 
     * @param usuario
     */
    void insertarUsuario(UserState usuario);
}
