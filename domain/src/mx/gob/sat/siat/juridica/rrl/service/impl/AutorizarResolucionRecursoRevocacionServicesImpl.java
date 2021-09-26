/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseBusinessServices;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarResolucionRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.helper.ResolucionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class AutorizarResolucionRecursoRevocacionServicesImpl extends BaseBusinessServices implements
        AutorizarResolucionRecursoRevocacionServices {

    /**
     * 
     */
    private static final long serialVersionUID = 6071545361390894241L;

    /**
     * Atributo privado catalogoDDao tipo CatalogoDDao
     */
    @Autowired
    private CatalogoDDao catalogoDDao;

    /**
     * Atributo privado resolucionDao tipo ResolucionDao
     */
    @Autowired
    private ResolucionDao resolucionDao;

    /**
     * Atributo privado resolucionHelper tipo ResolucionHelper
     */
    @Autowired
    private ResolucionHelper resolucionHelper;

    /**
     * Atributo privado resolucionHelper tipo ResolucionHelper
     */
    @Autowired
    private AsignarTareaCorreoService asignarTareaCorreoService;

    @Autowired
    private transient TareaServices tareaServices;

    /**
     * Atributo solicitudDAO tipo SolicitudDAO
     */
    @Autowired
    private SolicitudDAO solicitudDAO;


    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    /**
     * Atributo privado tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    /**
     * Atributo privado unidadAdministrativaDao tipo
     * UnidadAdministrativaDao
     */
    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Autowired
    private ResolucionDatosGeneradosDao resolucionDatosGeneradosDao;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private EnviarCorreoService enviarCorreoSerivce;

    /**
     * Metodo para obtener una resolicion por id de tramite
     * 
     * @param idTramite
     * @return Resolucion
     */
    @Override
    public Resolucion obtenerResolucionIdTramite(String idTramite) {
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(idTramite);
        resolucion.setResolucionesImpugnadas(resolucionDao.obtenerResolucionesImpugnadas(resolucion.getIdResolucion()));
        resolucion.setResolucionesCatalogoD(resolucionDao.obtenerResolucionesCatalogoD(resolucion.getIdResolucion()));
        if (resolucion.getResolucionesImpugnadas() != null && !resolucion.getResolucionesImpugnadas().isEmpty()) {
            for (ResolucionImpugnada resolucionImpugnada : resolucion.getResolucionesImpugnadas()) {
                resolucionImpugnada.setConceptos(resolucionDao.obtenerConceptosPorIdImpugnada(resolucionImpugnada
                        .getIdResolucionImpugnada()));
                resolucionImpugnada.setAgravios(resolucionDao.obtenerAgraviosPorIdImpugnada(resolucionImpugnada
                        .getIdResolucionImpugnada()));
            }
        }
        return resolucion;
    }

    /**
     * Metodo para guardar una resolucion
     * 
     * @param resolucion
     */
    @Override
    public void guardar(Resolucion resolucion) {

        if (resolucion.getIdResolucion() != null) {

            Resolucion resolucionBD = resolucionDao.obtenerResolucion(resolucion.getIdResolucion());

            resolucionBD.setMonto(resolucion.getMonto());

            List<ResolucionImpugnada> resolucionesImpugnadasBD =
                    resolucionDao.obtenerResolucionesImpugnadas(resolucion.getIdResolucion());

            resolucionDao.eliminarResolucionesImpugnadasPorIdResolucion(resolucion.getIdResolucion(), new Date());

            resolucionDao.eliminarResolucionesDetallePorIdResolucion(resolucion.getIdResolucion(), new Date());

            String idsRImpugnadas = resolucionHelper.concatenaIds(resolucionesImpugnadasBD);

            resolucionDao.eliminarResolucionesImpugnadasDetallePorIdsImpugnadas(idsRImpugnadas, new Date());

            List<ResolucionImpugnada> resolucionesImpugnadas = resolucion.getResolucionesImpugnadas();
            resolucion.setResolucionesImpugnadas(null);

            List<ResolucionCatalogoD> resolucionesCatalogoD = resolucion.getResolucionesCatalogoD();
            resolucion.setResolucionesCatalogoD(null);

            String ideSentidoResolucion = null;

            if (resolucionesImpugnadas != null) {
                for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {
                    resolucionImpugnada.setFechaEmision(new Date());
                    resolucionImpugnada.setResolucion(resolucionBD);
                    if (resolucionImpugnada.isImportante()) {
                        CatalogoD catalogoD =
                                catalogoDDao.obtenerCatalogoPorClave(resolucionImpugnada
                                        .getSentidoResolucionImpugnada().getClave());
                        ideSentidoResolucion = catalogoD.getDescripcionGenerica2();
                    }
                    List<ConceptoDetalle> conceptos = resolucionImpugnada.getConceptos();
                    resolucionImpugnada.setConceptos(null);

                    resolucionDao.crearResolucionImpugnada(resolucionImpugnada);
                    if (conceptos != null && !conceptos.isEmpty()) {
                        for (ConceptoDetalle conceptoDetalle : conceptos) {
                            conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);

                            resolucionDao.crearResolucionImpugnadaCatalogoD(conceptoDetalle);
                        }
                    }

                    List<AgravioDetalle> agravios = resolucionImpugnada.getAgravios();
                    resolucionImpugnada.setAgravios(null);

                    if (agravios != null && !agravios.isEmpty()) {
                        for (AgravioDetalle agravioDetalle : agravios) {
                            agravioDetalle.setResolucionImpugnada(resolucionImpugnada);
                            resolucionDao.crearResolucionImpugnadaCatalogoD(agravioDetalle);
                        }
                    }

                }
            }

            if (resolucionesCatalogoD != null && !resolucionesCatalogoD.isEmpty()) {
                for (ResolucionCatalogoD resolucionCatalogoD : resolucionesCatalogoD) {
                    resolucionCatalogoD.setResolucion(resolucion);
                    resolucionDao.crearResolucionCatalogoD(resolucionCatalogoD);
                }
            }

            resolucionBD.setIdeSentidoResolucion(ideSentidoResolucion);

        }

    }
    
    /**
     * Metodo para guardar una resolucion fecha de emision modificada
     * 
     * @param resolucion
     */
    @Override
    public void guardarImpFecEmis(Resolucion resolucion, Date[] arrResolFechaEmision) {

        if (resolucion.getIdResolucion() != null) {

            Resolucion resolucionBD = resolucionDao.obtenerResolucion(resolucion.getIdResolucion());

            resolucionBD.setMonto(resolucion.getMonto());

            List<ResolucionImpugnada> resolucionesImpugnadasBD =
                    resolucionDao.obtenerResolucionesImpugnadas(resolucion.getIdResolucion());

            resolucionDao.eliminarResolucionesImpugnadasPorIdResolucion(resolucion.getIdResolucion(), new Date());

            resolucionDao.eliminarResolucionesDetallePorIdResolucion(resolucion.getIdResolucion(), new Date());

            String idsRImpugnadas = resolucionHelper.concatenaIds(resolucionesImpugnadasBD);

            resolucionDao.eliminarResolucionesImpugnadasDetallePorIdsImpugnadas(idsRImpugnadas, new Date());

            List<ResolucionImpugnada> resolucionesImpugnadas = resolucion.getResolucionesImpugnadas();
            resolucion.setResolucionesImpugnadas(null);

            List<ResolucionCatalogoD> resolucionesCatalogoD = resolucion.getResolucionesCatalogoD();
            resolucion.setResolucionesCatalogoD(null);

            String ideSentidoResolucion = null;

            if (resolucionesImpugnadas != null) {
                int i = 0;
                for (ResolucionImpugnada resolucionImpugnada : resolucionesImpugnadas) {
                    resolucionImpugnada.setFechaEmision(arrResolFechaEmision[i]);
                    resolucionImpugnada.setResolucion(resolucionBD);
                    if (resolucionImpugnada.isImportante()) {
                        CatalogoD catalogoD =
                                catalogoDDao.obtenerCatalogoPorClave(resolucionImpugnada
                                        .getSentidoResolucionImpugnada().getClave());
                        ideSentidoResolucion = catalogoD.getDescripcionGenerica2();
                    }
                    List<ConceptoDetalle> conceptos = resolucionImpugnada.getConceptos();
                    resolucionImpugnada.setConceptos(null);

                    resolucionDao.crearResolucionImpugnada(resolucionImpugnada);
                    if (conceptos != null && !conceptos.isEmpty()) {
                        for (ConceptoDetalle conceptoDetalle : conceptos) {
                            conceptoDetalle.setResolucionImpugnada(resolucionImpugnada);

                            resolucionDao.crearResolucionImpugnadaCatalogoD(conceptoDetalle);
                        }
                    }

                    List<AgravioDetalle> agravios = resolucionImpugnada.getAgravios();
                    resolucionImpugnada.setAgravios(null);

                    if (agravios != null && !agravios.isEmpty()) {
                        for (AgravioDetalle agravioDetalle : agravios) {
                            agravioDetalle.setResolucionImpugnada(resolucionImpugnada);
                            resolucionDao.crearResolucionImpugnadaCatalogoD(agravioDetalle);
                        }
                    }
                    i++;

                }
            }

            if (resolucionesCatalogoD != null && !resolucionesCatalogoD.isEmpty()) {
                for (ResolucionCatalogoD resolucionCatalogoD : resolucionesCatalogoD) {
                    resolucionCatalogoD.setResolucion(resolucion);
                    resolucionDao.crearResolucionCatalogoD(resolucionCatalogoD);
                }
            }

            resolucionBD.setIdeSentidoResolucion(ideSentidoResolucion);

        }

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
        tramite.setEstadoTramite(EstadoTramite.EN_ESTUDIO);
        tramiteDao.modificarTramite(tramite);

        getLogger().debug("AutorizarResolucionRecursoRevocacionServicesImpl : rechazarResolucionAct");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            getLogger().debug("AutorizarResolucionRecursoRevocacionServicesImpl : rechazarResolucionNuevaTarea");
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(tareaAct.getClaveAdm());
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(resolucion.getRfcAbogado());
            tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
            tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);
        }
        catch (Exception ex){
            getLogger().error("Error al turnar el asunto en tarea y bitacora", ex);
        }

        asignarTareaCorreoService.enviarCorreo(numAsunto, resolucion.getRfcAbogado(), "Atender promoci&oacute;n");
        enviarCorreoSerivce.enviarCorreoRechazoAsunto(resolucion.getRfcAbogado(), numAsunto, idSolicitud,
                "Resoluci&oacute;n", resolucion.getFechaResolucion(), rfc);


    }

    /**
     * Metodo para obtener un tramite
     * 
     * @param resImpugnada
     * @return Tramite
     */
    @Override
    public Tramite obtenerTramite(String resImpugnada, String numAsunto) {
        return tramiteDao.obtenerTramitePorResolucion(resImpugnada, numAsunto);
    }

    /**
     * Metodo para obtener una unidad administrativa
     * 
     * @param numeroAsunto
     * @return UnidadAdministrativa
     */
    @Override
    public UnidadAdministrativa obtenerUnidadAdministrativa(String numeroAsunto) {
        return unidadAdministrativaDao.obtenerUnidadPorTramite(numeroAsunto);
    }

    public ResolucionDatosGenerados obtenerResolucionDatosGenerados(Long idResolucion) {
        return resolucionDatosGeneradosDao.obtenerResolucionDatosGeneradosIdResolucion(idResolucion);
    }

    public void guardarResolucionDatosGenerales(ResolucionDatosGenerados resolucionDatosGenerados) {
        resolucionDatosGeneradosDao.actualizaResolucionDatosGenerados(resolucionDatosGenerados);
    }

    @Override
    public List<DocumentoSolicitud> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud) {

        return documentoDao.obtenerDocumentosComplementarios(idSolicitud);
    }

    @Override
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma, String rfcFuncionario) {
        StringBuffer cadenaOriginal = new StringBuffer();

        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        Solicitud solicitud = solicitudDAO.obtenerSolicitudporId(idSolicitud);

        cadenaOriginal.append(generarCadenasHelper.generarCadenaGenerarAutorizarResolucionRRL(solicitud,
                rfcFuncionario, fechaFirma));

        List<DocumentoOficial> listaDocumentos =
                documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(tramite.getNumeroAsunto(),
                        TipoDocumentoOficial.OFICIO_RESOLUCION.getClave());
        cadenaOriginal.append(listaDocumentos != null ? generarCadenasHelper
                .generaCadenaDocumentoOficial(listaDocumentos) : "");

        return cadenaOriginal.toString();
    }

}
