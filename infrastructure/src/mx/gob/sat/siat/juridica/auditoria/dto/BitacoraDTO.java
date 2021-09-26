/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

package mx.gob.sat.siat.juridica.auditoria.dto;


/**
 * Bean para el registro sesiones, usuarios, movimientos
 * 
 * @author Softtek
 * 
 */

public class BitacoraDTO {

    private int idMovimiento;

    private int idProceso;

    private String observaciones;

    /**
     * 
     * @return clave del movimiento
     */
    public int getIdMovimiento() {
        return idMovimiento;
    }

    /**
     * 
     * @param idMovimiento
     *            a fijar
     */
    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    /**
     * 
     * @return clave del proceso
     */
    public int getIdProceso() {
        return idProceso;
    }

    /**
     * 
     * @param idProceso
     *            a fijar
     */
    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
