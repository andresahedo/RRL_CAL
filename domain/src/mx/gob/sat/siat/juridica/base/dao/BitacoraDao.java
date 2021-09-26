/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;

import java.io.Serializable;


/**
 * 
 * @author Softtek
 * 
 */
public interface BitacoraDao extends Serializable {

    Bitacora obtenerBitacoraPorId(String idBitacora);

    void insertarBitacora(Bitacora bitacora);
    
}
