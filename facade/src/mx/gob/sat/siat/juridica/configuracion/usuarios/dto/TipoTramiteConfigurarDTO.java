package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class TipoTramiteConfigurarDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7899962342863497227L;
    private String idServicio;
    private String idUnidadAdministrativa;
    private Integer blnSeleccionado;
    private String descripcionServicio;

    /**
     * @return the idServicio
     */
    public String getIdServicio() {
        return idServicio;
    }

    /**
     * @param idServicio
     *            the idServicio to set
     */
    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    /**
     * @return the idUnidadAdministrativa
     */
    public String getIdUnidadAdministrativa() {
        return idUnidadAdministrativa;
    }

    /**
     * @param idUnidadAdministrativa
     *            the idUnidadAdministrativa to set
     */
    public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
        this.idUnidadAdministrativa = idUnidadAdministrativa;
    }

    /**
     * @return the blnSeleccionado
     */
    public Integer getBlnSeleccionado() {
        return blnSeleccionado;
    }

    /**
     * @param blnSeleccionado
     *            the blnSeleccionado to set
     */
    public void setBlnSeleccionado(Integer blnSeleccionado) {
        this.blnSeleccionado = blnSeleccionado;
    }

    /**
     * @return the descripcionServicio
     */
    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    /**
     * @param descripcionServicio
     *            the descripcionServicio to set
     */
    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

}
