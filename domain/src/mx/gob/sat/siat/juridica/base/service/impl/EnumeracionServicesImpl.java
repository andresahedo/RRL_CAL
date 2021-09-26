/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.service.EnumeracionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class EnumeracionServicesImpl implements EnumeracionServices {

    /**
     * 
     */
    private static final long serialVersionUID = 6322350716586654030L;
    /**
     * Atributo privado "enumeracionDao" tipo EnumeracionDao
     */
    @Autowired
    private EnumeracionDao enumeracionDao;

    /**
     * Metodo para obtener una enumeracion por id
     * 
     * @param ideEnumeracionH
     * @return Lista de enumeracion
     */
    public List<EnumeracionTr> obtenerEnumeracionPorId(String ideEnumeracionH) {
        return enumeracionDao.obtenerEnumeracionPorId(ideEnumeracionH);
    }

    public String obtenerParametroByEnum(String ideEnumeracionH) {
        return enumeracionDao.obtenerParametroByEnum(ideEnumeracionH);
    }
}
