package mx.gob.sat.siat.juridica.base.dto;

import java.util.List;

public class ServicioDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7378942133226770743L;

    /**
     * Atributo privado "servicio" tipo String
     */
    private String servicio;

    /**
     * Atributo privado "servicio" tipo String
     */
    private String descripcionServicio;

    /**
     * Atributo privado "modalidades" tipo Vigencia
     */
    private List<TipoTramiteDTO> modalidades;

    /**
     * @return the modalidades
     */
    public List<TipoTramiteDTO> getModalidades() {
        return modalidades;
    }

    /**
     * @param modalidades
     *            the modalidades to set
     */
    public void setModalidades(List<TipoTramiteDTO> modalidades) {
        this.modalidades = modalidades;
    }

    /**
     * @return the servicio
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * @param servicio
     *            the servicio to set
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
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
