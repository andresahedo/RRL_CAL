/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;

/**
 * 
 * @author softtek
 * 
 */
public interface MedioDefensaDAO {

    /**
     * Metodo para obtener un medio de defensa por Id de solicitud
     * 
     * @param idSolicitud
     * @return
     */
    MedioDefensa obtenerMedioDefensaById(Long idSolicitud);

}
