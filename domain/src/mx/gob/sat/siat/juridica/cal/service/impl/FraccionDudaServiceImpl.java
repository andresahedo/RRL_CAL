/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.cal.dao.FraccionDudaDAO;
import mx.gob.sat.siat.juridica.cal.service.FraccionDudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author softtek
 */
@Service
public class FraccionDudaServiceImpl extends BaseSerializableBusinessServices implements FraccionDudaService {

    /**
     * 
     */
    private static final long serialVersionUID = -4775999780993237035L;
    /**
     * Atributo privado "medioDefensaDAO" tipo MedioDefensaDAO
     */
    @Autowired
    private FraccionDudaDAO fraccionDudaDAO;

    @Override
    public List<FraccionDuda> obtenerFraccionesDudaBySolicitudId(Long idSolicitud) {
        return fraccionDudaDAO.obtenerFraccionesDudaBySolicitudId(idSolicitud);
    }

}
