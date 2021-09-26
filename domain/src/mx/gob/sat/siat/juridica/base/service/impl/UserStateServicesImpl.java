/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.UserStateDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.service.UserStateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("userStateServices")
public class UserStateServicesImpl extends BaseBusinessServices implements UserStateServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = -1554188860334033540L;

    /**
     * Atributo privado "userStateDao" tipo UserStateDao
     */
    @Autowired
    private UserStateDao userStateDao;

    /**
     * Metodo para buscar un tramite por numero de asunto y id de
     * solicitud
     * 
     * @param rfc
     * @return UserState
     */
    public UserState buscarUsuario(String rfc) {
        return userStateDao.obtenerUsuario(rfc);
    }

    /**
     * Metodo para insertar un usuario
     * 
     * @param usuario
     */
    public void insertarUsuario(UserState usuario) {
        userStateDao.insertarUsuario(usuario);
    }
}
