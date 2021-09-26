package mx.gob.sat.siat.juridica.base.dto;

public class LocalidadDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 7965853547491791950L;

    private String nombre;
    private String clave;
    private DelegacionMunicipioDTO delegacionMunicipioDTO;
    private String codigoPostal;
    private String codigoSAT;

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
     * @return the delegacionMunicipioDTO
     */
    public DelegacionMunicipioDTO getDelegacionMunicipioDTO() {
        return delegacionMunicipioDTO;
    }

    /**
     * @param delegacionMunicipioDTO
     *            the delegacionMunicipioDTO to set
     */
    public void setDelegacionMunicipioDTO(DelegacionMunicipioDTO delegacionMunicipioDTO) {
        this.delegacionMunicipioDTO = delegacionMunicipioDTO;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @param codigoPostal
     *            the codigoPostal to set
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return the codigoSAT
     */
    public String getCodigoSAT() {
        return codigoSAT;
    }

    /**
     * @param codigoSAT
     *            the codigoSAT to set
     */
    public void setCodigoSAT(String codigoSAT) {
        this.codigoSAT = codigoSAT;
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

}
