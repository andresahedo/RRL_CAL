/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface PersonaDao extends Serializable {
    /**
     * Metodo para obtener una persona por id
     * 
     * @param idPersona
     * @return Persona
     */
    Persona obtenerPersonaPorId(Long idPersona);

    /**
     * Metodo para obtener una persona por RFC
     * 
     * @param rfc
     * @return Persona
     */
    Persona obtenerPersonaPorRFC(String rfc);

    Persona obtenerPersonaPorRFCUsuarios(String rfc);

    Persona obtenerPersonaPorRFCCorto(String rfc);

    /**
     * Metodo para obtener una lista de usuarios
     * 
     * @return lista de Personas
     */
    List<Persona> obtenerUsuarios();

    /**
     * Metodo para buscar una lista de personas por rol
     * 
     * @param tramite
     *            , rol
     * @return lista de Personas
     */
    List<Persona> buscarPersonaByRol(Tramite tramite, String rol);

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @param rfc
     *            , cveRol
     * @return lista de unidades administrativas
     */
    List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol);

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @param rfc
     *            , cveRol, tipoTramite
     * @return lista de unidades administrativas
     */
    List<String> obtenerUnidadesAdministrativas(String rfc, String cveRol, String tipoTramite);

    /**
     * Metodo para obtener un domicilio por id
     * 
     * @param idDomicilio
     * @return DomicilioSolicitud
     */
    DomicilioSolicitud obtenerDomicilioPorId(Long idDomicilio);

    /**
     * Metodo para buscar una persona solicitante por id de solicitud
     * 
     * @param idSolicitud
     * @return PersonaSolicitud
     */
    PersonaSolicitud buscarPersonaSolicitante(Long idSolicitud);

    /**
     * Metodo para buscar una persona representante legal por id de
     * solicitud
     * 
     * @param idSolicitud
     * @return RepresentanteLegal
     */
    RepresentanteLegal buscarPersonaRepresentanteLegal(Long idSolicitud);

    /**
     * Metodo para buscar una lista de personas por rol, sin
     * considerar la tabla RVCC_UNIADMINREL
     * 
     * @param tramite
     *            , rol
     * @return lista de Personas
     */
    List<Persona> buscarPersonaSinUARByRol(Tramite tramite, String rol);

    String obtenerAbogadoRequerimiento(Long idRequerimiento);

    Persona insertarPersona(Persona persona);

    Persona obtenerPersona(String rfc, String tipoPersona);

    /**
     * Metodo para obtener el rfc corto de una persona el rfc corto lo
     * tienen los funcionarios
     * 
     * @param rfc
     * @return rfcCorto del funcionario
     */
    String obtenerRfcCorto(String rfc);

    String obtenerRfcCortoLike(String rfcFuncionario);

    List<PersonaInterna> buscarPersonaCALByRolUnidad(Long ideTipoTramite, String unidadAdministrativa, String rol);

    List<PersonaInterna> buscarPersonaRRLByRolUnidad(Long ideTipoTramite, String unidadAdministrativa, String rol);

    void modificarPersona(Persona persona);

    List<Persona> buscarPersonasByRolReasignador(String idTipoTramite, String rol, String cveUniAdmin,
            PersonaInterna empleado);

    PersonaInterna obtenerPersonaInternaPorRFC(String rfc);

}
