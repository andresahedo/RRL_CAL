/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Remision;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface AutorizarRemitirService extends Serializable {

    /**
     * Metodo para guardar una lista de documento
     * 
     * @param numFolio
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    void guardar(String numFolio, List<DocumentoOficial> listaDocumentos) throws ArchivoNoGuardadoException;
    
    /**
     * Metodo para guardar una lista de documento
     * 
     * @param numFolio
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    void guardarExclusivoFondo(String numFolio, List<DocumentoOficial> listaDocs) throws ArchivoNoGuardadoException;
    
    
    /**
     * 
     * @param numAsunto
     * @param listaDocumentos
     * @return
     */
    List<DocumentoOficial> obtenerDocumentosOficialesAnexadosExclusivoFondo(String numAsunto);
    

    /**
     * Metodo para obtener un nombre
     * 
     * @param rfc
     */
    String obtenerNombre(String rfc);

    /**
     * Metodo para obtener un tramite
     * 
     * @param numAsunto
     */
    Tramite obtenerTramite(String numAsunto);

    /**
     * Metodo para obtener un catalogo de unidades administrativas
     */
    List<UnidadAdministrativa> obtenerCatalogo();

    /**
     * Metodo para obtener una unidad por numero de asunto
     * 
     * @param numAsunto
     */
    String obtenerUnidad(String numAsunto);

    /**
     * Metodo para obtener una remision por numero de asunto
     * 
     * @param numAsunto
     */
    Remision obtenerRemision(String numAsunto);

    /**
     * Metodo para guardar una remision
     * 
     * @param remision
     */
    void guardarRemision(Remision remision);

    /**
     * 
     * @param numFolio
     * @param listaDocumentos
     */
    Long guardarDoc(String numFolio, DocumentoOficial listaDocumentos);

    /**
     * Metodo para actualizar una remision
     * 
     * @param numAsunto
     * @param unidad
     * @throws RemitirAsuntoException
     */
    void actualizarRemision(String numAsunto, String unidad) throws RemitirAsuntoException;

    /**
     * Metodo para obtener documentos de tipo remision
     * 
     * @param numAsunto
     * @param tipoDocumento
     */
    List<DocumentoOficial> obtenerDocumentosPorTipo(String numAsunto, String tipoDocumento);

    void rechazarRemision(String numAsunto, String idTarea, String rfc, Long idSolicitud);

    /**
     * Metodo para generar la cadena original para firmar una remision
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @param rfcAutorizador
     * @return
     */
    String generaCadenaOriginalAutorizarRemitir(Long idSolicitud, String rfcAutorizador);
}
