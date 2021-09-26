/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.service.BalanceadorServices;
import mx.gob.sat.siat.juridica.bpm.constante.Participantes;
import mx.gob.sat.siat.juridica.rrl.dao.BalanceadorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class BalanceadorServicesImpl extends BaseSerializableBusinessServices implements BalanceadorServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "balanceadorDAO" tipo BalanceadorDAO
     */
    @Autowired
    private BalanceadorDAO balanceadorDAO;

    /**
     * Metodo para obtener una lista de roles
     * 
     * @param tipoTramite
     * @return null
     */
    @Override
    public List<Role> obtenerRoles(TipoTramite tipoTramite) {
        return null;
    }

    /**
     * Metodo para obtener usuario
     * 
     * @param solicitud
     *            a fijar
     * @param tramite
     *            a fijar
     * @param participante
     *            a fijar
     * @param estadoTramite
     *            a fijar
     * @return null
     */
    @Override
    public String obtenerUsuario(Solicitud solicitud, Tramite tramite, Participantes participante,
            EstadoTramite estadoTramite) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Metodo para obtener un usuario de carga menor
     * 
     * @param listaValida
     * @return usuarioLibre
     */
    @Override
    public String obtenerUsuarioMenorCarga(List<String> listaValida) {
        String usuarioLibre = "";

        if (listaValida == null || listaValida.isEmpty()) {
            getLogger().debug("Lista vacia me regreso con usuario {}", usuarioLibre);
            return usuarioLibre;
        }

        usuarioLibre = listaValida.get(0);

        return usuarioLibre;
    }

    /**
     * Metodo para obtener un usuario
     * 
     * @param parametros
     *            a fijar
     * @return null
     */
    @Override
    public Map<Participantes, String> obtenerUsuario(ParametrosTareaUsuario parametros) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Metodo para obtener un usuario
     * 
     * @param parametros
     *            a fijar
     * @param isValidaMismoUsuaio
     *            a fijar
     * @param cveUsuario
     *            a fijar
     * @return null
     */
    @Override
    public Map<Participantes, String> obtenerUsuario(ParametrosTareaUsuario parametros, boolean isValidaMismoUsuaio,
            String cveUsuario) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Metodo para obtener un autorizador responsable
     * 
     * @param solicitud
     */
    @Override
    public String obtenerAutorizadorResponsableRegistro(SolicitudDatosGenerales solicitud) {
        return balanceadorDAO.getAdministradorResponsableRegistro(solicitud.getUnidadAdminBalanceo(),
                solicitud.getDiscriminatorValue());
    }

    /**
     * Metodo para obtener un Autorizador sin considerar
     * UnidadAdministrativaRelacionada
     * 
     * @param solicitud
     * @return String
     */
    @Override
    public ResultadoAdminResponsable obtenerAutorizadorResponsableSinUAR(SolicitudDatosGenerales solicitud,
            Remision remision) {
        return balanceadorDAO.getAdministradorResponsableSinUAR(remision.getUnidadAdminNueva().getClave(),
                solicitud.getDiscriminatorValue());
    }

    /**
     * Metodo para obtener un autorizador responsable sin consultar
     * las unidades relacionadas
     * 
     * @param solicitud
     * @param unidadAdministrativa
     */
    @Override
    public String obtenerAutorizadorResponsableUnidadLocalRecaudadora(SolicitudDatosGenerales solicitud,
            UnidadAdministrativa unidadAdmin) {
        ResultadoAdminResponsable result =
                balanceadorDAO.getAdministradorResponsableUnidadLocalRecaudadora(unidadAdmin.getClave(),
                        solicitud.getDiscriminatorValue());
        if (result != null) {
            return result.getRfcAdministrador();
        }
        else {
            return null;
        }
    }

    @Override
    public String obtenerAutorizadorResponsableUnidadLocalRecaudadoraCAL(SolicitudDatosGenerales solicitud,
            UnidadAdministrativa unidadAdmin) {
        ResultadoAdminResponsable result =
                balanceadorDAO.getAdministradorResponsableUnidadLocalRecaudadoraCAL(unidadAdmin.getClave(),
                        solicitud.getDiscriminatorValue());
        if (result != null) {
            return result.getRfcAdministrador();
        }
        else {
            return null;
        }
    }

    @Override
    public boolean existeConfiguracionUnidadAdmin(String clave) {
        return balanceadorDAO.existeConfiguracionUnidadAdmin(clave);
    }

    @Override
    public ResultadoAdminResponsable obtenerAutorizadorResponsableUnidadLocalRecaudadora(
            SolicitudDatosGenerales solicitud) {
        return balanceadorDAO.getAdministradorResponsableUnidadLocalRecaudadora(solicitud.getUnidadAdminBalanceo(),
                solicitud.getDiscriminatorValue());

    }

    @Override
    public ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadoraCAL(
            SolicitudDatosGenerales solicitud) {
        return balanceadorDAO.getAdministradorResponsableUnidadLocalRecaudadoraCAL(solicitud.getUnidadAdminBalanceo(),
                solicitud.getDiscriminatorValue());

    }

    /**
     * Metodo para obtener un autorizador responsable
     * 
     * @param solicitud
     */
    @Override
    public ResultadoAdminResponsable obtenerAutorizadorResponsable(SolicitudDatosGenerales solicitud) {
        return balanceadorDAO.getAdministradorResponsable(solicitud.getUnidadAdminBalanceo(),
                solicitud.getDiscriminatorValue());
    }

    /**
     * Metodo para obtener un autorizador responsable
     * 
     * @param solicitud
     */
    @Override
    public ResultadoAdminResponsable obtenerAutorizadorResponsableRemision(SolicitudDatosGenerales solicitud,
            Remision remision) {
        return balanceadorDAO.getAdministradorResponsable(remision.getUnidadAdminNueva().getClave(),
                solicitud.getDiscriminatorValue());
    }
}
