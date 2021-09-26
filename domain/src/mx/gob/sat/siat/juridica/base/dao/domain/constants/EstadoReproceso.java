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
public enum EstadoReproceso {

    EXITOSO("ESTREP.SC", "Tramite reprocesado exitosamente"),
    EXITOSO_NO_REPROCESO("ESTREP.SR", "Tramite en \u00e9xito, no reprocesado"),
    ERROR("ESTREP.ER", "Tramite reprocesado no generado"),
    PENDIENTE("ESTREP.PN", "Tramite no reprocesado");

    private String clave;

    private String descripcion;

    private EstadoReproceso(String clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public String getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoReproceso parse(String clave) {
        EstadoReproceso right = null;
        for (EstadoReproceso item : EstadoReproceso.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }
        return right;
    }

}
