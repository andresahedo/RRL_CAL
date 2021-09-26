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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;

/**
 * 
 * @author softtek
 * 
 */
public class MensajeBPM extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3665385350038064044L;

    /**
     * Atributo privado "tipoMensajeBPM" tipo TipoMensajeBPM
     */
    private TipoMensajeBPM tipoMensajeBPM;

    /**
     * Atributo privado "bDoc" tipo String
     */
    private String bDoc;

    /**
     * Atributo privado "idTarea" tipo String
     */
    private String idTarea;

    /**
     * Atributo privado "idInstancia" tipo String
     */
    private int idInstancia;

    private String bpdId;

    private String processAppId;

    private String usuario;
    
    private String folioTramite;
    

    /**
     * 
     * @return tipoMensajeBPM
     */
    public TipoMensajeBPM getTipoMensajeBPM() {
        return tipoMensajeBPM;
    }

    /**
     * 
     * @param tipoMensajeBPM
     *            a fijar
     */
    public void setTipoMensajeBPM(TipoMensajeBPM tipoMensajeBPM) {
        this.tipoMensajeBPM = tipoMensajeBPM;
    }

    /**
     * 
     * @return bDoc
     */
    public String getbDoc() {
        return bDoc;
    }

    /**
     * 
     * @param bDoc
     *            a fijar
     */
    public void setbDoc(String bDoc) {
        this.bDoc = bDoc;
    }

    /**
     * 
     * @return idTarea
     */
    public String getIdTarea() {
        return idTarea;
    }

    /**
     * 
     * @param idTarea
     *            a fijar
     */
    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getBpdId() {
        return bpdId;
    }

    public void setBpdId(String bpdId) {
        this.bpdId = bpdId;
    }

    public String getProcessAppId() {
        return processAppId;
    }

    public void setProcessAppId(String processAppId) {
        this.processAppId = processAppId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public String getFolioTramite() {
        return folioTramite;
    }

    public void setFolioTramite(String folioTramite) {
        this.folioTramite = folioTramite;
    }    

}
