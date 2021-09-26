/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import javax.persistence.*;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue("RRAGRA")
public class AgravioDetalle extends ResolucionImpugnadaCatalogoD {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -4871880116480017101L;

    /**
     * Atributo privado "resolucionImpugnada" tipo ResolucionImpugnada
     */
    @JoinColumn(name = "IDRESOLIMPUG", referencedColumnName = "IDRESOLIMPUG")
    @ManyToOne(fetch = FetchType.LAZY)
    private ResolucionImpugnada resolucionImpugnada;

    /**
     * 
     * @return resolucionImpugnada
     */
    public ResolucionImpugnada getResolucionImpugnada() {
        return resolucionImpugnada;
    }

    /**
     * 
     * @param resolucionImpugnada
     *            a fijar
     */
    public void setResolucionImpugnada(ResolucionImpugnada resolucionImpugnada) {
        this.resolucionImpugnada = resolucionImpugnada;
    }

    /**
     * Constructor
     */
    public AgravioDetalle() {
        super();
    }

    /**
     * 
     * @param idResolucionCatD
     *            a fijar
     */
    public AgravioDetalle(Long idResolucionCatD) {
        super(idResolucionCatD);
    }

    /**
     * Sobreescritura del metodo hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((resolucionImpugnada == null) ? 0 : resolucionImpugnada.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResolucionImpugnadaCatalogoD)) {
            return false;
        }
        ResolucionImpugnadaCatalogoD other = (ResolucionImpugnadaCatalogoD) obj;
        if (getIdResolucionImpugnadaCatD() == null) {
            if (other.getIdResolucionImpugnadaCatD() != null) {
                return false;
            }
        }
        else if (!getIdResolucionImpugnadaCatD().equals(other.getIdResolucionImpugnadaCatD())) {
            return false;
        }
        return true;
    }

}
