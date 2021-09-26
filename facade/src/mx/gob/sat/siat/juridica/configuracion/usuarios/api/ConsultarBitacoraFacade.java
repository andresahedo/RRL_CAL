/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroBitacoraAGDTO;

import java.util.List;

/**
 * Clase DTO para el armado de los bienes resarcimeinto
 * 
 * @author Softtek - EQG
 * @since 02/10/2014
 */
public interface ConsultarBitacoraFacade extends BaseCloudFacade {

    /**
     * Metodo para obtener la Lista de Unidades Administrativas
     * 
     * @return List<CatalogoDTO>
     */
    List<CatalogoDTO> obtenerUnidadesAdministrativas();

    List<BitacoraDTO> obtenerDatosBitacora(FiltroBitacoraAGDTO filtroBitacora);

    List<CatalogoDTO> obtenerComboSeleccion();

    List<CatalogoDTO> obtenerComboSeleccionAplica();

    List<CatalogoDTO> obtenerUnidadAdministrativa(String cveUnidad);

    String obtenerRangoDeDias();

}
