package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.TurnarRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.TurnarRecursoRevocacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("turnarRecursoRevocacionFacade")
public class TurnarRecursoRevocacionFacadeImpl extends BaseFacadeImpl implements TurnarRecursoRevocacionFacade {

    private static final long serialVersionUID = -3780973513792415969L;

    @Autowired
    private transient TurnarRecursoRevocacionServices turnarRecursoRevocacionServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient TareaServices tareaServices;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient CatalogoDServices catalogoService;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    public List<AbogadoDTO> obtenerAbogados(String numAsunto) {
        List<Persona> personas = turnarRecursoRevocacionServices.obtenerPersonaAbogados(numAsunto);
        return obtenerAbogadosDB(personas);
    }

    public TramiteDTO obtenerEstadoProcesal(String numAasunto) {
        Tramite tramite = tramiteServices.buscarTramite(numAasunto, null);
        return tramiteDTOTransformer.transformarDTO(tramite);
    }

    public void enviarCorreo(String numeroAsunto, Long idPersona, String nombreTarea) {
        turnarRecursoRevocacionServices.enviarCorreo(numeroAsunto, idPersona, nombreTarea);

    }

    @Override
    public String getIdeTareaOrigen() {
        return turnarRecursoRevocacionServices.getIdeTareaOrigen();
    }

    @Override
    public List<CatalogoDTO> obtenerMotivosRechazo() {
        return catalogoDTOTransformer.transformaCatalogoDDTO(catalogoService
                .obtenerCatalogoPorIdH(CatalogoConstantes.CATALOGO_RECHAZO));
    }

    private List<AbogadoDTO> obtenerAbogadosDB(List<Persona> personas){
        List<AbogadoDTO> listaAbogados = new ArrayList<AbogadoDTO>();
        AbogadoDTO abogado = null;
        for(Persona persona : personas)
        {
            abogado = new AbogadoDTO();
            abogado.setIdPersona(persona.getIdPersona());
            abogado.setNombreAbogado(persona.getRazonSocial());
            abogado.setRfc(persona.getRfc());
            abogado.setNumPendientes(tareaServices.obtenerNumeroTareasEmpleado(persona.getRfc()));
            listaAbogados.add(abogado);
        }
        return listaAbogados;
    }

}
