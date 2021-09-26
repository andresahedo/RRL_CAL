/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface BandejaConsultasDAO {

    /**
     * Metodo que obtiene una lista de tramites segun los atributos
     * correspondientes
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
     * M&eacute;todo para obtener las modalidades del tr&aacute;mite
     * por id de servicio.
     */
    List<TipoTramite> obtenerModalidadesPorServicio(String idServicio);

}
