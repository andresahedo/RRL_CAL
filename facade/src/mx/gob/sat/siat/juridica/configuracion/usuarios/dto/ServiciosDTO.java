package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class ServiciosDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4111804009265834082L;

    private Long idComponente;
    private String url;
    private String descripcion;

    /**
     * @return the idComponente
     */
    public Long getIdComponente() {
        return idComponente;
    }

    /**
     * @param idComponente
     *            the idComponente to set
     */
    public void setIdComponente(Long idComponente) {
        this.idComponente = idComponente;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
