/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.bpm.constante.Participantes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
public interface BalanceadorServices extends Serializable {

    /**
     * Metodo para obtener una lista de roles
     * 
     * @param tipoTramite
     * @return Lista de roles
     */
    List<Role> obtenerRoles(TipoTramite tipoTramite);

    /**
     * Metodo para obtener un usuario
     * 
     * @param solicitud
     *            a fijar
     * @param tramite
     *            a fijar
     * @param participante
     *            a fijar
     * @param estadoTramite
     *            a fijar
     * @return String
     */
            String
            obtenerUsuario(Solicitud solicitud, Tramite tramite, Participantes participante, EstadoTramite estadoTramite);

    /**
     * Metodo para obtener un usuario de carga menor
     * 
     * @param listaValida
     * @return usuarioLibre
     */
    String obtenerUsuarioMenorCarga(List<String> listaValida);

    /**
     * Metodo para saber si existe configuracion de unidad
     * administrativa relacionada
     * 
     * @param unidadAdmin
     */
    boolean existeConfiguracionUnidadAdmin(String clave);

    /**
     * Metodo para obtener un Autorizador
     * 
     * @param solicitud
     * @return String
     */
    ResultadoAdminResponsable obtenerAutorizadorResponsable(SolicitudDatosGenerales solicitud);

    ResultadoAdminResponsable
            obtenerAutorizadorResponsableRemision(SolicitudDatosGenerales solicitud, Remision remision);

    /**
     * Metodo para obtener un Autorizador sin considerar
     * UnidadAdministrativaRelacionada
     * 
     * @param solicitud
     * @return String
     */
    ResultadoAdminResponsable obtenerAutorizadorResponsableSinUAR(SolicitudDatosGenerales solicitud, Remision remision);

    /**
     * Metodo para obtener un usuario
     * 
     * @param parametros
     *            a fijar
     * @return Mapa de participantes
     */
    Map<Participantes, String> obtenerUsuario(ParametrosTareaUsuario parametros);

    /**
     * Metodo para obtener un usuario
     * 
     * @param parametros
     *            a fijar
     * @param isValidaMismoUsuaio
     *            a fijar
     * @param cveUsuario
     *            a fijar
     * @return Mapa de participantes a fijar
     */
    Map<Participantes, String> obtenerUsuario(ParametrosTareaUsuario parametros, boolean isValidaMismoUsuaio,
            String cveUsuario);

    /**
     * Metodo para obtener un Autorizador sin consultar las unidades
     * admnistrativas relacionadas
     * 
     * @param solicitud
     * @return String
     */
    ResultadoAdminResponsable obtenerAutorizadorResponsableUnidadLocalRecaudadora(SolicitudDatosGenerales solicitud);

    /**
     * Metodo para obtener un Autorizador sin consultar las unidades
     * admnistrativas relacionadas
     * 
     * @param solicitud
     * @return String
     */
    String obtenerAutorizadorResponsableUnidadLocalRecaudadora(SolicitudDatosGenerales solicitud,
            UnidadAdministrativa unidad);

    String obtenerAutorizadorResponsableRegistro(SolicitudDatosGenerales solicitud);

    ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadoraCAL(SolicitudDatosGenerales solicitud);

    String obtenerAutorizadorResponsableUnidadLocalRecaudadoraCAL(SolicitudDatosGenerales solicitud,
            UnidadAdministrativa unidadAdmin);

}
