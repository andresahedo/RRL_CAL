package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

@XStreamAlias(value = "parametroTramite")
public class ParametroTramiteBPM extends BaseModel {

    private static final long serialVersionUID = 5755813573907790597L;
    private String fechaFinAtenderRequerimiento;
    private String tiempoTimerAtenReq;
    private String tiempoTimerReq;
    private String tiempoTimerConfReq;

    public String getFechaFinAtenderRequerimiento() {
        return fechaFinAtenderRequerimiento;
    }

    public void setFechaFinAtenderRequerimiento(
            String fechaFinAtenderRequerimiento) {
        this.fechaFinAtenderRequerimiento = fechaFinAtenderRequerimiento;
    }

    public String getTiempoTimerAtenReq() {
        return tiempoTimerAtenReq;
    }

    public void setTiempoTimerAtenReq(String tiempoTimerAtenReq) {
        this.tiempoTimerAtenReq = tiempoTimerAtenReq;
    }

    public String getTiempoTimerReq() {
        return tiempoTimerReq;
    }

    public void setTiempoTimerReq(String tiempoTimerReq) {
        this.tiempoTimerReq = tiempoTimerReq;
    }

    public String getTiempoTimerConfReq() {
        return tiempoTimerConfReq;
    }

    public void setTiempoTimerConfReq(String tiempoTimerConfReq) {
        this.tiempoTimerConfReq = tiempoTimerConfReq;
    }

}
