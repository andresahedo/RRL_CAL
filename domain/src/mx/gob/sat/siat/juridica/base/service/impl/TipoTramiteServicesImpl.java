/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service("tipoTramiteServices")
public class TipoTramiteServicesImpl extends BaseSerializableBusinessServices implements TipoTramiteServices {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "tipoTramiteDAO" tipo TipoTramiteDAO
     */
    @Autowired
    private TipoTramiteDAO tipoTramiteDAO;

    /**
     * Metodo para obtener un tipo de tramite por id
     * 
     * @param idTipoTramite
     * @return
     */
    public String obtenerTipoTramite(Integer idTipoTramite) {
        return tipoTramiteDAO.findById(idTipoTramite);
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.service.TipoTramiteServices#
     * obtenerTipoTramiteModulo()
     */
    @Override
    public List<TipoTramite> obtenerTipoTramiteModulo(String idTipoTramite) {
        return tipoTramiteDAO.obtenerTiposDeTramitesPorFiltros(Integer.valueOf(idTipoTramite), null);
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.sat.siat.juridica.base.service.TipoTramiteServices#
     * obtenerTipoTramiteId(java.lang.String)
     */
    @Override
    public TipoTramite obtenerTipoTramiteId(Integer idTipoTramite) {
        return tipoTramiteDAO.obtenerTiposDeTramitesPorId(idTipoTramite);
    }

    @Override
    public List<TipoTramite> obtenerTipoTramiteModulo1() {
        return tipoTramiteDAO.obtenerTipoTramiteModulo1();
    }
}
