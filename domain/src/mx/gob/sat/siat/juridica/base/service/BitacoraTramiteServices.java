/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface BitacoraTramiteServices extends Serializable {

    /**
     * Metodo para guardar bitacora
     * 
     */
    void insertarBitacora(Bitacora bitacora);
    
}
