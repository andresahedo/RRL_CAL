/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao.impl;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativaRelacionada;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResultadoAdminResponsable;
import mx.gob.sat.siat.juridica.base.dao.impl.BaseJPADao;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dao.BalanceadorDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class BalanceadorDAOImpl extends BaseJPADao implements BalanceadorDAO {

    /**
     * 
     */
    private static final long serialVersionUID = -8630271351978892654L;

    private static final String CAMPO_IDE_TIPO_TRAMITE = "ideTipoTramite";
    private static final String CAMPO_ROL = "rol";
    private static final String CAMPO_ADMINISTRADOR = "administrador";
    private static final String CONDICION_TIPO_TRAMITE =
            " and rolunitt.unidadAdminPK.ideTipoTramite = :ideTipoTramite ";
    private static final String CAMPO_UNIDAD = "unidadAdministrativa";

    /**
     * Metodo para obtener una lista de roles por id
     * 
     * @param tipoTramite
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<Role> getRolesByTipoTramite(String tipoTramite) {

        // TODO cambiar por query que traiga roles de acuerdo al tipo
        // tramite
        return getEntityManager().createQuery("from Role").getResultList();
    }

    /**
     * Metodo para obtener un administrador responsable
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public String getAdministradorResponsableRegistro(String unidadAdministrativa, String tipoTramite) {
        // TODO validar usuarios y roles vigentes en las tablas
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT per FROM PersonaInterna per,  UnidadAdministrativaRelacionada unidadAdmRel, InfRoleUnidadAdmin rolunitt "
                                        + " WHERE unidadAdmRel.clave = :unidadAdministrativa "
                                        + " and rolunitt.unidadAdminPK.claveRol = :rol "
                                        + CONDICION_TIPO_TRAMITE
                                        + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = unidadAdmRel.claveUnidadAdminR "
                                        + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario  "
                                        + " and rolunitt.vigencia.blnActivo = 1 "
                                        + " and not (per.fechaFinVigencia is not null and per.fechaFinVigencia < current_date() ) "
                                        + " and rolunitt.responsable = true  ");

        query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
        query.setParameter(CAMPO_UNIDAD, unidadAdministrativa);
        query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);
        List<Persona> administrador = query.getResultList();
        if (null != administrador && administrador.size() == 1) {
            return (administrador.get(0)).getClaveUsuario();
        }
        else {

            return null;
        }

    }

    /**
     * Metodo para obtener un administrador responsable
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public ResultadoAdminResponsable getAdministradorResponsable(String unidadAdministrativa, String tipoTramite) {
        ResultadoAdminResponsable resultado = new ResultadoAdminResponsable();
        // TODO validar usuarios y roles vigentes en las tablas
        Query query = getEntityManager() .createQuery(
            "SELECT per.rfc, rolunitt.unidadAdminPK.claveUnidadAdministrativa "
                    + "FROM PersonaInterna per,  UnidadAdministrativaRelacionada unidadAdmRel, InfRoleUnidadAdmin rolunitt "
                    + " WHERE unidadAdmRel.clave = :unidadAdministrativa "
                    + " and rolunitt.unidadAdminPK.claveRol = :rol "
                    + CONDICION_TIPO_TRAMITE
                    + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = unidadAdmRel.claveUnidadAdminR "
                    + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario  "
                    + " and rolunitt.vigencia.blnActivo = 1 "
                    + " and not (per.fechaFinVigencia is not null and per.fechaFinVigencia < current_date() ) "
                    + " and rolunitt.responsable = true  ");

        query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
        query.setParameter(CAMPO_UNIDAD, unidadAdministrativa);
        query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);
        List<Object[]> administrador = query.getResultList();

        Object[] result1 = null;
        if (administrador != null && !administrador.isEmpty()) {
            result1 = administrador.get(0);
            resultado.setRfcAdministrador(result1[0].toString());
            resultado.setUnidadAdmin(result1[1].toString());
            resultado.setAdministradorDefault(false);
        } else {
                query = getEntityManager().createNativeQuery(
                    "SELECT RUT.IDUSUARIO, P.VALOR FROM RVCA_ROL_USU_UA_TT RUT, RVCC_PARAMETRO P "
                            + " WHERE RUT.IDUNIADMIN = P.VALOR AND IDROL = :rol  "
                            + " AND RUT.BLNACTIVO = 1 AND P.BLNACTIVO = 1 AND IDTIPOTRAMITE = :ideTipoTramite "
                            + " AND P.IDPARAMETRO = 'UNIADMIN_ATENCION_XDEFECTO' ");

                query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
                query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);

                List<Object[]> listaResult = query.getResultList();
                Object[] result = null;
                if (listaResult != null && !listaResult.isEmpty()) {
                    result = listaResult.get(0);
                    resultado.setRfcAdministrador(result[0].toString());
                    resultado.setUnidadAdmin(result[1].toString());
                    resultado.setAdministradorDefault(true);
                } else {
                    resultado = null;
                }
        }
        return resultado;
    }

    /**
     * Metodo para obtener un administrador responsable sin considerar
     * UnidadAdministrativaRelacionada
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public ResultadoAdminResponsable getAdministradorResponsableSinUAR(String unidadAdministrativa, String tipoTramite) {
        // TODO validar usuarios y roles vigentes en las tablas
        ResultadoAdminResponsable resultado = new ResultadoAdminResponsable();
        Query query =
                getEntityManager()
                        .createQuery(
                                "SELECT per FROM PersonaInterna per,  InfRoleUnidadAdmin rolunitt "
                                        + " WHERE rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa"
                                        + " and rolunitt.unidadAdminPK.claveRol = :rol "
                                        + CONDICION_TIPO_TRAMITE
                                        + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario "
                                        + " and rolunitt.responsable = true"
                                        + " and rolunitt.vigencia.blnActivo=1 and per.fecBaja is null "
                                        + " and (SYSDATE BETWEEN rolunitt.vigencia.fechaInicioVigencia and rolunitt.vigencia.fechaFinVigencia OR rolunitt.vigencia.fechaFinVigencia IS NULL)");

        query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
        query.setParameter(CAMPO_UNIDAD, unidadAdministrativa);
        query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);
        List<Persona> administrador = query.getResultList();
        if (null != administrador && administrador.size() == 1) {
            resultado.setRfcAdministrador((administrador.get(0)).getClaveUsuario());
            resultado.setUnidadAdmin(unidadAdministrativa);
            resultado.setAdministradorDefault(false);
            return resultado;
        }
        else {
            query =
                    getEntityManager().createNativeQuery(
                            "SELECT RUT.IDUSUARIO, P.VALOR FROM RVCA_ROL_USU_UA_TT RUT, RVCC_PARAMETRO P"
                                    + " WHERE RUT.IDUNIADMIN = P.VALOR AND IDROL = :rol "
                                    + " AND RUT.BLNACTIVO = 1 AND P.BLNACTIVO = 1 AND IDTIPOTRAMITE = :ideTipoTramite"
                                    + " AND P.IDPARAMETRO = 'UNIADMIN_ATENCION_XDEFECTO'");

            query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
            query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);
            List<Object[]> listaResult = query.getResultList();
            Object[] result = null;
            if (listaResult != null && !listaResult.isEmpty()) {
                result = listaResult.get(0);
                resultado.setRfcAdministrador(result[0].toString());
                resultado.setUnidadAdmin(result[1].toString());
                resultado.setAdministradorDefault(true);
                return resultado;
            }
            else {
                return null;
            }
        }
    }

    /**
     * Metodo para obtener un administrador responsable de una unidad
     * local de recaudacion
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadora(String unidadAdministrativa,
            String tipoTramite) {
        ResultadoAdminResponsable resultado = new ResultadoAdminResponsable();
        Query query = getEntityManager().createQuery(
            "SELECT per FROM PersonaInterna per, InfRoleUnidadAdmin rolunitt "
                    + " WHERE rolunitt.unidadAdminPK.claveRol = :rol " + CONDICION_TIPO_TRAMITE
                    + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa "
                    + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario "
                    + " and rolunitt.responsable = true and rolunitt.vigencia.blnActivo = 1 ");

        query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
        query.setParameter(CAMPO_UNIDAD, unidadAdministrativa);
        query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);

        List<Persona> administrador = query.getResultList();
        if (null != administrador && administrador.size() == 1) {
            resultado.setRfcAdministrador((administrador.get(0)).getClaveUsuario());
            resultado.setUnidadAdmin(unidadAdministrativa);
            resultado.setAdministradorDefault(false);
        } else {
            query = getEntityManager().createNativeQuery(
                "SELECT RUT.IDUSUARIO, P.VALOR FROM RVCA_ROL_USU_UA_TT RUT, RVCC_PARAMETRO P"
                        + " WHERE RUT.IDUNIADMIN = P.VALOR AND IDROL = :rol "
                        + " AND RUT.BLNACTIVO = 1 AND P.BLNACTIVO = 1 AND IDTIPOTRAMITE = :ideTipoTramite"
                        + " AND P.IDPARAMETRO = 'UNIADMIN_ATENCION_XDEFECTO' ");

            query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
            query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);

            List<Object[]> listaResult = query.getResultList();
            Object[] result = null;
            if (listaResult != null && !listaResult.isEmpty()) {
                result = listaResult.get(0);
                resultado.setRfcAdministrador(result[0].toString());
                resultado.setUnidadAdmin(result[1].toString());
                resultado.setAdministradorDefault(true);
            } else {
                resultado = null;
            }
        }
        return resultado;
    }

    /**
     * Metodo para obtener un administrador responsable de una unidad
     * local de recaudacion
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    @Override
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadoraCAL(String unidadAdministrativa,
            String tipoTramite) {
        // TODO validar usuarios y roles vigentes en las tablas
        ResultadoAdminResponsable resultado = new ResultadoAdminResponsable();
        Query query =
                getEntityManager().createQuery(
                        "SELECT per FROM PersonaInterna per, InfRoleUnidadAdmin rolunitt "
                                + " WHERE rolunitt.unidadAdminPK.claveRol = :rol " + CONDICION_TIPO_TRAMITE
                                + " and rolunitt.unidadAdminPK.claveUnidadAdministrativa = :unidadAdministrativa "
                                + " and per.claveUsuario = rolunitt.unidadAdminPK.claveUsuario "
                                + " and rolunitt.responsable = true and rolunitt.vigencia.blnActivo = 1 ");

        query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
        query.setParameter(CAMPO_UNIDAD, unidadAdministrativa);
        query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);

        List<Persona> administrador = query.getResultList();
        if (null != administrador && administrador.size() == 1) {
            resultado.setRfcAdministrador((administrador.get(0)).getClaveUsuario());
            resultado.setUnidadAdmin(unidadAdministrativa);
            resultado.setAdministradorDefault(false);
            return resultado;
        }
        else {

            query =
                    getEntityManager().createNativeQuery(
                            "SELECT RUT.IDUSUARIO, P.VALOR FROM RVCA_ROL_USU_UA_TT RUT, RVCC_PARAMETRO P"
                                    + " WHERE RUT.IDUNIADMIN = P.VALOR AND IDROL = :rol "
                                    + " AND RUT.BLNACTIVO = 1 AND P.BLNACTIVO = 1 AND IDTIPOTRAMITE = :ideTipoTramite"
                                    + " AND P.IDPARAMETRO = 'UNIADMIN_ATENCION_XDEFECTO' ");

            query.setParameter(CAMPO_IDE_TIPO_TRAMITE, Long.valueOf(tipoTramite));
            query.setParameter(CAMPO_ROL, CAMPO_ADMINISTRADOR);

            List<Object[]> listaResult = query.getResultList();
            Object[] result = null;
            if (listaResult != null && !listaResult.isEmpty()) {
                result = listaResult.get(0);
                resultado.setRfcAdministrador(result[0].toString());
                resultado.setUnidadAdmin(result[1].toString());
                resultado.setAdministradorDefault(true);
                return resultado;
            }
            else {
                return null;
            }

        }
    }

    @Override
    public boolean existeConfiguracionUnidadAdmin(String clave) {
        Query query =
                getEntityManager().createQuery(
                        "SELECT unidadAdmRel from UnidadAdministrativaRelacionada unidadAdmRel"
                                + " WHERE unidadAdmRel.clave = :unidadAdministrativa ");
        query.setParameter(CAMPO_UNIDAD, clave);
        List<UnidadAdministrativaRelacionada> unidades = query.getResultList();
        return (unidades != null && !unidades.isEmpty());
    }

    @Override
    public String getUnidadPorTipoRol(String tipoTramite, String tipoRolContribuyente) {
        String parametro = (tipoRolContribuyente.equals(ProcesosConstantes.GRAN_CONTRIBUYENTE)? ProcesosConstantes.PARAM_GRANDES_CONTRIBUYENTES : ProcesosConstantes.PARAM_HIDROCARBUROS);
        String uniadmin;
        Query query =
                getEntityManager().createNativeQuery("SELECT TU.IDUNIADMIN FROM RVCA_TIPOTRAMITE_UNIADMIN TU,RVCC_PARAMETRO P " +
        "WHERE TU.IDTIPOTRAMITE =:tipoTramite "+
        "AND p.VALOR = TU.IDUNIADMIN "+
        "AND P.IDPARAMETRO =:parametro AND TU.BLNACTIVO=1");
        query.setParameter("tipoTramite", tipoTramite);
        query.setParameter("parametro", parametro);
        try{
            uniadmin =(String) query.getSingleResult();
        }catch (NoResultException e) {
            uniadmin = null;
        }
        return uniadmin;
    }
}
