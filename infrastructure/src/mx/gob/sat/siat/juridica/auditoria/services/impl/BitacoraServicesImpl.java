/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.auditoria.services.impl;

import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.juridica.auditoria.services.BitacoraServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de bitacora
 * 
 * @author softtek
 * 
 */
@Service("bitacoraServices")
public class BitacoraServicesImpl extends BaseSerializableBusinessServices implements BitacoraServices {

    private static final long serialVersionUID = 6443090730231417996L;
    /**
     * Inyeccion para el DAO de Movimientos
     */
    @Autowired
    private SegbMovimientosDAO segbMovimientosDAO;

    /**
     * Metodo para agregar registros al DAO de movimientos
     */
    public void agregarMovimiento() {

        try {
            segbMovimientosDAO.insert(new SegbMovimientoDTO());
        }
        catch (DaoException e) {
            // TODO Auto-generated catch block
            getLogger().error("", e);
        }
    }

}
