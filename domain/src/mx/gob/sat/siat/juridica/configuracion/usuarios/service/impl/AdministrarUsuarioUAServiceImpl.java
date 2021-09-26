package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.InfRoleUnidadAdminDao;
import mx.gob.sat.siat.juridica.base.dao.InformacionUsuarioDao;
import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.RolesDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.UsuarioRolUnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.AdministrarUsuarioUAService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.util.exception.TreasPendientesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministrarUsuarioUAServiceImpl extends BaseSerializableBusinessServices implements
        AdministrarUsuarioUAService {

    private static final long serialVersionUID = 5335889097461374613L;


    @Autowired
    private InfRoleUnidadAdminDao infRoleUnidadAdminDao;

    @Autowired
    private TipoTramiteDAO tipoTramiteDao;

    @Autowired
    private InformacionUsuarioDao informacionUsuarioDao;

    @Autowired
    private UsuarioRolUnidadAdministrativaDao usuarioRolUnidadAdministrativaDao;

    @Autowired
    private RolesDao rolesDao;

    @Autowired
    TareaServices tareaServices;
    

    private List<String> tramitesResponsableExistente = new ArrayList<String>();

    @Override
    public List<String> actualizarTramitesAsignadosFuncionario(List<TipoTramite> modalidadesSelect,
            UnidadAdministrativa unidad, InformacionUsuario funcionario) throws TreasPendientesException {

        List<InfRoleUnidadAdmin> tramitesAsignados = new ArrayList<InfRoleUnidadAdmin>();
        setTramitesResponsableExistente(new ArrayList<String>());

        for (TipoTramite modalidadSel : modalidadesSelect) {
            InfRoleUnidadAdmin tramiteInfoRol = new InfRoleUnidadAdmin();
            tramiteInfoRol.setResponsable(modalidadSel.getBlnResponsable() != null ? modalidadSel.getBlnResponsable()
                    .intValue() == 1 : false);
            Vigencia vigencia = new Vigencia();
            vigencia.setBlnActivo(modalidadSel.getAsignado() != null && modalidadSel.getAsignado() ? 1 : 0);
            tramiteInfoRol.setVigencia(vigencia);
            InfRoleUnidadAdminPK unidadAdminPK = new InfRoleUnidadAdminPK();
            unidadAdminPK.setClaveRol(funcionario.getInformacionUsuarioPK().getIdRol());
            unidadAdminPK.setClaveUnidadAdministrativa(unidad.getClave());
            unidadAdminPK.setClaveUsuario(funcionario.getInformacionUsuarioPK().getIdUsuario());
            unidadAdminPK.setIdeTipoTramite(Long.valueOf(modalidadSel.getIdTipoTramite().toString()));

            tramiteInfoRol.setUnidadAdminPK(unidadAdminPK);
            tramitesAsignados.add(tramiteInfoRol);

            if (tramiteInfoRol.isResponsable()) {
                if (getInfRoleUnidadAdminDao().validarResponsableDuplicado(unidadAdminPK)) {
                    getTramitesResponsableExistente().add(
                            getTipoTramiteDao().obtenerDescripcionModalidad(modalidadSel.getIdTipoTramite()));
                }
            }

        }

        if (getTramitesResponsableExistente().isEmpty()) {
            infRoleUnidadAdminDao.guardarModalidadesAsignadosUsuarios(tramitesAsignados);
            return null;
        }
        else {
            return getTramitesResponsableExistente();
        }
    }

 
    @Override
    public List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol) {

        return infRoleUnidadAdminDao.obtenerPermisosPorFuncionario(rfc, unidadAdmin, rol);
    }

    @Override
    public List<TipoTramite> buscarTramites() {
        return tipoTramiteDao.buscarTramites();
    }

    @Override
    public List<InformacionUsuario> obtenerRolesFuncionario(String idUsuario) {

        return informacionUsuarioDao.consultarRolesFuncionario(idUsuario);
    }

    @Override
    public Role obtenerRolePorIdRol(String idRol) {

        return rolesDao.obtenerRolePorIdRol(idRol);
    }

    @Override
    public List<UsuarioRolUnidadAdministrativa> obtenerPermisosDeUsuarioPorUnidad(String idUniAdmin, String idUsuario) {
        return getUsuarioRolUnidadAdministrativaDao().obtenerPermisosDeUsuarioPorUnidad(idUniAdmin, idUsuario);
    }

    public InfRoleUnidadAdminDao getInfRoleUnidadAdminDao() {
        return infRoleUnidadAdminDao;
    }

    public void setInfRoleUnidadAdminDao(InfRoleUnidadAdminDao infRoleUnidadAdminDao) {
        this.infRoleUnidadAdminDao = infRoleUnidadAdminDao;
    }

    public List<String> getTramitesResponsableExistente() {
        return tramitesResponsableExistente;
    }

    public void setTramitesResponsableExistente(List<String> tramitesResponsableExistente) {
        this.tramitesResponsableExistente = tramitesResponsableExistente;
    }

    public TipoTramiteDAO getTipoTramiteDao() {
        return tipoTramiteDao;
    }

    public void setTipoTramiteDao(TipoTramiteDAO tipoTramiteDao) {
        this.tipoTramiteDao = tipoTramiteDao;
    }

    public UsuarioRolUnidadAdministrativaDao getUsuarioRolUnidadAdministrativaDao() {
        return usuarioRolUnidadAdministrativaDao;
    }

    public void
            setUsuarioRolUnidadAdministrativaDao(UsuarioRolUnidadAdministrativaDao usuarioRolUnidadAdministrativaDao) {
        this.usuarioRolUnidadAdministrativaDao = usuarioRolUnidadAdministrativaDao;
    }

}
