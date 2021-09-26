package mx.gob.sat.siat.juridica.base.dto;

public class AgraviosDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 338294374410896700L;
    private String clave;
    private String descripcion;
    private String idAgravio;

    public AgraviosDTO() {}

    public AgraviosDTO(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public AgraviosDTO(String clave, String descripcion, String idAgravio) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.idAgravio = idAgravio;
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
        try {

            if (((AgraviosDTO) obj).getClave() == null) {
                return false;
            }
            return (((AgraviosDTO) obj).getClave().equals(getClave()));
        }
        catch (ClassCastException e) {
            return false;
        }
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

    public String getIdAgravio() {
        return idAgravio;
    }

    public void setIdAgravio(String idAgravio) {
        this.idAgravio = idAgravio;
    }

}
