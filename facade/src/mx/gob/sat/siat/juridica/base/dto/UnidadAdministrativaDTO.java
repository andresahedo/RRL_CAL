package mx.gob.sat.siat.juridica.base.dto;

public class UnidadAdministrativaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 5665201883823436923L;

    private String clave;
    private String descripcion;
    private Integer nivel;
    private String claveUnidadAdministrativaR;
    private String tipoUnidad;
    private String acronimo;
    private String nombre;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clave == null) ? 0 : clave.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = false;
        flag = super.equals(obj);
        if (flag) {
            return true;
        }
        else {
            if (obj != null) {
                if (obj.equals(clave)) {
                    return true;
                }
            }
        }
        return false;
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

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getClaveUnidadAdministrativaR() {
        return claveUnidadAdministrativaR;
    }

    public void setClaveUnidadAdministrativaR(String claveUnidadAdministrativaR) {
        this.claveUnidadAdministrativaR = claveUnidadAdministrativaR;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
