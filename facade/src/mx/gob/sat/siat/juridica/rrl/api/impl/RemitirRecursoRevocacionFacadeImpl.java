package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.rrl.api.RemitirRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AtenderSolicitudRevocacionService;
import mx.gob.sat.siat.juridica.rrl.service.RemitirRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("remitirRecursoRevocacionFacade")
public class RemitirRecursoRevocacionFacadeImpl extends BaseFacadeImpl implements RemitirRecursoRevocacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -2183981642428801454L;

    @Autowired
    private transient RemitirRecursoRevocacionServices remitirRecursoRevocacionServices;
    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient AtenderSolicitudRevocacionService atenderSolicitudRevocacionService;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Override
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<UnidadAdministrativa> listaUA = remitirRecursoRevocacionServices.obtenerCatalogo();

        return catalogoDTOTransformer.transformarDTO(listaUA);
    }

    @Override
    public List<CatalogoDTO> obtenerPersonasAsignar(String numAsunto) {
        Tramite tramite = remitirRecursoRevocacionServices.obtenerTramitePorId(numAsunto);
        List<Persona> listaUA = remitirRecursoRevocacionServices.obtenerPersonaByRol(tramite);

        return catalogoDTOTransformer.transformarPersonas(listaUA);
    }

    @Override
    public void remitir(String numAsunto, String unidadAdministrativa, Long idTarea, String rfcAsignar,
            String rfcUsuario, String ideTareaOrigen) throws RemitirAsuntoException {
        remitirRecursoRevocacionServices.remitir(numAsunto, unidadAdministrativa, idTarea, rfcAsignar, rfcUsuario,
                ideTareaOrigen);
    }

    public void enviarCorreo(String numeroAsunto, String rfc, String nombreTarea) {
        remitirRecursoRevocacionServices.enviarCorreo(numeroAsunto, rfc, nombreTarea);
    }

    public TramiteDTO obtenerTramite(String numAsunto) {
        TramiteDTO tramiteDto = null;
        if (numAsunto != null) {
            Tramite tramite = tramiteServices.buscarTramite(numAsunto, null);
                                                                              // no
                                                                              // se
                                                                              // envia
                                                                              // id
                                                                              // de
                                                                              // solicitud
                                                                              // al
                                                                              // servicio
                                                                              // para
                                                                              // que
                                                                              // solo
                                                                              // busque
                                                                              // por
                                                                              // num.
                                                                              // de
                                                                              // asunto
            if (tramite != null) {
                tramiteDto = tramiteDTOTransformer.transformarDTO(tramite);
            }
        }
        return tramiteDto;
    }

    @Override
    public String getDefaultIdeTareaOrigen() {
        return remitirRecursoRevocacionServices.getDefaultIdeTareaOrigen();
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {

        return documentoDTOTransformer.tranformarDocSolicitud(atenderSolicitudRevocacionService
                .obtenerDocumentosComplementarios(idSolicitud));
    }

}
