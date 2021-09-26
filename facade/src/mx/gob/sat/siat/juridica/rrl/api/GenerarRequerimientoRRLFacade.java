/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface GenerarRequerimientoRRLFacade extends BaseFacade {

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     * 
     * @return Lista de CatalogoDTO
     */
    List<CatalogoDTO> obtenerTiposDeRequerimiento();

    /**
     * M&eacute;todo para obtener los autorizadores del requerimiento.
     * 
     * @param numeroAsunto
     * @return Lista de CatalogoDTO
     */
    List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto);

    /**
     * M&eacute;todo para preparar la generaci&oacute;n del
     * requerimiento.
     * 
     * @param numeroAsunto
     * @param userProfile
     * @return RequerimientoDTO
     */
    RequerimientoDTO prepararRequerimiento(String numeroAsunto, UserProfileDTO userProfile);

    /**
     * M&eacute;todo para guardar el requerimiento.
     * 
     * @param requerimiento
     * @param datosBandeja
     * @throws BusinessException 
     */
    void generarRequerimiento(String rfcAutorizador, List<RequerimientoDTO> requerimientos,
            DatosBandejaTareaDTO datosBandeja, String rfc) throws BusinessException;

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     * 
     * @return Lista de CatalogoDTOn
     */
    List<CatalogoDTO> obtenerAutoridadesRRL();

    List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud);

}
