package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.dao.ManifiestoDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Manifiesto;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.cal.service.ManifiestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManifiestoServiceImpl extends BaseSerializableBusinessServices implements ManifiestoService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ManifiestoDAO manifiestoDAO;

    @Override
    public List<Manifiesto> obtenerManifiestosPorIdModalidad(Integer idModalidad) {
        return manifiestoDAO.obtenerManifiestosPorIdModalidad(idModalidad);
    }

    @Override
    public Manifiesto obtenerManifiestoPorId(Integer idManifiesto) {
        return manifiestoDAO.obtenerManifiestoPorId(idManifiesto);
    }

}
