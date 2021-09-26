/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

import java.util.Date;

/**
 * DTO que representa los datos de una solicitud.
 * 
 * @author Softtek
 * 
 */
public class RemisionDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = -9080983534763646760L;

    /**
     * Numero de asunto
     */
    private String numAsunto;

    /**
     * Identificador de la Autoridad Emisora
     */
    private String idAtuoridadEmisora;

    /**
     * Fecha de la creracion de la remision
     */
    private Date fecCreacion;

    /**
     * Fecha de autorizacion de la remision.
     */
    private Date fecAutorizacion;

    /**
     * @return the numAsunto
     */
    public String getNumAsunto() {
        return numAsunto;
    }

    /**
     * @param numAsunto
     *            the numAsunto to set
     */
    public void setNumAsunto(String numAsunto) {
        this.numAsunto = numAsunto;
    }

    /**
     * @return the idAtuoridadEmisora
     */
    public String getIdAtuoridadEmisora() {
        return idAtuoridadEmisora;
    }

    /**
     * @param idAtuoridadEmisora
     *            the idAtuoridadEmisora to set
     */
    public void setIdAtuoridadEmisora(String idAtuoridadEmisora) {
        this.idAtuoridadEmisora = idAtuoridadEmisora;
    }

    /**
     * @return the fecCreacion
     */
    public Date getFecCreacion() {
        return fecCreacion != null ? (Date) fecCreacion.clone() : null;
    }

    /**
     * @param fecCreacion
     *            the fecCreacion to set
     */
    public void setFecCreacion(Date fecCreacion) {
        this.fecCreacion = fecCreacion != null ? (Date) fecCreacion.clone() : null;
    }

    /**
     * @return the fecAutorizacion
     */
    public Date getFecAutorizacion() {
        return fecAutorizacion != null ? (Date) fecAutorizacion.clone() : null;
    }

    /**
     * @param fecAutorizacion
     *            the fecAutorizacion to set
     */
    public void setFecAutorizacion(Date fecAutorizacion) {
        this.fecAutorizacion = fecAutorizacion != null ? (Date) fecAutorizacion.clone() : null;
    }

}
