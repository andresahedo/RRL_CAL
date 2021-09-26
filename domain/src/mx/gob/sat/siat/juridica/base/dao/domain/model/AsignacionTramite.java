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

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCT_ASIGNATRAMITE")
public class AsignacionTramite extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 12345456457565666L;

    /**
     * Atributo privado "idAsignacionTramite" tipo Long
     */
    @Id
    @Column(name = "IDASIGNATRAMITE", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_ASIGNATRAMITE")
    @SequenceGenerator(name = "RVCQ_ASIGNATRAMITE", sequenceName = "RVCQ_ASIGNATRAMITE", allocationSize = 1)
    private Long idAsignacionTramite;

    /**
     * Atributo privado "persona" tipo Persona
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    private PersonaInterna persona;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "estadoAsignacionTramite" tipo String
     */
    @Column(name = "IDEROL")
    private String tipoRol;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * 
     * @return idAsignacionTramite
     */
    public Long getIdAsignacionTramite() {
        return idAsignacionTramite;
    }

    /**
     * 
     * @param idAsignacionTramite
     *            a fijar
     */
    public void setIdAsignacionTramite(Long idAsignacionTramite) {
        this.idAsignacionTramite = idAsignacionTramite;
    }

    /**
     * 
     * @return tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * 
     * @param tramite
     *            a fijar
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the tipoRol
     */
    public String getTipoRol() {
        return tipoRol;
    }

    /**
     * @param tipoRol
     *            the tipoRol to set
     */
    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
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

    /**
     * @return the persona
     */
    public PersonaInterna getPersona() {
        return persona;
    }

    /**
     * @param persona
     *            the persona to set
     */
    public void setPersona(PersonaInterna persona) {
        this.persona = persona;
    }

}
