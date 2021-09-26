/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Remision;

/**
 * 
 * @author Softtek
 * 
 */
public interface RemisionDao {
    /**
     * Metodo para guardar una remision
     * 
     * @param remision
     */
    void guardarRemision(Remision remision);

    /**
     * Metodo para obtener la ultima remision por id de tramite
     * 
     * @param numAsunto
     * @return Remision
     */
    Remision obtenerUltimaRemisionPorIdTramite(String numAsunto);

}
