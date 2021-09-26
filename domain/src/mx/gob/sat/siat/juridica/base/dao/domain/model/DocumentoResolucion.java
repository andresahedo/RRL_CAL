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
@Table(name = "RVCA_DOCOFI_RESOL")
public class DocumentoResolucion extends BaseModel implements Serializable {

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
     * Atributo privado "resolucion" tipo Resolucion
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDRESOLUCION", referencedColumnName = "IDRESOLUCION", insertable = false, updatable = false)
    private Resolucion resolucion;

    /**
     * Atributo privado "idResolucion" tipo Long
     */
    @Column(name = "IDRESOLUCION")
    private Long idResolucion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * Atributo privado "idResolucion" tipo Long
     */
    @Column(name = "IDEESTDOCOFICIAL")
    private String estado;

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado
     *            the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

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
     * @return resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * 
     * @param resolucion
     *            a fijar
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * 
     * @return idResolucion
     */
    public Long getIdResolucion() {
        return idResolucion;
    }

    /**
     * 
     * @param idResolucion
     *            a fijar
     */
    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
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
