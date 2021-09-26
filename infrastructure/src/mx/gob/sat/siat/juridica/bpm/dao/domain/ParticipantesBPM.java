package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

@XStreamAlias(value = "participantes")
public class ParticipantesBPM extends BaseModel {

    private static final long serialVersionUID = -6316321036769303338L;
    private String rfcPromovente;
    private String promovente;
    private String abogado;
    private String administrador;
    private String oficialDePartes;

    public String getRfcPromovente() {
        return rfcPromovente;
    }

    public void setRfcPromovente(String rfcPromovente) {
        this.rfcPromovente = rfcPromovente;
    }

    public String getPromovente() {
        return promovente;
    }

    public void setPromovente(String promovente) {
        this.promovente = promovente;
    }

    public String getAbogado() {
        return abogado;
    }

    public void setAbogado(String abogado) {
        this.abogado = abogado;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getOficialDePartes() {
        return oficialDePartes;
    }

    public void setOficialDePartes(String oficialDePartes) {
        this.oficialDePartes = oficialDePartes;
    }

}
