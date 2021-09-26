/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.constants;

/**
 * 
 * @author Softtek
 *
 */
public enum TipoErrorEnvioBPM {

    FAILED("ERRENV.FL", "Instancia con error"),
    FAILED_USUARIO("ERRENV.FU", "Instancia con error por usuario no encontrado"),
    INEXISTENTE("ERRENV.IN", "Instancia no generada"),
    AUTENTICACION("ERRENV.AU", "Instancia no generada por error de administrador no existente");

    private String clave;

    private String descripcion;

    private TipoErrorEnvioBPM(final String clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public String getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoErrorEnvioBPM parse(String clave) {
        TipoErrorEnvioBPM right = null;
        for (TipoErrorEnvioBPM item : TipoErrorEnvioBPM.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
