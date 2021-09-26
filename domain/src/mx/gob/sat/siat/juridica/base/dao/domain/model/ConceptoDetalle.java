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
@DiscriminatorValue("RRCONC")
public class ConceptoDetalle extends ResolucionImpugnadaCatalogoD {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 464114324252214739L;

    /**
     * Atributo privado "resolucionImpuganda" tipo ResolucionImpugnada
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
    public ConceptoDetalle() {
        super();
    }

    /**
     * 
     * @param idResolucionCatD
     *            a fijar
     */
    public ConceptoDetalle(Long idResolucionCatD) {
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
