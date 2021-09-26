/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.api.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.sat.siat.juridica.auditoria.facade.impl.BaseAuditoriaFacadeImpl;
import mx.gob.sat.siat.juridica.base.api.FirmarTareaFacade;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRechazo;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.service.AsignacionTramiteServices;
import mx.gob.sat.siat.juridica.base.service.BitacoraTramiteServices;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.service.TurnarService;
import mx.gob.sat.siat.juridica.cal.op.service.MotivosRechazoServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.TurnarDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.MotivoRechazoDTOTranformer;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;

/**
 * Implementacion de la fachada Base, proporciona soporte para el manejo del
 * log.
 * 
 * @author Softtek
 * 
 */
@Component("firmarTareaFacade")
public class FirmarTareaFacadeImpl extends BaseAuditoriaFacadeImpl implements FirmarTareaFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -7798875049379953792L;

    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Autowired
    private transient FirmarServices firmarServices;

    @Autowired
    private transient TareaServices tareaServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient AutorizarRemitirService autorizarRemitirService;

    @Autowired
    private AsignacionTramiteServices asignacionTramiteServices;

    @Autowired
    private transient SolicitudService solicitudService;

    /**
     * Atributo asignarTareaCorreoService tipo AsignarTareaCorreoService
     */
    @Autowired
    private AsignarTareaCorreoService asignarTareaCorreoService;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Autowired
    private transient MotivosRechazoServices motivosRechazoServices;

    @Autowired
    private transient MotivoRechazoDTOTranformer motivoTransformer;

    @Autowired
    private transient TurnarDTOTransformer turnarTransformer;

    @Autowired
    private transient BitacoraTramiteServices bitacoraTramiteServices;

    @Autowired
    private transient TurnarService turnarService;

    @Override
    public void firmarTurnar(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario,
            TurnarDTO turnarDTO) {
        getLogger().debug("TareaServicesImpl : firmarTurnarAct");
        Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
        tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
        tareaAct.setFechaAct(new Date());
        tareaServices.guardarTarea(tareaAct);
        getLogger().debug("TareaServicesI|mpl : firmarTurnarNuevaTarea");
        Date dt = new Date();
        Tarea tarea = new Tarea();
        tarea.setNumeroAsunto(numAsunto);
        tarea.setClaveAdm(rfcUsuario);
        tarea.setClaveAbogado(rfcAsignar);
        tarea.setClaveAsignado(rfcAsignar);
        tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
        tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
        tarea.setFechaCreacion(dt);
        tarea.setFechaAct(dt);
        tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
        tareaServices.guardarTarea(tarea);
        turnarService.guardarDatosTurnado(turnarTransformer.transformarDTO(turnarDTO));
        getLogger().debug("TareaServicesImpl : guardaBitacora");
        Tramite tramite = tramiteServices.buscarTramiteTarea(numAsunto);
        tramite.setFechaEstatus(new Date());
        tramiteServices.modificarTramite(tramite);
        asignarTareaCorreoService.enviarCorreo(numAsunto, rfcAsignar, "Atender promoci&oacute;n");
        SolicitudDatosGenerales solicitud = solicitudService
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        asignacionTramiteServices.guardarAsignacionTramite(tramite.getNumeroAsunto(), rfcAsignar,
                TipoRol.ABOGADO.getClave());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(numAsunto);
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.REGISTRO.getClave());
        bitacora.setRfcNuevo(rfcAsignar);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    @Override
    public void firmarReasignar(String idTarea, String rfcUsuario, String rfcAsignar, String numeroAsunto,
            String rfcPropietario, Long idInstancia) {
        firmaTareaService.firmarReasignar(idTarea, rfcUsuario, rfcAsignar, numeroAsunto, rfcPropietario, idInstancia);
    }

    @Override
    public void firmarRemitir(String numAsunto, Long idTarea, String rfcUsuario) {
        firmaTareaService.firmarRemitir(numAsunto, idTarea, rfcUsuario);
    }

    @Override
    public void atender(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario, String numeroAsunto) {
        firmaTareaService.terminarAtenderSolicitud(numAsunto, idTarea, rfcAsignar, rfcUsuario);

    }

    @Override
    public Long firmarAutorizar(String numAsunto, String idTarea, String rfcUsuario) {
        return firmaTareaService.firmarAutorizar(numAsunto, idTarea, rfcUsuario);
    }

    @Override
    public void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Long idRequerimiento,
            String rfcUsuario) {
        firmaTareaService.firmarConfirmarNotificacion(numAsunto, idTarea, idRequerimiento, rfcUsuario);

    }

    @Override
    public void firmarCapturaFechaNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Boolean blnResolucion,
            String rfcUsuario, Long idNotificacion, Long idRequerimiento) {
        firmaTareaService.firmarCapturaFechaNotificacion(numAsunto, idTarea, idTareaBPM, blnResolucion, rfcUsuario,
                idNotificacion, idRequerimiento);

    }

    @Override
    public Long guardarDocOficial(String numFolio, DocumentoOficial documentoOficial) {

        return autorizarRemitirService.guardarDoc(numFolio, documentoOficial);

    }

    @Override
    public void firmarDocumentos(Long idSolicitud, FirmaDTO firmaDTO) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        solicitudService.firmarDocumentos(idSolicitud, firma, true);
    }

    @Override
    public FirmaDTO obtenSelloSIAT(String cadenaOriginal) {
        Firma firmaSIAT = firmarServices.obtenFirmaSelloSIAT(cadenaOriginal);
        return firmaTransformer.transformarFirma(firmaSIAT);
    }

    @Override
    public void rechazoAsunto(MotivoRechazoDTO motivosDto) {
        motivosDto.setIdTipoRechazo(TipoRechazo.RECHAZO_ASU.getClave());
        motivosDto.setIdEstRechazo(EstadoTramite.RECHAZADO.getClave());
        MotivoRechazo motivoRechazo = motivoTransformer.transformar(motivosDto);
        motivosRechazoServices.rechazoAsuntoOficialia(motivoRechazo, motivosDto.getRfc(),
                motivosDto.getIdTarea().toString());
    }

    @Override
    public void rechazoDocumento(MotivoRechazoDTO motivosDto) {
        motivosDto.setIdTipoRechazo(TipoRechazo.RECHAZO_DOC.getClave());
        motivosDto.setIdEstRechazo(EstadoDocumento.RECHAZADO.getClave());
        MotivoRechazo motivoRechazo = motivoTransformer.transformar(motivosDto);
        motivosRechazoServices.rechazoDocumento(motivoRechazo, motivosDto.getRfc());
    }

    public BitacoraTramiteServices getBitacoraTramiteServices() {
        return bitacoraTramiteServices;
    }

    public void setBitacoraTramiteServices(BitacoraTramiteServices bitacoraTramiteServices) {
        this.bitacoraTramiteServices = bitacoraTramiteServices;
    }

}
