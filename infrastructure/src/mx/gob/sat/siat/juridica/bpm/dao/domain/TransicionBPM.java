package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

@XStreamAlias(value = "transicion")
public class TransicionBPM extends BaseModel {

    private static final long serialVersionUID = -3592237489980560659L;

    private String tipoTransicion;

    public String getTipoTransicion() {
        return tipoTransicion;
    }

    public void setTipoTransicion(String tipoTransicion) {
        this.tipoTransicion = tipoTransicion;
    }

}
