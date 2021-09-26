/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase que sirve de base para los repositorios con JPA
 * 
 * @author softtek
 * 
 */
public class BaseJPARepository {

    /**
     * Instancia del EntityManager
     */
     @PersistenceContext
     private EntityManager entityManager;

    /**
     * Constructor de la clase
     */
    protected BaseJPARepository() {
        super();
    }

    /**
     * Metodo que obtiene el EntityManager
     * 
     * @return
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
