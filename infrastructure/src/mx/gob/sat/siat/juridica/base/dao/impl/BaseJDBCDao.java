/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Clase DAO principal que sirve de base para los DAO JDBC
 * 
 * @author softtek
 * 
 */
public class BaseJDBCDao extends JdbcTemplate {

    /**
     * Constructor de la clase
     */
    protected BaseJDBCDao() {
        super();
    }

    /**
     * Metodo llama al metodo del padre para establecer el valor del
     * DataSource
     */
    @Autowired
    @Qualifier("dataSource")
    @Override
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
    }
}
