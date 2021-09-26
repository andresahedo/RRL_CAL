/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.emitirresolucion;

import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.bussiness.BaseAuditoriaBussinessBean;
import mx.gob.sat.siat.juridica.base.api.FirmarTareaFacade;
import mx.gob.sat.siat.juridica.base.constantes.MovimientosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.SistemasConstantes;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.rrl.api.AtenderSolicitudRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "emitirResolucionBusiness")
@NoneScoped
public class EmitirResolucionBusiness extends BaseAuditoriaBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6503297719232205613L;

    /**
     * Fachada para obtener los agravios y sentidos
     */
    @ManagedProperty(value = "#{atenderSolicitudRevocacionFacade}")
    private AtenderSolicitudRevocacionFacade atenderSolicitudRevocacionFacade;

    /**
     * Fachada para manejar la tarea
     */
    @ManagedProperty(value = "#{firmarTareaFacade}")
    private FirmarTareaFacade firmarTareaFacade;

    @PostConstruct
    public void init() {

    }

    @Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_BANDEJA_TAREAS,
            movimiento = MovimientosConstantes.MOVIMIENTO_ABOGADO_RESOLUCION,
            claveSistema = SistemasConstantes.SISTEMA_RRL)
    public void terminarAtenderSolicitud(DatosBandejaTareaDTO datosBandeja, String rfcAsignar, String rfcUsuario,
            String numeroAsunto) {
        firmarTareaFacade.atender(datosBandeja.getNumeroAsunto(), datosBandeja.getIdTareaUsuario().toString(),
                rfcAsignar, rfcUsuario, numeroAsunto);
        guardarObservacionesBitacora("Asunto Resuelto:" + numeroAsunto);
    }

    public ResolucionAbogadoDTO mostrarResoluciones(SolicitudDTO solicitud, TramiteDTO tramiteDTO) {

        return this.atenderSolicitudRevocacionFacade.obtenerInformacionFuncionario(solicitud, tramiteDTO);
    }

    /**
     * M&eacute;todo para obtener los sentidos de la
     * resoluci&oacute;n.
     * 
     * @return
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        return atenderSolicitudRevocacionFacade.obtenerCatalogoSentido();
    }
    
    /**
     * Obtiene el catalogo de sentidos perdedores
     * @return 
     */
    public Map<String,CatalogoDTO> obtenerSentidosResolucionPerdedores(){
        return atenderSolicitudRevocacionFacade.obtenerSentidosResolucionPerdedores();
    }

    public List<AgraviosDTO> obtenerAgravios() {
        return atenderSolicitudRevocacionFacade.obtenerCatalogoAgravio();
    }

    public String guardarResolucionesImpugnadas(ResolucionAbogadoDTO resolucion) {
        atenderSolicitudRevocacionFacade.autorizarSolicitud(resolucion);
        return "";
    }

    /**
     * Metodo para obtener los autorizadores
     */
    public List<CatalogoDTO> obtenerAutorizadores(String numAsunto) {

        return atenderSolicitudRevocacionFacade.obtenerAutorizadores(numAsunto);
    }

    /**
     * @return el(la) atenderSolicitudRevocacionFacade
     */
    public AtenderSolicitudRevocacionFacade getAtenderSolicitudRevocacionFacade() {
        return atenderSolicitudRevocacionFacade;
    }

    /**
     * @param atenderSolicitudRevocacionFacade
     *            el(la) atenderSolicitudRevocacionFacade a fijar
     */
    public void setAtenderSolicitudRevocacionFacade(AtenderSolicitudRevocacionFacade atenderSolicitudRevocacionFacade) {
        this.atenderSolicitudRevocacionFacade = atenderSolicitudRevocacionFacade;
    }

    public FirmarTareaFacade getFirmarTareaFacade() {
        return firmarTareaFacade;
    }

    public void actualizaDatosBP(int idInstancia, String numAsunto, String rfcUsuario) {
        atenderSolicitudRevocacionFacade.actualizaDatosBP(idInstancia, numAsunto, rfcUsuario);
    }

    public void setFirmarTareaFacade(FirmarTareaFacade firmarTareaFacade) {
        this.firmarTareaFacade = firmarTareaFacade;
    }

    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {
        return atenderSolicitudRevocacionFacade.obtenerDocumentosComplementarios(idSolicitud);
    }

}
