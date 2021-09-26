/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Softtek
 * 
 */
@Repository
public class ResolucionDatosGeneradosDaoImpl extends BaseJPADao implements ResolucionDatosGeneradosDao {

    /**
     * 
     */
    private static final long serialVersionUID = -7600390739743356002L;

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #crearResolucionDatosGenerados
     * (mx.gob.sat.siat.juridica.base.dao
     * .domain.model.ResolucionDatosGenerados)
     */
    @Override
    public ResolucionDatosGenerados crearResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        if (resolucionDatosGenerados != null) {
            getEntityManager().persist(resolucionDatosGenerados);
            getEntityManager().flush();
        }
        return resolucionDatosGenerados;
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #crearResolucionDatosGenerados
     * (mx.gob.sat.siat.juridica.base.dao
     * .domain.model.ResolucionDatosGenerados)
     */
    @Override
    public ResolucionDatosGenerados
            modificarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        if (resolucionDatosGenerados != null) {
            getEntityManager().merge(resolucionDatosGenerados);
            getEntityManager().flush();
        }
        return resolucionDatosGenerados;
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #obtenerResolucionDatosGeneradosIdResolucion(java.lang.Long)
     */
    @Override
    public ResolucionDatosGenerados obtenerResolucionDatosGeneradosIdResolucion(Long idResolucion) {
        Query query =
                getEntityManager().createQuery(
                        "select p from ResolucionDatosGenerados p where p.resolucion.idResolucion = :idResolucion",
                        ResolucionDatosGenerados.class);
        query.setParameter("idResolucion", idResolucion);
        try {
            return (ResolucionDatosGenerados) query.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #obtenerResolucionDatosGeneradosUnidadadminn(java.lang.Long)
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<ResolucionDatosGenerados> obtenerResolucionDatosGeneradosUnidadadminn(Long idUnidadAdministrativa) {
        Query query =
                getEntityManager().createQuery(
                        "select p from ResolucionDatosGenerados p where p.Resolucion.idResolucion = :idUniAdmin",
                        ResolucionDatosGenerados.class);
        query.setParameter("idUniAdmin", idUnidadAdministrativa);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #obtenerResolucionDatosGeneradosNumeroAsunto(java.lang.String)
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<ResolucionDatosGenerados> obtenerResolucionDatosGeneradosNumeroAsunto(String numeroAsunto) {
        Query query =
                getEntityManager().createQuery(
                        "select p from ResolucionDatosGenerados p where p.numeroAsunto = :numeroAsunto",
                        ResolucionDatosGenerados.class);
        query.setParameter("numeroAsunto", numeroAsunto);
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see
     * mx.gob.sat.siat.juridica.base.dao.ResolucionDatosGeneradosDao
     * #actualizaResolucionDatosGenerados
     * (mx.gob.sat.siat.juridica.base
     * .dao.domain.model.ResolucionDatosGenerados)
     */
    @Override
    public ResolucionDatosGenerados
            actualizaResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados) {
        if (resolucionDatosGenerados != null) {
            getEntityManager().merge(resolucionDatosGenerados);
            getEntityManager().flush();
        }
        return resolucionDatosGenerados;
    }

}
