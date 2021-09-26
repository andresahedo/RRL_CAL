package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

@XStreamAlias(value = "solicitud")
public class SolicitudBPM extends BaseModel {

    private static final long serialVersionUID = 8137948477277432864L;

    /**
     * Estado procesal
     */
    private String estadoProcesal;

    /**
     * 
     * @return estadoProcesal
     */
    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    /**
     * 
     * @param estadoProcesal
     *            a fijar
     */
    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

}
