package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes;

public interface OficialiaConstantesController {

    String CONTEXT_URL = "";
    String FACES_REDIRECT = "faces-redirect=true";

    // Paginas a redireccionar
    String PROMOCION_NUEVA = CONTEXT_URL + "/resources/pages/cal/registroSolicitud/seleccionRegistroOficialia.xhtml";
    String CONSULTAR_ASUNTO = CONTEXT_URL + "/resources/pages/rrl/bandeja/bandejaOficialia.xhtml";
    String ADJUNTAR_DOCTO_CUMPLIMENTACION_OFICIALIA = CONTEXT_URL
            + "/resources/pages/consultas/adjuntarDocumentoCumpOficialia.xhtml";
    String REGISTRO_FOLIO_OFICIALIA = CONTEXT_URL + "/resources/pages/op/registroFolioOficialia.xhtml";
    String RECHAZAR_ASUNTO_OFICIALIA = CONTEXT_URL + "/resources/pages/op/rechazoAsunto.xhtml";
    String RECHAZAR_DOCUMENTO_OFICIALIA = CONTEXT_URL + "/resources/pages/op/rechazoDocumentoOficialia.xhtml";
    String PROMOCION_NUEVA_RRL_OFICIALIA = CONTEXT_URL + "/resources/pages/rrl/op/terminos.xhtml";
    String PROMOCION_NUEVA_CAL_OFICIALIA = CONTEXT_URL
            + "/resources/pages/cal/op/registroSolicitud/buscarPersonaOficialiaCA.xhtml";

}
