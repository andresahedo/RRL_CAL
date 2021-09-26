/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConfigurarTramitesUnidadAdministrativaFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@SessionScoped
@ManagedBean(name = "configurarTramitesBusiness")
public class ConfigurarTramitesUnidadAdministrativaBusiness extends BaseCloudBusiness {

    /**
     * 
     */
    private static final long serialVersionUID = 8950348180611809741L;

    @ManagedProperty("#{configurarTramitesUnidadAdministrativaFacade}")
    private ConfigurarTramitesUnidadAdministrativaFacade configurarTramitesFacade;

    public List<UnidadAdministrativaDTO> comboUnidades() {
        return configurarTramitesFacade.comboUnidades();
    }

    public List<TipoTramiteDTO> buscarTramites(String idUnidad) {
        return configurarTramitesFacade.buscarTramites(idUnidad);
    }

    public void actualizarTramitesUnidad(List<TipoTramiteDTO> listaSelect, String idUnidadAdministrativa) {
        configurarTramitesFacade.actualizarTramitesUnidad(listaSelect, idUnidadAdministrativa);
    }

    @Override
    public BaseCloudFacade getCloudFacade() {

        return null;
    }

    /**
     * @return the configurarTramitesFacade
     */
    public ConfigurarTramitesUnidadAdministrativaFacade getConfigurarTramitesFacade() {
        return configurarTramitesFacade;
    }

    /**
     * @param configurarTramitesFacade
     *            the configurarTramitesFacade to set
     */
    public void setConfigurarTramitesFacade(ConfigurarTramitesUnidadAdministrativaFacade configurarTramitesFacade) {
        this.configurarTramitesFacade = configurarTramitesFacade;
    }

}
