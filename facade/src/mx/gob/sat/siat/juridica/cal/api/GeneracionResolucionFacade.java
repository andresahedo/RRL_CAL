/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import java.util.List;

public interface GeneracionResolucionFacade extends BaseFacade {
    /**
     * 
     * @param type
     * @return
     */
    List<CatalogoDTO> obtenerResolucionCatalog(String type);

    /**
     * 
     * @param type
     * @return
     */
    List<CatalogoDTO> obtenerAutorizadores(String type);

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerMediosDeDefensa();

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    List<CatalogoDTO> obtenerMediosDeTransporte();

    /**
     * M&eacute;todo para almacenar bienResolucion.
     * 
     * @param bienResarcimientoDTO
     * @return
     */
    void guardaBienResolucion(BienResarcimientoDTO bienResarcimientoDTO);

    /**
     * M&eacute;todo para guardar una resoluci&oacute;n de tipo
     * clasificaci&oacute;n con los datos extras de la
     * clasificaci&oacute;n
     * 
     * @param dataDTO
     */
    void guardarResolucionclasificacion(ResolucionDatosGeneradosDTO dataDTO, String rfcAbogado);

    /**
     * M&eacute;tos para guardar un resarcimiento.
     * 
     * @param dataDTO
     */
    void guardarResolucionResarcimiento(ResolucionDatosGeneradosDTO dataDTO, String rfcAbogado);

    /**
     * M&eacute;todo para obtener las unidades administrativas para el
     * cbo.
     * 
     * @return
     */
    List<UnidadAdministrativaDTO> obtenerAutoridades();

    /**
     * M&eacute;todo para obtener los bienes de una resoluci&oacute;n.
     * 
     * @param filtroBandejaTareaDTO
     * @return
     */
    List<BienResarcimientoDTO> obtenerBienesResolucion(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO);

    /**
     * 
     * @param numero
     * @return
     */
    TramiteDTO obtenerSolicitud(String numero);

    /**
     * 
     * @return
     */
    List<TipoTramiteDTO> getTipoTramiteModulo(String idTipoTramite);

    /**
     * 
     * @param numeroAsunto
     * @return
     */
    ResolucionDatosGeneradosDTO obtenerResolucionDatosGenerados(String numeroAsunto);

    /**
     * 
     * @param idTarea
     * @param numAsunto
     */
    void actualizaDatosBP(DatosBandejaTareaDTO datosBandejaTareaDTO,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO);

    /**
     * M&eacute;todo para actualizar una resoluci&oacute;n al
     * autorizar.
     * 
     * @param dto
     * @param resolucionDatosGeneradosDTO
     */
    void actualizarResolucion(ResolucionDatosGeneradosDTO dto,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO);

    void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud);

    boolean obtenerBlnAvanzarResolucion(String numeroAsunto);
    
}
