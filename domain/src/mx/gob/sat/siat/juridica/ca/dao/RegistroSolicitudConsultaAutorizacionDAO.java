/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Autoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;

import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface RegistroSolicitudConsultaAutorizacionDAO {

    /**
     * M&eacute;todo para obtener las modalidades del tr&aacute;mite
     * por id de subservicio.
     * 
     * @param idSubservicio
     * @return Lista TipoTramite
     */
    List<TipoTramite> obtenerModalidadesPorSubservicio(String idSubservicio, String tipoPersona);

    /**
     * M&eacute;todo para obtener los tipos de documentos (requeridos
     * u opcionales) asociados a la modalidad.
     * 
     * @param idTipoTramite
     * @param opcional
     * @return Lista DocumentoTramite
     */
    List<DocumentoTramite> obtenerTiposDocumentosPorTramite(Integer idTipoTramite, Integer opcional);

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudConsultaAutorizacion
     */
    SolicitudConsultaAutorizacion crearSolicitud(SolicitudConsultaAutorizacion solicitud);

    /**
     * M&eacute;todo para guardar personas relacionadas a la
     * solicitud.
     * 
     * @param solicitante
     */
    PersonaSolicitud crearPersonaSolicitud(PersonaSolicitud personaSolicitud);

    /**
     * Metodo para crear un medio de defensa
     * 
     * @param medioDefensa
     */
    MedioDefensa crearMedioDefensa(MedioDefensa medioDefensa);

    void crearAutoridad(Autoridad autoridad);

    Autoridad obtenerAutoridad(Long idSolicitud, TipoAutoridad tipoAutoridad);

    void crearFraccionDuda(FraccionDuda fraccionDuda);

    void actualizaFraccionDuda(FraccionDuda fraccionDuda);

    TipoTramite obtenerTipoTramitePorId(String idTIpoTramite);

    DocumentoTramite obtenerDocumentoRazonesLogicoJuridicas();
}
