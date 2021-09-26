package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@NamedQueries({
        @NamedQuery(name = "Tarea.findByNumeroAsunto",
                query = "select t from Tarea t where t.numeroAsunto = :idTramite and t.idTarea = :idTarea and t.estadoTarea = 'ESTTAR.PROCESO'"),
        @NamedQuery(name = "Tarea.findByClaveAsignado",
                query = "select t from Tarea t where t.claveAsignado=:Asignado and t.estadoTarea = 'ESTTAR.PROCESO'")})
@Table(name = "RVCT_TAREA")
public class Tarea extends BaseModel {
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3990607942633355293L;

    /**
     * Atributo privado "idtarea" tipo Long
     */
    @Id
    @Column(name = "IDTAREA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_TAREA_SEQ")
    @SequenceGenerator(name = "RVCQ_TAREA_SEQ", sequenceName = "RVCQ_TAREA_SEQ", allocationSize = 1)
    private Long idTarea;

    /**
     * Atributo privado "numeroAsunto" tipo String
     */
    @Column(name = "IDTRAMITE", length = NumerosConstantes.TREINTA)
    private String numeroAsunto;

    /**
     * Atributo privado "claveAdm" tipo String
     */
    @Column(name = "ADMIN", length = NumerosConstantes.VEINTICINCO)
    private String claveAdm;

    /**
     * Atributo privado "claveAdm" tipo String
     */
    @Column(name = "ABOGADO", length = NumerosConstantes.VEINTICINCO)
    private String claveAbogado;
    
    /**
     * Atributo privado "claveAsignado" tipo String
     */
    @Column(name = "ASIGNADO", length = NumerosConstantes.VEINTICINCO)
    private String claveAsignado;

    /**
     * Atributo privado "claveAsignado" tipo String
     */
    @Column(name = "TAREA", length = NumerosConstantes.TREINTA_DOS)
    private String tarea;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIP", length = NumerosConstantes.VEINTICINCO)
    private String descripcion;

    /**
     * Atributo privado "fechaFin" tipo Date
     */
    @Column(name = "FECFIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    /**
     * Atributo privado "estadoTarea" tipo String
     */
    @Column(name = "ESTADO")
    private String estadoTarea;


    /**
     * Atributo privado "fechaInicio" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "fechaFin" tipo Date
     */
    @Column(name = "FECACT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAct;


    @Column(name = "IDREQUERIMIENTO")
    private Long idRequerimeinto;


    /**
     *
     * @return idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     *
     * @param idTarea
     *            a fijar
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }


    /**
     *
     * @return numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     *
     * @param numeroAsunto
     *            a fijar
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     *
     * @return claveAdm
     */
    public String getClaveAdm() {
        return claveAdm;
    }

    /**
     *
     * @param claveAdm
     *            a fijar
     */
    public void setClaveAdm(String claveAdm) {
        this.claveAdm = claveAdm;
    }

    /**
     *
     * @return claveAsignado
     */
    public String getClaveAsignado() {
        return claveAsignado;
    }

    /**
     *
     * @param claveAsignado
     *            a fijar
     */
    public void setClaveAsignado(String claveAsignado) {
        this.claveAsignado = claveAsignado;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return fechaFin
     */
    public Date getFechaFin() { return fechaFin != null ? (Date) fechaFin.clone() : null; }

    public void setFechaFin(Date fechaFin){
        if (fechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFin);
            this.fechaFin = cal.getTime();
        }
        else {
            this.fechaFin = null;
        }
    }

    public String getEstadoTarea(){
        return estadoTarea;
    }

    public void setEstadoTarea(String estadoTarea){
        this.estadoTarea = estadoTarea;
    }

    /**
     *
     * @return fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    /**
     *
     * @param fechaCreacion
     *            a fijar
     */
    public void setFechaCreacion(Date fechaCreacion) {
        if (fechaCreacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCreacion);
            this.fechaCreacion = cal.getTime();
        }
        else {
            this.fechaCreacion = null;
        }
    }

    /**
     *
     * @return fechaCreacion
     */
    public Date getFechaAct() {
        return fechaAct != null ? (Date) fechaAct.clone() : null;
    }

    /**
     *
     * @param fechaAct
     *            a fijar
     */
    public void setFechaAct(Date fechaAct) {
        if (fechaAct != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAct);
            this.fechaAct = cal.getTime();
        }
        else {
            this.fechaAct = null;
        }
    }

    public Long getIdRequerimeinto() {
        return idRequerimeinto;
    }

    public void setIdRequerimeinto(Long idRequerimeinto) {
        this.idRequerimeinto = idRequerimeinto;
    }

    public String getClaveAbogado() {
        return claveAbogado;
    }

    public void setClaveAbogado(String claveAbogado) {
        this.claveAbogado = claveAbogado;
    }

}
