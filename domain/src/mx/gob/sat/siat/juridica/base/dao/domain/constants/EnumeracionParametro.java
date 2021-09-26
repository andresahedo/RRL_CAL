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
public enum EnumeracionParametro {

    /**
     * Enumeracion de tipo de dias
     */
    DIAS_CONF_NOTIF_RESOLUCION("DIAS_CONF_NOTIF_RESOLUCION",
            "N\u00FAmero de d\u00EDas para confirmar notificaci\u00F3n de resoluci\u00F3n"),
    DIAS_CONF_NOTIF_REQUERIMIENTO("DIAS_CONF_NOTIF_REQUERIMIENTO",
            "N\u00FAmero de d\u00EDas para confirmar notificaci\u00F3n de requerimiento"),
    DIAS_ATENDER_REQ_INFORMACION("DIAS_ATENDER_REQ_INFORMACION", "N\u00FAmero de d\u00EDas para atender requerimiento"),
    DIAS_ATENDER_REQ_INFORMACION_CAL("DIAS_ATENDER_REQ_INFO_CAL",
            "N\u00FAmero de d\u00EDas para atender requerimiento CAL"),
    DIAS_HABILES_SIG_REG_PROM("DIAS_HABILES_SIG_REG_PROM",
            "N\u00FAmero de d\u00EDas h\u00E1biles a partir del d\u00EDa h\u00E1bil siguiente de la fecha de registro de la promoci\u00F3n."),
    DIAS_HABILES_SIG_REG_DOC_APA("DIAS_HABILES_SIG_REG_DOC_APA",
            "N\u00FAmero de d\u00EDas h\u00E1biles a partir del d\u00EDa h\u00E1bil siguiente de la fecha de registro del Anuncio de Pruebas Adicionales."),
    DIAS_CONF_NOTIF_REQ_AUTORIDAD("DIAS_CONF_NOTIF_REQ_AUTORIDAD",
            "N\u00FAmero de d\u00EDas para confirmar notificaci\u00F3n de requerimiento por la Autoridad"),
    DIAS_VENCIMIENTO_CAL("DIAS_VENCIMIENTO_CAL", "N\u00FAmero de d\u00EDas vencimiento de solicitud CAL"),
    DIAS_VENCIMIENTO_CAL_CLASIF("DIAS_VENCIMIENTO_CAL_CLASIF",
            "N\u00FAmero de d\u00EDas vencimiento de solicitud CAL modalidad clasificaci\u00F3n arancelaria"),
    DIAS_HAB_CAMBIO_FEC_AUDIENCIA("DIAS_HAB_CAMBIO_FEC_AUDIENCIA","Número de días hábiles para cambiar fecha de audiencia");
    /**
     * Atributo privado "clave" tipo String
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
    private EnumeracionParametro(final String clave, final String descripcion) {
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
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo para parsear tipo EnumeracionParamtro
     * 
     * @param clave
     * @return right
     */
    public static EnumeracionParametro parse(String clave) {
        EnumeracionParametro right = null; 
        // Default

        for (EnumeracionParametro item : EnumeracionParametro.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
