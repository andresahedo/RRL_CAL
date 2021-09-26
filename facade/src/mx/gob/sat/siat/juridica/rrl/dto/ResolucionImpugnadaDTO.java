/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.AgraviosDTO;
import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa una resolucion impuganada.
 * 
 * @author Softtek
 * 
 */
public class ResolucionImpugnadaDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = -8244555118575821941L;

    private Long id;
    private String resImpugnada;
    private String idTramite;
    private CatalogoDTO procedimiento;
    private UnidadAdministrativaDTO autoridadEmisora;
    private Date fechaEmision;
    private Boolean determinanteCred;
    private boolean importante;
    private BigDecimal monto;
    private CatalogoDTO ideSentidoResolucion;
    private List<ConceptosDTO> conceptos = new ArrayList<ConceptosDTO>();
    private List<AgraviosDTO> agravios = new ArrayList<AgraviosDTO>();

    /**
     * Auxiliar para editar la resolucion que sea seleccionada
     */
    private boolean editarResolSelected;

    public boolean isEditarResolSelected() {
        return editarResolSelected;
    }

    public void setEditarResolSelected(boolean editarResolSelected) {
        this.editarResolSelected = editarResolSelected;
    }

    /**
     * Constructor default.
     */
    public ResolucionImpugnadaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResImpugnada() {
        return resImpugnada;
    }

    public void setResImpugnada(String resImpugnada) {
        this.resImpugnada = resImpugnada;
    }

    public CatalogoDTO getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(CatalogoDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Date getFechaEmision() {
        return fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    public Boolean getDeterminanteCred() {
        return determinanteCred;
    }

    public void setDeterminanteCred(Boolean determinanteCred) {
        this.determinanteCred = determinanteCred;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public List<ConceptosDTO> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptosDTO> conceptos) {
        this.conceptos = conceptos;
    }

    public UnidadAdministrativaDTO getAutoridadEmisora() {
        return autoridadEmisora;
    }

    public void setAutoridadEmisora(UnidadAdministrativaDTO autoridadEmisora) {
        this.autoridadEmisora = autoridadEmisora;
    }

    public void setIdeSentidoResolucion(CatalogoDTO ideSentidoResolucion) {
        this.ideSentidoResolucion = ideSentidoResolucion;
    }

    public CatalogoDTO getIdeSentidoResolucion() {
        return ideSentidoResolucion;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public List<AgraviosDTO> getAgravios() {
        return agravios;
    }

    public void setAgravios(List<AgraviosDTO> agravios) {
        this.agravios = agravios;
    }

    public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public BigDecimal getMaxMontoValue() {
        if (getDeterminanteCred() != null && getDeterminanteCred()) {
            return new BigDecimal("99999999999999.99");
        }
        else {
            return new BigDecimal("100000000000000000");
        }

    }

}
