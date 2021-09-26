/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.service.InformacionUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("informacionUsuarioServices")
public class InformacionUsuarioServicesImpl extends BaseBusinessServices implements InformacionUsuarioServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = -1554188860334033540L;

    @Autowired
    private InformacionUsuarioDao informacionUsuarioDao;

    public void insertarRolUsuario(InformacionUsuario informacionUsuario) {
        informacionUsuarioDao.insertarRolUsuario(informacionUsuario);
    }
}
