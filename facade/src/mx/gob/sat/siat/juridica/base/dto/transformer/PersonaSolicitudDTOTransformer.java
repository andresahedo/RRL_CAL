package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dto.DomicilioSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonaSolicitudDTOTransformer extends DTOTransformer<Solicitante, PersonaSolicitudDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -8489114331223110984L;

    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    @Override
    public PersonaSolicitudDTO transformarDTO(Solicitante solicitante) {

        PersonaSolicitudDTO personaSolicitud = new PersonaSolicitudDTO();
        DomicilioSolicitudDTO domicilioDTO = new DomicilioSolicitudDTO();
        personaSolicitud.setRfc(solicitante.getRfc());
        personaSolicitud.setNombre(solicitante.getNombre());
        personaSolicitud.setApellidoMaterno(solicitante.getApellidoMaterno());
        personaSolicitud.setApellidoPaterno(solicitante.getApellidoPaterno());
        personaSolicitud.setEstadoContribuyente(solicitante.getEstadoContribuyente());
        personaSolicitud.setRazonSocial(solicitante.getRazonSocial());
        personaSolicitud.setBlnExtranjero(solicitante.getBooleanExtranjero());
        personaSolicitud.setAmparado(true);
        personaSolicitud.setPersonaMoral(solicitante.getPersonaMoral());
        if (solicitante.getDomicilio() != null) {

            domicilioDTO.setCalle(solicitante.getDomicilio().getCalle());
            domicilioDTO.setNumeroExterior(solicitante.getDomicilio().getNumeroExterior());
            domicilioDTO.setNumeroInterior(solicitante.getDomicilio().getNumeroInterior());
            domicilioDTO.setTelefono(solicitante.getDomicilio().getTelefono());
            domicilioDTO.setCorreoElectronico(solicitante.getCorreoElectronico());
            domicilioDTO.setCodigoPostal(solicitante.getDomicilio().getCodigoPostal());
            domicilioDTO.setCorreoElectronico(solicitante.getCorreoElectronico());
            
            if (solicitante.getDomicilio().getColonia() != null) {
                domicilioDTO.setColonia(solicitante.getDomicilio().getColonia().getNombre());
                domicilioDTO.setCveColonia(solicitante.getDomicilio().getColonia().getClave());
            }else{
                domicilioDTO.setColonia("");
                domicilioDTO.setCveColonia("");
            }
            if (solicitante.getDomicilio().getDelegacionMunicipio() != null) {
                domicilioDTO.setCveDelegacion(
                        solicitante.getDomicilio().getDelegacionMunicipio().getClave());
                domicilioDTO.setDelegacionMunicipio(
                        solicitante.getDomicilio().getDelegacionMunicipio().getNombre());
            }else{
                domicilioDTO.setCveDelegacion("");
                domicilioDTO.setDelegacionMunicipio("");
            }
            if (solicitante.getDomicilio().getEntidadFederativa() != null) {
                domicilioDTO.setCveEstado(solicitante.getDomicilio().getEntidadFederativa().getClave());
                domicilioDTO.setEstado(solicitante.getDomicilio().getEntidadFederativa().getNombre());
            }else{
                domicilioDTO.setCveEstado("");
                domicilioDTO.setEstado("");
            }
            if (solicitante.getDomicilio().getPais() != null) {
                domicilioDTO.setCvePais(solicitante.getDomicilio().getPais().getClave());
                domicilioDTO.setNombrePais(solicitante.getDomicilio().getPais().getNombre());
            }else{
                domicilioDTO.setCvePais("");
                domicilioDTO.setNombrePais("");
            }
            
            personaSolicitud.setDomicilio(domicilioDTO);
            if (solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                personaSolicitud.getDomicilio().setAdministracionLocal(
                        solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora());
                personaSolicitud.getDomicilio().setNombreAdministracionLocal(
                        unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                                solicitante.getDomicilio().getClaveAdministracionLocalRecaudadora()).getNombre());
            }

        }
        personaSolicitud.setBlnTieneCorreo(solicitante.isTieneCorreo());
        return personaSolicitud;
    }

}
