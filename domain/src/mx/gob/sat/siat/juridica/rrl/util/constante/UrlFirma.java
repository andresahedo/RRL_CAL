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
 * @author SAS
 * 
 */
public enum UrlFirma {
    PAGINA_FIRMA_SOLICITUD, PAGINA_FIRMA_SOLICITUD_CAL, PAGINA_FIRMA_TURNAR, PAGINA_FIRMA_REMITIR(),
    PAGINA_FIRMA_REQUERIMIENTO(), PAGINA_FIRMA_RESOLUCION(), PAGINA_FIRMA_CAPTURA_FECHA(),
    PAGINA_FIRMA_ATENDER_REQUERIMIENTO(), PAGINA_FIRMA_ATENDER_REQUERIMIENTO_AUTORIDAD(),
    PAGINA_FIRMA_CONFIRMAR_NOTIFICACION(), FIRMA_CONSULTAS(), PAGINA_FIRMA_OBSERVACION_CAL,
    PAGINA_FIRMA_OBSERVACION_RRL, PAGINA_FIRMA_RECHAZO_ASUNTO_OFICIALIA, PAGINA_FIRMA_SOLICITUD_RRL_OP,
    PAGINA_FIRMA_SOLICITUD_CAL_OP, PAGINA_FIRMA_RECHAZO_DOCUMENTO_OFICIALIA, PAGINA_FIRMA_DOCUMENTO_OFICIALIA, PAGINA_FIRMA_FALTANTES;

    @Override
    public String toString() {
        String url = "";

        if (Runtime.getInstance().isFielRequired()) {
            switch (this) {
            case PAGINA_FIRMA_SOLICITUD:
                url = "/resources/pages/firma/firmaSolicitud.xhtml";
                break;
            case PAGINA_FIRMA_TURNAR:
                url = "/resources/pages/firma/firmaTurnar.jsf";
                break;
            case PAGINA_FIRMA_REMITIR:
                url = "/resources/pages/firma/firmaRemitir.jsf";
                break;
            case PAGINA_FIRMA_REQUERIMIENTO:
                url = "/resources/pages/firma/firmaRequerimiento.jsf";
                break;
            case PAGINA_FIRMA_RESOLUCION:
                url = "/resources/pages/firma/firmaResolucion.jsf";
                break;
            case PAGINA_FIRMA_CAPTURA_FECHA:
                url = "/resources/pages/firma/firmaCapturaFecha.jsf";
                break;
            case PAGINA_FIRMA_ATENDER_REQUERIMIENTO:
                url = "/resources/pages/firma/firmarAtenderRequerimiento.jsf";
                break;
            case PAGINA_FIRMA_ATENDER_REQUERIMIENTO_AUTORIDAD:
                url = "/resources/pages/firma/firmaAtenderRequerimientoAutoridad.jsf";
                break;
            case PAGINA_FIRMA_CONFIRMAR_NOTIFICACION:
                url = "/resources/pages/firma/firmaConfirmarNotificacion.jsf";
                break;
            case FIRMA_CONSULTAS:
                url = "/resources/pages/firma/firmaConsulta.jsf";
                break;
            case PAGINA_FIRMA_SOLICITUD_CAL:
                url = "/resources/pages/firma/firmaSolicitudCAL.xhtml";
                break;
            case PAGINA_FIRMA_OBSERVACION_CAL:
                url = "/resources/pages/cal/op/observacion/firmaObservacionCAL.xhtml";
                break;
            case PAGINA_FIRMA_OBSERVACION_RRL:
                url = "/resources/pages/rrl/op/observacion/firmaObservacionRRL.xhtml";
                break;
            case PAGINA_FIRMA_RECHAZO_ASUNTO_OFICIALIA:
                url = "/resources/pages/op/firmaRechazoAsuntoOficialia.xhtml";
                break;
            case PAGINA_FIRMA_SOLICITUD_RRL_OP:
                url = "/resources/pages/rrl/op/firmaSolicitud.xhtml";
                break;
            case PAGINA_FIRMA_SOLICITUD_CAL_OP:
                url = "/resources/pages/firma/firmaSolicitudCALOP.xhtml";
                break;
            case PAGINA_FIRMA_RECHAZO_DOCUMENTO_OFICIALIA:
                url = "/resources/pages/op/firmaRechazoDocumentoOficialia.xhtml";
                break;
            case PAGINA_FIRMA_DOCUMENTO_OFICIALIA:
                url = "/resources/pages/consultas/firmaDocumentoOficialia.xhtml";
                break;
            case PAGINA_FIRMA_FALTANTES:
                url = "/resources/pages/firma/firmaAcusesFaltantes.xhtml";
                break; 
            default:
                break;
            }
        }
        else {
            switch (this) {
            case PAGINA_FIRMA_SOLICITUD:
                url = "/resources/pages/firma/estres/firmaSolicitud.xhtml";
                break;
            case PAGINA_FIRMA_TURNAR:
                url = "/resources/pages/firma/estres/firmaTurnar.jsf";
                break;
            case PAGINA_FIRMA_REMITIR:
                url = "/resources/pages/firma/estres/firmaRemitir.jsf";
                break;
            case PAGINA_FIRMA_REQUERIMIENTO:
                url = "/resources/pages/firma/estres/firmaRequerimiento.jsf";
                break;
            case PAGINA_FIRMA_RESOLUCION:
                url = "/resources/pages/firma/estres/firmaResolucion.jsf";
                break;
            case PAGINA_FIRMA_CAPTURA_FECHA:
                url = "/resources/pages/firma/estres/firmaCapturaFecha.jsf";
                break;
            case PAGINA_FIRMA_ATENDER_REQUERIMIENTO:
                url = "/resources/pages/firma/estres/firmarAtenderRequerimiento.jsf";
                break;
            case PAGINA_FIRMA_ATENDER_REQUERIMIENTO_AUTORIDAD:
                url = "/resources/pages/firma/estres/firmaAtenderRequerimientoAutoridad.jsf";
                break;
            case PAGINA_FIRMA_CONFIRMAR_NOTIFICACION:
                url = "/resources/pages/firma/estres/firmaConfirmarNotificacion.jsf";
                break;
            case FIRMA_CONSULTAS:
                url = "/resources/pages/firma/estres/firmaConsulta.jsf";
                break;
            case PAGINA_FIRMA_SOLICITUD_CAL:
                url = "/resources/pages/firma/estres/firmaSolicitudCAL.xhtml";
                break;
            case PAGINA_FIRMA_OBSERVACION_CAL:
                url = "/resources/pages/firma/estres/op/firmaObservacionCAL.xhtml";
                break;
            case PAGINA_FIRMA_OBSERVACION_RRL:
                url = "/resources/pages/firma/estres/op/firmaObservacionRRL.xhtml";
                break;
            case PAGINA_FIRMA_RECHAZO_ASUNTO_OFICIALIA:
                url = "/resources/pages/firma/estres/op/firmaRechazoAsuntoOficialia.xhtml";
                break;
            case PAGINA_FIRMA_SOLICITUD_RRL_OP:
                url = "/resources/pages/firma/estres/op/firmaSolicitud.xhtml";
                break;
            case PAGINA_FIRMA_SOLICITUD_CAL_OP:
                url = "/resources/pages/firma/estres/op/firmaSolicitudCALOP.xhtml";
                break;
            case PAGINA_FIRMA_RECHAZO_DOCUMENTO_OFICIALIA:
                url = "/resources/pages/firma/estres/op/firmaRechazoDocumentoOficialia.xhtml";
                break;
            case PAGINA_FIRMA_DOCUMENTO_OFICIALIA:
                url = "/resources/pages/firma/estres/op/firmaDocumentoOficialia.xhtml";
                break;
            default:
                break;
            }
        }

        return url;
    }
}
