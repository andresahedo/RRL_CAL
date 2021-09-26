package mx.gob.sat.siat.juridica.configuracion.usuarios.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;

public class PersonaReasignacionDTO extends BaseDataTransferObject {

    private static final long serialVersionUID = 835486349588597055L;

    private Long idPersona;
    private String rfc;
    private String nombreAbogado;
    private String numPendientes;
    private Long idTarea;
    private Long numEmpleado;

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

    public String getNombreAbogado() {
        return nombreAbogado;
    }

    public void setNombreAbogado(String nombreAbogado) {
        this.nombreAbogado = nombreAbogado;
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

    public Long getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Long numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

}
