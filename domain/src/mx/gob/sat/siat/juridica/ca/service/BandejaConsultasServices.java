/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface BandejaConsultasServices extends Serializable {

    /**
     * Metodo para obtener una lista de trmites
     * 
     * @param numAsunto
     * @param fechaInicio
     * @param fechaFin
     * @param estadoProcesal
     * @param tipoTramite
     * @return
     */
    List<Tramite> obtenerTramites(String numAsunto, Date fechaInicio, Date fechaFin, String estadoProcesal,
            String tipoTramite);

    /**
     * Metodo para obtener las modalidades de un tramite
     * 
     * @param idServicio
     * @return
     */
    List<TipoTramite> obtenerModalidadesPorServicio(String idServicio);
}
