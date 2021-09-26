/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import javax.persistence.*;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCA_USU_ROLUA")
public class UsuarioRolUnidadAdministrativa extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 51475970590440853L;

    @Id
    @Column(name = "IDUSUROLUA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_USU_ROLUA")
    @SequenceGenerator(name = "RVCQ_USU_ROLUA", sequenceName = "RVCQ_USU_ROLUA", allocationSize = 1)
    private Long idUsuarioRolUA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL")
    private Role rol;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDUNIADMIN", referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    private UserState usuario;

    @Column(name = "BLNUNIADMINPRINCIPAL")
    private Boolean uniAdminPrincipal;

    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * @return the idUsuarioRolUA
     */
    public Long getIdUsuarioRolUA() {
        return idUsuarioRolUA;
    }

    /**
     * @param idUsuarioRolUA
     *            the idUsuarioRolUA to set
     */
    public void setIdUsuarioRolUA(Long idUsuarioRolUA) {
        this.idUsuarioRolUA = idUsuarioRolUA;
    }

    /**
     * @return the rol
     */
    public Role getRol() {
        return rol;
    }

    /**
     * @param rol
     *            the rol to set
     */
    public void setRol(Role rol) {
        this.rol = rol;
    }

    /**
     * @return the unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @return the usuario
     */
    public UserState getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(UserState usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the uniAdminPrincipal
     */
    public Boolean getUniAdminPrincipal() {
        return uniAdminPrincipal;
    }

    /**
     * @param uniAdminPrincipal
     *            the uniAdminPrincipal to set
     */
    public void setUniAdminPrincipal(Boolean uniAdminPrincipal) {
        this.uniAdminPrincipal = uniAdminPrincipal;
    }

    /**
     * @return the blnActivo
     */
    public Boolean getBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(Boolean blnActivo) {
        this.blnActivo = blnActivo;
    }

}
