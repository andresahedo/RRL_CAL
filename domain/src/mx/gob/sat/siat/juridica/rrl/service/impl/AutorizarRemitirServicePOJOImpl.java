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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarRemitirService;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service("autorizarRemitirService")
public class AutorizarRemitirServicePOJOImpl extends BaseSerializableBusinessServices implements
        AutorizarRemitirService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private transient TareaServices tareaServices;

    /**
     * Atributo asignarTareaCorreoService tipo
     * AsignarTareaCorreoService
     */
    @Autowired
    private AsignarTareaCorreoService asignarTareaCorreoService;

    @Autowired
    private EnviarCorreoService enviarCorreoService;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;


    /**
     * Atributo remisionDao tipo RemisionDao
     */
    @Autowired
    private RemisionDao remisionDao;

    /**
     * Atributo documentoDao tipo DocumentoDao
     */
    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private SolicitudDAO solicitudDao;

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    /**
     * Metodo para guardar una lista de documento
     * 
     * @param numFolio
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    @Override
    public void guardar(String numFolio, List<DocumentoOficial> listaDocs) throws ArchivoNoGuardadoException {
        Tramite tramite = obtenerTramite(numFolio);
        for (int i = 0; i < listaDocs.size(); i++) {
            DocumentoOficial doc = new DocumentoOficial();
            doc.setIdeTipoDocOficial(listaDocs.get(i).getIdeTipoDocOficial());
            doc.setUrlDocumento(listaDocs.get(i).getUrlDocumento());
            doc.setTramite(tramite);
            doc.setFechaCreacion(new Date());
            doc.setHash(listaDocs.get(i).getHash());
            doc.setDescripcionDocumento(listaDocs.get(i).getDescripcionDocumento());
            doc.setIdDocumentoOficial(listaDocs.get(i).getIdDocumentoOficial() != 0 ? listaDocs.get(i)
                    .getIdDocumentoOficial() : null);
            doc.setLongitud(listaDocs.get(i).getLongitud());
            doc.setBlnActivo(1);
            doc.setEstadoDocumento(EstadoDocumento.ANEXADO.getClave());
            doc.setFechaAudiencia(listaDocs.get(i).getFechaAudiencia());
            doc.setFechaNotificacion(listaDocs.get(i).getFechaNotificacion());
            documentoDao.guardarDocumentoOficial(doc);
        }
    }
    
    /**
     * Metodo para guardar una lista de documento
     * 
     * @param numFolio
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    @Override
    public void guardarExclusivoFondo(String numFolio, List<DocumentoOficial> listaDocs) throws ArchivoNoGuardadoException {
        Tramite tramite = obtenerTramite(numFolio);
        for (int i = 0; i < listaDocs.size(); i++) {
            DocumentoOficial doc = new DocumentoOficial();
            doc.setIdeTipoDocOficial(listaDocs.get(i).getIdeTipoDocOficial());
            doc.setUrlDocumento(listaDocs.get(i).getUrlDocumento());
            doc.setTramite(tramite);
            doc.setFechaCreacion(listaDocs.get(i).getFechaCreacion());
            doc.setHash(listaDocs.get(i).getHash());
            doc.setDescripcionDocumento(listaDocs.get(i).getDescripcionDocumento());
            doc.setIdDocumentoOficial(listaDocs.get(i).getIdDocumentoOficial() != 0 ? listaDocs.get(i)
                    .getIdDocumentoOficial() : null);
            doc.setLongitud(listaDocs.get(i).getLongitud());
            doc.setBlnActivo(1);
            doc.setEstadoDocumento(EstadoDocumento.ANEXADO.getClave());
            doc.setFechaAudiencia(listaDocs.get(i).getFechaAudiencia());
            doc.setFechaNotificacion(listaDocs.get(i).getFechaNotificacion());
            doc.setNumFolioOficio(EstadoDocumento.EXCLUSIVO_FONDO.getClave());
            documentoDao.guardarDocumentoOficial(doc);
        }
    }
    /**
     * 
     * @param numAsunto
     * @param listaDocumentos
     * @return
     */
    @Override
    public List<DocumentoOficial> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto) {
        return documentoDao.obtenerDocumentosOficialesAnexadosExclusivoFondo(numAsunto);
    }

    /**
     * 
     * @param numFolio
     * @param listaDocumentos
     * @return
     */
    @Override
    public Long guardarDoc(String numFolio, DocumentoOficial listaDocumentos) {
        Tramite tramite = obtenerTramite(numFolio);

        DocumentoOficial doc = new DocumentoOficial();
        doc.setIdeTipoDocOficial(listaDocumentos.getIdeTipoDocOficial());
        doc.setUrlDocumento(listaDocumentos.getUrlDocumento());
        doc.setHash(listaDocumentos.getHash());
        doc.setTramite(tramite);
        doc.setFechaCreacion(new Date());
        doc.setDescripcionDocumento(listaDocumentos.getDescripcionDocumento());
        doc.setLongitud(listaDocumentos.getLongitud());
        doc.setEstadoDocumento(listaDocumentos.getEstadoDocumento());
        doc.setBlnActivo(listaDocumentos.getBlnActivo());
        doc.setNumFolioOficio(listaDocumentos.getNumFolioOficio());
        documentoDao.guardarDocumentoOficial(doc);

        return doc.getIdDocumentoOficial();

    }

    /**
     * Metodo para obtener un nombre
     * 
     * @param rfc
     * @return
     */
    @Override
    public String obtenerNombre(String rfc) {
        StringBuilder sb = new StringBuilder();
        Persona persona = personaDao.obtenerPersonaPorRFC(rfc);
        sb.append(persona.getNombre());
        sb.append(" ");
        sb.append(persona.getApellidoPaterno());
        sb.append(" ");
        sb.append(persona.getApellidoMaterno());
        return sb.toString();
    }

    /**
     * Metodo para obtener un tramite
     * 
     * @param numAsunto
     * @return
     */
    @Override
    public Tramite obtenerTramite(String numAsunto) {
        return tramiteDao.obtenerTramitePorId(numAsunto);
    }

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     */
    @Override
    public List<UnidadAdministrativa> obtenerCatalogo() {

        return unidadAdministrativaDao.obtenerUnidAdmvasVigentes();
    }

    /**
     * Metodo para obtener una unidad por numero de asunto
     * 
     * @param numAsunto
     */
    @Override
    public String obtenerUnidad(String numAsunto) {
        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(numAsunto);
        if (remision.getUnidadAdminNueva() != null && remision.getUnidadAdminNueva() != null) {
            return remision.getUnidadAdminNueva().getClave();
        } else {
            return null;
        }
    }

    /**
     * Metodo para obtener una remision por numero de asunto
     * 
     * @param numAsunto
     */
    @Override
    public Remision obtenerRemision(String numAsunto) {
        return remisionDao.obtenerUltimaRemisionPorIdTramite(numAsunto);
    }

    /**
     * Metodo para actualizar una remision
     * 
     * @param numAsunto
     * @param unidad
     */
    @Override
    public void actualizarRemision(String numAsunto, String unidad) throws RemitirAsuntoException {

        Date fechaFirma = new Date();
        UnidadAdministrativa unidadAdministrativa = unidadAdministrativaDao.obtenerUnidadPorId(unidad);
        Remision remision = obtenerRemision(numAsunto);
        if (unidad != null && unidad.equals(remision.getUnidadAdminQueRemite().getClave())) {
            throw new RemitirAsuntoException();
        }
        remision.setUnidadAdminNueva(unidadAdministrativa);
        if (unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                || unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                        .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())) {
            remision.setTipoRemision(TipoRemision.AUTORIDAD_INT_EXT.getClave());
        } else {
            remision.setTipoRemision(TipoRemision.AUTORIDAD_PROPIA.getClave());
        }
        remision.setFechaAutorizacion(fechaFirma);
        guardarRemision(remision);
    }

    /**
     * Metodo para rechazar una autorizacion
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    @Override
    public void rechazarRemision(String numAsunto, String idTarea, String rfc, Long idSolicitud) {

        Remision remision = obtenerRemision(numAsunto);
        remision.setEstadoRemision(EstadoRemision.RECHAZADA.getClave());
        remision.setFechaAutorizacion(new Date());
        getLogger().debug("AutorizarRemision : rechazarRemisionCloseTarea");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            getLogger().debug("AutorizarRemision : rechazarRemisionNuevaTarea");
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(tareaAct.getClaveAdm());
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(remision.getRfcAbogado());
            if(remision.getIdeTareaOrigen().equals(TareaOrigen.TURNAR_ASUNTO.getClave())){
                tarea.setTarea(EnumeracionBitacora.TURNAR_ASUNTO.getClave());
                tarea.setDescripcion(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion());
            }else {
                tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
            }
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);
        }
        catch (Exception ex){
            getLogger().error("Error al turnar el asunto en tarea y bitacora", ex);
        }


        asignarTareaCorreoService.enviarCorreo(numAsunto, remision.getRfcAbogado(), "Atender promoci&oacute;n");
        enviarCorreoService.enviarCorreoRechazoAsunto(remision.getRfcAbogado(), numAsunto, idSolicitud,
                "Remisi&oacute;n", remision.getFechaAutorizacion(), rfc);

    }

    /**
     * Metodo para guardar una remision
     * 
     * @param remision
     */
    @Override
    public void guardarRemision(Remision remision) {
        remisionDao.guardarRemision(remision);
    }

    /**
     * Metodo para obtener lista de documentos tipo remision
     * 
     * @param numAsunto
     */
    public List<DocumentoOficial> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento) {
        return documentoDao.obtenerDocumentosPorTipo(numAsunto, tipoDocumento);
    }

    /**
     * Metodo para generar la cadena original para firmar una remision
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @param rfcAutorizador
     * @return
     */
    @Override
    public String generaCadenaOriginalAutorizarRemitir(Long idSolicitud, String rfcAutorizador) {
        StringBuffer cadenaOriginal = new StringBuffer();

        Solicitud solicitud = solicitudDao.obtenerSolicitudporId(idSolicitud);
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(idSolicitud);
        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(tramite.getNumeroAsunto());
        List<DocumentoOficial> listaDocumentoOficial =
                documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(tramite.getNumeroAsunto(),
                        TipoDocumentoOficial.OFICIO_REMISION.getClave());
     // |cadena|datos|remision|
        cadenaOriginal.append(generarCadenasHelper.generarCadenaAutorizarRemision(remision, solicitud, rfcAutorizador)); 
     // doc1|hash1|doc2|hash2|
        cadenaOriginal.append(generarCadenasHelper.generaCadenaDocumentoOficial(listaDocumentoOficial)); 

        return cadenaOriginal.toString();
    }

}
