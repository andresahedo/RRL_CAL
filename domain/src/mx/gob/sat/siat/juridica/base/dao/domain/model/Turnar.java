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

import javax.persistence.*;
import java.util.Date;

/**
 * Clase Model para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
@Entity
@Table(name = "RVCT_TURNADO")
public class Turnar extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 9047751233428972218L;

    /**
     * Atributo privado "idReasignacion" tipo Long
     */
    // TODO Agregar secuencia correspondiente para el guardado
    @Id
    @Basic(optional = false)
    @Column(name = "IDTURNADO ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_TURNADO")
    @SequenceGenerator(name = "RVCQ_TURNADO", sequenceName = "RVCQ_TURNADO", allocationSize = 1)
    private Long idTurnar;

    /**
     * Atributo "tramite" tipo Tramite
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo "UserStateAsigna" tipo UserState
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUSUARIOTURNA", referencedColumnName = "IDUSUARIO")
    private UserState usuarioAsigna;

    /**
     * Atributo "usuarioAnterior" tipo UserState
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUSUARIOANTERIOR", referencedColumnName = "IDUSUARIO")
    private UserState usuarioAnterior;

    /**
     * Atributo "usuarioNuevo" tipo UserState
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUSUARIONUEVO", referencedColumnName = "IDUSUARIO")
    private UserState usuarioNuevo;

    /**
     * Atributo privado "nombreTarea" tipo String
     */
    @Column(name = "NOMTAREA")
    private String nombreTarea;

    /**
     * Atributo privado "fechaAsignacion" tipo Date
     */
    @Column(name = "FECTURNADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTurnado;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo descObservacion tipo String
     */
    @Column(name = "DESCOBSERVATURNA")
    private String descObservacion;

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public UserState getUsuarioAsigna() {
        return usuarioAsigna;
    }

    public void setUsuarioAsigna(UserState usuarioAsigna) {
        this.usuarioAsigna = usuarioAsigna;
    }

    public UserState getUsuarioAnterior() {
        return usuarioAnterior;
    }

    public void setUsuarioAnterior(UserState usuarioAnterior) {
        this.usuarioAnterior = usuarioAnterior;
    }

    public UserState getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(UserState usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public Date getFechaBaja() {
        return (null == fechaBaja ? null : (Date) fechaBaja.clone());
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = (null == fechaBaja ? null : (Date) fechaBaja.clone());
    }

    public String getDescObservacion() {
        return descObservacion;
    }

    public void setDescObservacion(String descObservacion) {
        this.descObservacion = descObservacion;
    }

    public Long getIdTurnar() {
        return idTurnar;
    }

    public void setIdTurnar(Long idTurnar) {
        this.idTurnar = idTurnar;
    }

    public Date getFechaTurnado() {
        return (null == fechaTurnado ? null : (Date) fechaTurnado.clone());
    }

    public void setFechaTurnado(Date fechaTurnado) {
        this.fechaTurnado = (null == fechaTurnado ? null : (Date) fechaTurnado.clone());
    }

}
