package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import java.util.List;

public interface ConsultasAutorizacionesCALFacade extends BaseFacade {

    DataPage obtenerDocumentosRegistroObligatoriosLazy(String idSol);    
    DataPage obtenerDocumentosRegistroOpcionalesLazy(String idSol);
    
    SolicitudCALDTO obtenerDatosSolicitud(Long idSolicitud);

    DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud);

    TramiteDTO obtenerDatosTramite(Long idSolicitud);

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    List<CatalogoDTO> obtenerListaSiNo();

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    List<CatalogoDTO> obtenerMediosDeDefensa();

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    List<CatalogoDTO> obtenerSentidosResolucion();

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1);

    /**
     * M&eacute;todo para obtener lista de tipos de
     * clasificaci&oacute;n arancelaria
     */
    List<CatalogoDTO> obtenerListaTipoClasificacion();

    String obtenerDescripcionModalidad(String idTipoTramite);

    String obtenerNombreAdministracion(Long idSolicitud);

    /**
     * Metodo que obtiene los documentos obligatorios correspondiente
     * al idSol y los devuelve en una lista DocumentoDTO.
     * 
     * @param numFolio
     * @return
     */
    List<DocumentoDTO> obtenerDocumentosRegistroObligatorios(String idSol);

    /**
     * Metodo que obtiene los documentos opcionales correspondiente al
     * idSol y los devuelve en una lista DocumentoDTO.
     * 
     * @param numFolio
     * @return
     */
    List<DocumentoDTO> obtenerDocumentosRegistroOpcionales(String idSol);
    
    List<ManifiestoDTO> obtenerManifiestosPorModalidad(Integer modalidad);

}
