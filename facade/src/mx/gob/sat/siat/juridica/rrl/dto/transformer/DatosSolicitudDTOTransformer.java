package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DomicilioSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.service.DomicilioServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class DatosSolicitudDTOTransformer extends DTOTransformer<Solicitante, DatosSolicitudDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 7835310138942601112L;
    @Autowired
    private transient RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    @Autowired
    private transient DomicilioServices domicilioServices;

    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    @Override
    public DatosSolicitudDTO transformarDTO(Solicitante solicitante) {
        DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
        datosSolicitud.setRfcContribuyente(solicitante.getRfc());
        datosSolicitud.setEstadoContribuyente(solicitante.getEstadoContribuyente());
        datosSolicitud.setNombre(solicitante.getNombre());
        datosSolicitud.setApellidoMaterno(solicitante.getApellidoMaterno());
        datosSolicitud.setApellidoPaterno(solicitante.getApellidoPaterno());
        datosSolicitud.setCorreoElectronico(solicitante.getCorreoElectronico());
        datosSolicitud.setRazonSocial(solicitante.getRazonSocial());
        
        if (solicitante.getDomicilio() != null) {
            datosSolicitud.setCalle(solicitante.getDomicilio().getCalle());
            datosSolicitud.setNumeroExterior(solicitante.getDomicilio().getNumeroExterior());
            datosSolicitud.setNumeroInterior(solicitante.getDomicilio().getNumeroInterior());
            datosSolicitud.setTelefono(solicitante.getDomicilio().getTelefono());
            datosSolicitud.setCodigoPostal(solicitante.getDomicilio().getCodigoPostal());
            datosSolicitud.setColonia(solicitante.getDomicilio().getColonia() != null ? 
                                    solicitante.getDomicilio().getColonia().getNombre() : null);
            
            datosSolicitud.setClaveColonia(solicitante.getDomicilio().getColonia() != null ? 
                                    solicitante.getDomicilio().getColonia().getClave() : null);
            datosSolicitud.setDelegacionMunicipio(solicitante.getDomicilio().getDelegacionMunicipio() != null ? 
                                    solicitante.getDomicilio().getDelegacionMunicipio().getNombre() : null);
            datosSolicitud.setClaveDelegacion(solicitante.getDomicilio().getDelegacionMunicipio() != null ? 
                                    solicitante.getDomicilio().getDelegacionMunicipio().getClave() : null);
            
            datosSolicitud.setEstado(solicitante.getDomicilio().getEntidadFederativa() != null ? solicitante
                    .getDomicilio().getEntidadFederativa().getNombre() : "");
            datosSolicitud.setClaveEstado(solicitante.getDomicilio().getEntidadFederativa() != null ? solicitante
                    .getDomicilio().getEntidadFederativa().getClave() : null);
            datosSolicitud.setPaisCve(solicitante.getDomicilio().getPais() != null ? solicitante.getDomicilio()
                    .getPais().getClave() : "");
            datosSolicitud.setPaisNombre(solicitante.getDomicilio().getPais() != null ? solicitante.getDomicilio()
                    .getPais().getNombre() : "");
            if (solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                datosSolicitud.setAdministracionLocal(solicitante.getDomicilio()
                        .getClaveAdministracionLocalRecaudadora());
                datosSolicitud.setNombreAdministracionLocal(unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                        solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora()).getNombre());
            }
        }
        return datosSolicitud;
    }

    public SolicitudRecursoRevocacion transformarSolicitudModel(DatosSolicitudDTO datosSolicitud) {
        SolicitudRecursoRevocacion solicitud = new SolicitudRecursoRevocacion();
        UnidadAdministrativa ua = new UnidadAdministrativa();
        ua.setClave(datosSolicitud.getIdAutoridadEmisora());
        solicitud.setUnidadAdministrativaRepresentacionFederal(ua);
        solicitud.setFechaCreacion(datosSolicitud.getFechaCreacion());
        solicitud.setFechaInicioTramite(datosSolicitud.getFechaInicioTramite());
        if (datosSolicitud.getIdSolicitud() != null) {
            solicitud.setIdSolicitud(datosSolicitud.getIdSolicitud());
            solicitud.setFechaCreacion(datosSolicitud.getFechaCreacion());
        }
        else {
            solicitud.setFechaCreacion(new Date());
        }
        solicitud.setCveRolCapturista(datosSolicitud.getRolCapturista());
        if (datosSolicitud.getNumeroFolio() != null && datosSolicitud.getFechaApertura() != null) {
            solicitud.setFolio(Long.parseLong(datosSolicitud.getNumeroFolio()));
            solicitud.setFechaApertura(datosSolicitud.getFechaApertura());
        }
        return solicitud;
    }

    public Solicitante transformarSolicitanteModel(DatosSolicitudDTO datosSolicitud) {
        Solicitante solicitante = new Solicitante();
        DomicilioSolicitud domicilio = new DomicilioSolicitud();
        solicitante.setApellidoMaterno(datosSolicitud.getApellidoMaterno());
        solicitante.setApellidoPaterno(datosSolicitud.getApellidoPaterno());
        solicitante.setNombre(datosSolicitud.getNombre());
        solicitante.setRfc(datosSolicitud.getRfcContribuyente());
        solicitante.setEstadoContribuyente(datosSolicitud.getEstadoContribuyente());
        solicitante.setCorreoElectronico(datosSolicitud.getCorreoElectronico());
        domicilio.setCalle(datosSolicitud.getCalle());
        domicilio.setNumeroExterior(datosSolicitud.getNumeroExterior());
        domicilio.setNumeroInterior(datosSolicitud.getNumeroInterior());
        domicilio.setTelefono(datosSolicitud.getTelefono());
        domicilio.setCodigoPostal(datosSolicitud.getCodigoPostal());
        domicilio.setClaveAdministracionLocalRecaudadora(datosSolicitud.getAdministracionLocal());
        if (datosSolicitud.getClaveEstado() != null) {
            domicilio.setEntidadFederativa(domicilioServices.buscarEntidadByClave(datosSolicitud.getClaveEstado()));
        }
        if (datosSolicitud.getClaveColonia() != null) {
            domicilio.setColonia(domicilioServices.buscarColoniaByClave(datosSolicitud.getClaveColonia()));
        }
        if (datosSolicitud.getClaveDelegacion() != null) {
            domicilio.setDelegacionMunicipio(domicilioServices.buscarDelegacionByClave(datosSolicitud
                    .getClaveDelegacion()));
        }
        if (datosSolicitud.getPaisCve() != null) {
            domicilio.setPais(domicilioServices.buscarPaisByClave(datosSolicitud.getPaisCve()));
        }
        solicitante.setDomicilio(domicilio);
        solicitante.setRazonSocial(datosSolicitud.getRazonSocial());
        solicitante.setPersonaMoral(datosSolicitud.getRazonSocial() != null);
        if (datosSolicitud.getSolicitante() != null) {
            solicitante.setBooleanExtranjero(datosSolicitud.getSolicitante().getBlnExtranjero() != null
                    ? datosSolicitud.getSolicitante().getBlnExtranjero() : false);
        }
        return solicitante;
    }

    public DatosSolicitudDTO transformarDTO(SolicitudDatosGenerales solicitud) {
        DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(solicitud.getFechaInicioTramite());
        cal.add(Calendar.MONTH, NumerosConstantes.TRES);
        Solicitante solicitante = registroRecursoRevocacionDAO.getSolicituanteByRfc(solicitud.getIdSolicitud());
        if (solicitante != null) {
            datosSolicitud.setRfcContribuyente(solicitante.getRfc());
            datosSolicitud.setEstadoContribuyente(solicitante.getEstadoContribuyente());
            datosSolicitud.setNombre(solicitante.getNombre());
            datosSolicitud.setApellidoMaterno(solicitante.getApellidoMaterno());
            datosSolicitud.setApellidoPaterno(solicitante.getApellidoPaterno());
            datosSolicitud.setCorreoElectronico(solicitante.getCorreoElectronico());
            datosSolicitud.setCalle(solicitante.getDomicilio().getCalle());
            datosSolicitud.setNumeroExterior(solicitante.getDomicilio().getNumeroExterior());
            datosSolicitud.setNumeroInterior(solicitante.getDomicilio().getNumeroInterior());
            datosSolicitud.setColonia(solicitante.getDomicilio().getColonia() != null ? solicitante.getDomicilio()
                    .getColonia().getNombre() : "");
            datosSolicitud.setEstado(solicitante.getDomicilio().getEntidadFederativa() != null ? solicitante
                    .getDomicilio().getEntidadFederativa().getNombre() : "");
            datosSolicitud.setDelegacionMunicipio(solicitante.getDomicilio().getDelegacionMunicipio() != null
                    ? solicitante.getDomicilio().getDelegacionMunicipio().getNombre() : "");
            datosSolicitud.setCodigoPostal(solicitante.getDomicilio().getCodigoPostal());
            datosSolicitud.setTelefono(solicitante.getDomicilio().getTelefono());
            datosSolicitud.setFechaInicio(solicitud.getFechaInicioTramite());
            datosSolicitud.setFechaFin(cal.getTime());
            datosSolicitud.setRazonSocial(solicitante.getRazonSocial());
            if (solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                datosSolicitud.setAdministracionLocal(solicitante.getDomicilio()
                        .getClaveAdministracionLocalRecaudadora());
                datosSolicitud.setNombreAdministracionLocal(unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                        solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora()).getNombre());
            }
        }
        datosSolicitud.setIdAutoridadEmisora(solicitud.getUnidadAdministrativaRepresentacionFederal().getClave());
        datosSolicitud.setNombreAutoridadEmisora(solicitud.getUnidadAdministrativaRepresentacionFederal().getNombre());
        datosSolicitud.setIdSolicitud(solicitud.getIdSolicitud());
        datosSolicitud.setFechaCreacion(solicitud.getFechaCreacion());
        datosSolicitud.setFechaInicioTramite(solicitud.getFechaInicioTramite());
        datosSolicitud.setRolCapturista(solicitud.getCveRolCapturista());
        if (solicitud.getFolio() != null && solicitud.getFechaApertura() != null) {
            datosSolicitud.setNumeroFolio(String.valueOf(solicitud.getFolio()));
            datosSolicitud.setFechaApertura(solicitud.getFechaApertura());
        }
        return datosSolicitud;
    }

    public DatosSolicitudDTO transformarSolicitanteDTO(Solicitante solicitante) {
        DatosSolicitudDTO datosSolicitud = new DatosSolicitudDTO();
        if (solicitante != null) {
            datosSolicitud.setRfcContribuyente(solicitante.getRfc());
            datosSolicitud.setEstadoContribuyente(solicitante.getEstadoContribuyente());
            datosSolicitud.setRazonSocial(solicitante.getRazonSocial());
            datosSolicitud.setNombre(solicitante.getNombre());
            datosSolicitud.setApellidoMaterno(solicitante.getApellidoMaterno());
            datosSolicitud.setApellidoPaterno(solicitante.getApellidoPaterno());
            datosSolicitud.setCorreoElectronico(solicitante.getCorreoElectronico());
            datosSolicitud.setCalle(solicitante.getDomicilio().getCalle());
            datosSolicitud.setNumeroExterior(solicitante.getDomicilio().getNumeroExterior());
            datosSolicitud.setNumeroInterior(solicitante.getDomicilio().getNumeroInterior());
            datosSolicitud.setTelefono(solicitante.getDomicilio().getTelefono());
            datosSolicitud.setCodigoPostal(solicitante.getDomicilio().getCodigoPostal());
            if (solicitante.getDomicilio().getEntidadFederativa() != null) {
                datosSolicitud.setEstado(solicitante.getDomicilio().getEntidadFederativa().getNombre());
            }
            if (solicitante.getDomicilio().getDelegacionMunicipio() != null) {
                datosSolicitud.setDelegacionMunicipio(solicitante.getDomicilio().getDelegacionMunicipio().getNombre());
            }
            if (solicitante.getDomicilio().getColonia() != null) {
                datosSolicitud.setColonia(solicitante.getDomicilio().getColonia().getNombre());
            }
            if (solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                datosSolicitud.setAdministracionLocal(solicitante.getDomicilio()
                        .getClaveAdministracionLocalRecaudadora());
                datosSolicitud.setNombreAdministracionLocal(unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                        solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora()).getNombre());
            }
        }
        return datosSolicitud;
    }
}
