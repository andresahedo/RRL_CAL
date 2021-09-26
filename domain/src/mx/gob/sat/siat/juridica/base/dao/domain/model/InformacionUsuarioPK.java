/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author softtek
 * 
 */
@Embeddable
public class InformacionUsuarioPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idUsuario" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDUSUARIO", length = NumerosConstantes.QUINCE)
    private String idUsuario;

    /**
     * Atributo privado "idRol" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDROL", length = NumerosConstantes.QUINCE)
    private String idRol;

    /**
     * 
     * @param idUsuario
     *            a fijar
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * 
     * @return idUsuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * 
     * @param idRol
     *            a fijar
     */
    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    /**
     * 
     * @return adRol
     */
    public String getIdRol() {
        return idRol;
    }

}
