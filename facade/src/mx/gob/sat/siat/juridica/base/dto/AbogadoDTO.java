package mx.gob.sat.siat.juridica.base.dto;

public class AbogadoDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 2729038447092383606L;
    private Long idPersona;
    private String rfc;
    private String nombreAbogado;
    private String numPendientes;
    private Long idTarea;

    public AbogadoDTO() {

    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNumPendientes() {
        return numPendientes;
    }

    public void setNumPendientes(String numPendientes) {
        this.numPendientes = numPendientes;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombreAbogado() {
        return nombreAbogado;
    }

    public void setNombreAbogado(String nombreAbogado) {
        this.nombreAbogado = nombreAbogado;
    }

}
