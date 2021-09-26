/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.ca.dao.MedioDefensaDAO;
import mx.gob.sat.siat.juridica.ca.service.MedioDefensaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ivan.guzman
 */
@Service
public class MedioDefensaServiceImpl extends BaseSerializableBusinessServices implements MedioDefensaService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -6689658408319393704L;

    /**
     * Atributo privado "medioDefensaDAO" tipo MedioDefensaDAO
     */
    @Autowired
    private MedioDefensaDAO medioDefensaDAO;

    /**
     * Metodo para obtener los medios de defensa por id
     * 
     * @param idSolicitud
     * @return
     */
    public MedioDefensa obtenerMedioDefensaById(Long idSolicitud) {
        return medioDefensaDAO.obtenerMedioDefensaById(idSolicitud);
    }

}
