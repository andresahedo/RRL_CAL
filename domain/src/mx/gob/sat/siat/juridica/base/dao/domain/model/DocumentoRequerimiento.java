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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Softtek
 * 
 */
@Entity
@Table(name = "RVCA_DOCOFI_REQ")
public class DocumentoRequerimiento extends BaseModel implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDocumentoOficial" tipo Long
     */
    @Id
    @Column(name = "IDDOCOFICIAL")
    private Long idDocumentoOficial;

    /**
     * Atributo privado "documentoOficial" tipo DocumentoOficial
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDDOCOFICIAL", referencedColumnName = "IDDOCOFICIAL", insertable = false, updatable = false)
    private DocumentoOficial documentoOficial;

    /**
     * Atributo privado "requerimiento" tipo Requerimiento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDREQUERIMIENTO", referencedColumnName = "IDREQUERIMIENTO", insertable = false,
            updatable = false)
    private Requerimiento requerimiento;

    /**
     * Atributo privado "idRequerimiento" tipo Long
     */
    @Column(name = "IDREQUERIMIENTO")
    private Long idRequerimiento;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * 
     * @return idDocumentoOficial
     */
    public Long getIdDocumentoOficial() {
        return idDocumentoOficial;
    }

    /**
     * 
     * @param idDocumentoOficial
     *            a fijar
     */
    public void setIdDocumentoOficial(Long idDocumentoOficial) {
        this.idDocumentoOficial = idDocumentoOficial;
    }

    /**
     * 
     * @return documentoOficial
     */
    public DocumentoOficial getDocumentoOficial() {
        return documentoOficial;
    }

    /**
     * 
     * @param documentoOficial
     *            a fijar
     */
    public void setDocumentoOficial(DocumentoOficial documentoOficial) {
        this.documentoOficial = documentoOficial;
    }

    /**
     * 
     * @return requerimiento
     */
    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    /**
     * 
     * @param requerimiento
     *            a fijar
     */
    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    /**
     * 
     * @return idRequerimiento
     */
    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * 
     * @param idRequerimiento
     *            a fijar
     */
    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    /**
     * 
     * @param fechaBaja
     *            a fijar
     */
    public void setFechaBaja(Date fechaBaja) {
        if (fechaBaja != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaBaja);
            this.fechaBaja = cal.getTime();
        }
        else {
            this.fechaBaja = null;
        }
    }

}
