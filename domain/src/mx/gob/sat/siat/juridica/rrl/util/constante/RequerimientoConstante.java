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
public interface RequerimientoConstante {

    /**
     * Constante para la url de contexto
     */
    String CONTEXT_URL = "";

    /**
     * Constante para la bandeja
     */
    String BANDEA_JSF = CONTEXT_URL + "/resources/pages/rrl/bandeja/bandeja.jsf";
    // Autorizar Requerimiento

    /**
     * Constantes para actividades relacionadas con un requerimiento
     */
    String AUTORIZAR_REQUERIMIENTO_RRL = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/autorizarrequerimiento/autorizar_requerimiento_rrl.jsf";
    String ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/autorizarrequerimiento/autorizar_requerimiento_anexar_rrl.jsf";
    String ANEXAR_DOCS_AUTORIZAR_REQUERIMIENTO_RRL_NYV = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/autorizarrequerimiento/autorizar_requerimiento_anexar_rrl_nyv.jsf";
    String FIRMAR_AUTORIZAR_REQUERIMIENTO_RRL = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/autorizarrequerimiento/autorizar_requerimiento_firma_rrl.jsf";
    String GENERAR_REQUERIMIENTO_RRL = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/generarrequerimiento/generar_requerimiento_rrl.jsf";
    // Atender Requerimiento
    String FIRMA_ATENDER_REQUERIMIENTO = CONTEXT_URL
            + "/resources/pages/rrl/requerimiento/atenderrequerimiento/firmarAtenderRequerimiento.jsf";
}
