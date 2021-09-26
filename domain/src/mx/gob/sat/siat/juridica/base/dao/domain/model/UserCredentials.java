/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author softtek
 * 
 */
 @MappedSuperclass
 public class UserCredentials extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;
     @Id
    /**
     * Atributo privado "userName" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String userName;

    /**
     * Atributo privado "password" tipo String
     */
     @Column(name = "PASSWORD")
   private String password;

    /**
     * Constructor
     */
    public UserCredentials() {
        super();
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param userName
     * @param password
     */
    public UserCredentials(final String userName, final String password) {
        this();

        this.userName = userName;
        this.password = password;
    }

    /**
     * 
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *            a fijar
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *            a fijar
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Metodo toString
     * 
     * @return ""
     */
    @Override
    public String toString() {
        final String temporaryPassword = password;

        password = "********";

        password = temporaryPassword;

        return "";
    }
}
