package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;

public class RecursosDTO extends BaseDataTransferObject {
    /**
     * 
     */
    private static final long serialVersionUID = -3062268374425866114L;
    private CatalogoDTO recurso;

    public CatalogoDTO getRecurso() {
        return recurso;
    }

    public void setRecurso(CatalogoDTO recurso) {
        this.recurso = recurso;
    }

}
