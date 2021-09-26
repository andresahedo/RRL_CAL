package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

@XStreamAlias(value = "items")
public class RequerimientoBPM extends BaseModel {

    private static final long serialVersionUID = 2887060072952875657L;
    private int idSubProceso;
    private String tipoRequerimiento;

    public int getIdSubProceso() {
        return idSubProceso;
    }

    public void setIdSubProceso(int idSubProceso) {
        this.idSubProceso = idSubProceso;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

}
