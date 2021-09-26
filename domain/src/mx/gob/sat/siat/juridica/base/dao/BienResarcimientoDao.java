/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BienResarcimiento;

import java.util.List;

/**
 * Interfaz que implementa los m&eacute;todos para el DAO Bien
 * Resarcimeinto
 * 
 * @author antonio.flores Softtek.
 * @since 03/27/2014
 */
public interface BienResarcimientoDao {

    /**
     * 
     * @param bienResarcimiento
     */
    void guardaBienResarcimiento(BienResarcimiento bienResarcimiento);

    /**
     * M&eacute;todo para obtener los bienes por el id de la
     * resoluci&oacute;n.
     * 
     * @param idResolucion
     * @return
     */
    List<BienResarcimiento> obtenBienResarcimientoIdResolucion(Long idResolucion);

    /**
     * M&eacutetodo para obtener los bienres por su id.
     * 
     * @param idBienResarcimiento
     * @return Lista con los bienes
     */
    List<BienResarcimiento> obtenBienResarcimientoIdBienResarcimiento(Long idBienResarcimiento);

    /**
     * M&eacute;todo para eliminar los bienes resarcimiento de la
     * resoluci&oacute;n.
     * 
     * @param bienResarcimiento
     */
    void eliminarBienResarcimiento(BienResarcimiento bienResarcimiento);

}
