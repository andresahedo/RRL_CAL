/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.AsignacionTramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.AsignacionTramite;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository("asignacionTramiteDao")
public class AsignacionTramiteDaoImpl extends BaseJPADao implements AsignacionTramiteDao {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -2806573293424439509L;

    @Override
    public AsignacionTramite guardarAsignacion(AsignacionTramite asignacionTramite) {

            AsignacionTramite asignacionTramiteBase =
                    buscarAsignacionesRolAsunto(asignacionTramite.getTramite().getNumeroAsunto(),
                            asignacionTramite.getTipoRol());

            if (asignacionTramiteBase != null && asignacionTramiteBase.getIdAsignacionTramite() != null) {

                asignacionTramiteBase.setPersona(asignacionTramite.getPersona());

                getEntityManager().merge(asignacionTramiteBase);
                getEntityManager().flush();
            }
            else {
                getEntityManager().persist(asignacionTramite);
                getEntityManager().flush();

            }

        return asignacionTramite;
    }

    @Override
    public List<AsignacionTramite> buscarAsignacionesByRFC(String rfc) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select astr from AsignacionTramite as astr ");
        sb.append(" where astr.persona.idPersona = :rfc ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("rfc", rfc);

        List<AsignacionTramite> data = createQuery.getResultList();

        return data;
    }

    @Override
    public List<AsignacionTramite> buscarAsignacionesByIdAsunto(String idAsunto) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select astr from AsignacionTramite as astr ");
        sb.append(" where astr.tramite.numeroAsunto = :idAsunto ");
        sb.append(" and astr.blnActivo = true ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idAsunto", idAsunto);

        List<AsignacionTramite> data = createQuery.getResultList();

        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AsignacionTramite buscarAsignacionesRolAsunto(String numAsunto, String rol) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select astr from AsignacionTramite as astr ");
        sb.append(" where astr.tramite.numeroAsunto = :numAsunto and astr.tipoRol= :rol ");
        sb.append(" and astr.blnActivo = true ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("numAsunto", numAsunto);
        createQuery.setParameter("rol", rol);

        List<AsignacionTramite> data = createQuery.getResultList();
        AsignacionTramite asignacionTramite = null;
        if (data != null && !data.isEmpty()) {
            asignacionTramite = (AsignacionTramite) data.get(0);
        }
        return asignacionTramite;

    }

}
