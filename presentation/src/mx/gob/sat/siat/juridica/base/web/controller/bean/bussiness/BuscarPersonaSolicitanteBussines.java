package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.api.BuscarPersonaSolicitanteFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@NoneScoped
@ManagedBean(name = "buscarPersonaBussines")
public class BuscarPersonaSolicitanteBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 8104532037679851701L;

    @ManagedProperty("#{buscarPersonaSolicitanteFacade}")
    private transient BuscarPersonaSolicitanteFacade buscarPersonaSolicitanteFacade;

    public PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        return buscarPersonaSolicitanteFacade.buscarPersonaSolicitante(rfc);
    }

    public List<CatalogoDTO> comboTiposPersona() {

        return buscarPersonaSolicitanteFacade.comboTipoPersona();
    }

    public PaisDTO obtenerPais() {
        return buscarPersonaSolicitanteFacade.obtenerPaises();
    }

    public List<EntidadFederativaDTO> obtenerEstados() {
        return buscarPersonaSolicitanteFacade.obtenerEstados();
    }

    public List<ColoniaDTO> buscarColoniasPorCp(String cp) {
        return buscarPersonaSolicitanteFacade.obtenerColoniasPorCp(cp);
    }

    public List<DelegacionMunicipioDTO> obtenerDelegMunicipioPorCveEstado(String cveEstado) {
        return buscarPersonaSolicitanteFacade.obtenerDelegacionMunicipioPorCveEstado(cveEstado);
    }

    public List<ColoniaDTO> buscarColoniaPorClaveDelegacion(String claveDelegacion) {

        return buscarPersonaSolicitanteFacade.buscarColoniaPorClaveDelegacion(claveDelegacion);
    }

    public List<LocalidadDTO> buscarLocalidadesPorCveMunicipio(String claveMunicipio) {
        return buscarPersonaSolicitanteFacade.buscarLocalidadPorClaveMunicipio(claveMunicipio);
    }

    public List<ColoniaDTO> buscarColoniaPorCveLocalidad(String claveLocalidad) {
        return buscarPersonaSolicitanteFacade.buscarColoniaPorCveLocalidad(claveLocalidad);
    }

    public List<LocalidadDTO> buscarLocalidadPorCP(String cp) {
        return buscarPersonaSolicitanteFacade.buscarLocalidadPorCP(cp);
    }

    /**
     * @return the buscarPersonaSolicitanteFacade
     */
    public BuscarPersonaSolicitanteFacade getBuscarPersonaSolicitanteFacade() {
        return buscarPersonaSolicitanteFacade;
    }

    /**
     * @param buscarPersonaSolicitanteFacade
     *            the buscarPersonaSolicitanteFacade to set
     */
    public void setBuscarPersonaSolicitanteFacade(BuscarPersonaSolicitanteFacade buscarPersonaSolicitanteFacade) {
        this.buscarPersonaSolicitanteFacade = buscarPersonaSolicitanteFacade;
    }

}
