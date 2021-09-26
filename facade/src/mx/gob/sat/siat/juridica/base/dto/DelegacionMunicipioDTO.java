package mx.gob.sat.siat.juridica.base.dto;

import java.util.List;

public class DelegacionMunicipioDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -8142075924836764742L;

    private String clave;
    private String nombre;
    private String idMunicipioIDC;
    private List<ColoniaDTO> coloniasDTO;
    private EntidadFederativaDTO entidadFederativaDTO;

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
     * @return the idMunicipioIDC
     */
    public String getIdMunicipioIDC() {
        return idMunicipioIDC;
    }

    /**
     * @param idMunicipioIDC
     *            the idMunicipioIDC to set
     */
    public void setIdMunicipioIDC(String idMunicipioIDC) {
        this.idMunicipioIDC = idMunicipioIDC;
    }

    /**
     * @return the coloniasDTO
     */
    public List<ColoniaDTO> getColoniasDTO() {
        return coloniasDTO;
    }

    /**
     * @param coloniasDTO
     *            the coloniasDTO to set
     */
    public void setColoniasDTO(List<ColoniaDTO> coloniasDTO) {
        this.coloniasDTO = coloniasDTO;
    }

    /**
     * @return the entidadFederativaDTO
     */
    public EntidadFederativaDTO getEntidadFederativaDTO() {
        return entidadFederativaDTO;
    }

    /**
     * @param entidadFederativaDTO
     *            the entidadFederativaDTO to set
     */
    public void setEntidadFederativaDTO(EntidadFederativaDTO entidadFederativaDTO) {
        this.entidadFederativaDTO = entidadFederativaDTO;
    }

}
