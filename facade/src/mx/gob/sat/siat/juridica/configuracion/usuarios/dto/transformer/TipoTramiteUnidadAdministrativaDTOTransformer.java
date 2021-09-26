/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TipoTramiteUnidadAdministrativaCatalogo;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TipoTramiteUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class TipoTramiteUnidadAdministrativaDTOTransformer extends
        DTOTransformer<TipoTramiteUnidadAdministrativaCatalogo, TipoTramiteUnidadAdministrativaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 8815141577729991714L;

    @Override
    public TipoTramiteUnidadAdministrativaDTO transformarDTO(TipoTramiteUnidadAdministrativaCatalogo source) {

        TipoTramiteUnidadAdministrativaDTO tipoTramiteUniAdmin = new TipoTramiteUnidadAdministrativaDTO();

        tipoTramiteUniAdmin.setIdTipoTramiteUniAdmin(source.getIdTipoTramiteUniAdmin());
        tipoTramiteUniAdmin.setIdeTipoTramiteUniAdmin(source.getIdeTipoTramiteUniAdmin());
        tipoTramiteUniAdmin.setBlnActivo(source.getVigencia().getBlnActivo());
        tipoTramiteUniAdmin.setIdTipoTramite(source.getTipoTramite().getClaveModulo());
        tipoTramiteUniAdmin.setIdUnidadAdministrativa(source.getUnidadAdministrativa().getClave());
        tipoTramiteUniAdmin.setDescModalidad(source.getTipoTramite().getDescripcionModalidad());

        return tipoTramiteUniAdmin;
    }

    public TipoTramiteUnidadAdministrativaCatalogo transformarModel(TipoTramiteUnidadAdministrativaDTO source) {

        TipoTramiteUnidadAdministrativaCatalogo tipoTramiteModel = new TipoTramiteUnidadAdministrativaCatalogo();
        tipoTramiteModel.setIdeTipoTramiteUniAdmin(source.getIdeTipoTramiteUniAdmin());
        tipoTramiteModel.setIdTipoTramiteUniAdmin(source.getIdTipoTramite());
        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(source.getIdTipoTramite());
        tipoTramiteModel.setTipoTramite(tipoTramite);
        UnidadAdministrativa unidad = new UnidadAdministrativa();
        unidad.setClave(source.getIdUnidadAdministrativa());
        tipoTramiteModel.setUnidadAdministrativa(unidad);
        Vigencia vigencia = new Vigencia();
        vigencia.setBlnActivo(source.getBlnActivo());
        tipoTramiteModel.setVigencia(vigencia);

        return tipoTramiteModel;

    }

}
