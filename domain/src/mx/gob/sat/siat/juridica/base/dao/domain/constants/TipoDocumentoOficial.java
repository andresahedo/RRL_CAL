/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.constants;

/**
 * 
 * @author softtek
 * 
 */
public enum TipoDocumentoOficial {


    /**
     * Estados de un Documento Oficial
     */
    ACUSE_RECEPCION_TRAMITE("TIDOF.ACRC", "Acuse de recepci\u00F3n de tr\u00E1mite"),
    ACUSE_RECEPCION_PROMOCION("TIDOF.ACPR", "Acuse de recepci\u00F3n de promoci\u00F3n"),
    ACUSE_NOTIFICACION("TIDOF.ACNT", "Acuse de Notificaci\u00F3n"), AVISE_NOTIFICACION_REQUERIMIENTO("TIDOF.AVNR",
            "Aviso de Notificaci\u00F3n de requerimiento"), ACTA_NOTIFICACION("TIDOF.ACNOT",
            "Acta de Notificaci\u00F3n"), OFICIO_RESOLUCION("TIDOF.OFRS", "Oficio de Resoluci\u00F3n"),
    OFICIO_REQUERIMIENTO("TIDOF.OFRE", "Oficio de Requerimiento"), CONSTANCIA_RECEPCION_DOCUMENTO("TIDOF.CORD",
            "Constancia de Firmado Electr\u00F3nico de Documentos"), OFICIO_REMISION("TIDOF.OFRM",
            "Oficio de Remisi\u00F3n"), FORMATO_SOLICITUD_PROMOCION("TIDOF.FSP", "Formato Solicitud Promoci\u00F3n"),
    OFICIO_ATENCION_RETRO("TIDOF.ATR", "Oficio de Atenci\u00F3n de Retroalimentacion"),
    OFICIO_TERMINOS_CONDICIONES("TIDOF.ATYC", "Acuse de T\u00E9rminos y Condiciones"),
    /**Documentos oficiales de Exclusivo de Fondo**/
    OFICIO_ADMISION_CON_AUDIENCIA("TIDOF.OACA","Oficio de Admisi\u00F3n con Audiencia"),
    OFICIO_ADMISION_SIN_AUDIENCIA("TIDOF.OASA","Oficio de Admisi\u00F3n sin Audiencia"),	
    ACTA_AUDIENCIA_CON_CONTRIBUYENTE("TIDOF.AACC","Acta de Audiencia con Contribuyente"),
    ACTA_AUDIENCIA_SIN_CONTRIBUYENTE("TIDOF.AASC","Acta de Audiencia sin Contribuyente"),
    OFICIO_CAMBIO_FECHA("TIDOF.OCFA", "Oficio Cambio Fecha de Audiencia"),
    OFICIO_AUDIENCIA_PERITO("TIDOF.OAEP", "Oficio Audiencia Especial con Perito"),
    ACTA_AUDIENCIA_PERITO("TIDOF.AUEP", "Acta de Audiencia Especial con Perito"),
    DICTAMEN_INICIAL_RRL("TIDOF.DIRR", "Dictamen Inicial del Recurso de Revocaci\u00F3n"),
    DICTAMEN_FINAL_RRL("TIDOF.DFRR", "Dictamen Final del Recurso de Revocaci\u00F3n"),
    INSTRUCTIVO_RRL("TIDOF.IRR", "Instructivo del Recurso de Revocaci\u00F3n"),
    PROTOCOLO_AUDIENCIA_CONRIBUYENTE("TIDOF.PAC", "Protocolo para Audiencia Contribuyente"),
    PROTOCOLO_AUDIENCIA_PERITO("TIDOF.PAP", "Protocolo para Audiencia con Perito"),
    OTROS("TIDOF.OTROS", "Otros"),
    ACTA_NOTIFICACION_CONTRIBUYENTE("TIDOF.ANC", "Acta de Notificación del Contribuyente"),
    ACTA_NOTIFICACION_PERITO("TIDOF.ANP", "Acta de Notificación del Perito");

    /**
     * Atributo privado Wclave" tipo String
     */
    private String clave;

    /**
     * Atributo privado "descripcion" tipo String
     */
    private String descripcion;

    /**
     * Constructor
     * 
     * @param clave
     * @param descripcion
     */
    private TipoDocumentoOficial(final String clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo que parsea el TipoDocumentoOficial
     * 
     * @param clave
     * @return right
     */
    public static TipoDocumentoOficial parse(String clave) {
        TipoDocumentoOficial right = null;
        for (TipoDocumentoOficial item : TipoDocumentoOficial.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
