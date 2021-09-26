/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.constante;

/**
 * 
 * @author softtek
 * 
 */
public interface TareasBPMConstantes {

    /**
     * Constantes para actividades relacionadas con una solicitud
     */
    Long TURNAR_SOLICITUD = 1L;
    Long ATENDER_SOLICITUD = 2L;
    Long AUTORIZAR_SOLICITUD = 3L;

    /**
     * Constantes para actividades relacionadas con una resolucion
     */
    Long CONFIRMAR_NOTIFICACION_RESOLUCION = 5L;
    Long REGISTRAR_FECHA = 2L;

    /**
     * Constantes para actividades relacionadas con un requerimiento
     */
    Long AUTORIZAR_REQUERIMIENTO = 6L;
    Long CONFIRMAR_NOTIFICACION_REQUERIMIENTO = 8L;
    Long CONFIRMAR_NOTIFICACION_REQUERIMIENTO2 = 10L;
    Long ATENDER_REQUERIMIENTO = 9L;
    Long ATENDER_REQUERIMIENTO2 = 11L;

    /**
     * Constantes para actividades relacionadas con una remision
     */
    Long AUTORIZAR_REMISION = 18L;
    Long NOTIFICAR_REMISION = 13L;
    Long CONFIRMAR_NOTIFICACION_REMISION = 20L;

}
