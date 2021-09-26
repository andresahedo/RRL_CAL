/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface ObservacionDao extends Serializable {

    Observacion obtenerObservacionPorId(Long idObservacion);

    Observacion guardarObservacion(Observacion observacion);

    Observacion obtenerUltimaObservacionPorTramite(String idTramite);

    List<Observacion> obtenerObservacionesPorTramite(String numAsunto);

}
