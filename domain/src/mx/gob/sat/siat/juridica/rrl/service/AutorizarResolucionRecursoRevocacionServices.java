/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author softtek
 */
public interface AutorizarResolucionRecursoRevocacionServices extends Serializable {

    /**
     * Metodo para obtener una resolicion por id de tramite
     * 
     * @param idTramite
     * @return Resolucion
     */
    Resolucion obtenerResolucionIdTramite(String idTramite);

    /**
     * Metodo para guardar una resolucion
     * 
     * @param resolucion
     */
    void guardar(Resolucion resolucion);
    
    
    /**
     * Metodo para guardar una resolucion
     * 
     * @param resolucion
     */
    void guardarImpFecEmis(Resolucion resolucion, Date[] arrResolFechaEmision);
    
    

    /**
     * Metodo para obtener un tramite
     * 
     * @param resImpugnada
     * @return Tramite
     */
    Tramite obtenerTramite(String resImpugnada, String numAsunto);

    /**
     * Metodo para obtener una unidad administrativa
     * 
     * @param numeroAsunto
     * @return UnidadAdministrativa
     */
    UnidadAdministrativa obtenerUnidadAdministrativa(String numeroAsunto);

    ResolucionDatosGenerados obtenerResolucionDatosGenerados(Long idResolucion);

    void guardarResolucionDatosGenerales(ResolucionDatosGenerados resolucionDatosGenerados);

    List<DocumentoSolicitud> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud);

    void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud);

    String generaCadenaOriginal(long idSolicitud, Date fechaFirma, String rfcFuncionario);

}
