/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.FirmarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * 
 * @author softtek
 * 
 */
@Repository("firmarDAO")
public class FirmarDAOImpl extends BaseJPADao implements FirmarDAO {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Metodo para guardar una firma
     * 
     * @return firma
     */
    public Firma guardarFirma(Firma firma) {
        if (firma != null) {
            getEntityManager().persist(firma);
            getEntityManager().flush();
        }
        return firma;
    }

    /**
     * Metodo para Guardar una solicitud firmada
     */
    public void guardarFirmaSolicitud(FirmaSolicitud firmaSol) {
        if (firmaSol != null) {
            getEntityManager().persist(firmaSol);
            getEntityManager().flush();
        }
    }

    /**
     * Metodo para guardar una autorizacion de resolucion
     */
    public void guardarFirmaAutorizacionResolicion(FirmaResolucion firmaResol) {
        if (firmaResol != null) {
            getEntityManager().persist(firmaResol);
            getEntityManager().flush();
        }
    }

    @Override
    public void guardarFirmaRequerimientoAutorizacion(FirmaRequerimiento firmaReq) {
        if (firmaReq != null) {
            getEntityManager().persist(firmaReq);
            getEntityManager().flush();
        }

    }

    @Override
    public void guardarFirmaAutorizacionRemision(FirmaRemision firmaRem) {
        if (firmaRem != null) {
            getEntityManager().persist(firmaRem);
            getEntityManager().flush();
        }
    }

    @Override
    public void guardarFirmaSNotificacion(FirmaNotificacion firmaNot) {
        if (firmaNot != null) {
            getEntityManager().persist(firmaNot);
            getEntityManager().flush();
        }
    }

    @Override
    public FirmaNotificacion obtenerFirmaNotificacion(Long idNotificacion) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT fir from FirmaNotificacion fir ");
        sb.append("where fir.firmaNotificacionPK.idNotificacion = :idNotificacion ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("idNotificacion", idNotificacion);

        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return (FirmaNotificacion) query.getResultList().get(0);
        }
        else {
            return null;
        }

    }

    @Override
    public void guardarFirmaObservacion(FirmaObservacion firmaObservacion) {
        if (firmaObservacion != null) {
            getEntityManager().persist(firmaObservacion);
            getEntityManager().flush();
        }

    }

}
