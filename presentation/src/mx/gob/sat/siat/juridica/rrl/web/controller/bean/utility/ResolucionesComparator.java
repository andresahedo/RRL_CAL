package mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import java.io.Serializable;
import java.util.Comparator;

public class ResolucionesComparator implements Comparator<ResolucionImpugnadaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4459553770956361613L;

    @Override
    public int compare(ResolucionImpugnadaDTO o1, ResolucionImpugnadaDTO o2) {
        // TODO Auto-generated method stub
        return (o2.getMonto()).compareTo(o1.getMonto());
    }

}
