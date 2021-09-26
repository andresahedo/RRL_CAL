package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class FraccionArancelariaDudaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long idFraccionArancelaria;

    private String fraccionArancelaria;

    private boolean nuevo;

    public FraccionArancelariaDudaDTO() {
        super();
    }

    public FraccionArancelariaDudaDTO(Long idFraccionArancelaria, String fraccionArancelaria, boolean nuevo) {
        super();
        this.idFraccionArancelaria = idFraccionArancelaria;
        this.fraccionArancelaria = fraccionArancelaria;
        this.nuevo = nuevo;
    }

    /**
     * @return the idFraccionArancelaria
     */
    public Long getIdFraccionArancelaria() {
        return idFraccionArancelaria;
    }

    /**
     * @param idFraccionArancelaria
     *            the idFraccionArancelaria to set
     */
    public void setIdFraccionArancelaria(Long idFraccionArancelaria) {
        this.idFraccionArancelaria = idFraccionArancelaria;
    }

    /**
     * @return the fraccionArancelaria
     */
    public String getFraccionArancelaria() {
        return fraccionArancelaria;
    }

    /**
     * @param fraccionArancelaria
     *            the fraccionArancelaria to set
     */
    public void setFraccionArancelaria(String fraccionArancelaria) {
        this.fraccionArancelaria = fraccionArancelaria;
    }

    /**
     * @return the nuevo
     */
    public boolean isNuevo() {
        return nuevo;
    }

    /**
     * @param nuevo
     *            the nuevo to set
     */
    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (obj instanceof FraccionArancelariaDudaDTO) {
            iguales = ((FraccionArancelariaDudaDTO) obj).getFraccionArancelaria().equals(this.fraccionArancelaria);
        }
        return iguales;
    }

    public int hashCode() {
        int hash = 0;
        hash += (idFraccionArancelaria != null ? idFraccionArancelaria.hashCode() : 0);
        return hash;
    }

}
