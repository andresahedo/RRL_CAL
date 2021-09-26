/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * DAO para atender las solicitudes a BD de las enumeraciones.
 * 
 * @author Softtek
 * 
 */
@Repository
public class EnumeracionDaoImpl extends BaseJPADao implements EnumeracionDao {

    /**
     * 
     */
    private static final long serialVersionUID = -7915099476643454299L;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento por id de
     * enumeraci&oacute;n.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<EnumeracionTr> obtenerEnumeracionPorId(String ideEnumeracionH) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM EnumeracionTr e");
        sb.append(" WHERE e.catEnumeracionD.catEnumeracionH.cveEnumeracionH = :ideEnumeracionH ");
        sb.append(" AND e.vigencia.blnActivo = 1 AND e.catEnumeracionD.vigencia.blnActivo = 1 AND e.catEnumeracionD.cveEnumeracion NOT IN ( '"
                + EstadoTramite.CONCLUIDO.getClave() + "', '" 
                + EstadoTramite.OBSERVADO.getClave() + "', '"
                + EstadoTramite.RECHAZADO.getClave()
                + "' ) ORDER BY e.catEnumeracionD.orden");
        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("ideEnumeracionH", ideEnumeracionH);

        return createQuery.getResultList();
    }

    /**
     * Metodo para buscar el parametro de una enumeracion
     * 
     * @param enu
     * @return listObj
     */
    @Override
    public String obtenerParametroByEnum(String enu) {

        Query query =
                getEntityManager().createNativeQuery(
                        " SELECT parametro.VALOR FROM RVCC_PARAMETRO parametro WHERE parametro.IDPARAMETRO = :enu ");
        query.setParameter("enu", enu);

        @SuppressWarnings("rawtypes")
        List listObj = query.getResultList();
        if (listObj != null && listObj.size() > 0) {
            return (String) (listObj.get(0));
        }
        return null;
    }
    
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public void guardarParametro(String llave, String descripcion, String valor, Date fecinivigencia, 
            Date fecfinvigencia, boolean activo) {
        String q = "SELECT parametro.IDPARAMETRO FROM RVCC_PARAMETRO parametro WHERE parametro.IDPARAMETRO= :llave";
        String u = "";
        Query query = getEntityManager()
                .createNativeQuery(q)
                .setParameter("llave", llave);

        List<Object> result = query.getResultList();

        if (result.size() > 0) {
            u += "UPDATE RVCC_PARAMETRO SET valor= :valor, fecinivigencia= :fecinivigencia, "
                    + "descripcion= :descripcion, fecfinvigencia= :fecfinvigencia, blnactivo= :blnactivo "
                    + "WHERE idparametro= :idparametro";
        }
        else {
            u += "INSERT INTO RVCC_PARAMETRO VALUES(:idparametro, :descripcion, :valor, :fecinivigencia, "
                    + ":fecfinvigencia, :blnactivo)";
        }

        query = getEntityManager()
                .createNativeQuery(u)
                .setParameter("idparametro", llave)
                .setParameter("descripcion", descripcion)
                .setParameter("valor", valor)
                .setParameter("fecinivigencia", fecinivigencia, TemporalType.DATE)
                .setParameter("fecfinvigencia", fecfinvigencia, TemporalType.DATE)
                .setParameter("blnactivo", activo);

        query.executeUpdate();

        getEntityManager().flush();
    }

    /**
     * Obtiene en registro de EnumeracionTr por su clave de
     * enumeracion
     * 
     * @param idEnumeracionD
     * @return
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public EnumeracionTr obtenerEnumeracionPorCveEnum(String idEnumeracionD) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM EnumeracionTr e");
        sb.append(" WHERE e.id.cveEnumeracionD = :idEnumeracionD ");
        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idEnumeracionD", idEnumeracionD);
        List<EnumeracionTr> data = createQuery.getResultList();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

}
