/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DiaInhabil;

import java.util.Date;
import java.util.List;

/**
 * Interface para obtener una lista de dias habiles
 * 
 * @author Softtek
 */
public interface DiaInhabilDAO {

    List<DiaInhabil> obtenerDiasInhabiles();

    int numeroDiasFeriadosEnCalendario(Date fechaInicio, Date fechaFin);

}
