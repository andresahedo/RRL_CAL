/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.auditoria.controller;

import mx.gob.sat.siat.juridica.auditoria.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.auditoria.interfaces.Auditable;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;

/**
 * 
 * @author softtek
 * 
 */
public abstract class BaseAuditoriaControllerBean extends BaseControllerBean implements Auditable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -5476225162782762604L;
    private BitacoraDTO bitacoraDTO = new BitacoraDTO();

    /**
     * Metodo que obtiene los valores de la bitacora
     */
    public BitacoraDTO getBitacoraDTO() {
        return this.bitacoraDTO;
    }

    public void guardarDatosBitacora(int idProceso, int idMovimiento, String observaciones) {
        bitacoraDTO.setIdProceso(idProceso);
        bitacoraDTO.setIdMovimiento(idMovimiento);
        bitacoraDTO.setObservaciones(observaciones);
    }

    public void guardarObservacionesBitacora(String observaciones) {
        bitacoraDTO.setObservaciones(observaciones);
    }

}
