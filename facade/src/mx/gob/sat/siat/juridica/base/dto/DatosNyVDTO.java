package mx.gob.sat.siat.juridica.base.dto;

import java.util.Date;

public class DatosNyVDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = -12330713135526714L;

    /**
     * Atributo String firma para NyV
     */
    private String firmaNyV;

    /**
     * Atrubuto String Cadena original para NyV
     */
    private String cadenaOriginalNyV;

    /**
     * Atributo Date que contiene la fecha de notificacion en NyV
     */
    private Date fechaNyV;

    public String getFirmaNyV() {
        return firmaNyV;
    }

    public void setFirmaNyV(String firmaNyV) {
        this.firmaNyV = firmaNyV;
    }

    public String getCadenaOriginalNyV() {
        return cadenaOriginalNyV;
    }

    public void setCadenaOriginalNyV(String cadenaOriginalNyV) {
        this.cadenaOriginalNyV = cadenaOriginalNyV;
    }

    public Date getFechaNyV() {
        return fechaNyV != null ? (Date) fechaNyV.clone() : null;
    }

    public void setFechaNyV(Date fechaNyV) {
        this.fechaNyV = fechaNyV != null ? (Date) fechaNyV.clone() : null;
    }
}
