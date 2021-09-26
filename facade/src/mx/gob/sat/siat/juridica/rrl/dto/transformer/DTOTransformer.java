package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import java.io.Serializable;

public abstract class DTOTransformer<S, T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3116332867345048434L;

    public abstract T transformarDTO(S source);

}
