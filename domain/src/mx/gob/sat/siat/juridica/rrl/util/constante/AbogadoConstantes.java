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
 */
public interface AbogadoConstantes {

    /**
     * Costantes para remitir
     */
    String REMITIR_JSF = "/resources/pages/abogado/emitirresolucion/remitir.jsf";
    String EMITIR_JSF = "/resources/pages/abogado/emitirresolucion/emitir_resolucion.jsf";
    String OBSERVAR_JSF = "/resources/pages/abogado/observacion/generarObservacion.jsf";

    /**
     * Constante para regresar a abogado captura
     */
    String ANTERIOR_GENERAR_REQUERIMIENTO_JSF = "/resources/pages/abogado/abogadoCaptura.jsf";

    /**
     * Constante para mensaje de resolucion
     */
    String MENSAJE_RESOLUCION_JSF = "/resources/pages/abogado/emitirresolucion/mensaje_resolucion.jsf";

    /**
     * Cosntante para generar un requerimiento
     */
    String GENERAR_REQUERIMIENTO =
            "/resources/pages/rrl/requerimiento/generarrequerimiento/generar_requerimiento_rrl.jsf";

    /**
     * Cosntante para atender un asunto
     */
    String ATENDER_ASUNTO = "/resources/pages/abogado/abogadoCaptura.jsf";

    /**
     * Constante para validar una resolucion repetida o duplicada
     */
    Integer RESOLUCION_REPETIDA = 1;
    Integer RESOLUCION_DUPLICADA = 0;
    Integer CONCEPTO_DUPLICADO = 2;
    String FIRMAR_REGISTRO_FECHA = "/resources/pages/abogado/firmaRegistroFecha.jsf";

    String MENSAJE_ABOGADO_NO_BP = "Este usuario no existe en el gestor de procesos.";

}
