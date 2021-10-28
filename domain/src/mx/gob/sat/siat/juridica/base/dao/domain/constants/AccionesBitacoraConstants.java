/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.constants;

/**
 * 
 * @author softtek
 * 
 */

public interface AccionesBitacoraConstants {

    /**
     * Constantes para el registro de acciones en bitacora
     * 
     */

    String REASIGNAR_TAREAS = "EACBT.RT";
    String REVOCAR_PERMISO_TRAMITE = "EACBT.RPT";
    String MODIFICACION_DATOS_EMPLEADO = "EACBT.MDE";
    String ASIGNAR_PERMISO_TRAMITE = "EACBT.APT";
    String BAJA_TEMPORAL_EMPLEADO = "EACBT.BTE";
    String ASIGNAR_PERMISO_ADMINISTRADOR_GLOBAL = "EACBT.APAG";
    String REVOCAR_PERMISO_EMPLEADO = "EACBT.RPE";
    String ALTA_ROL = "EACBT.AR";
    String BAJA_DEFINITIVA_EMPLEADO = "EACBT.BDE";
    String ASIGNAR_PERMISO_ADMINISTRADOR_UA = "EACBT.APAUA";
    String REVOCAR_PERMISO_ADMINISTRADOR_GLOBAL = "EACBT.RPAG";
    String ASIGNAR_PERMISO_EMPLEADO = "EACBT.APE";
    String ALTA_EMPLEADO = "EACBT.AE";
    String REVOCAR_PERMISO_ADMINISTRADOR_UA = "EACBT.RPAUA";
    String ACTUALIZAR_TRAMITES_UA = "EACBT.ATUA";
    String BAJA_ROL_EMPLEADO = "EACBT.BRE";

    /*
     * Constantes para el tipo de persona que realiza la accion
     */
    String ACCION_REALIZA_SISTEMA = "EACBIT.SIS";
    String ACCION_REALIZA_EMPLEADO = "EACBIT.EMP";
    String ACCION_REALIZA_EMPLEADO_APLICA = "ACTBT1.EMP";
    String ACCION_REALIZA_UNIDAD_ADMIN = "ACTBT1.UNA";
    
    String ERROR_REGISTRO_RRL = "ERROR.RRL";
    String ERROR_REGISTRO_CAL = "ERROR.CAL";

}
