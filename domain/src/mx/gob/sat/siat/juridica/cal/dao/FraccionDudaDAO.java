/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface FraccionDudaDAO {

    /**
     * Metodo para obtener fracciones arancelarias en las que existe
     * duda por Id de solicitud
     * 
     * @param idSolicitud
     * @return
     */
    List<FraccionDuda> obtenerFraccionesDudaBySolicitudId(Long idSolicitud);

    FraccionDuda obtenerFraccionById(Long idFraccion);

}
