package mx.gob.sat.siat.juridica.base.dto;

public class EntidadFederativaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -1607316019218432382L;

    private String clave;
    private String nombre;
    private PaisDTO paisDTO;
    private String claveEnIDC;

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave
     *            the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the paisDTO
     */
    public PaisDTO getPaisDTO() {
        return paisDTO;
    }

    /**
     * @param paisDTO
     *            the paisDTO to set
     */
    public void setPaisDTO(PaisDTO paisDTO) {
        this.paisDTO = paisDTO;
    }

    /**
     * @return the claveEnIDC
     */
    public String getClaveEnIDC() {
        return claveEnIDC;
    }

    /**
     * @param claveEnIDC
     *            the claveEnIDC to set
     */
    public void setClaveEnIDC(String claveEnIDC) {
        this.claveEnIDC = claveEnIDC;
    }

}
