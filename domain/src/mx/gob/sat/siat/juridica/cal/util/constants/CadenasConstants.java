/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.util.constants;

/**
 * 
 * @author softtek
 * 
 */
public interface CadenasConstants {

    /**
     * Separador
     */
    String SEPARADOR_CADENA = "|";

    /*
     * Constants for atender promocion radio selection.
     */
    String GENERAR_RESOLUCION = "1";
    String REQUERIMIENTO_RETROALIMENTACION = "2";
    String GENERAR_REMISION = "3";
    String GENERAR_OBSERVACION_OFICIALIA = "4";

    /*
     * For navigation
     */
    String GENERAR_RESOLUCION_ACTION_NAVEGATION_CLASIFICACION =
            "/resources/pages/cal/generacion/generarResolucionClasificacion.jsf?faces-redirect=true";
    String GENERAR_RESOLUCION_ACTION_NAVEGATION_RESOLUCION =
            "/resources/pages/cal/generacion/generarResolucionResarcimiento.jsf?faces-redirect=true";
    String REQUERIMIENTO_RETROALIMENTACION_ACTION_NAVEGATION =
            "/resources/pages/cal/requerimiento/generarRequerimiento.jsf?faces-redirect=true";
    String GENERAR_REMISION_ACTION_NAVEGATION =
            "/resources/pages/abogado/emitirresolucion/remitir.jsf?faces-redirect=trues";
    String GENERAR_RESOLUCION_ACTION_NAVEGATION_AUTORIZACION =
            "/resources/pages/cal/generacion/generarResolucionAutorizacion.jsf?faces-redirect=true";
    String GENERAR_OBSERVACION_OFICIALIA_ACTION_NAVEGATION =
            "/resources/pages/cal/observacion/generarObservacionCAL.jsf?faces-redirect=true";
    String ATENDER_OBSERVACION_OFICIALIA_ACTION_NAVEGATION =
            "/resources/pages/cal/observacion/atenderObservacionCAL.jsf?faces-redirect=true";

    /*
     * Panel type
     */
    String PANEL_TIPO_CLASIFICACION = "1";
    String PANEL_TIPO_RESARCIMIENTO = "2";

    /*
     * Resolucion type
     */
    String RESOLUCION_TYPE_CONSULTA = "CONSEN";
    String RESOLUCION_TYPE_AUTORIZACION = "AUTSEN";
    String RESOLUCION_TYPE_AVISO = "AVISEN";
    String RESOLUCION_TYPE_CONSULTA_DESCRIPCION = "Consulta";
    String RESOLUCION_TYPE_AUTORIZACION_DESCRIPCION = "Autorizacion";
    String RESOLUCION_TYPE_AVISO_DESCRIPCION = "Aviso";
    
}
