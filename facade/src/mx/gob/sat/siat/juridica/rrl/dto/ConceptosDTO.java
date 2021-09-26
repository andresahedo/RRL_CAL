package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;

import java.math.BigDecimal;

public class ConceptosDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = 675105292070350491L;
    private CatalogoDTO conceptoValor;
    private BigDecimal importe;
    private Long id;
    private boolean editarConcepto;

    public ConceptosDTO(CatalogoDTO conceptoValor, BigDecimal importe) {
        this.conceptoValor = conceptoValor;
        this.importe = importe;

    }

    public ConceptosDTO() {}

    public CatalogoDTO getConceptoValor() {
        return conceptoValor;
    }

    public void setConceptoValor(CatalogoDTO conceptoValor) {
        this.conceptoValor = conceptoValor;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEditarConcepto() {
        return editarConcepto;
    }

    public void setEditarConcepto(boolean editarConcepto) {
        this.editarConcepto = editarConcepto;
    }

}
