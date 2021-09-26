package mx.gob.sat.siat.juridica.base.dto;

public class PaisDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4111804009265834082L;

    private String clave;
    private String nombre;
    private String cvePaisWco;
    private String nombreAlterno;

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
     * @return the cvePaisWco
     */
    public String getCvePaisWco() {
        return cvePaisWco;
    }

    /**
     * @param cvePaisWco
     *            the cvePaisWco to set
     */
    public void setCvePaisWco(String cvePaisWco) {
        this.cvePaisWco = cvePaisWco;
    }

    /**
     * @return the nombreAlterno
     */
    public String getNombreAlterno() {
        return nombreAlterno;
    }

    /**
     * @param nombreAlterno
     *            the nombreAlterno to set
     */
    public void setNombreAlterno(String nombreAlterno) {
        this.nombreAlterno = nombreAlterno;
    }

}
