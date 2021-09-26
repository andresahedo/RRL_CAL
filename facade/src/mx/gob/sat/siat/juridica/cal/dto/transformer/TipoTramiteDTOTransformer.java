package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TipoTramiteDTOTransformer extends DTOTransformer<TipoTramite, TipoTramiteDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 2062383533273475097L;

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer
     * #transformarDTO(java.lang.Object)
     */
    @Override
    public TipoTramiteDTO transformarDTO(TipoTramite source) {
        TipoTramiteDTO tipoTramiteDTO = new TipoTramiteDTO();
        tipoTramiteDTO.setAsignado(source.getAsignado());
        tipoTramiteDTO.setBlnAutomatico(source.getBlnAutomatico() != null ? source.getBlnAutomatico() : "false");
        tipoTramiteDTO.setBlnReplicaInfo(source.getBlnReplicaInfo() != null ? source.getBlnReplicaInfo() : "false");
        tipoTramiteDTO.setClaveModulo(source.getClaveModulo());
        tipoTramiteDTO.setDependencia(source.getDependencia());
        tipoTramiteDTO.setDescripcionFlujo(source.getDescripcionFlujo());
        tipoTramiteDTO.setDescripcionModalidad(source.getDescripcionModalidad());
        tipoTramiteDTO.setDescripcionSubservicio(source.getDescripcionSubservicio());
        tipoTramiteDTO.setFechaCaptura(source.getFechaCaptura());
        tipoTramiteDTO.setIdeTipoOperacion(source.getIdeTipoOperacion());
        tipoTramiteDTO.setIdTipoTramite(source.getIdTipoTramite());
        Vigencia vigencia = new Vigencia();
        tipoTramiteDTO.setIdVigencia(source.getVigencia() != null ? source.getVigencia().getBlnActivo() : vigencia
                .getBlnActivo());
        tipoTramiteDTO.setModalidad(source.getModalidad());
        tipoTramiteDTO.setNivelServicio(source.getNivelServicio());
        tipoTramiteDTO.setNombre(source.getNombre());
        tipoTramiteDTO.setNombreMensajeAxway(source.getNombreMensajeAxway());
        tipoTramiteDTO.setNombreServicioAxway(source.getNombreServicioAxway());
        tipoTramiteDTO.setServicio(source.getServicio());
        tipoTramiteDTO.setSubservicio(source.getSubservicio());
        tipoTramiteDTO.setUrlAxway(source.getUrlAxway());
        tipoTramiteDTO.setDescripcionServicio(source.getDescripcionServicio());
        return tipoTramiteDTO;
    }

    /**
     * M&eacute;todo para transformar un arreglor de tipoTramite a
     * tipoTramiteDTO
     * 
     * @param tiposTramite
     * @return
     */
    public List<TipoTramiteDTO> transformarDTOList(List<TipoTramite> tiposTramite) {
        List<TipoTramiteDTO> tramites = new ArrayList<TipoTramiteDTO>();
        for (TipoTramite tipoTramite : tiposTramite) {
            tramites.add(transformarDTO(tipoTramite));
        }
        return tramites;
    }

    /**
     * Metodo para transformar objetos TipoTramiteDTO seleccionados en
     * la tabla de tramites por asignar a una unidad administrativa.
     * 
     * @param tramites
     * @return
     */
    public List<TipoTramite> transformarModel(List<TipoTramiteDTO> tramites) {
        List<TipoTramite> tramitesModel = new ArrayList<TipoTramite>();
        for (TipoTramiteDTO tramite : tramites) {
            TipoTramite tram = new TipoTramite();
            tram.setServicio(tramite.getServicio());
            tram.setDescripcionServicio(tramite.getDescripcionServicio());
            tram.setBlnServicioSelect(tramite.getBlnServicioSelect() ? 1 : 0);
            tramitesModel.add(tram);
        }
        return tramitesModel;
    }

    /**
     * Metodo utilizado para transformar las modalidades a asignar a
     * un empleado.
     * 
     * @param tramites
     * @return
     */

    public List<TipoTramite> transformarModels(List<TipoTramiteDTO> tramites) {
        List<TipoTramite> tramitesModel = new ArrayList<TipoTramite>();
        for (TipoTramiteDTO tram : tramites) {
            TipoTramite modalidad = new TipoTramite();
            modalidad.setAsignado(tram.getBlnAsignado());

            modalidad.setBlnResponsable(tram.getBlnResponsable() != null && tram.getBlnResponsable() ? 1 : 0);

            modalidad.setClaveModulo(tram.getClaveModulo());
            modalidad.setIdTipoTramite(tram.getIdTipoTramite());
            modalidad.setNombre(tram.getNombre());
            modalidad.setServicio(tram.getServicio());
            modalidad.setBlnActivo(tram.getBlnAsignado() ? "1" : "0");
            tramitesModel.add(modalidad);
        }
        return tramitesModel;
    }
}
