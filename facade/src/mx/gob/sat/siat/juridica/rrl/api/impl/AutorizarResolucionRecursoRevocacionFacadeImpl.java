package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.cal.dto.transformer.ResolucionDatosGeneradosDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.api.AutorizarResolucionRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FuncionarioDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.FuncionarioDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.ResolucionDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AutorizarResolucionRecursoRevocacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("autorizarResolucionRecursoRevocacionFacade")
public class AutorizarResolucionRecursoRevocacionFacadeImpl extends BaseFacadeImpl implements
        AutorizarResolucionRecursoRevocacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 5929463554198770719L;

    @Autowired
    private transient AutorizarResolucionRecursoRevocacionServices autorizarResolucionRecursoRevocacionServices;

    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    @Autowired
    private transient FuncionarioDTOTransformer funcionarioDTOTransformer;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient ResolucionDTOTransformer resolucionDTOTransformer;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient ResolucionDatosGeneradosDTOTransformer resolDatosGenTransformer;

    @Autowired
    private transient DocumentoDTOTransformer documentoTransformer;

    /*
     * Obtiene informacion de la primera seccion
     */
    @Override
    public ResolucionAbogadoDTO consultarInformacionInicial(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO) {
        PersonaInterna personaInterna = consultaSolicitudServices.obtenerDatosFuncionarioIdc(solicitudDTO.getRfc());
        FuncionarioDTO funcionarioDTO = funcionarioDTOTransformer.transformarDTO(personaInterna);
        Tramite tramite = tramiteServices.buscarTramite(tramiteDTO.getNumeroAsunto(), solicitudDTO.getIdSolicitud());
        TramiteDTO tramiteDTOCompleto = tramiteDTOTransformer.transformarDTO(tramite);
        Resolucion resolucionG =
                autorizarResolucionRecursoRevocacionServices.obtenerResolucionIdTramite(tramiteDTO.getNumeroAsunto());
        ResolucionDatosGenerados resolucionDatosGenerados =
                autorizarResolucionRecursoRevocacionServices.obtenerResolucionDatosGenerados(resolucionG
                        .getIdResolucion());
        ResolucionAbogadoDTO resolucionAbogadoDTO = resolucionDTOTransformer.transformarDTO(resolucionG);
        if (resolucionDatosGenerados != null) {
            resolucionAbogadoDTO.setFechaEmision(resolucionDatosGenerados.getFechaOficio());
            resolucionAbogadoDTO.setNumeroOficio(resolucionDatosGenerados.getNumeroOficio());
        }
        resolucionAbogadoDTO.setFuncionarioDTO(funcionarioDTO);
        resolucionAbogadoDTO.setSolicitudDTO(solicitudDTO);
        resolucionAbogadoDTO.setTramiteDTO(tramiteDTOCompleto);
        return resolucionAbogadoDTO;
    }

    @Override
    public void guardar(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        Resolucion resolucion = resolucionDTOTransformer.transformarModel(resolucionAbogadoDTO);
        ResolucionDatosGenerados resolDatosGen = resolDatosGenTransformer.transformarResolDatosGeneradosDTO(resolucion);
        autorizarResolucionRecursoRevocacionServices.guardarImpFecEmis(resolucion, getArrResolucion(resolucionAbogadoDTO));
        autorizarResolucionRecursoRevocacionServices.guardarResolucionDatosGenerales(resolDatosGen);
    }
    
    
    private Date[] getArrResolucion(ResolucionAbogadoDTO resolucionAbogadoDTO) {
        Date [] dat = new Date[resolucionAbogadoDTO.getResolucionesDTO().size()];
        for (int i=0;i<resolucionAbogadoDTO.getResolucionesDTO().size();i++) {
            dat[i] = resolucionAbogadoDTO.getResolucionesDTO().get(i).getFechaEmision();
        }
        return dat;
        
    }
    

    @Override
    public void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud) {
        getAutorizarResolucionRecursoRevocacionServices().rechazarResolucion(numAsunto, idTarea, rfc, idSolicitud);
    }

    @Override
    public ResolucionImpugnadaDTO validarExistencia(ResolucionImpugnadaDTO abogadoDTO) {
        ResolucionImpugnadaDTO dto = new ResolucionImpugnadaDTO();
        Tramite tramite =
                autorizarResolucionRecursoRevocacionServices.obtenerTramite(abogadoDTO.getResImpugnada(),
                        abogadoDTO.getIdTramite());
        if (tramite != null && tramite.getNumeroAsunto() != null && !tramite.getNumeroAsunto().equals("")) {
            dto.setIdTramite(tramite.getNumeroAsunto());
            UnidadAdministrativa administrativa =
                    autorizarResolucionRecursoRevocacionServices.obtenerUnidadAdministrativa(tramite.getNumeroAsunto());
            if (administrativa != null && administrativa.getNombre() != null && !administrativa.getNombre().equals("")) {
                dto.setAutoridadEmisora(new UnidadAdministrativaDTO());
                dto.getAutoridadEmisora().setNombre(administrativa.getNombre());
            }
        }
        return dto;
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud) {

        return documentoTransformer.tranformarDocSolicitud(autorizarResolucionRecursoRevocacionServices
                .obtenerDocumentosComplementariosAutorizacion(idSolicitud));
    }

    /**
     * @return the autorizarResolucionRecursoRevocacionServices
     */
    public AutorizarResolucionRecursoRevocacionServices getAutorizarResolucionRecursoRevocacionServices() {
        return autorizarResolucionRecursoRevocacionServices;
    }

    /**
     * @param autorizarResolucionRecursoRevocacionServices
     *            the autorizarResolucionRecursoRevocacionServices to
     *            set
     */
    public void setAutorizarResolucionRecursoRevocacionServices(
            AutorizarResolucionRecursoRevocacionServices autorizarResolucionRecursoRevocacionServices) {
        this.autorizarResolucionRecursoRevocacionServices = autorizarResolucionRecursoRevocacionServices;
    }

    /**
     * @return the consultaSolicitudServices
     */
    public ConsultaSolicitudServices getConsultaSolicitudServices() {
        return consultaSolicitudServices;
    }

    /**
     * @param consultaSolicitudServices
     *            the consultaSolicitudServices to set
     */
    public void setConsultaSolicitudServices(ConsultaSolicitudServices consultaSolicitudServices) {
        this.consultaSolicitudServices = consultaSolicitudServices;
    }

    /**
     * @return the funcionarioDTOTransformer
     */
    public FuncionarioDTOTransformer getFuncionarioDTOTransformer() {
        return funcionarioDTOTransformer;
    }

    /**
     * @param funcionarioDTOTransformer
     *            the funcionarioDTOTransformer to set
     */
    public void setFuncionarioDTOTransformer(FuncionarioDTOTransformer funcionarioDTOTransformer) {
        this.funcionarioDTOTransformer = funcionarioDTOTransformer;
    }

    /**
     * @return the tramiteServices
     */
    public TramiteServices getTramiteServices() {
        return tramiteServices;
    }

    /**
     * @param tramiteServices
     *            the tramiteServices to set
     */
    public void setTramiteServices(TramiteServices tramiteServices) {
        this.tramiteServices = tramiteServices;
    }

    /**
     * @return the resolucionDTOTransformer
     */
    public ResolucionDTOTransformer getResolucionDTOTransformer() {
        return resolucionDTOTransformer;
    }

    /**
     * @param resolucionDTOTransformer
     *            the resolucionDTOTransformer to set
     */
    public void setResolucionDTOTransformer(ResolucionDTOTransformer resolucionDTOTransformer) {
        this.resolucionDTOTransformer = resolucionDTOTransformer;
    }

    /**
     * @return the tramiteDTOTransformer
     */
    public TramiteDTOTransformer getTramiteDTOTransformer() {
        return tramiteDTOTransformer;
    }

    /**
     * @param tramiteDTOTransformer
     *            the tramiteDTOTransformer to set
     */
    public void setTramiteDTOTransformer(TramiteDTOTransformer tramiteDTOTransformer) {
        this.tramiteDTOTransformer = tramiteDTOTransformer;
    }

}
