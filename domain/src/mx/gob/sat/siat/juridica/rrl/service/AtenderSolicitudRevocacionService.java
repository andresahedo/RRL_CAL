/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface AtenderSolicitudRevocacionService extends Serializable {

    /**
     * Metodo para obtener informacion de un funcionario
     * 
     * @param idPersona
     * @return
     */
    Persona obtenerInformacionFuncionario(Long idPersona);

    /**
     * Metodo para buscar un tramite
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @return
     */
    Tramite buscarTramite(String numeroAsunto, Long idSolicitud);

    /**
     * Metodo para consultar una solicitud
     * 
     * @param solicitud
     * @return
     */
    Solicitud consultarSolicitud(Solicitud solicitud);

    /**
     * Metodo para obtener una resolucion
     * 
     * @param idResolucion
     * @return
     */
    Resolucion obtenerResolucion(Long idResolucion);

    /**
     * Metodo para obtener una resolucion por id de tramite
     * 
     * @param idTramite
     * @return
     */
    Resolucion obtenerResolucionIdTramite(String idTramite);

    /**
     * Metodo para obtener los conceptos de una resolucion
     * 
     * @param resolucionImpugnada
     * @return
     */
    List<ConceptoDetalle> obtenerConceptos(ResolucionImpugnada resolucionImpugnada);

    /**
     * Metodo para guardar una resolucion
     * 
     * @param resolucion
     */
    void guardar(Resolucion resolucion);

    /**
     * Metodo para autorizar una resolucion
     * 
     * @param resolucionAbogado
     * @param idTramite
     */
    void autorizar(Resolucion resolucionAbogado, String idTramite);

    /**
     * Metodo para firmar una resolucion
     * 
     * @param resolucion
     */
    void firmar(Resolucion resolucion);

    /**
     * Metodo para obtener autorizadores
     * 
     * @param numeroAsunto
     * @return
     */
    List<Persona> obtenerAutorizadores(String numeroAsunto);

    /**
     * Metodo para obtener un tramite por numero de resolucion
     * 
     * @param numResolucion
     * @return
     */
    Tramite obtenerTramite(String numResolucion, String numAsunto);

    /**
     * Metodo para obtener unidad administrativa
     * 
     * @param idTramite
     * @return unidadAdministrativaDao
     */
    UnidadAdministrativa obtenerUnidadAdministrativa(String idTramite);

    /**
     * Metodo para obtener los documentos que fueron adjuntados por el
     * promovente despues del registro.
     * 
     * @param idSolicitud
     * @return Lista documentos complementarios.
     */
    List<DocumentoSolicitud> obtenerDocumentosComplementarios(Long idSolicitud);

    void guardarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados);

    String getIdeTareaOrigen();

    Tramite obtenerTramiteParaMensajeAviso(String numResolucion, String numAsunto);

    UnidadAdministrativa obtenerUnidadAdministrativaBalanceo(String idTramite);
}
