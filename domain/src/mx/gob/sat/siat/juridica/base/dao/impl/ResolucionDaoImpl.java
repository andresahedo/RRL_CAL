/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.ResolucionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Repository
public class ResolucionDaoImpl extends BaseJPADao implements ResolucionDao {

    /**
     * 
     */
    private static final long serialVersionUID = -4672633086900686741L;
    /**
     * Atributo protegido "logger" tipo estatico final Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstants.DOMAIN_REPOSITORY_LOGGER);

    @Value("${parametro.idResolucion}")
    private String parametroIdResolucion;
    @Value("${parametro.idResolucionImpugnada}")
    private String parametroIdResolucionImpugnada;

    @Value("${resoluciondaoimpl.condicion.fechabaja}")
    private String condicionFechaBaja;

    /**
     * Metodo para crear una resolucion
     */
    @Override
    public Resolucion crearResolucion(Resolucion resolucion) {
        if (resolucion != null) {
            if (resolucion.getIdResolucion() == null) {
                getEntityManager().persist(resolucion);
                getEntityManager().flush();
            }
            else {
                getEntityManager().merge(resolucion);
                getEntityManager().flush();
            }
        }
        return resolucion;
    }

    /**
     * Metodo para obtener una resolucion
     */
    public Resolucion obtenerResolucion(Long idResolucion) {
        Query query =
                getEntityManager().createQuery("select p from Resolucion p where p.idResolucion = :idResolucion",
                        Resolucion.class);
        query.setParameter(this.parametroIdResolucion, idResolucion);
        return (Resolucion) query.getSingleResult();

    }

    /**
     * Metodo para obtener una resolucion por id de tramite
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Resolucion obtenerResolucionIdTramite(String idTramite) {
        Query query =
                getEntityManager().createQuery("select r from Resolucion r where r.tramite.numeroAsunto = :idTramite",
                        Resolucion.class);
        query.setParameter("idTramite", idTramite);
        List<Resolucion> lista = query.getResultList();

        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        else {
            return null;
        }

    }

    /**
     * Metodo para obtener una lista de resolcuiones impugnadas por id
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<ResolucionImpugnada> obtenerResolucionesImpugnadas(Long idResolucion) {
        Query query =
                getEntityManager().createQuery(
                        "select r from ResolucionImpugnada r where r.resolucion.idResolucion = :idResolucion "
                                + this.condicionFechaBaja, ResolucionImpugnada.class);
        query.setParameter(this.parametroIdResolucion, idResolucion);
        return query.getResultList();

    }

    /**
     * Metodo para obtener una lista de los conceptos de una solicitud
     * impugnada por Id
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<ConceptoDetalle> obtenerConceptosPorIdImpugnada(Long idResolucionImpugnada) {
        Query query =
                getEntityManager().createQuery(
                        "select cd from ConceptoDetalle cd where cd.resolucionImpugnada.idResolucionImpugnada = :idResolucionImpugnada"
                                + this.condicionFechaBaja, ConceptoDetalle.class);
        query.setParameter(this.parametroIdResolucionImpugnada, idResolucionImpugnada);
        return query.getResultList();
    }

    /**
     * matodo para obtener los agravios detallados de una resolucion
     * impugnada por Id
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<AgravioDetalle> obtenerAgraviosPorIdImpugnada(Long idResolucionImpugnada) {
        Query query =
                getEntityManager().createQuery(
                        "select ad from AgravioDetalle ad where ad.resolucionImpugnada.idResolucionImpugnada = :idResolucionImpugnada "
                                + this.condicionFechaBaja, AgravioDetalle.class);
        query.setParameter(this.parametroIdResolucionImpugnada, idResolucionImpugnada);
        return query.getResultList();
    }

    /**
     * Metodo para obtener una resolucion impugnada por Id
     */
    public ResolucionImpugnada obtenerResolucionImpugnada(Long idResolucionImpugnada) {
        Query query =
                getEntityManager().createQuery(
                        "select r from ResolucionImpugnada r where r.idResolucionImpugnada = :idResolucionImpugnada "
                                + " and fechaBaja is null  ", ResolucionImpugnada.class);
        query.setParameter(this.parametroIdResolucionImpugnada, idResolucionImpugnada);
        return (ResolucionImpugnada) query.getSingleResult();

    }

    /**
     * Metodo para obtener una resolucion impugnada del cataologoD por
     * id
     */
    public ResolucionImpugnada obtenerResolucionCatalogoDPorId(Long idResolucionImpugnada) {
        Query query =
                getEntityManager().createQuery(
                        "select r from ResolucionCatalogoD r where r.idResolucionImpugnada = :idResolucionImpugnada",
                        ResolucionImpugnada.class);
        query.setParameter(this.parametroIdResolucionImpugnada, idResolucionImpugnada);
        return (ResolucionImpugnada) query.getSingleResult();

    }

    /**
     * Metodo para obtener una lista de resoluciones del catalogoD por
     * Id
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<ResolucionCatalogoD> obtenerResolucionesCatalogoD(Long idResolucion) {
        Query query =
                getEntityManager().createQuery(
                        "select r from ResolucionCatalogoD r where r.resolucion.idResolucion = :idResolucion "
                                + this.condicionFechaBaja, ResolucionCatalogoD.class);
        query.setParameter(this.parametroIdResolucion, idResolucion);
        return query.getResultList();
    }

    /**
     * Metodo para crear una resolucion impugnada
     */
    @Override
    public ResolucionImpugnada crearResolucionImpugnada(ResolucionImpugnada resolucion) {
        if (resolucion != null) {
            getEntityManager().persist(resolucion);
        }
        return resolucion;
    }

    /**
     * Metodo para modificar una resolucion
     */
    @Override
    public void modificarResolucion(Resolucion resolucion) {
        if (resolucion != null) {
            getEntityManager().merge(resolucion);
            getEntityManager().flush();
        }
    }

    /**
     * metodo para modificar una resolucion impugnada
     */
    @Override
    public void modificarResolucionImpugnada(ResolucionImpugnada resolucion) {
        if (null != resolucion) {
            getEntityManager().merge(resolucion);
        }
    }

    /**
     * Metodo para crear una resolucion en catalogoD
     */
    @Override
    public ResolucionCatalogoD crearResolucionCatalogoD(ResolucionCatalogoD resolucionCatalogoD) {
        if (resolucionCatalogoD != null) {
            getEntityManager().persist(resolucionCatalogoD);
        }
        return resolucionCatalogoD;
    }

    /**
     * Medoto para modificar una resolucion del catalogoD
     */
    @Override
    public void modificarResolucionCatalogoD(ResolucionCatalogoD resolucionCatalogoD) {
        if (null != resolucionCatalogoD) {
            getEntityManager().merge(resolucionCatalogoD);
        }
    }

    /**
     * Metodo para crear una resolucion impugnada del catalogoD
     */
    @Override
    public ResolucionImpugnadaCatalogoD crearResolucionImpugnadaCatalogoD(ResolucionImpugnadaCatalogoD conceptoDetalle) {
        if (conceptoDetalle != null) {
            getEntityManager().persist(conceptoDetalle);
        }
        return conceptoDetalle;
    }

    /**
     * Metodo para modificar una resolucion impugnada del catalogoD
     */
    @Override
    public void modificarResolucionImpugnadaCatalogoD(ResolucionImpugnadaCatalogoD conceptoDetalle) {

        if (null != conceptoDetalle) {
            if (conceptoDetalle.getIdResolucionImpugnadaCatD() != null) {
                getEntityManager().merge(conceptoDetalle);
            }
            else {
                getEntityManager().persist(conceptoDetalle);
            }
        }
    }

    /**
     * Metodo para Eliminar resoluciones impugnadas detalle por Id
     */
    @Override
    public void eliminarResolucionesImpugnadasDetallePorIdsImpugnadas(String idsResolucionesImpugnadas, Date fechaBaja) {

        StringBuffer sbUpdate = new StringBuffer();
        sbUpdate.append("update rvcd_resolimp_catd set fecbaja = :fechaBaja where idresolimpug in ");
        sbUpdate.append(idsResolucionesImpugnadas);

        Query query = getEntityManager().createNativeQuery(sbUpdate.toString());
        query.setParameter("fechaBaja", fechaBaja);
        query.executeUpdate();
    }

    /**
     * Metodo para eliminar resoluciones impugnadas por el id de la
     * resolucion
     */
    @Override
    public void eliminarResolucionesImpugnadasPorIdResolucion(Long idResolucion, Date fechaBaja) {
        StringBuffer sbUpdate = new StringBuffer();
        sbUpdate.append("update rvct_resolimpug set fecbaja = :fechaBaja where idresolucion =:idResolucion");

        Query query = getEntityManager().createNativeQuery(sbUpdate.toString());
        query.setParameter("fechaBaja", fechaBaja);
        query.setParameter(this.parametroIdResolucion, idResolucion);
        query.executeUpdate();
    }

    /**
     * Metodo para Eliminar resoluciones detalle por Id
     */
    @Override
    public void eliminarResolucionesDetallePorIdResolucion(Long idResolucion, Date fechaBaja) {
        StringBuffer sbUpdate = new StringBuffer();
        sbUpdate.append("update rvcd_resol_catd set fecbaja = :fechaBaja where idresolucion =:idResolucion");

        Query query = getEntityManager().createNativeQuery(sbUpdate.toString());
        query.setParameter("fechaBaja", fechaBaja);
        query.setParameter(this.parametroIdResolucion, idResolucion);
        query.executeUpdate();
    }

    /**
     * Metodo para obtener la ultima resolucion por id de tramite
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public Resolucion obtenerUltimaResolucionPorTramite(String numeroAsunto) {
        Resolucion resolucion = null;
        Query query =
                getEntityManager().createQuery(
                        "SELECT t FROM Resolucion t "
                                + " WHERE t.tramite.numeroAsunto = :nAsunto ORDER BY t.idResolucion DESC",
                        Resolucion.class);
        query.setParameter("nAsunto", numeroAsunto);
        List<Resolucion> lista = query.getResultList();
        if (!lista.isEmpty()) {
            resolucion = lista.get(0);
        }
        return resolucion;
    }

}
