/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface MedioDefensaService extends Serializable {

    /**
     * Metodo para obtener los medios de defensa por id
     * 
     * @param idSolicitud
     * @return
     */
    MedioDefensa obtenerMedioDefensaById(Long idSolicitud);

}
