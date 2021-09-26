package mx.gob.sat.siat.juridica.base.dto;

public class GenericDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String clave;

    private String descripcion;

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

    public GenericDTO(String clave, String descripcion) {
        super();
        this.clave = clave;
        this.descripcion = descripcion;
    }

}
