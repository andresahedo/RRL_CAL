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
public enum OrdenBandeja {

    /**
     * Enumeracion de tipo de BITACORA
     */
    BDFOLIO_TRAMITE("numeroAsunto", "BDFOLIO_TRAMITE"), DUE("fechaAsignacion", "DUE"),
    DUEREQ("fechaAsignacionRequerimiento", "DUE"), BDRFC_PROMOVENTE("rfcSolicitante", "BDRFC_PROMOVENTE"),
    BDDESCRIPCION_TIPO_TRAMITE("descripcionTipoTramite", "BDDESCRIPCION_TIPO_TRAMITE"), NAME("nombreTarea", "NAME"),
    BDESTADO_PROCESAL("estadoProcesal", "BDESTADO_PROCESAL"), OWNER("nombrePersonaAsignada", "OWNER"), FUNCIONARIO("rfcUsuarioAsignado", "FUNCIONARIO"),
    EST_PROM("edoToUser", "NAME");
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
    private OrdenBandeja(final String clave, final String descripcion) {
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
    public static OrdenBandeja parse(String clave) {
        OrdenBandeja right = null; 
        // Default

        for (OrdenBandeja item : OrdenBandeja.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
