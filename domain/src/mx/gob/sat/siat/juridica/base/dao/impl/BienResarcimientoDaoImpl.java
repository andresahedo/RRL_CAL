/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.BienResarcimientoDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BienResarcimiento;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Clase que implementa los metodos de la interfaz
 * BienResarcimientoDao.
 * 
 * @author antonio.flores Softtek.
 * @since 03/27/2017
 */
@Repository
public class BienResarcimientoDaoImpl extends BaseJPADao implements BienResarcimientoDao {

    /**
     * 
     */
    private static final long serialVersionUID = 4510633333224440158L;

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.BienResarcimientoDao#
     * guardaBienResarcimiento
     * (mx.gob.sat.siat.juridica.base.dao.domain
     * .model.BienResarcimiento)
     */
    @Override
    public void guardaBienResarcimiento(BienResarcimiento bienResarcimiento) {
        if (bienResarcimiento != null) {
            if (bienResarcimiento.getIdBienRerarcimiento() != null) {
                getEntityManager().merge(bienResarcimiento);
            }
            else {
                getEntityManager().persist(bienResarcimiento);
            }
            getEntityManager().flush();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.BienResarcimientoDao#
     * obtenBienResarcimientoIdResolucion(java.lang.Long)
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<BienResarcimiento> obtenBienResarcimientoIdResolucion(Long idResolucion) {
        Query query =
                getEntityManager()
                        .createQuery(
                                "select p from BienResarcimiento p where p.resolucion.idResolucion = :idResolucion and p.activo = 1",
                                BienResarcimiento.class);
        query.setParameter("idResolucion", idResolucion);
        query.getResultList().size();
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.BienResarcimientoDao#
     * obtenBienResarcimientoIdBienResarcimiento(java.lang.Long)
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<BienResarcimiento> obtenBienResarcimientoIdBienResarcimiento(Long idBienResarcimiento) {
        Query query =
                getEntityManager().createQuery(
                        "select p from BienResarcimiento p where p.idBienRerarcimiento = :idBienRerarcimiento",
                        BienResarcimiento.class);
        query.setParameter("idBienRerarcimiento", idBienResarcimiento);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.dao.BienResarcimientoDao#
     * eliminarBienResarcimiento
     * (mx.gob.sat.siat.juridica.base.dao.domain
     * .model.BienResarcimiento)
     */
    @Override
    public void eliminarBienResarcimiento(BienResarcimiento bienResarcimiento) {
        if (bienResarcimiento != null) {
            getEntityManager().remove(bienResarcimiento);
            getEntityManager().flush();
        }
    }

}
