/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.BienResarcimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionDatosGenerados;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Interfazz del servicio para la generaci&oacute;n de una
 * resoluci&oacute;n.
 * 
 * @author antonio.flores Softtek
 * @since 03/27/2014
 */
public interface GenerarResolucionCALService extends Serializable {

    /**
     * M&eacute;todo para almacenar un BienResarcimiento.
     * 
     * @param bienResarcimiento
     * @return
     * @since 03/27/2014
     */
    void guardarBienResarcimiento(BienResarcimiento bienResarcimiento);

    /**
     * M&eacute;todo para guardar una resoluci&oacute;n.
     * 
     * @param resolucion
     * @return
     * @since 03/27/2014
     */
    Resolucion guardarResolucion(Resolucion resolucion);

    /**
     * M&eacute;todo para guardar los datos generados de una
     * resoluci&oacute;n.
     * 
     * @param transformarResolucionDatosGenerados
     * @since 03/27/2014
     */
    ResolucionDatosGenerados guardaDatosGenerados(ResolucionDatosGenerados transformarResolucionDatosGenerados);

    /**
     * M&eacute;todo para obtener los bienes asociados a una
     * resoluci&oacute;n
     * 
     * @param numAsunto
     * @return
     * @since 03/27/2014
     */
    List<BienResarcimiento> obtenerBienesResolucion(Long idResolucion);

    /**
     * M&eacute;todo para obtener la resoluci&oacute;n por numero de
     * asunto.
     * 
     * @param numAsunto
     * @return
     * @since 03/27/2014
     */
    Resolucion obtenerResolucionNumeroAsunto(String numAsunto);

    /**
     * M&eacute;todo para obtener la resoluci&oacute;n por numero de
     * resoluci&oacute;n.
     * 
     * @param numeroAsunto
     * @return
     * @since 03/27/2014
     */
    ResolucionDatosGenerados obtenerResolucionDatosGenerados(Long idResolucion);

    /**
     * M&eacute;todo apra obtener una resoluci&oacute;n por su Id
     * 
     * @param idResolucion
     * @return
     * @since 03/27/2014
     */
    Resolucion obtenerResolucionIdResolucion(Long idResolucion);

    /**
     * M&eacute;todo para actualizar la tarea.
     * 
     * @param idTarea
     * @param numAsunto
     * @since 03/27/2014
     */
    void actualizaDatosBP(String idTarea, String numAsunto, String usuario, String administrador);

    /**
     * M&eacute;todo para actualizar los datos generados por una
     * resoluci&oacute;n.
     * 
     * @param resolucionDatosGenerados
     * @since 03/27/2014
     */
    void actualizarResolucionDatosGenerados(ResolucionDatosGenerados resolucionDatosGenerados);

    /**
     * M&eacute;todo para actualizar una resoluci&oacute;n.
     * 
     * @param resolucion
     * @since 03/27/2014
     */
    void actualizaResolucion(Resolucion resolucion);

    /**
     * Metodo para generar la cadena original
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    String generaCadenaOriginal(long idSolicitud, Date fechaFirma, String rfcFuncionario);

    void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud);

}
