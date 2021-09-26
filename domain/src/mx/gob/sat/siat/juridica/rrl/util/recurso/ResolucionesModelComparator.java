package mx.gob.sat.siat.juridica.rrl.util.recurso;

import mx.gob.sat.siat.juridica.base.dao.domain.model.ResolucionImpugnada;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparador para el ordenamiento de ResolucionImpugnada
 * 
 * @author Softtek
 * @see mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.ResolucionesComparator
 * 
 */
public class ResolucionesModelComparator implements Comparator<ResolucionImpugnada>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8052266093172463369L;

    @Override
    public int compare(ResolucionImpugnada o1, ResolucionImpugnada o2) {
        if (o1 != null && o2 != null) {
            if (o1.getBlnDeterminanteCredito().compareTo(o2.getBlnDeterminanteCredito()) == 1) {
                return 1;
            }
            else if (o1.getBlnDeterminanteCredito().compareTo(o2.getBlnDeterminanteCredito()) == -1) {
                return -1;
            }
            else {

                if (o1.getUnidadAdministrativa() != null && o2.getUnidadAdministrativa() != null) {
                    if (o1.getUnidadAdministrativa().getNivel() != null
                            && o2.getUnidadAdministrativa().getNivel() != null
                            && o1.getUnidadAdministrativa().getNivel() > o2.getUnidadAdministrativa().getNivel()) {
                        return 1;
                    }
                    else if (o1.getUnidadAdministrativa().getNivel() != null
                            && o2.getUnidadAdministrativa().getNivel() != null
                            && o1.getUnidadAdministrativa().getNivel() < o2.getUnidadAdministrativa().getNivel()) {
                        return -1;
                    }
                }
                else {
                    if (o1.getMonto() != null && o2.getMonto() != null && (o1.getMonto().compareTo(o2.getMonto()) < 0)) {
                        return 1;
                    }
                    else if (o1.getMonto() != null && o2.getMonto() != null
                            && (o1.getMonto().compareTo(o2.getMonto()) > 0)) {
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
}
