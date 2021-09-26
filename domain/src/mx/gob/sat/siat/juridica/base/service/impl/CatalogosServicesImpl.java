package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.CatalogosDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;
import mx.gob.sat.siat.juridica.base.service.CatalogosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class CatalogosServicesImpl implements CatalogosServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 6487914823502457102L;

    /**
     * Atributo privado "catalogosDao" tipo CatalogosDao
     */
    @Autowired
    private CatalogosDao catalogosDao;

    @Override
    public Descripcion buscarDescripcionPorId(Long idDescripcion) {
        if (idDescripcion != null) {
            return catalogosDao.buscarDescripcionPorId(idDescripcion);
        }
        return null;
    }

    @Override
    public Descripcion buscarDescripcionPorTipoIdentificador(String tipo, String identificador) {
        if (tipo != null && identificador != null) {
            return catalogosDao.buscarDescripcionPorTipoIdentificador(tipo, identificador);
        }
        return null;
    }

}
