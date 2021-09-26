/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class PersonaDaoImpl extends BaseJPADao implements PersonaDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7480008152608280013L;

    /**
     * Atributo protegido "LOGGER" tipo estatico final Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstants.DOMAIN_REPOSITORY_LOGGER);
    @Value("${parametro.rfc}")
    private String parametroRfc;
    private static final String ROLUNIT_VIG_PER_FEC = " and rolunitt.vigencia.blnActivo=1 and per.fecBaja is null ";
    private static final String IDETIPOTRAMITE = "ideTipoTramite";
    private static final String ROLUNIT_UNIADMIN_CLAVE = " and rolunitt.unidadAdminPK.claveRol = :rol ";
    private static final String UNIDAD_ADMINISTRATIVA = "unidadAdministrativa";
    private static final String SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN =
            " and (SYSDATE BETWEEN rolunitt.vigencia.fechaInicioVigencia and rolunitt.vigencia.fechaFinVigencia OR rolunitt.vigencia.fechaFinVigencia IS NULL)";
    private static final String ROLUNIT_UNIADMIN_IDETIPOTRAMITE =
            " and rolunitt.unidadAdminPK.ideTipoTramite = :ideTipoTramite ";
    private static final String ROL = "rol";

    /**
     * Metodo para buscar una persona por id
     */
    public Persona obtenerPersonaPorId(Long idPersona) {
        Query query =
                getEntityManager().createQuery(
                        "select p from Persona p where p.idPersona = :idPersona and p.fecBaja is null ", Persona.class);
        query.setParameter("idPersona", idPersona);
        return (Persona) query.getSingleResult();
    }

    /**
     * Metodo para buscar una persona por RFC
     */
    @Override
    public Persona obtenerPersonaPorRFC(String rfc) {
        Query query =
                getEntityManager().createQuery("select p from Persona p where p.rfc = :rfc and p.fecBaja is null");
        query.setParameter(this.parametroRfc, rfc);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (Persona) resultado.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaPorRFCUsuarios(String rfc) {
        Query query = getEntityManager().createQuery("select p from Persona p where p.rfc = :rfc");
        query.setParameter(this.parametroRfc, rfc);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (Persona) resultado.get(0);
        }
        else {
            return null;
        }
    }

    public Persona obtenerPersonaPorRFCCorto(String rfc) {
        Query query =
                getEntityManager().createQuery(
                        "select p from PersonaInterna p where p.rfcCorto = :rfcCorto and p.fecBaja is null");
        query.setParameter("rfcCorto", rfc);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (Persona) resultado.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Metodo para obtener una lista de usuarios
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Persona> obtenerUsuarios() {
        Query query =
                getEntityManager()
                        .createQuery(
                                "select p from Persona p, mx.gob.sat.siat.juridica.base.dao.domain.model.UserCredentials u where p.rfc = u.userName and p.fecBaja is null");
        List<Persona> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return resultado;
        }
        else {
            return null;
        }
    }

    /**
     * Metodo para buscar una lista de personas por Rol
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Persona> buscarPersonaByRol(Tramite tramite, String rol) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT per FROM PersonaInterna per,  UnidadAdministrativaRelacionada unidadAdmRel, InfRoleUnidadAdmin rolunitt "
                                        + " WHERE unidadAdmRel.clave = :unidadAdministrativa"
                                        + ROLUNIT_UNIADMIN_CLAVE
                                        + ROLUNIT_UNIADMIN_IDETIPOTRAMITE
                                        + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = unidadAdmRel.claveUnidadAdminR and per.fecBaja is null "
                                        + ROLUNIT_VIG_PER_FEC
                                        + SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN
                                        + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario order by per.nombre ");

        query.setParameter(IDETIPOTRAMITE, tramite.getSolicitud().getClaveModalidad());
        query.setParameter(UNIDAD_ADMINISTRATIVA,
                ((SolicitudDatosGenerales) tramite.getSolicitud()).getUnidadAdminBalanceo());
        query.setParameter(ROL, rol);
        return query.getResultList();

    }

    /**
     * Metodo para buscar una lista de personas por Rol
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<PersonaInterna>
            buscarPersonaRRLByRolUnidad(Long ideTipoTramite, String unidadAdministrativa, String rol) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT per FROM PersonaInterna per,  UnidadAdministrativaRelacionada unidadAdmRel, InfRoleUnidadAdmin rolunitt "
                                        + " WHERE unidadAdmRel.clave = :unidadAdministrativa"
                                        + ROLUNIT_UNIADMIN_CLAVE
                                        + ROLUNIT_UNIADMIN_IDETIPOTRAMITE
                                        + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = unidadAdmRel.claveUnidadAdminR and per.fecBaja is null "
                                        + ROLUNIT_VIG_PER_FEC
                                        + SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN
                                        + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario order by per.nombre ");

        query.setParameter(IDETIPOTRAMITE, ideTipoTramite);
        query.setParameter(UNIDAD_ADMINISTRATIVA, unidadAdministrativa);
        query.setParameter(ROL, rol);
        return query.getResultList();

    }

    /**
     * Metodo para buscar una lista de personas por Rol, sin
     * considerar la tabla RVCC_UNIADMINREL que hasta se usa para el
     * tramite 1
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Persona> buscarPersonaSinUARByRol(Tramite tramite, String rol) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT per FROM PersonaInterna per,  InfRoleUnidadAdmin rolunitt "
                                + " WHERE rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa"
                                + ROLUNIT_UNIADMIN_CLAVE + ROLUNIT_UNIADMIN_IDETIPOTRAMITE + ROLUNIT_VIG_PER_FEC
                                + SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN
                                + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario order by per.nombre");
        query.setParameter(IDETIPOTRAMITE, tramite.getSolicitud().getClaveModalidad());
        query.setParameter(UNIDAD_ADMINISTRATIVA,
                ((SolicitudDatosGenerales) tramite.getSolicitud()).getUnidadAdminBalanceo());
        query.setParameter(ROL, rol);
        return query.getResultList();

    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<PersonaInterna>
            buscarPersonaCALByRolUnidad(Long ideTipoTramite, String unidadAdministrativa, String rol) {

        Query query =
                getEntityManager().createQuery(
                        "SELECT per FROM PersonaInterna per,  InfRoleUnidadAdmin rolunitt "
                                + " WHERE rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa"
                                + ROLUNIT_UNIADMIN_CLAVE + ROLUNIT_UNIADMIN_IDETIPOTRAMITE + ROLUNIT_VIG_PER_FEC
                                + SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN
                                + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario order by per.nombre");
        query.setParameter(IDETIPOTRAMITE, ideTipoTramite);
        query.setParameter(UNIDAD_ADMINISTRATIVA, unidadAdministrativa);
        query.setParameter(ROL, rol);
        return query.getResultList();

    }

    /**
     * Metodo para obtener una lista de unidades Administrativas
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT distinct rolunitt.unidadAdminPK.claveUnidadAdministrativa FROM InfRoleUnidadAdmin rolunitt "
                                + " WHERE rolunitt.unidadAdminPK.claveUsuario = :claveUsuario "
                                + " AND  rolunitt.unidadAdminPK.claveRol = :claveRol ");
        query.setParameter("claveUsuario", rfc);
        query.setParameter("claveRol", cveRol);
        return query.getResultList();

    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol, String tipoTamite) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT rolunitt.unidadAdminPK.claveUnidadAdministrativa FROM InfRoleUnidadAdmin rolunitt "
                                + " WHERE rolunitt.unidadAdminPK.claveUsuario = :claveUsuario "
                                + " AND rolunitt.unidadAdminPK.claveRol = :claveRol "
                                + " AND rolunitt.unidadAdminPK.ideTipoTramite = :tipoTramite ");
        query.setParameter("claveUsuario", rfc);
        query.setParameter("claveRol", cveRol);
        query.setParameter("tipoTramite", new Long(tipoTamite));
        return query.getResultList();

    }

    /**
     * Metodo para obtener el abogado que genero la resolucion
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public String obtenerAbogadoRequerimiento(Long idRequerimiento) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT req.idUsuario FROM Requerimiento req "
                                + " WHERE req.idRequerimiento = :idRequerimiento ");
        query.setParameter("idRequerimiento", idRequerimiento);
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (resultado.get(0)).toString();
        }
        else {
            return null;
        }

    }

    /**
     * Metodo para obtener el domicilio de una solicitud
     */
    public DomicilioSolicitud obtenerDomicilioPorId(Long idDomicilio) {
        Query query = getEntityManager().createNamedQuery("Domicilio.buscarPorId");
        query.setParameter("idDomicilio", idDomicilio);
        return (DomicilioSolicitud) query.getSingleResult();
    }

    /**
     * Metodo para buscar una persona solicitante
     */
    @Override
    public PersonaSolicitud buscarPersonaSolicitante(Long idSolicitud) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT ps from Solicitante ps WHERE ps.solicitud.idSolicitud = :idSolicitud");
        query.setParameter("idSolicitud", idSolicitud);
        return (PersonaSolicitud) query.getSingleResult();
    }

    /**
     * Metodo para buscar una persona representante legal por id de
     * solicitud
     * 
     * @param idSolicitud
     * @return RepresentanteLegal
     */
    @Override
    public RepresentanteLegal buscarPersonaRepresentanteLegal(Long idSolicitud) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT rl from RepresentanteLegal rl WHERE rl.solicitud.idSolicitud = :idSolicitud");
        query.setParameter("idSolicitud", idSolicitud);

        RepresentanteLegal rl = null;

        try {
            rl = (RepresentanteLegal) query.getSingleResult();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return rl;
    }

    public Persona insertarPersona(Persona persona) {
        if (persona != null) {
            getEntityManager().persist(persona);
        }
        return persona;
    }

    public Persona obtenerPersona(String rfc, String tipoPersona) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "select p from Persona p where p.rfc = :rfc and p.ideTipoPersona = :tipoPersona and p.fecBaja is null");
        query.setParameter(this.parametroRfc, rfc);
        query.setParameter("tipoPersona", tipoPersona);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (Persona) resultado.get(0);
        }
        else {
            return null;
        }
    }

    public String obtenerRfcCorto(String rfc) {
        String rfcCorto = "";
        Query query = getEntityManager().createQuery("select p from PersonaInterna p where p.rfc = :rfc");
        query.setParameter(this.parametroRfc, rfc);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            PersonaInterna personaInterna = (PersonaInterna) resultado.get(0);
            rfcCorto = personaInterna.getRfcCorto();
        }
        if (rfcCorto.equals("")) {
            return null;
        }
        return rfcCorto;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public String obtenerRfcCortoLike(String rfc) {
        String rfcCorto = "";
        Query query = getEntityManager().createQuery("select p from PersonaInterna p where p.rfc LIKE :rfc");
        query.setParameter(this.parametroRfc, rfc.toUpperCase() + "%");
        List<PersonaInterna> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            PersonaInterna personaInterna = (PersonaInterna) resultado.get(0);
            rfcCorto = personaInterna.getRfcCorto();
        }
        if (rfcCorto.equals("")) {
            return null;
        }
        return rfcCorto;
    }

    public void modificarPersona(Persona persona) {
        getEntityManager().merge(persona);
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<Persona> buscarPersonasByRolReasignador(String idTipoTramite, String rol, String cveUniAdmin,
            PersonaInterna empleado) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT per FROM PersonaInterna per, InfRoleUnidadAdmin rolunitt WHERE "
                                + " rolunitt.unidadAdminPK.claveRol = :rol " + ROLUNIT_UNIADMIN_IDETIPOTRAMITE
                                + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa "
                                + " and per.fecBaja is null " + " and rolunitt.vigencia.blnActivo=1 "
                                + SYS_ROLUNIT_VIG_FEC_INI_FEC_FIN
                                + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario"
                                + " and per.numeroEmpleado <> :numEmpleado" + " order by per.nombre");
        query.setParameter(IDETIPOTRAMITE, Long.parseLong(idTipoTramite));
        query.setParameter(UNIDAD_ADMINISTRATIVA, cveUniAdmin);
        query.setParameter(ROL, rol);
        query.setParameter("numEmpleado", empleado.getNumeroEmpleado());
        return query.getResultList();

    }

    public PersonaInterna obtenerPersonaInternaPorRFC(String rfc) {
        Query query =
                getEntityManager().createQuery(
                        "select p from PersonaInterna p where p.rfc = :rfc and p.fecBaja is null");
        query.setParameter(this.parametroRfc, rfc);
        @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
        List<Object> resultado = query.getResultList();
        if (resultado != null && !resultado.isEmpty()) {
            return (PersonaInterna) resultado.get(0);
        }
        else {
            return null;
        }
    }
}
