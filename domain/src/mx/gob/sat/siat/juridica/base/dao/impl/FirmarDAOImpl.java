/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.gob.sat.siat.juridica.base.dao.FirmarDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaNotificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaObservacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRemision;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaSolicitud;

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
    
    @Override
    public Firma obtenerFirmaSolicitud(String idSolicitud, String claveUsuario) {
    	StringBuffer sb = new StringBuffer();
    	String cadenaOriginal = "|" + idSolicitud +"|%";
        sb.append("SELECT firm from Firma firm ");
        sb.append("where firm.claveUsuario = :claveUsuario ");
        sb.append("and ideProcesoFirma = 'TIPPRO.RGS' ");
        sb.append("and cadenaOriginal like :cadenaOriginal ");
        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter("claveUsuario", claveUsuario);
        query.setParameter("cadenaOriginal", cadenaOriginal);
        if (query.getResultList() != null && !query.getResultList().isEmpty()) {
            return (Firma) query.getResultList().get(0);
        }
        else {
            return new Firma();
        }
    }
    

}
