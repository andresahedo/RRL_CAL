/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
public interface BandejaServices extends Serializable {

    /**
     * Metodo para buscar una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    Persona obtenerPersonaPorRfc(String rfc);

    /**
     * @param numeroAsunto
     * @return
     */
    Date obtenerFechaRequerimiento(String numeroAsunto);
}
