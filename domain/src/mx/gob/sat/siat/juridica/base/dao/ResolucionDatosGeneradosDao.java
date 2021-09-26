/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;

import java.util.List;

public interface ResolucionDatosGeneradosDao {

    /**
     * M&ecuate;todo para guardar un bien generado.
     * 
     * @param resolucionDatosGenerados
     */
    ResolucionDatosGenerados crearResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados);

    /**
     * 
     * @param idResolucion
     * @return
     */
    ResolucionDatosGenerados obtenerResolucionDatosGeneradosIdResolucion(Long idResolucion);

    /**
     * 
     * @param idUnidadAdministrativa
     * @return
     */
    List<ResolucionDatosGenerados> obtenerResolucionDatosGeneradosUnidadadminn(Long idUnidadAdministrativa);

    /**
     * 
     * @param idUnidadAdministrativa
     * @return
     */
    List<ResolucionDatosGenerados> obtenerResolucionDatosGeneradosNumeroAsunto(String numeroAsunto);

    /**
     * 
     * @param resolucionDatosGenerados
     * @return
     */
    ResolucionDatosGenerados actualizaResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados);

    ResolucionDatosGenerados modificarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados);
}
