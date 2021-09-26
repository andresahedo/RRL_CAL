package mx.gob.sat.siat.juridica.base.constantes;

public final class ProcesosConstantes {
    /**
     * Constantes para los procesos definidos para los roles de los
     * usuarioa
     */

    private ProcesosConstantes() {
        // Se coloca constructor privado para no instarciar esta clase
    }

    public static final String PROCESO_CONTRIBUYENTE_BANDEJA_TAREAS = "RRLC00001";
    public static final String PROCESO_CONTRIBUYENTE_REGISTRO_RRL = "RRLC00002";
    public static final String PROCESO_CONTRIBUYENTE_CONSULTA_RECURSOS = "RRLC00003";
    public static final String PROCESO_CONTRIBUYENTE_ASUNTOS_PARCIALES = "RRLC00004";
    public static final String PROCESO_CONTRIBUYENTE_REGISTRO_CAL = "RRLC00005";

    public static final String PROCESO_EMPLEADO_REGISTRO_RRL = "RRL00000";
    public static final String PROCESO_EMPLEADO_BANDEJA_TAREAS = "RRL00001";
    public static final String PROCESO_EMPLEADO_REASIGNAR_RRL = "RRL00002";
    public static final String PROCESO_EMPLEADO_REASIGNAR_CAL = "RRL00007";

    // OFICIALIA DE PARTES
    public static final String PROCESO_EMPLEADO_REGISTRO_OFICIALIA = "OFP00001";
    public static final String PROCESO_EMPLEADO_RECHAZOS_OFICIALIA = "OFP00003";
    public static final String HIDROCARBUROS="Hidrocarburos";
    public static final String GRAN_CONTRIBUYENTE="Gran Contribuyente";
    public static final String PARAM_HIDROCARBUROS="HIDROCARBUROS";
    public static final String PARAM_GRANDES_CONTRIBUYENTES="GRANDES_CONTRIBUYENTES";

    //Estado procesal Tareas
    public static final String PROCESO_TAREA_PROCESO="PROCESO";
    public static final String PROCESO_TAREA_ATENDIDO="ATENDIDO";
    public static final String PROCESO_TAREA_CANCELADO="CANCELADO";

}
