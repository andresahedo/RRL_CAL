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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "RVCB_BITACORA")
public class Bitacora extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2588037366270937532L;

    /**
     * Nuacute;mero de serie
     */

    /**
     * Atributo privado "idBitacora" tipo Long
     */
    @Id
    @Column(name = "IDBITACORA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_BITACORA")
    @SequenceGenerator(name = "RVCQ_BITACORA", sequenceName = "RVCQ_BITACORA", allocationSize = 1)
    private Long idBitacora;

    /**
     * Atributo privado "unidadAdministrativa" tipo Resolucion
     */
    @Column(name = "IDUNIDADMIN")
    private String unidadAdministrativa;

    /**
     * Atributo privado "tarea" tipo Resolucion
     */
    @Column(name = "IDETAREA")
    private String tarea;

    /**
     * Atributo privado "rfcUsuario" tipo Resolucion
     */
    @Column(name = "IDUSUARIO")
    private String rfcUsuario;

    /**
     * Atributo privado "fechaEvento" tipo Date
     */
    @Column(name = "FECEVENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEvento;

    /**
     * Atributo privado "numeroAsunto" tipo Resolucion
     */
    @Column(name = "IDTRAMITE")
    private String numeroAsunto;

    /**
     * Atributo privado "rfcActual" tipo Resolucion
     */
    @Column(name = "RFCACTUAL")
    private String rfcActual;

    /**
     * Atributo privado "rfcNuevo" tipo Resolucion
     */
    @Column(name = "RFCNUEVO")
    private String rfcNuevo;

    /**
     * 
     * @return fecha
     */
    public Date getFechaEvento() {
        return fechaEvento != null ? (Date) fechaEvento.clone() : null;
    }

    /**
     * 
     * @param fechaEvento
     *            a fijar
     */
    public void setFechaEvento(Date fechaEvento) {
        if (fechaEvento != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaEvento);
            this.fechaEvento = cal.getTime();
        }
        else {
            this.fechaEvento = null;
        }
    }

    /**
     * @return the idBitacora
     */
    public Long getIdBitacora() {
        return idBitacora;
    }

    /**
     * @param idBitacora
     *            the idBitacora to set
     */
    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    /**
     * @return the unidadAdministrativa
     */
    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @return the tarea
     */
    public String getTarea() {
        return tarea;
    }

    /**
     * @param tarea
     *            the tarea to set
     */
    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    /**
     * @return the rfcUsuario
     */
    public String getRfcUsuario() {
        return rfcUsuario;
    }

    /**
     * @param rfcUsuario
     *            the rfcUsuario to set
     */
    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    /**
     * @return the numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * @param numeroAsunto
     *            the numeroAsunto to set
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * @return the rfcActual
     */
    public String getRfcActual() {
        return rfcActual;
    }

    /**
     * @param rfcActual
     *            the rfcActual to set
     */
    public void setRfcActual(String rfcActual) {
        this.rfcActual = rfcActual;
    }

    /**
     * @return the rfcNuevo
     */
    public String getRfcNuevo() {
        return rfcNuevo;
    }

    /**
     * @param rfcNuevo
     *            the rfcNuevo to set
     */
    public void setRfcNuevo(String rfcNuevo) {
        this.rfcNuevo = rfcNuevo;
    }
}
