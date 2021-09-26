package mx.gob.sat.siat.juridica.base.dto;

public class ColoniaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -3449756137808792979L;

    private String clave;
    private DelegacionMunicipioDTO delegacionMunicipioDTO;
    private String nombre;
    private LocalidadDTO localidad;
    private String cp;
    private String idColoniaIDC;

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
     * @return the localidad
     */
    public LocalidadDTO getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad
     *            the localidad to set
     */
    public void setLocalidad(LocalidadDTO localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp
     *            the cp to set
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * @return the idColoniaIDC
     */
    public String getIdColoniaIDC() {
        return idColoniaIDC;
    }

    /**
     * @param idColoniaIDC
     *            the idColoniaIDC to set
     */
    public void setIdColoniaIDC(String idColoniaIDC) {
        this.idColoniaIDC = idColoniaIDC;
    }

}
