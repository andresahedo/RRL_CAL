/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.CatalogoDDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
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
public class CatalogoDDaoImpl extends BaseJPADao implements CatalogoDDao {

    /**
     * 
     */
    private static final long serialVersionUID = -4492165245043160031L;

    private static final String CONSULTAR_CATALOGO_D = "select c from CatalogoD c ";

    /**
     * Metodo obtenerCatalogoPorIdH
     * 
     * @return data
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<CatalogoD> obtenerCatalogoPorIdH(String idCatalogoH) {
        StringBuilder sb = new StringBuilder();
        sb.append(CONSULTAR_CATALOGO_D);
        sb.append(" where c.catalogoH.clave = :idCatalogoH and c.blnActivo = true");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idCatalogoH", idCatalogoH);

        List<CatalogoD> data = createQuery.getResultList();

        return data;
    }

    /**
     * Metodo obtenerCatalogoPorIdH
     * 
     * @return data
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoD> obtenerSentidosModH(String tipoInv) {
        StringBuilder sb = new StringBuilder();
        sb.append(CONSULTAR_CATALOGO_D);
        sb.append(" where (c.codigoGenerico2 IS NULL or c.codigoGenerico2 <> :idDisc) and c.catalogoH.clave = :idCatalogoH and c.blnActivo = true");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idCatalogoH", "RRSENT");
        createQuery.setParameter("idDisc", tipoInv);

        List<CatalogoD> data = createQuery.getResultList();

        return data;
    }
    
    /**
     * Obtiene el catalogo de sentidos perdedores
     * @param tipoCatalogo
     * @param descGen2
     * @return 
     */
    @Override
    public List<CatalogoD> obtenerSentidosResolucionPerdedores(String tipoCatalogo, String descGen2){
        StringBuilder sb = new StringBuilder();
        sb.append(CONSULTAR_CATALOGO_D);
        sb.append(" where c.catalogoH.clave = :idCatalogoH");
        sb.append(" and c.descripcionGenerica2 = :descGen2 ");
        sb.append(" and c.blnActivo = true");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("idCatalogoH", tipoCatalogo);
        createQuery.setParameter("descGen2", descGen2);

        return createQuery.getResultList();
    }

    /**
     * Metodo obtenerCatalogoPorClave
     * 
     * @return ctalogoD
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public CatalogoD obtenerCatalogoPorClave(String clave) {
        StringBuilder sb = new StringBuilder();
        sb.append(CONSULTAR_CATALOGO_D);
        sb.append(" where c.clave = :clave ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("clave", clave);

        List<CatalogoD> data = createQuery.getResultList();
        CatalogoD catalogoD = null;
        if (data != null && !data.isEmpty()) {
            catalogoD = (CatalogoD) data.get(0);
        }
        return catalogoD;
    }

    /**
     * Metodo obtenerCatalogoPorClave
     * 
     * @return ctalogoD
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    public List<CatalogoD> obtenerCatalogoPorCodGen1(String codGen1) {
        StringBuilder sb = new StringBuilder();
        sb.append(CONSULTAR_CATALOGO_D);
        sb.append(" where c.codigoGenerico1 = :codGen1 ");

        Query createQuery = getEntityManager().createQuery(sb.toString());
        createQuery.setParameter("codGen1", codGen1);

        List<CatalogoD> data = createQuery.getResultList();

        return data;
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public List<CatalogoD> comboTipoPersona() {
        StringBuilder consulta = new StringBuilder();
        consulta.append(" select cat from CatalogoD cat ");
        consulta.append(" where cat.catalogoH = 'PRSOLC' ");
        Query query = getEntityManager().createQuery(consulta.toString());
        return query.getResultList();
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public Date obtenerFechaRequerimiento(String numeroAsunto) {
        StringBuilder consulta = new StringBuilder();
        consulta.append(" select req.fechaAutorizacion from Requerimiento req ");
        consulta.append(" where (req.tipoRequerimiento = '" + TipoRequerimiento.CONTRIBUYENTE.getClave()
                + "' or req.tipoRequerimiento = '" + TipoRequerimiento.PROMOVENTE_CAL.getClave()
                + "') and req.estadoRequerimiento = '" + EstadoRequerimiento.AUTORIZADO.getClave()
                + "' and req.tramite.numeroAsunto = :numeroAsunto ORDER BY req.tramite.numeroAsunto ");
        Query query = getEntityManager().createQuery(consulta.toString());
        query.setParameter("numeroAsunto", numeroAsunto);
        List<Date> fechas = query.getResultList();
        if (fechas != null && !fechas.isEmpty()) {
            return fechas.get(0);
        }
        else {
            return null;
        }
    }

}
