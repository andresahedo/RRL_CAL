/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.rrl.service.BandejaReasignarRecursoRevocacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandejaReasignarRecursoRevocacionServicePOJOImpl extends BaseSerializableBusinessServices implements
        BandejaReasignarRecursoRevocacionService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    /**
     * Metodo para obtener una unidad administrativa por rfc y calve
     * del rol
     * 
     * @param rfc
     * @param cveRol
     * @return Lista
     */
    public List<String> obtenerUnidadAdministrativa(String rfc, String cveRol) {
        return personaDao.obtenerUnidadesAdministrativas(rfc, cveRol);
    }
}
