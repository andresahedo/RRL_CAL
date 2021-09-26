/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.dao.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.util.List;

/**
 * Bean para el mensaje de tarea
 * 
 * @author softtek
 * 
 */
@XStreamAlias(value = "root")
public class MensajeTarea extends BaseModel {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 6446464769774721949L;

    private TransicionBPM transicion;

    private ParametroTramiteBPM parametroTramite;

    private List<RequerimientoBPM> requerimientos;

    private String administrador;

    private String administradorAI;

    private String abogado;

    private String estadoProcesal;
    
    private String fechaFinAtenderRequerimiento;
    
    private String fechaNotificacionResolucion;
    
    private String tipoRequerimiento;

    /**
     * Unidad Administrativa
     */
    private String unidadAdmin;

    private Boolean esAceptado;

    public TransicionBPM getTransicion() {
        return transicion;
    }

    public void setTransicion(TransicionBPM transicion) {
        this.transicion = transicion;
    }

    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public ParametroTramiteBPM getParametroTramite() {
        return parametroTramite;
    }

    public void setParametroTramite(ParametroTramiteBPM parametroTramite) {
        this.parametroTramite = parametroTramite;
    }

    /**
     * 
     * @return unidad administrativa
     */
    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    /**
     * 
     * @param unidad
     *            administrativa a fijar
     */
    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

    public List<RequerimientoBPM> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<RequerimientoBPM> requerimientos) {
        this.requerimientos = requerimientos;
    }

    /**
     * @return the esAceptado
     */
    public Boolean isEsAceptado() {
        return esAceptado;
    }

    /**
     * @param esAceptado
     *            the esAceptado to set
     */
    public void setEsAceptado(Boolean esAceptado) {
        this.esAceptado = esAceptado;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getAdministradorAI() {
        return administradorAI;
    }

    public void setAdministradorAI(String administradorAI) {
        this.administradorAI = administradorAI;
    }

    public String getAbogado() {
        return abogado;
    }

    public void setAbogado(String abogado) {
        this.abogado = abogado;
    }

    public String getFechaFinAtenderRequerimiento() {
        return fechaFinAtenderRequerimiento;
    }

    public void setFechaFinAtenderRequerimiento(String fechaFinAtenderRequerimiento) {
        this.fechaFinAtenderRequerimiento = fechaFinAtenderRequerimiento;
    }

    public String getFechaNotificacionResolucion() {
        return fechaNotificacionResolucion;
    }

    public void setFechaNotificacionResolucion(String fechaNotificacionResolucion) {
        this.fechaNotificacionResolucion = fechaNotificacionResolucion;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }     

}
