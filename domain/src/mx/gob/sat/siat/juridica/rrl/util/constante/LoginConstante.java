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
public interface LoginConstante {

    /**
     * Constante para la url de contexto
     */
    String CONTEXT_URL = "";

    /**
     * Constantes de la bandeja
     */
    String BANDEA_JSF = "/resources/pages/rrl/bandeja/bandeja.jsf";
    String BANDEJA_ASUNTOS_CONCLUIDOS= "/resources/pages/rrl/bandeja/BandejaAsuntosConcluidos.jsf";
    String BANDEA_TURNAR_JSF = "/resources/pages/rrl/bandeja/bandejaTurnar.jsf";
    String BANDEJA_CONSULTAS_AUTORIZACIONES_JSF = "/resources/pages/ca/consultas/bandejaConsultas.jsf";
    String BANDEJA_RETURNAR_JSF = "/resources/pages/rrl/bandejaReasignarRecursoRevocacion.jsf";
    String REGISTRO_JSF = "/resources/pages/rrl/capturaSolicitud.jsf";
    String BANDEJA_REMISION = CONTEXT_URL + "/resources/pages/rrl/bandeja/bandeja.jsf";
    String DESCARGA_DOCUMENTO = "/resources/pages/rrl/descargarDocumento.jsf";
    String FIRMA_CONSULTAS = "/resources/pages/consultas/firmaConsulta.jsf";
    String BANDEJA_BUSCAR_PROMOCION = "/resources/pages/consultas/bandejaConsultas.jsf";
    String FIRMA_DOCUMENTO_OFICIALIA = CONTEXT_URL + "/resources/pages/consultas/firmaDocumentoOficialia.xhtml";

}
