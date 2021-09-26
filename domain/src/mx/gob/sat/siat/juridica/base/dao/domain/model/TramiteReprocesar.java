/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCT_REPROCTRAMITE")
public class TramiteReprocesar extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6478698709756334L;

    @Id
    @Basic(optional = false)
    @Column(name = "IDTRAMITE")
    private String numeroAsunto;
    
    @Column(name = "TIPOERROR")
    private String tipoError;
    
    @Column(name = "ESTATUSENVIO")
    private String estatusEnvio;
    
    @Column(name = "REINTENTOS")
    private int reintentos;
    
    @Column(name = "FECREGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    
    @Column(name = "PARAMETROS")
    private String parametros;
    

    /**
     * Constructor vacio
     */
    public TramiteReprocesar() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param numeroAsunto
     */
    public TramiteReprocesar(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public String getTipoError() {
        return tipoError;
    }

    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    public String getEstatusEnvio() {
        return estatusEnvio;
    }

    public void setEstatusEnvio(String estatusEnvio) {
        this.estatusEnvio = estatusEnvio;
    }

    public int getReintentos() {
        return reintentos;
    }

    public void setReintentos(int reintentos) {
        this.reintentos = reintentos;
    }

    public Date getFechaRegistro() {
        return fechaRegistro != null ? (Date) fechaRegistro.clone() : null;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaRegistro);
            this.fechaRegistro = cal.getTime();
        }
        else {
            this.fechaRegistro = null;
        }
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

}
