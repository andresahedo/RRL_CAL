/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.BitacoraTramiteServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseBusinessServices;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que implementa los m&eacute;todos de la interfaz
 * BaseBusinessServices.
 * 
 * @author antonio.flores Softtek
 * @since 03/27/2014
 */
@Service
public class GenerarResolucionCALServiceImpl extends BaseBusinessServices implements GenerarResolucionCALService {
    /**
     * Serial version
     */
    private static final long serialVersionUID = 5436149149061012156L;

    @Autowired
    private BienResarcimientoDao bienResarcimientoDao;
    @Autowired
    private ResolucionDao resolucionDao;
    @Autowired
    private ResolucionDatosGeneradosDao resolucionDatosGeneradosDao;

    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private ResolucionDatosGeneradosDao datosGeneradosDao;


    @Autowired
    private SolicitudDAO solicitudDao;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private transient TareaServices tareaServices;

    @Autowired
    private BitacoraTramiteServices bitacoraTramiteServices;

    @Override
    public void guardarBienResarcimiento(BienResarcimiento bienResarcimiento) {
        bienResarcimientoDao.guardaBienResarcimiento(bienResarcimiento);
    }

    /**
     * Metodo para rechazar una autorizacion
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    @Override
    public void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(numAsunto);
        resolucion.setIdeEstadoResolucion(EstadoResolucion.RECHAZADA.getClave());
        resolucionDao.modificarResolucion(resolucion);
        tramite.setEstadoTramite(EstadoTramite.EN_ESTUDIO);
        tramiteDao.modificarTramite(tramite);

        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            getLogger().debug("TareaServicesImpl : firmarTurnarNuevaTarea");
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(tareaAct.getClaveAdm());
            tarea.setClaveAsignado(resolucion.getRfcAbogado());
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
            tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);
        }
        catch (Exception ex){
            getLogger().error("Error al turnar el asunto en tarea", ex);
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #
     * guardarResolucion(mx.gob.sat.siat.juridica.base.dao.domain.model
     * .Resolucion)
     */
    @Override
    public Resolucion guardarResolucion(Resolucion resolucion) {
        Resolucion resolucionDB = resolucionDao.obtenerResolucionIdTramite(resolucion.getTramite().getNumeroAsunto());
        if (resolucionDB != null) {
            resolucion.setIdResolucion(resolucionDB.getIdResolucion());
        }
        return resolucionDao.crearResolucion(resolucion);

    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #
     * guardaDatosGenerados(mx.gob.sat.siat.juridica.base.dao.domain.model
     * .ResolucionDatosGenerados)
     */
    @Override
    public ResolucionDatosGenerados guardaDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        ResolucionDatosGenerados res =
                resolucionDatosGeneradosDao.obtenerResolucionDatosGeneradosIdResolucion(resolucionDatosGenerados
                        .getResolucion().getIdResolucion());
        if (res == null) {
            return resolucionDatosGeneradosDao.crearResolucionDatosGenerados(resolucionDatosGenerados);
        }
        else {
            return resolucionDatosGeneradosDao.modificarResolucionDatosGenerados(resolucionDatosGenerados);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #obtenerBienesResolucion(java.lang.String)
     */
    @Override
    public List<BienResarcimiento> obtenerBienesResolucion(Long idResolucion) {
        return bienResarcimientoDao.obtenBienResarcimientoIdResolucion(idResolucion);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #obtenerResolucionNumeroAsunto(java.lang.String)
     */
    @Override
    public Resolucion obtenerResolucionNumeroAsunto(String numAsunto) {
        return resolucionDao.obtenerUltimaResolucionPorTramite(numAsunto);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #obtenerResolucionIdResolucion(java.lang.String)
     */
    @Override
    public Resolucion obtenerResolucionIdResolucion(Long idResolucion) {
        return resolucionDao.obtenerResolucion(idResolucion);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #obtenerResolucionDatosGenerados(java.lang.String)
     */
    @Override
    public ResolucionDatosGenerados obtenerResolucionDatosGenerados(Long numeroAsunto) {
        return resolucionDatosGeneradosDao.obtenerResolucionDatosGeneradosIdResolucion(numeroAsunto);
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #actualizaDatosBP(java.lang.String, java.lang.String)
     */
    @Override
    public void actualizaDatosBP(String idTarea, String numAsunto, String usuario, String administrador) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        if (tramite.getEstadoTramite() == EstadoTramite.EN_ESTUDIO) {
            tramite.setEstadoTramite(EstadoTramite.EN_FIRMA);
            tramite.setFechaEstatus(new Date());
            tramiteDao.modificarTramite(tramite);

            try {
                Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
                tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
                tareaAct.setFechaAct(new Date());
                tareaServices.guardarTarea(tareaAct);

                getLogger().debug("FirmaTareaServiceImpl : firmarResolucion");
                Date dt = new Date();
                Tarea tarea = new Tarea();
                tarea.setNumeroAsunto(numAsunto);
                tarea.setClaveAdm(tareaAct.getClaveAdm());
                tarea.setClaveAbogado(tareaAct.getClaveAbogado());
                tarea.setClaveAsignado(administrador);
                tarea.setTarea(EnumeracionBitacora.FIRMAR_RESOLUCION.getClave());
                tarea.setDescripcion(EnumeracionBitacora.FIRMAR_RESOLUCION.getDescripcion());
                tarea.setFechaCreacion(dt);
                tarea.setFechaAct(dt);
                tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                tareaServices.guardarTarea(tarea);
            }
            catch (Exception ex){
                getLogger().error("Error al firmar resolucion el asunto en tarea y bitacora", ex);
            }


            SolicitudDatosGenerales solicitud =
                    solicitudDao.obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
            Bitacora bitacora = new Bitacora();
            bitacora.setFechaEvento(new Date());
            bitacora.setNumeroAsunto(numAsunto);
            bitacora.setRfcUsuario(usuario);
            bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
            bitacora.setTarea(EnumeracionBitacora.GENERAR_RESOL.getClave());
            bitacora.setRfcNuevo(usuario);
            bitacoraTramiteServices.insertarBitacora(bitacora);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #
     * actualizarResolucion(mx.gob.sat.siat.juridica.base.dao.domain.model
     * .ResolucionDatosGenerados)
     */
    @Override
    public void actualizarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        resolucionDatosGeneradosDao.actualizaResolucionDatosGenerados(resolucionDatosGenerados);

    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.cal.service.GenerarResolucionCALService
     * #
     * actualizaResolucion(mx.gob.sat.siat.juridica.base.dao.domain.model
     * .Resolucion)
     */
    @Override
    public void actualizaResolucion(Resolucion resolucion) {
        resolucionDao.modificarResolucion(resolucion);

    }

    @Override
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma, String rfcFuncionario) {
        StringBuffer cadenaOriginal = new StringBuffer();

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        Solicitud solicitud = solicitudDao.obtenerSolicitudporId(idSolicitud);
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(tramite.getNumeroAsunto());
        List<BienResarcimiento> bienesResarcimiento =
                bienResarcimientoDao.obtenBienResarcimientoIdResolucion(resolucion.getIdResolucion());
        ResolucionDatosGenerados datos =
                datosGeneradosDao.obtenerResolucionDatosGeneradosIdResolucion(resolucion.getIdResolucion());

        cadenaOriginal.append(generarCadenasHelper.generarCadenaGenerarAutorizarResolucionCAL(solicitud, datos,
                rfcFuncionario, fechaFirma));
        if (solicitud.getTipoTramiteSolicitud().getClaveModulo() != null) {
            // 1 - Clasificacion
            if (solicitud.getTipoTramiteSolicitud().getClaveModulo() == 1) {
                cadenaOriginal.append(generarCadenasHelper.generarCadenaGenerarDatosClasif(datos));
            }
            // 2 - Resarcimiento
            if (solicitud.getTipoTramiteSolicitud().getClaveModulo() == 2) {
                cadenaOriginal.append(generarCadenasHelper.generarCadenaGenerarDatosResarcimiento(bienesResarcimiento,
                        datos, resolucion));
            }
        }
        List<DocumentoOficial> listaDocumentos =
                documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(tramite.getNumeroAsunto(),
                        TipoDocumentoOficial.OFICIO_RESOLUCION.getClave());
        cadenaOriginal.append(listaDocumentos != null ? generarCadenasHelper
                .generaCadenaDocumentoOficial(listaDocumentos) : "");

        return cadenaOriginal.toString();
    }

    public List<Documento> documentos(Solicitud solicitud) {
        String idSolicitud = solicitud.getIdSolicitud().toString();
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud =
                registroRecursoRevocacionDAO.obtenerDocumentoSolicitud(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }
        return listaDocumentos;
    }
}
