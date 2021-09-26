/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.AnexarDocumentosFacade;
import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bussines que implementan la l&oacute;gica de negocio para adjuntar,
 * descargar y guardar documentos de la solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "anexarDocumentoBussines")
@NoneScoped
public class AnexarDocumentoBussines extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5029874903504814541L;

    /** Facade para anexar documentos */
    @ManagedProperty("#{anexarDocumentosFacade}")
    private AnexarDocumentosFacade anexarDocumentosFacade;

    /**
     * Metodo para adjuntar un documento oficial.
     * 
     * @param doc
     * @param listaDocumentos
     * @return
     * @throws ArchivoNoGuardadoException
     */
    public DocumentoOficialDTO adjuntarDocumentoOficial(DocumentoOficialDTO doc,
            List<DocumentoOficialDTO> listaDocumentos, String numAsunto) throws ArchivoNoGuardadoException {

        doc.setFechaCreacion(new Date());

        return doc;

    }

    /**
     * Metodo para obtener los documentos oficiales.
     * 
     * @param numAsunto
     * @return
     */
    public List<DocumentoOficialDTO> obtenerDocumentos(String numAsunto) {
        return getAnexarDocumentosFacade().obtenerDocumentos(numAsunto);
    }

    /**
     * Metodo para obtener los documentos oficiales.
     * 
     * @param numAsunto
     * @return
     */
    public List<DocumentoOficialDTO> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento) {
        return getAnexarDocumentosFacade().obtenerDocumentosPorTipo(numAsunto, tipoDocumento);
    }

    /**
     * Metodo para obtener los documentos de acuerdo al idDocs.
     * 
     * @param idDocs
     * @return
     */
    public List<DocumentoOficialDTO> obtenerDocumentosByIdDoc(List<Long> idDocs) {
        return getAnexarDocumentosFacade().obtenerDocumentosByIdDoc(idDocs);
    }

    /**
     * M&eacute;todo para descargar un documento
     */
    public InputStream descargarDocumento(DocumentoDTO documento) {
        return getAnexarDocumentosFacade().descargarDocumento(documento);
    }

    /**
     * Metodo para descargar un documento oficial.
     * 
     * @param documento
     * @return
     */
    public InputStream descargarDocumentoOficial(DocumentoOficialDTO documento) {
        return getAnexarDocumentosFacade().descargarDocumentoOficial(documento);
    }

    /**
     * M&eacute;todo para almacenar los documentos a la solicitud
     */
    public void guardarDocumentosSolicitud(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException {
        getAnexarDocumentosFacade().guardarDocumentosSolicitud(idSolicitud, documentos, tipoTramite, rfc);
    }

    /**
     * M&eacute;todo para borrado l&oacute;gico de documentos
     */
    public void eliminaDocumento(Long idDoc) {
        getAnexarDocumentosFacade().eliminaDocumento(idDoc);
    }

    /**
     * M&eacute;todo para borrado l&oacute;gico de documentos
     * oficiales
     */
    public void eliminaDocumentoOficial(Long idDoc) {
        getAnexarDocumentosFacade().eliminaDocumentoOficial(idDoc);
    }

    /**
     * Metodo para guardar los documentos de consulta.
     * 
     * @param idSolicitud
     * @param documentos
     * @param tipoTramite
     * @param rfc
     * @throws ArchivoNoGuardadoException
     */
    public void guardarDocumentosConsulta(long idSolicitud, List<DocumentoDTO> documentos, String tipoTramite,
            String rfc) throws ArchivoNoGuardadoException {
        getAnexarDocumentosFacade().guardarDocumentosConsulta(idSolicitud, documentos, tipoTramite, rfc);
    }

    /**
     * 
     * @return anexarDocumentosFacade
     */
    public AnexarDocumentosFacade getAnexarDocumentosFacade() {
        return anexarDocumentosFacade;
    }

    /**
     * 
     * @param anexarDocumentosFacade
     *            a fijar
     */
    public void setAnexarDocumentosFacade(AnexarDocumentosFacade anexarDocumentosFacade) {
        this.anexarDocumentosFacade = anexarDocumentosFacade;
    }

    /**
     * Metodo que guarda el documento oficial de remision.
     * 
     * @param numFolio
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    public void guardarDocumentosOficiales(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException {
        anexarDocumentosFacade.guardarDocumentosOficiales(numFolio, documentos);

    }
    
    /**
     * Metodo que guarda el documento oficial de exclusivo de fondo.
     * @param numFolio
     * @param documentos
     * @throws ArchivoNoGuardadoException
     */
    public void guardarDocumentosOficialesExlusivoFondo(String numFolio, List<DocumentoOficialDTO> documentos)
            throws ArchivoNoGuardadoException {
        anexarDocumentosFacade.guardarDocumentosOficialesExlusivoFondo(numFolio, documentos);

    }
    
    /**
     * Metodo para obtener los documentos Exclusivos de Fondo
     * @param numAsunto
     * @return ListDocumentosOficiales
     */

    
    public List<DocumentoOficialDTO> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto){
        return anexarDocumentosFacade.obtenerDocumentosOficialesAnexadosExclusivoFondo(numAsunto);
    }

    public List<DocumentoDTO> obtenerDocumentosRegistro(Long idSolicitud) {
        return anexarDocumentosFacade.obtenerDocumentosRegistro(idSolicitud);
    }

    /**
     * M&eacute;todo para generar la cadena original de la captura de
     * la solicitud
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return getAnexarDocumentosFacade().generaCadenaOriginalDocumentosAdicionales(idSolicitud, fechaFirma);
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        return anexarDocumentosFacade;
    }

    public void eliminarDocumentosOficialesAnexadosPorTipo(String numFolio, String cveDocumentos) {
        getAnexarDocumentosFacade().eliminarDocumentosOficialesAnexadosPorTipo(numFolio, cveDocumentos);
    }

}
