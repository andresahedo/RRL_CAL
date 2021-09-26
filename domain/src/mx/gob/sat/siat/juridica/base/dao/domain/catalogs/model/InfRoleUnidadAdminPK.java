/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author softtek
 */
@Embeddable
public class InfRoleUnidadAdminPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "claveUnidadAdministrativa" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDUNIADMIN", length = NumerosConstantes.DIEZ)
    private String claveUnidadAdministrativa;

    /**
     * Atributo privado "claveUsuario" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDUSUARIO", length = NumerosConstantes.TREINTA_DOS)
    private String claveUsuario;

    /**
     * Atributo privado "claveRol" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDROL", length = NumerosConstantes.TREINTA_DOS)
    private String claveRol;

    /**
     * Atributo privado "ideTipoTramite" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDTIPOTRAMITE", length = NumerosConstantes.SEIS)
    private Long ideTipoTramite;

    /**
     * 
     * @return ideTipoTramite
     */
    public Long getIdeTipoTramite() {
        return ideTipoTramite;
    }

    /**
     * 
     * @param ideTipoTramite
     *            a fijar
     */
    public void setIdeTipoTramite(Long ideTipoTramite) {
        this.ideTipoTramite = ideTipoTramite;
    }

    /**
     * 
     * @return claveUnidadAdministrativa
     */
    public String getClaveUnidadAdministrativa() {
        return claveUnidadAdministrativa;
    }

    /**
     * 
     * @param claveUnidadAdministrativa
     *            a fijar
     */
    public void setClaveUnidadAdministrativa(String claveUnidadAdministrativa) {
        this.claveUnidadAdministrativa = claveUnidadAdministrativa;
    }

    /**
     * 
     * @return claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * 
     * @param claveUsuario
     *            a fijar
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    /**
     * 
     * @return claveRol
     */
    public String getClaveRol() {
        return claveRol;
    }

    /**
     * 
     * @param claveRol
     *            a fijar
     */
    public void setClaveRol(String claveRol) {
        this.claveRol = claveRol;
    }

}
