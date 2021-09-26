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

/**
 * 
 * @author softtek
 * 
 */
public class ParametrosTareaUsuario extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "actionId" tipo String
     */
    private String actionId;

    /**
     * Atributo privado "actionName" tipo String
     */
    private String actionName;

    /**
     * Atributo privado "bpiId" tipo String
     */
    private String bpiId;

    /**
     * Atributo privado "bpName" tipo String
     */
    private String bpName;

    /**
     * Atributo privado "paginaNombre" tipo String
     */
    private String paginaNombre;

    /**
     * Atributo privado "finTramite" tipo String
     */
    private String finTramite;

    /**
     * Atributo privado "folioTramite" tipo String
     */
    private String folioTramite;

    /**
     * Atributo privado "estadoTramite" tipo String
     */
    private String estadoTramite;

    /**
     * Atributo privado "fechaInicioTramite" tipo String
     */
    private String fechaInicioTramite;

    /**
     * Atributo privado "rfc" tipo String
     */
    private String rfc;

    /**
     * Atributo privado "razonSocial" tipo String
     */
    private String razonSocial;

    /**
     * Atributo privado "idSolicitud" tipo String
     */
    private String idSolicitud;

    /**
     * Atributo privado "user" tipo UserCredentials
     */
    private UserCredentials user;

    /**
     * Atributo privado "selloGenerado" tipo String
     */
    private String selloGenerado;

    /**
     * Atributo privado "datosObjetoFirmar" tipo String
     */
    private String datosObjetoFirmar;

    /**
     * Atributo privado "accionRetorno" tipo String
     */
    private String accionRetorno;

    /**
     * Atributo privado "currentUser" tipo String
     */
    private String currentUser;

    /**
     * Atributo privado "tipoTramite" tipo String
     */
    private String tipoTramite;

    /**
     * Atributo privado "idTarea" tipo String
     */
    private String idTarea;

    /**
     * Atributo privado "numeroIteracion" tipo Integer
     */
    private Integer numeroIteracion;

    /**
     * Atributo privado "dias" tipo int
     */
    private int dias;

    /**
     * Atributo privado "generico" tipo String
     */
    private String generico;

    /**
     * 
     * @return generico
     */
    public String getGenerico() {
        return generico;
    }

    /**
     * 
     * @param generico
     *            a fijar
     */
    public void setGenerico(String generico) {
        this.generico = generico;
    }

    /**
     * 
     * @return numeroIteracion
     */
    public Integer getNumeroIteracion() {
        return numeroIteracion;
    }

    /**
     * 
     * @param numeroIteracion
     *            a fijar
     */
    public void setNumeroIteracion(Integer numeroIteracion) {
        this.numeroIteracion = numeroIteracion;
    }

    /**
     * 
     * @return folioTramite
     */
    public String getFolioTramite() {
        return folioTramite;
    }

    /**
     * 
     * @param folioTramite
     *            a fijar
     */
    public void setFolioTramite(String folioTramite) {
        this.folioTramite = folioTramite;
    }

    /**
     * 
     * @return actionId
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * 
     * @param actionId
     *            a fijar
     */
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    /**
     * 
     * @return actionName
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 
     * @param actionName
     *            a fijar
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * 
     * @return bpiId
     */
    public String getBpiId() {
        return bpiId;
    }

    /**
     * 
     * @param bpiId
     *            a fijar
     */
    public void setBpiId(String bpiId) {
        this.bpiId = bpiId;
    }

    /**
     * 
     * @return bpName
     */
    public String getBpName() {
        return bpName;
    }

    /**
     * 
     * @param bpName
     *            a fijar
     */
    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    /**
     * 
     * @return paginaNombre
     */
    public String getPaginaNombre() {
        return paginaNombre;
    }

    /**
     * 
     * @param paginaNombre
     *            a fijar
     */
    public void setPaginaNombre(String paginaNombre) {
        this.paginaNombre = paginaNombre;
    }

    /**
     * 
     * @param finTramite
     *            a fijar
     */
    public void setFinTramite(String finTramite) {
        this.finTramite = finTramite;
    }

    /**
     * 
     * @return finTramite
     */
    public String getFinTramite() {
        return finTramite;
    }

    /**
     * 
     * @return estadoTramite
     */
    public String getEstadoTramite() {
        return estadoTramite;
    }

    /**
     * 
     * @param estadoTramite
     *            a fijar
     */
    public void setEstadoTramite(String estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    /**
     * 
     * @return fechaInicioTramite
     */
    public String getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    /**
     * 
     * @param fechaInicioTramite
     *            a fijar
     */
    public void setFechaInicioTramite(String fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    /**
     * 
     * @return rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * 
     * @param rfc
     *            a fijar
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * 
     * @return razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * 
     * @param razonSocial
     *            a fijar
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * 
     * @return idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * 
     * @param idSolicitud
     *            a fijar
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return user
     */
    public UserCredentials getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *            a fijar
     */
    public void setUser(UserCredentials user) {
        this.user = user;
    }

    /**
     * 
     * @return selloGenerado
     */
    public String getSelloGenerado() {
        return selloGenerado;
    }

    /**
     * 
     * @param selloGenerado
     *            a fijar
     */
    public void setSelloGenerado(String selloGenerado) {
        this.selloGenerado = selloGenerado;
    }

    /**
     * 
     * @return datosObjetoFirmar
     */
    public String getDatosObjetoFirmar() {
        return datosObjetoFirmar;
    }

    /**
     * 
     * @param datosObjetoFirmar
     *            a fijar
     */
    public void setDatosObjetoFirmar(String datosObjetoFirmar) {
        this.datosObjetoFirmar = datosObjetoFirmar;
    }

    /**
     * 
     * @return accionRetorno
     */
    public String getAccionRetorno() {
        return accionRetorno;
    }

    /**
     * 
     * @param accionRetorno
     *            a fijar
     */
    public void setAccionRetorno(String accionRetorno) {
        this.accionRetorno = accionRetorno;
    }

    /**
     * 
     * @return currentUser
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * 
     * @param currentUser
     *            a fijar
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * 
     * @return tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * 
     * @param tipoTramite
     *            a fijar
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * 
     * @return dias
     */
    public int getDias() {
        return dias;
    }

    /**
     * 
     * @param dias
     *            a fijar
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * 
     * @return idTarea
     */
    public String getIdTarea() {
        return idTarea;
    }

    /**
     * 
     * @param idTarea
     *            a fijar
     */
    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

}
