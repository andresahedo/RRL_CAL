package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;

public class FiltroPersonaTareaDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = -7820867418150107347L;
    private String usuario;
    private String permiso;
    private String claveUnidadAdministrativa;
    private String rfcEmpleado;
    private CatalogoDTO tipoTramite;
    private Integer first;
    private Integer pageSize;
    private Integer page;
    private String tramite;
    private String rol;
    private String cveTipoTramite;

    /**
     * @return the permiso
     */
    public String getPermiso() {
        return permiso;
    }

    /**
     * @param permiso
     *            the permiso to set
     */
    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    /**
     * @return the claveUnidadAdministrativa
     */
    public String getClaveUnidadAdministrativa() {
        return claveUnidadAdministrativa;
    }

    /**
     * @param claveUnidadAdministrativa
     *            the claveUnidadAdministrativa to set
     */
    public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
        this.claveUnidadAdministrativa = claveUnidadAdministrativa;
    }

    /**
     * @return the rfcEmpleado
     */
    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    /**
     * @param rfcEmpleado
     *            the rfcEmpleado to set
     */
    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    /**
     * @return the tipoTramite
     */
    public CatalogoDTO getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @param tipoTramite
     *            the tipoTramite to set
     */
    public void setTipoTramite(CatalogoDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * @return the first
     */
    public Integer getFirst() {
        return first;
    }

    /**
     * @param first
     *            the first to set
     */
    public void setFirst(Integer first) {
        this.first = first;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCveTipoTramite() {
        return cveTipoTramite;
    }

    public void setCveTipoTramite(String cveTipoTramite) {
        this.cveTipoTramite = cveTipoTramite;
    }

}
