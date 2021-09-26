package mx.gob.sat.siat.juridica.base.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.Collection;

public class UserProfileDTO extends BaseModel {

    private static final long serialVersionUID = 7276244993138610152L;

    private String rfc;
    private String nombreCompleto;
    private String usuario;
    private String rol;
    private Collection<String> roles;
    private Collection<String> rolesNovell;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String ip;
    private boolean esContribuyente;
    private String sessionIdNovell;
    private String idTipoPersona;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Collection<String> getRolesNovell() {
        return rolesNovell;
    }

    public void setRolesNovell(Collection<String> rolesNovell) {
        this.rolesNovell = rolesNovell;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean getEsContribuyente() {
        return esContribuyente;
    }

    public void setEsContribuyente(boolean esContribuyente) {
        this.esContribuyente = esContribuyente;
    }

    public String getSessionIdNovell() {
        return sessionIdNovell;
    }

    public void setSessionIdNovell(String sessionIdNovell) {
        this.sessionIdNovell = sessionIdNovell;
    }

    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }
}
