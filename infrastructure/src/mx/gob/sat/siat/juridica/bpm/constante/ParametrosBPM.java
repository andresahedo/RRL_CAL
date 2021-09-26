/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
* parcial o total.
*/
package mx.gob.sat.siat.juridica.bpm.constante;

/**
 * Clase que define constantes para BPM 
 * @author softtek
 *
 */
public interface ParametrosBPM {
    
       String NOMBRE_CONSULTA = "BandejaTareasSIAT";
       String NOMBRE_APLICACION_PROCESO = "Sistema Integral de Administracion Tributaria";
    
       String FOLIO_TRAMITE = "BDFOLIO_TRAMITE";
       String ESTADO_PROCESAL = "BDESTADO_PROCESAL";
       String RFC_PROMOVENTE = "BDRFC_PROMOVENTE";
       String ABOGADO = "BDABOGADO";
       String ADMINISTRADOR = "BDADMINISTRADOR";
       String OFICIAL_DE_PARTES = "BDOFICIAL_DE_PARTES";
       String UNIDAD_ADMIN = "BDUNIDAD_ADMIN";
       String ID_SOLICITUD = "BDID_SOLICITUD";
       String TIPO_TRAMITE = "BDTIPO_TRAMITE";
       String DESCRIPCION_TIPO_TRAMITE = "BDDESCRIPCION_TIPO_TRAMITE";
       String ID_SUB_PROCESO = "idSubProceso";
       String TIPO_REQUERIMIENTO = "tipoRequerimiento";
       String FECHA_INICIO = "startTime";
       String FECHA_INICIO_ATENDER_REQ = "startTaskAtenderRequerimientoOrigen";
    
       String DUE = "DUE";
       String OWNER = "OWNER";    
       String NAME = "NAME";
       String ID_TAREA = "TASK.TKIID";
       String ID_INSTANCIA = "PROCESS_INSTANCE.PIID";
       String ESTADO_TAREA = "STATE";
       String ESTADO_TAREA_RECLAMADO = "STATE_CLAIMED";
       String ESTADO_TAREA_DISPONIBLE = "STATE_READY";
    
       // Reprocesamiento
       String STR_TRAMITE = "tramite";
       String STR_JSON = "json";       
       String STR_BPDID = "bpdId";       
       String STR_PROCESSAPPID = "processAppId";       
       String STR_TIPOREPROCESO = "tipoReproceso";       
       String STR_DATA = "data";       
       String STR_FAILED = "Failed";       
       String STR_EXECUTIONSTATE = "executionState";       
       String STR_PIID = "piid";       
       String STR_INSTANCEERROR = "instanceError";
       String STR_FOLIOTRAMITE = "folioTramite";
       
       //Tipos de reproceso
       String INICIAR_TRAMITE = "iniciarTramite";
       String REINTENTAR_INICIAR = "reintentarIniciar";
       String REINTENTAR_COMPLETAR_TAREA = "reintentarCompletarTarea";
       
       //Errores BPM
       String ERROR_USUARIO = "CWLLG2221E";

}
