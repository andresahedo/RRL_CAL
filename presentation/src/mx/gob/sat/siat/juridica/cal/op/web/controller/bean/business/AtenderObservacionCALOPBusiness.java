package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.AtenderObservacionBussines;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.op.api.AtenderObservacionCALOPFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@ManagedBean
@NoneScoped
public class AtenderObservacionCALOPBusiness extends AtenderObservacionBussines {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{atenderObservacionCALOPFacade}")
    private AtenderObservacionCALOPFacade atenderObservacionCALOPFacade;

    public SolicitudCALDTO obtenerSolicitud(Long idSolicitud) {
        return getAtenderObservacionCALOPFacade().obtenerSolicitud(idSolicitud);
    }

    public TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite) {
        return getAtenderObservacionCALOPFacade().obtenerTipoTramitePorId(idTIpoTramite);
    }

    public void eliminaPersona(Long idPersona) {
        getAtenderObservacionCALOPFacade().eliminaPersona(idPersona);
    }

    public void eliminaFraccion(Long idFraccion) {
        getAtenderObservacionCALOPFacade().eliminaFaccion(idFraccion);
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudCALDTO
     */
    public SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException {
        return getAtenderObservacionCALOPFacade().guardarSolicitud(solicitud, rfcSolicitante);
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion(String codGen1) {
        return getAtenderObservacionCALOPFacade().obtenerCatalogoDPorCodGen1(codGen1);
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        return getAtenderObservacionCALOPFacade().obtenerAutoridadesEmisoras();
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        return getAtenderObservacionCALOPFacade().obtenerListaSiNo();
    }

    public List<CatalogoDTO> obtenerListaTipoPersona() {
        return getAtenderObservacionCALOPFacade().obtenerListaTipoPersona();
    }

    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        return getAtenderObservacionCALOPFacade().obtenerListaTipoClasificacion();
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        return getAtenderObservacionCALOPFacade().obtenerMediosDeDefensa();
    }

    /**
     * Metodo que hace una busqueda por idSolicitud y devuelve un
     * objeto DatosSolicitudDTO.
     */
    public DatosSolicitudDTO buscarSolicitante(Long idSolicitud) {
        return getAtenderObservacionCALOPFacade().obtenerDatosSolicitante(idSolicitud);
    }

    /**
     * @return the atenderObservacionCALOPFacade
     */
    public AtenderObservacionCALOPFacade getAtenderObservacionCALOPFacade() {
        return atenderObservacionCALOPFacade;
    }

    /**
     * @param atenderObservacionCALOPFacade
     *            the atenderObservacionCALOPFacade to set
     */
    public void setAtenderObservacionCALOPFacade(AtenderObservacionCALOPFacade atenderObservacionCALOPFacade) {
        this.atenderObservacionCALOPFacade = atenderObservacionCALOPFacade;
    }

}
