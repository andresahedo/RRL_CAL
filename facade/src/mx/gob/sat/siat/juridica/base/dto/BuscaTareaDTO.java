package mx.gob.sat.siat.juridica.base.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BuscaTareaDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = 2688225041815076156L;
    private String numasunto;
    private String usuario;
    private String promovente;
    private Date fecIni;
    private Date fecFin;
    private String edoprocesal;
    private String idTipoTramite;

    public BuscaTareaDTO(){}

    public String getNumasunto() {
        return numasunto;
    }

    public void setNumasunto(String numasunto) {
        this.numasunto = numasunto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPromovente() {
        return promovente;
    }

    public void setPromovente(String promovente) {
        this.promovente = promovente;
    }

    public Date getFecIni() {
        return fecIni != null ? (Date) fecIni.clone() : null;
    }

    public void setFecIni(Date fecIni) {
        if (fecIni != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fecIni);
            this.fecIni = cal.getTime();
        }
        else {
            this.fecIni = null;
        }
    }

    public Date getFecFin() {
        return fecFin != null ? (Date) fecFin.clone() : null;
    }

    public void setFecFin(Date fecFin) {
        if (fecFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fecFin);
            this.fecFin = cal.getTime();
        }
        else {
            this.fecFin = null;
        }
    }

    public String getEdoprocesal() {
        return edoprocesal;
    }

    public void setEdoprocesal(String edoprocesal) {
        this.edoprocesal = edoprocesal;
    }

    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }
}
