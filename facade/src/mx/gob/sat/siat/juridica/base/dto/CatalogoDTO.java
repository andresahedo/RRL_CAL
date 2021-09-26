package mx.gob.sat.siat.juridica.base.dto;

public class CatalogoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 338294374410896700L;
    private String clave;
    private String descripcion;

    public CatalogoDTO() {}

    public CatalogoDTO(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clave == null) ? 0 : clave.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CatalogoDTO)) {
            return false;
        }
        if (((CatalogoDTO) obj).getClave() == null) {
            return false;
        }
        return (((CatalogoDTO) obj).getClave().equals(getClave()));
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
