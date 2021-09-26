package mx.gob.sat.siat.juridica.comun.service.impl;

import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.comun.service.ObtenerUnidadesAdministrativasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerUnidadesAdministrativasServiceImpl extends BaseSerializableBusinessServices implements
        ObtenerUnidadesAdministrativasService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -6047720474972053758L;

    /**
     * Atributo unidadAdministrativaDao tipo UnidadAdministrativaDao
     */
    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;

    @Override
    public List<UnidadAdministrativa> obtenerCatalogoUnidadesAdministrativas() {
        return unidadAdministrativaDao.obtenerUnidadesAdministrativas();
    }

}
