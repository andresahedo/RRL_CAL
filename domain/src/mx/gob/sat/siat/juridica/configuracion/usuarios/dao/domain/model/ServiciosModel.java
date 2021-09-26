package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.math.BigDecimal;

public class ServiciosModel extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7265378387957289656L;

    private BigDecimal idComponente;
    private String url;
    private String descripcion;

    public BigDecimal getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigDecimal idComponente) {
        this.idComponente = idComponente;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
