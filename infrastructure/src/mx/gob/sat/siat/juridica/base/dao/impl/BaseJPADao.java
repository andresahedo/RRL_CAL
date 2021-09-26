/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Clase DAO principal que sirve de base para los DAO JPA
 * 
 * @author softtek
 * 
 */
public class BaseJPADao implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -951551007682763589L;

    /**
     * Instancia para el registro de eventos
     */
    private final transient  Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Constructor de la clase
     */
    protected BaseJPADao() {
        super();
    }

     @PersistenceContext
     private transient EntityManager entityManager;

    /**
     * Metodo para inicializar el EntityManager
     */
    @PostConstruct
    public void init() {
        setEntityManager(entityManager);
    }

    /**
     * 
     * @return
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * 
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Logger getLog() {
        return this.log;
    }

}
