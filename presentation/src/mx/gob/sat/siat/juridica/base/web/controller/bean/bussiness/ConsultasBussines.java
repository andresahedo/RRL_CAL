/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.api.ConsultaRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Bussines que implementa la logica de negocio para las consultas.
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean(name = "consultasBussines")
public class ConsultasBussines extends BaseCloudBusiness implements Serializable {

    /**
     * Metodo que obtiene los documentos de un registro para el LazyData;
     */
    public DataPage obtenerDocumentosRegistroLazy(String idsolicitud) {
        return consultaRecursoRevocacionFacade.obtenerDocumentosRegistroLazy(idsolicitud);
    }

    /**
     * Metodo que obtiene los documentosOficiales de un registro para el LazyData;
     */
    public DataPage obtenerDocumentoslazy(String numFolio) {
        return getConsultaRecursoRevocacionFacade().obtenerDocumentosOficialesLazy(numFolio);
    }

    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = 1L;

    /** Facade para consultar recursos de revocacion. */
    @ManagedProperty("#{consultaRecursoRevocacionFacade}")
    private ConsultaRecursoRevocacionFacade consultaRecursoRevocacionFacade;

    /**
     * Metodo que hace una busqueda por idSolicitud y devuelve un objeto
     * DatosSolicitudDTO.
     */
    public DatosSolicitudDTO buscar(Long idSolicitud) {
        return getConsultaRecursoRevocacionFacade().buscarSolicitud(idSolicitud);
    }

    /**
     * 
     * @return consultaRecursoRevocacionFacade.
     */
    public ConsultaRecursoRevocacionFacade getConsultaRecursoRevocacionFacade() {
        return consultaRecursoRevocacionFacade;
    }

    /**
     * 
     * @param consultaRecursoRevocacionFacade la consultaRecursoRevocacionFacade a
     *                                        ser fijada.
     */
    public void setConsultaRecursoRevocacionFacade(ConsultaRecursoRevocacionFacade consultaRecursoRevocacionFacade) {
        this.consultaRecursoRevocacionFacade = consultaRecursoRevocacionFacade;
    }

    /**
     * Metodo que obtiene los documentos correspondiente al numFolio y los devuelve
     * en una lista DocumentoDTO.
     * 
     * @param numFolio
     * @return
     */
    public List<DocumentoOficialDTO> obtenerDocumentos(String numFolio) {
        return getConsultaRecursoRevocacionFacade().obtenerDocumentosOficiales(numFolio);
    }

    /**
     * M�todo que devuelve los documentos oficiales generados por el sistema para la
     * consulta de promociones del promovente.
     * 
     */
    public List<DocumentoOficialDTO> obtenerDocOficialesGenerados(String numAsunto) {
        return getConsultaRecursoRevocacionFacade().obtenerDocOficialesGenerados(numAsunto);
    }

    /**
     * Metodo que obtiene documentos de un tramite correspondientes al tipo de
     * tramite y el numero de asunto.
     */

    public List<DocumentoDTO> obtenerDocumentosTramite(String idTipoTramite, String numAsunto) {
        return consultaRecursoRevocacionFacade.obtenerTiposDocumentos(idTipoTramite, numAsunto);
    }

    /**
     * Metodo que obtiene los documentos de un registro correspondiente a su
     * solicitud.
     */
    public List<DocumentoDTO> obtenerDocumentosRegistro(String idSol) {
        return consultaRecursoRevocacionFacade.obtenerDocumentosRegistro(idSol);
    }

    /**
     * Metodo que obtiene los documentos de un registro correspondiente a su
     * solicitud.
     */

    public DocumentoDTO obtenerDocumentoSolicitudTipoDoc(String idSol, Integer idTipoDocumento) {
        return consultaRecursoRevocacionFacade.obtenerDocumentoSolicitudTipoDoc(idSol, idTipoDocumento);
    }

    /**
     * Metodo que descarga un documento
     */
    public InputStream descargarDocumento(DocumentoDTO documento) {
        return consultaRecursoRevocacionFacade.descargarDocumento(documento);
    }

    /**
     * Metodo que obtiene los requerimientos del numero de folio que recibe como
     * parametro.
     */
    public List<DatosRequerimientoDTO> obtenerRequerimientos(String numFolio) {
        return getConsultaRecursoRevocacionFacade().obtenerRequerimientos(numFolio);
    }

    /**
     * Metodo para actualizar los requerimientos
     */
    public void actualizarRequerimiento(DatosRequerimientoDTO datosRequerimientoDTO) throws FechaInvalidaException {
        getConsultaRecursoRevocacionFacade().actualizarRequerimiento(datosRequerimientoDTO);
    }

    public List<DocumentoOficialDTO> obtenerDocumentosOficialesPromovente(String numFolio, Long idRequerimiento) {
        return getConsultaRecursoRevocacionFacade().obtenerDocumentosOficialesPromovente(numFolio, idRequerimiento);
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        // TODO Auto-generated method stub
        return consultaRecursoRevocacionFacade;
    }

    public void eliminaDocumento(long idDocumentoSolicitud) {
        getConsultaRecursoRevocacionFacade().eliminaDocumento(idDocumentoSolicitud);

    }

    public void guardarDocumentos(List<DocumentoOficialDTO> listaDocumentosOficiales, String numeroAsunto,
            Long idRequerimiento) {
        getConsultaRecursoRevocacionFacade().guardarDocumentos(listaDocumentosOficiales, numeroAsunto, idRequerimiento);

    }

    public void eliminaDocumentosNoFirmados(long idSolicitud) {
        getConsultaRecursoRevocacionFacade().eliminaDocumentosNoFirmados(idSolicitud);
    }

    public TramiteDTO obtenerTramitePorId(String numFolio) {
        return getConsultaRecursoRevocacionFacade().obtenerTramitePorId(numFolio);
    }

    public List<CatalogoDTO> obtenerMotivosRechazo() {
        return getConsultaRecursoRevocacionFacade().obtenerMotivosRechazo();
    }

    public List<ObservacionDTO> obtenerObservacionesPorTramite(String numAsunto) {
        return getConsultaRecursoRevocacionFacade().obtenerObservacionesPorTramite(numAsunto);
    }

}
