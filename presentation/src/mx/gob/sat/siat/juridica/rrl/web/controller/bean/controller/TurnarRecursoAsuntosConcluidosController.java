/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.RolesConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.TurnarRecursoRevocacionBussines;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ResourceBundle;

/**
 * Bean que implementan la l&oacute;gica de negocio para turnar la
 * solicitud para asuntos concluidos
 * 
 * @author Consorcio
 * 
 */

@ViewScoped
@ManagedBean(name = "turnarRecursoAsuntosConcluidosController")
public class TurnarRecursoAsuntosConcluidosController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    /** DTO que representa los datos obtenidos en la bandeja */
    private DatosBandejaTareaDTO datosBandejaTareaDTO;
    /** Bussines para Turnar Recurso Revocacion */
    @ManagedProperty(value = "#{turnarRecursoRevocacionBussines}")
    private TurnarRecursoRevocacionBussines turnarRecursoRevocacionBussines;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;
    /**
     * Propiedad para manejar los mensajes de error en pantalla.
     */
    @ManagedProperty("#{errmsg}")
    private transient ResourceBundle errorMsg;

    private TramiteDTO tramite;
    
    private boolean tipoTramite = false;

    /**
     * Constructor
     */
    public TurnarRecursoAsuntosConcluidosController() {

    }
    
    @PostConstruct
    public void cargarDatosBandeja() {
        setDatosBandejaTareaDTO((DatosBandejaTareaDTO) getFlash().get(VistaConstantes.DATOS_BANDEJA_DTO));
        setTipoTramite(getDatosBandejaTareaDTO().getTipoTramite().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO));
    }


    /**
     * 
     * @return datosBandejaTareaDTO
     */
    public DatosBandejaTareaDTO getDatosBandejaTareaDTO() {
        return datosBandejaTareaDTO;
    }

    /**
     * 
     * @param datosBandejaTareaDTO
     *            a fijar
     */
    public void setDatosBandejaTareaDTO(DatosBandejaTareaDTO datosBandejaTareaDTO) {
        this.datosBandejaTareaDTO = datosBandejaTareaDTO;
    }

    /**
     * 
     * @return turnarRecursoRevocacionBussines
     */
    public TurnarRecursoRevocacionBussines getTurnarRecursoRevocacionBussines() {
        return turnarRecursoRevocacionBussines;
    }

    /**
     * 
     * @param turnarRecursoRevocacionBussines
     *            a fijar
     */
    public void setTurnarRecursoRevocacionBussines(TurnarRecursoRevocacionBussines turnarRecursoRevocacionBussines) {
        this.turnarRecursoRevocacionBussines = turnarRecursoRevocacionBussines;
    }

    public TramiteDTO getTramite() {
        tramite = turnarRecursoRevocacionBussines.obtenerEstadoProcesal(datosBandejaTareaDTO.getNumeroAsunto());
        return tramite;
    }
    
    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }
    
    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public ResourceBundle getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ResourceBundle errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void validaAccesoAdministrador() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoRolEmpleado(RolesConstantes.ROL_EMPLEADO_ADMINISTRADOR);
    }


    public boolean isTipoTramite() {
        return tipoTramite;
    }


    public void setTipoTramite(boolean tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

}
