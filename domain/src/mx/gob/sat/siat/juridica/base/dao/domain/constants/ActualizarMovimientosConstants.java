package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public interface ActualizarMovimientosConstants {

    // Tipos de movimientos
    String BAJA_TEMPORAL = "ETNVL.BT";
    String BAJA_DEFINITIVA = "ETNVL.BD";
    String BAJA_ROL = "ETNVL.BR";
    String ALTA_EMPLEADO = "ETNVL.AE";
    String ALTA_ROL = "ETNVL.AR";
    String MODIFICACION_EMPLEADO = "ETNVL.MD";

    // Actores para registro en Bitacora
    String ACTOR_EMPLEADO = "EACBIT.EMP";
    String ACTOR_SISTEMA = "EACBIT.SIS";
    String ACTOR_APLICADO_EMPLEADO = "ACTBT1.EMP";

    // Estado de la actualizaci&oacute;n
    String EDO_INSERTADO = "CSTCJV.IN";
    String EDO_PROCESADO = "CSTCJV.PR";
    String EDO_EXCEPCION = "CSTCJV.EX";
    String EDO_NOTIFICADO = "CSTCJV.NT";

    // Descripciones estado de la actualizacion
    String EDO_EXITOSO_DESC = "Exitoso";
    String EDO_EXCEPCION_DESC = "Excepci&oacute;n";

}
