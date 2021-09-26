package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import java.util.Date;
import java.util.List;

public interface RegistroRecursoRevocacionFacade extends BaseFacade {

    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    DatosSolicitudDTO obtenerInformacionIDC(String rfc);

    SolicitudDTO guardarSolicitud(DatosSolicitudDTO solicitud) throws SolicitudNoGuardadaException;

    String generarCadenaOriginal(long idSolicitud, Date fechaFirma);

    DatosSolicitudDTO obtenerSolicitudPorId(Long idSolicitud);

    void firmarDocumentos(Long idSolicitud, FirmaDTO firmaDTO);

    /**
     * Obtiene una Firma con la cadena original, y la firma (sello)
     * generada por la SIAT JURIDICA
     * 
     * @param numAsunto
     *            Numero de asunto
     * @param idSolicitud
     *            Id de la solicitud
     * @param fechaFirma
     *            Fecha de firma
     * @return FirmaDTO con la cadena y sello de la selladora (SIAT
     *         Juridica)
     */
    FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    void sellaDocumentos(Long idSolicitud);

    DatosSolicitudDTO guardarSolicitud(DatosSolicitudDTO datosSolicitud, String rfcCapturista)
            throws SolicitudNoGuardadaException;

    List<DocumentoDTO> getDocumentosObligatorios(Integer idTipoTramite);

    DocumentoDTO[] obtenerDocumentosSeleccionados(long idSolicitud, List<DocumentoDTO> listaDocumentosOpcionales);

    List<DocumentoDTO> getDocumentosOpcionales(Integer idTipoTramite);

    String firmarSolicitud(long idSolicitud, FirmaDTO firma, String usuario, String rfcContribuyente,
            Object ceritifcadoUtilizado) throws TareaInicialException, TereaSinUsuarioAsignadoException;

    String obtenerModalidadDeTramite(Integer idTipoTramite);

    List<DocumentoDTO> obtenerDocumentosPorIdSolicitud(Long idSolicitud);
}
