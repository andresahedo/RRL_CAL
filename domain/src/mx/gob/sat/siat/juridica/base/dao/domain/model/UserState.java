/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCP_USUARIO")
public class UserState extends UserCredentials {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6396164332328754658L;

    /**
     * Atributo privado "active" tipo boolean
     */
    @Column(name = "BLNACTIVO")
    private boolean active;

    /**
     * Atributo privado "logged" tipo boolean
     */
    @Column(name = "BLNCONECTADO")
    private Boolean logged;

    /**
     * Atributo privado "lastAccessTime" tipo Long
     */
    @Column(name = "ULTIMOACCESO")
    private Long lastAccessTime;

    /**
     * Atributo privado "aliveSignal" tipo Long
     */
    @Column(name = "ALIVESIGNAL")
    private Long aliveSignal;

    /**
     * Atributo privado "tokenConfirmacion" tipo String
     */
    @Column(name = "TOKENCONFIRMACION")
    private String tokenConfirmacion;

    @Column(name = "BLNACTIVOGLOBAL")
    private Boolean blnActivoGlobal;

    /**
     * Constructor
     */
    protected UserState() {
        super();
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param userName
     * @param password
     */
    public UserState(final String userName, final String password) {
        super(userName, password);
    }

    /**
     * 
     * @param userCredentials
     *            a fijar
     */
    public UserState(final UserCredentials userCredentials) {
        this(null != userCredentials ? userCredentials.getUserName() : null, null != userCredentials ? userCredentials
                .getPassword() : null);
    }

    /**
     * 
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * 
     * @param active
     *            a fijar
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return logger
     */
    public Boolean isLogged() {
        return logged;
    }

    /**
     * 
     * @param logged
     *            a fijar
     */
    public void setLogged(final Boolean logged) {
        this.logged = logged;
    }

    /**
     * 
     * @return lastAccessTime
     */
    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * 
     * @param lastAccessTime
     *            a fijar
     */
    public void setLastAccessTime(final Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * 
     * @return aliveSignal
     */
    public Long getAliveSignal() {
        return aliveSignal;
    }

    /**
     * 
     * @param aliveSignal
     *            a fijar
     */
    public void setAliveSignal(final Long aliveSignal) {
        this.aliveSignal = aliveSignal;
    }

    /**
     * 
     * @return tokenConfirmacion
     */
    public String getTokenConfirmacion() {
        return tokenConfirmacion;
    }

    /**
     * 
     * @param tokenConfirmacion
     *            a fijar
     */
    public void setTokenConfirmacion(String tokenConfirmacion) {
        this.tokenConfirmacion = tokenConfirmacion;
    }

    /**
     * @return the blnActivoGlobal
     */
    public Boolean getBlnActivoGlobal() {
        return blnActivoGlobal;
    }

    /**
     * @param blnActivoGlobal
     *            the blnActivoGlobal to set
     */
    public void setBlnActivoGlobal(Boolean blnActivoGlobal) {
        this.blnActivoGlobal = blnActivoGlobal;
    }

}
