package mx.gob.sat.siat.juridica.base.excepcion;

public class PDFException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2040387171678401988L;

    public static final int PDF_PASSWORD = -1;

    private Integer causa = null;

    public PDFException() {
    }

    public PDFException(Integer causa) {
        this.causa = causa;
    }

    public Integer getCausa() {
        return causa;
    }

    public void setCause(Integer causa) {
        this.causa = causa;
    }

}
