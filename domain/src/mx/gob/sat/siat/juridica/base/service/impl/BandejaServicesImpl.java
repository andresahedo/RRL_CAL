/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.service.BandejaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class BandejaServicesImpl implements BandejaServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "personaDao" tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private RequerimientoDao requerimientoDao;

    /**
     * Metodo para buscar una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    public Persona obtenerPersonaPorRfc(String rfc) {
        return personaDao.obtenerPersonaPorRFC(rfc);
    }

    /**
     * Metodo para buscar una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    @Override
    public Date obtenerFechaRequerimiento(String numeroAsunto) {

        return requerimientoDao.obtenerFechaRequerimiento(numeroAsunto);
    }
}
