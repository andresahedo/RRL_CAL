/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface FraccionDudaService extends Serializable {

    /**
     * Metodo para obtener las fracciones duda por id
     * 
     * @param idSolicitud
     * @return
     */
    List<FraccionDuda> obtenerFraccionesDudaBySolicitudId(Long idSolicitud);

}
