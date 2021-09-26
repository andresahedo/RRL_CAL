/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TurnarDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Turnar;
import mx.gob.sat.siat.juridica.base.service.TurnarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase Service para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
@Service
public class TurnarServiceImpl extends BaseBusinessServices implements TurnarService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5973217313844773967L;

    @Autowired
    private TurnarDao turnarDao;

    /**
     * Metodo para guardar las observaciones del turnado
     * 
     * @param datosTurnar
     */
    public void guardarDatosTurnado(Turnar datosTurnar) {
        turnarDao.guardaDatosTurnar(datosTurnar);
    }

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    @Override
    public String obtenerObservacionTurnado(String numeroAsunto) {
        return turnarDao.obtenerObservacionTurnado(numeroAsunto);
    }

}
