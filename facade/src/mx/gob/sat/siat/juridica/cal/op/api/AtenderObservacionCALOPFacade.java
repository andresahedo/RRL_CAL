package mx.gob.sat.siat.juridica.cal.op.api;

import mx.gob.sat.siat.juridica.base.api.AtenderObservacionFacade;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import java.util.List;

public interface AtenderObservacionCALOPFacade extends AtenderObservacionFacade {

    SolicitudCALDTO obtenerSolicitud(Long idSolicitud);

    void eliminaFaccion(Long idFraccion);

    void eliminaPersona(Long idPersona);

    TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite);

    List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1);

    List<CatalogoDTO> obtenerMediosDeDefensa();

    List<CatalogoDTO> obtenerListaSiNo();

    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    List<CatalogoDTO> obtenerListaTipoPersona();

    List<CatalogoDTO> obtenerListaTipoClasificacion();

    SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException;

    DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud);
}
