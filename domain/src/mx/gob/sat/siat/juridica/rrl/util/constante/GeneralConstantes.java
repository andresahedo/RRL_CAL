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
public interface GeneralConstantes {

    /**
     * Constante para mensaje redirrecion
     */
    String MENSAJE_REDIRECT = "?msgRedirect=";
    String TAMANIO_APP = "836";
    String TIPO_TAMANIO_APP = "px";
    String TAMANIO_BUZON = "100";
    String TIPO_TAMANIO_BUZON = "%";

    /**
     * Constante para paginas de firma
     */
    String PAGINA_FIRMA_SOLICITUD = "/resources/pages/firma/firmaSolicitud.jsf";
    String PAGINA_FIRMA_TURNAR = "/resources/pages/firma/firmaTurnar.jsf";
    String PAGINA_FIRMA_REMITIR = "/resources/pages/firma/firmaRemitir.jsf";
    String PAGINA_FIRMA_REQUERIMIENTO = "/resources/pages/firma/firmaRequerimiento.jsf";
    String PAGINA_FIRMA_RESOLUCION = "/resources/pages/firma/firmaResolucion.jsf";
    String PAGINA_FIRMA_CAPTURA_FECHA = "/resources/pages/firma/firmaCapturaFecha.jsf";
    String PAGINA_FIRMA_ATENDER_REQUERIMIENTO = "resources/pages/firma/firmaRequerimiento.xhtml";
    String PAGINA_FIRMA_ATENDER_REQUERIMIENTO_AUTORIDAD =
            "/resources/pages/firma/firmaAtenderRequerimientoAutoridad.xhtml";
    String FIRMA_ATENDER_OBSERVACION_RRL = "/resources/pages/rrl/op/observacion/firmaObservacionRRL.jsf";
    String FIRMA_ATENDER_OBSERVACION_CAL = "/resources/pages/rrl/op/observacion/firmaObservacionCAL.jsf";
    
    /* Constantes para Validar PDF */
    
    int OK = 0;
    int NO_OK = -1;
    int NOMBRE_CARACTER_INVALIDO = -2;
    String CHAR_SPLIT = "\\|\\|\\?";
    int BLANK_THRESHOLD = 140;
    int PDF_INVALIDO = -3;

}
