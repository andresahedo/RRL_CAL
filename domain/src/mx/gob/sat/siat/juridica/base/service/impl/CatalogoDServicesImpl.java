/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.CatalogoDDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class CatalogoDServicesImpl implements CatalogoDServices {

    /**
     * 
     */
    private static final long serialVersionUID = 5414240704995560369L;
    /**
     * Atributo "catalogoDDao" tipo CatalogoDDao
     */
    @Autowired
    private CatalogoDDao catalogoDDao;

    /**
     * Metodo para obtener una lista de catalogos por id
     * 
     * @param idCatalogoH
     * @return List<CatalogoD>
     */
    @Override
    public List<CatalogoD> obtenerCatalogoPorIdH(String idCatalogoH) {

        return catalogoDDao.obtenerCatalogoPorIdH(idCatalogoH);
    }
    
    /**
     * Obtiene el catalogo de sentidos perdedores
     * @param tipoCatalogo
     * @param descGen2
     * @return 
     */
    @Override
    public List<CatalogoD> obtenerSentidosResolucionPerdedores(String tipoCatalogo, String descGen2){
        return catalogoDDao.obtenerSentidosResolucionPerdedores(tipoCatalogo, descGen2);
    }

    /**
     * Metodo para obtener un catalogo por clave
     * 
     * @param clave
     * @return
     */
    @Override
    public CatalogoD obtenerCatalogoPorClave(String clave) {

        return catalogoDDao.obtenerCatalogoPorClave(clave);
    }

    /**
     * Metodo para obtener un catalogo por clave
     * 
     * @param clave
     * @return
     */
    @Override
    public Date obtenerFechaRequerimiento(String numeroAsunto) {
        return catalogoDDao.obtenerFechaRequerimiento(numeroAsunto);
    }
}
