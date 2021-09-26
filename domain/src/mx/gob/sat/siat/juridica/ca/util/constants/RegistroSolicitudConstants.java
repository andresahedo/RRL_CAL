/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.constants;

/**
 * 
 * @author softtek
 * 
 */
public interface RegistroSolicitudConstants {

    // Constantes generales
    String CONTEXT_URL = "";
    String FACES_REDIRECT = "?faces-redirect=true";

    // Constantes Wizard
    String DATOS_SOLICITANTE = "datosSolicitante";
    String PERSONAS_RELACIONADAS = "personasRelacionadas";
    String DATOS_PROMOCION = "datosPromocion";
    String DATOS_ADICIONALES = "datosAdicionales";
    String DATOS_SOLICITUD = "datosSolicitud";
    String SELECCION_DOCUMENTOS = "seleccionDocumentos";
    String ANEXAR_DOCUMENTOS_SOLICITUD = "anexarDocumentos";
    String FIRMAR = "firmar";

    // Paginas Registro Solicitud
    String CAPTURA_SOLICUTD = CONTEXT_URL + "/resources/pages/ca/registroSolicitud/captura_solicitud_ca.jsf";
    String SELECCIONAR_DOCUMENTOS = CONTEXT_URL + "/resources/pages/ca/registroSolicitud/seleccion_documentos_ca.jsf";
    String ANEXAR_DOCUMENTOS = CONTEXT_URL + "/resources/pages/ca/registroSolicitud/anexar_documentos_ca.jsf";
    String FIRMAR_SOLICITUD = CONTEXT_URL + "/resources/pages/ca/registroSolicitud/firmar_solicitud_ca.jsf";
    String CAPTURA_SOLICITUD_CAL = CONTEXT_URL
            + "/resources/pages/cal/registroSolicitud/captura_solicitud_ca.jsf?faces-redirect=true";
    String FIRMAR_SOLICITUD_CAL = CONTEXT_URL + "/resources/pages/firma/firmaSolicitudCAL.jsf";

    String FIRMAR_SOLICITUD_JSF = "firmar_solicitud_ca.jsf";

    // Tipos de modalidades
    String MODALIDADES_CONSULTA = "01";
    String MODALIDADES_AUTORIZACION = "02";
    String MODALIDADES_AVISO = "03";
    // Tipos de archivos
    String EXTENSION_ARCHIVO_PDF = "PDF";
    String EXTENSION_ARCHIVO_JPG = "JPG";
    // Tipos de documentos
    Integer DOCUMENTOS_OBLIGATORIOS = Integer.valueOf("1");
    Integer DOCUMENTOS_OPCIONALES = Integer.valueOf("0");

    // Mensajes de validacion
    String SOLICITUD_NO_GUARDADA_SOLICITANTE = "El solicitante no se encuentra registrado en la ventanilla.";
    String SOLICITUD_NO_GUARDADA_UA = "La solicitud no tiene asignada una unidad administrativa.";
    String SOLICITUD_NO_GUARDADA_EP = "El estado procesal seleccionado no cumple con los lineamientos.";

    String DOCUMENTOS_REQUERIDOS_EMPTY = "Debe proporcionar todos los requisitos obligatorios del asunto.";
    String ERROR_GUARDAR_ARCHIVO = "Error al intentar anexar el archivo.";
    String DOCUMENTO_DUPLICADO = "No se permite anexar archivos con el mismo nombre.";
    String TAMANIO_MAXIMO_SUPERADO_PDF = "El tama\u00F1o del archivo ha superado su limite permitido (4MB).";
    String TAMANIO_MAXIMO_SUPERADO_JPG = "El tama\u00F1o del archivo ha superado su limite permitido (4MB).";
}
