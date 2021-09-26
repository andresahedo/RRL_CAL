package mx.gob.sat.siat.juridica.base.service.impl;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;
import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.service.BuscarPersonaSolicitanteService;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.buzon.service.BuzonTributarioService;
import mx.gob.sat.siat.juridica.idc.service.IdcJuridicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarPersonaSolicitanteServiceImpl extends BaseBusinessServices implements
        BuscarPersonaSolicitanteService {

    /**
     * 
     */
    private static final long serialVersionUID = 16405650359763469L;

    @Autowired
    private transient IdcJuridicoService idcJuridicoService;

    @Autowired
    private transient CatalogoDDao catalogoDDao;

    @Autowired
    private transient ColoniaDao coloniaDao;

    @Autowired
    private transient DelegacionMunicipioDao delegacionMunicipioDao;

    @Autowired
    private transient PaisDao paisDao;

    @Autowired
    private transient LocalidadDao localidadDao;

    @Autowired
    private EntidadFederativaDao entidadDao;

    @Autowired
    private transient BuzonTributarioService buzonTributarioService;

    @Override
    public Solicitante buscarPersonaSolicitanteIdc(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        Solicitante sol = idcJuridicoService.buscarContribuyenteIdc(rfc, new Solicitante());

        try {
            sol.setCorreoElectronico(buzonTributarioService.obtenerCorreos(rfc));
            sol.setTieneCorreo(true);
            sol.setBlnAmparado(true);
        }
        catch (BuzonNoDisponibleException e) {
            sol.setTieneCorreo(false);
        }
        catch (CorreoNoRegistradoException e) {
            sol.setTieneCorreo(false);
        }
        return sol;
    }

    @Override
    public List<CatalogoD> comboTiposPersona() {

        return catalogoDDao.comboTipoPersona();
    }

    @Override
    public List<Colonia> obtenerColoniasPorCp(String cp) {

        List<Colonia> colonias = coloniaDao.obtenerColoniaPorCodigoPostal(cp);
        if (colonias != null && !colonias.isEmpty()) {
            for (Colonia col : colonias) {
                col.getDelegacionMunicipio().getNombre();
                col.getDelegacionMunicipio().getEntidadFederativa().getNombre();

            }
        }
        return colonias;
    }

    @Override
    public List<Colonia> obtenerColonias() {

        return coloniaDao.obtenerColonias();
    }

    @Override
    public List<DelegacionMunicipio> obtenerDelegacionesMunicipios() {

        return delegacionMunicipioDao.obtenerDelegacionesMunicipios();
    }

    @Override
    public List<Colonia> buscarColoniaPorClaveDelegacion(String claveDelegacion) {
        List<Colonia> colonias = coloniaDao.buscarColoniaPorClaveDelegacion(claveDelegacion);
        if (colonias != null) {

            for (Colonia col : colonias) {
                col.getDelegacionMunicipio();
                col.getDelegacionMunicipio().getNombre();
                col.getCp();

            }
        }
        return colonias;
    }

    @Override
    public Pais buscarPaisMexico() {

        return paisDao.buscarPaisMexico();
    }

    @Override
    public List<Localidad> obtenerLocalidadPorClaveDelegacion(String claveDelegacion) {
        List<Localidad> localidades = localidadDao.obtenerLocalidadPorClaveDelegacion(claveDelegacion);
        for (Localidad loc : localidades) {

            loc.getDelegacionMunicipio();
            loc.getDelegacionMunicipio().getNombre();
            loc.getDelegacionMunicipio().getEntidadFederativa().getNombre();

        }
        return localidades;
    }

    @Override
    public List<EntidadFederativa> obtenerEstados() {
        for (EntidadFederativa est : entidadDao.obtenerEstados()) {
            est.getPais();
            est.getPais().getNombre();
        }
        return entidadDao.obtenerEstados();
    }

    @Override
    public List<DelegacionMunicipio> obtenerDelegacionMunicipioPorCveEstado(String claveEstado) {
        List<DelegacionMunicipio> delegaciones =
                delegacionMunicipioDao.obtenerDelegacionMunicipioPorCveEstado(claveEstado);
        if (delegaciones != null && !delegaciones.isEmpty()) {
            for (DelegacionMunicipio del : delegaciones) {
                del.getEntidadFederativa();
                del.getEntidadFederativa().getNombre();

                for (Colonia col : del.getColonias()) {
                    col.getDelegacionMunicipio().getNombre();
                }
            }
        }
        return delegaciones;
    }

    @Override
    public List<Colonia> buscarColoniaPorCveLocalidad(String claveLocalidad) {
        List<Colonia> colonias = coloniaDao.buscarColoniaPorCveLocalidad(claveLocalidad);
        if (colonias != null && !colonias.isEmpty()) {
            for (Colonia col : colonias) {
                col.getDelegacionMunicipio().getNombre();
                col.getLocalidad().getNombre();

            }
        }
        return colonias;
    }

    @Override
    public List<Localidad> buscarLocalidadPorCP(String cp) {
        List<Localidad> localidades = localidadDao.buscarLocalidadPorCP(cp);

        return localidades;
    }

}
