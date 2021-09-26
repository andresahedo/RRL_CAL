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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import javax.persistence.*;
import java.util.Date;

/**
 * Clase Model para la bitacora
 * 
 * @author Softtek - EQG
 * @since 02/10/2014
 */
@Entity
@Table(name = "RVCB_BITACORAAU")
public class BitacoraAU extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5683052455768229576L;

    /**
     * Atributo privado "idBitacora" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDBITACORAAU ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_BITACORAAU")
    @SequenceGenerator(name = "RVCQ_BITACORAAU", sequenceName = "RVCQ_BITACORAAU", allocationSize = 1)
    private Long idBitacora;

    /**
     * Atributo "tipoTramite" tipo TipoTramite
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDTIPOTRAMITE", referencedColumnName = "IDTIPOTRAMITE")
    private TipoTramite tipoTramite;

    /**
     * Atributo "tramite" tipo Tramite
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "idRol" tipo String
     */
    @Column(name = "IDROL")
    private String idRol;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIADMIN", nullable = true, referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "idAccion" tipo String
     */
    @Column(name = "IDEACCION")
    private String idAccion;

    /**
     * Atributo privado "fechaAccion" tipo Date
     */
    @Column(name = "FECHAACCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAccion;

    /**
     * Atributo privado "nombreUnidadAdmin" tipo String
     */
    @Column(name = "NOMUNIADMIN")
    private String nombreUnidadAdmin;

    /**
     * Atributo privado "idRealizadoPor" tipo String
     */
    @Column(name = "IDEREALIZADOPOR")
    private String idRealizadoPor;

    /**
     * Atributo privado "idBitacora" tipo String
     */
    @Column(name = "NUMEMPLEADORP ")
    private String numEmpleadoRP;

    /**
     * Atributo privado "rfcRP" tipo String
     */
    @Column(name = "RFCRP")
    private String rfcRP;

    /**
     * Atributo privado "nombreRP" tipo String
     */
    @Column(name = "NOMBRERP")
    private String nombreRP;

    /**
     * Atributo privado "idAplicadoA" tipo String
     */
    @Column(name = "IDEAPLICADOA")
    private String idAplicadoA;

    /**
     * Atributo privado "numEmpleadoAA" tipo String
     */
    @Column(name = "NUMEMPLEADOAA")
    private String numEmpleadoAA;

    /**
     * Atributo privado "rfcAA" tipo String
     */
    @Column(name = "RFCRPAA")
    private String rfcAA;

    /**
     * Atributo privado "nombreAA" tipo String
     */
    @Column(name = "NOMBRERPAA")
    private String nombreAA;

    /**
     * Atributo privado "descripcion" tipo Long
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "idModalidad" tipo String
     */
    @Column(name = "IDMODALIDAD ")
    private Long idModalidad;

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    public String getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(String idAccion) {
        this.idAccion = idAccion;
    }

    public Date getFechaAccion() {
        return (null == fechaAccion ? null : (Date) fechaAccion.clone());
    }

    public void setFechaAccion(Date fechaAccion) {
        this.fechaAccion = (null == fechaAccion ? null : (Date) fechaAccion.clone());
    }

    public String getNombreUnidadAdmin() {
        return nombreUnidadAdmin;
    }

    public void setNombreUnidadAdmin(String nombreUnidadAdmin) {
        this.nombreUnidadAdmin = nombreUnidadAdmin;
    }

    public String getIdRealizadoPor() {
        return idRealizadoPor;
    }

    public void setIdRealizadoPor(String idRealizadoPor) {
        this.idRealizadoPor = idRealizadoPor;
    }

    public String getNumEmpleadoRP() {
        return numEmpleadoRP;
    }

    public void setNumEmpleadoRP(String numEmpleadoRP) {
        this.numEmpleadoRP = numEmpleadoRP;
    }

    public String getRfcRP() {
        return rfcRP;
    }

    public void setRfcRP(String rfcRP) {
        this.rfcRP = rfcRP;
    }

    public String getNombreRP() {
        return nombreRP;
    }

    public void setNombreRP(String nombreRP) {
        this.nombreRP = nombreRP;
    }

    public String getIdAplicadoA() {
        return idAplicadoA;
    }

    public void setIdAplicadoA(String idAplicadoA) {
        this.idAplicadoA = idAplicadoA;
    }

    public String getNumEmpleadoAA() {
        return numEmpleadoAA;
    }

    public void setNumEmpleadoAA(String numEmpleadoAA) {
        this.numEmpleadoAA = numEmpleadoAA;
    }

    public String getRfcAA() {
        return rfcAA;
    }

    public void setRfcAA(String rfcAA) {
        this.rfcAA = rfcAA;
    }

    public String getNombreAA() {
        return nombreAA;
    }

    public void setNombreAA(String nombreAA) {
        this.nombreAA = nombreAA;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }

}
