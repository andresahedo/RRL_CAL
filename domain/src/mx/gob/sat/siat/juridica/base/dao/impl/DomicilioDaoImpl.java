package mx.gob.sat.siat.juridica.base.dao.impl;

import mx.gob.sat.siat.juridica.base.dao.DomicilioDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("domicilioDAO")
public class DomicilioDaoImpl extends BaseJPADao implements DomicilioDAO {

    private static final String PARAMETRO_CLAVE = "clave";
    private static final String PARAMETRO_CLAVE_IDC = "claveIDC";

    /**
     * 
     */
    private static final long serialVersionUID = 5713109366679600739L;

    @Override
    public EntidadFederativa buscarEntidadByClave(String clave) {
        StringBuilder sb = new StringBuilder("from EntidadFederativa e where e.clave = :clave");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE, clave);
        List<EntidadFederativa> entidades = query.getResultList();
        if (entidades != null && entidades.size() > 0) {
            return entidades.get(0);
        }
        return null;
    }

    @Override
    public Colonia buscarColoniaByClave(String clave) {
        StringBuilder sb = new StringBuilder("from Colonia c where c.clave = :clave");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE, clave);
        List<Colonia> colonias = query.getResultList();
        if (colonias != null && colonias.size() > 0) {
            return colonias.get(0);
        }
        return null;
    }

    @Override
    public Pais buscarPaisByClave(String clave) {
        StringBuilder sb = new StringBuilder("from Pais p where p.clave = :clave");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE, clave);
        List<Pais> paises = query.getResultList();
        if (paises != null && paises.size() > 0) {
            return paises.get(0);
        }
        return null;
    }

    @Override
    public DelegacionMunicipio buscarDelegacionByClave(String clave) {
        StringBuilder sb = new StringBuilder("from DelegacionMunicipio d where d.clave = :clave");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE, clave);
        List<DelegacionMunicipio> delegaciones = query.getResultList();
        if (delegaciones != null && delegaciones.size() > 0) {
            return delegaciones.get(0);
        }
        return null;
    }

    @Override
    public Localidad buscarLocalidadByClave(String clave) {
        StringBuilder sb = new StringBuilder("from Localidad l where l.clave = :clave");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE, clave);
        List<Localidad> localidades = query.getResultList();
        if (localidades != null && localidades.size() > 0) {
            return localidades.get(0);
        }
        return null;
    }

    @Override
    public EntidadFederativa buscarEntidadByClaveIDC(String claveIDC) {
        StringBuilder sb = new StringBuilder("from EntidadFederativa e where e.claveEnIDC = :claveIDC");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE_IDC, claveIDC);
        List<EntidadFederativa> entidades = query.getResultList();
        if (entidades != null && entidades.size() > 0) {
            return entidades.get(0);
        }
        return null;
    }

    @Override
    public Colonia buscarColoniaByClaveIDC(String claveIDC) {
        StringBuilder sb = new StringBuilder("from Colonia c where c.idColoniaIDC = :claveIDC");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE_IDC, claveIDC);
        List<Colonia> colonias = query.getResultList();
        if (colonias != null && colonias.size() > 0) {
            return colonias.get(0);
        }
        return null;
    }

    @Override
    public DelegacionMunicipio buscarDelegacionByClaveIDC(String claveIDC, String claveEntidad) {
        StringBuilder sb = new StringBuilder("from DelegacionMunicipio d where d.idMunicipioIDC = :claveIDC and d.entidadFederativa.clave = :entidad " );

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE_IDC, claveIDC);
        query.setParameter("entidad", claveEntidad);
        List<DelegacionMunicipio> delegaciones = query.getResultList();
        if (delegaciones != null && delegaciones.size() > 0) {
            return delegaciones.get(0);
        }
        return null;
    }

    @Override
    public Localidad buscarLocalidadByClaveIDC(String claveIDC) {
        StringBuilder sb = new StringBuilder("from Localidad l where l.codigoSAT = :claveIDC");

        Query query = getEntityManager().createQuery(sb.toString());
        query.setParameter(PARAMETRO_CLAVE_IDC, claveIDC);
        List<Localidad> localidades = query.getResultList();
        if (localidades != null && localidades.size() > 0) {
            return localidades.get(0);
        }
        return null;
    }

}
