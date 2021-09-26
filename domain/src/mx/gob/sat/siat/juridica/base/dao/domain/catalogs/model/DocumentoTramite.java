/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCD_DOC_TRAMITE")
public class DocumentoTramite extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "documentoTramitePK" tipo DocumentoTramitePK
     */
    @EmbeddedId
    private DocumentoTramitePK documentoTramitePK;

    /**
     * Atributo privado "tipoDoc" tipo TipoDocumento
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTIPODOCUMENTO", insertable = false, updatable = false)
    private TipoDocumento tipoDoc;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTIPOTRAMITE", insertable = false, updatable = false)
    private TipoTramite tipoTramite;

    /**
     * Atributo privado "especifico" tipo Integer
     */
    @Column(name = "BLNESPECIFICO")
    private Integer especifico;

    /**
     * Atributo privado "especifico" tipo Integer
     */
    @Column(name = "ORDEN")
    private Integer orden;

    /**
     * Atributo privado "clasificacionDocumento" tipo String
     */
    @Column(name = "IDECLASIFICACIONDOCUMENTO")
    private String clasificacionDocumento;

    /**
     * Atributo privado "tipoSolicitanteRFE" tipo String
     */
    @Column(name = "IDETIPOSOLICITANTERFE")
    private String tipoSolicitanteRFE;

    /**
     * Atributo privado "soloAnexar" tipo Boolean
     */
    @Column(name = "BLNSOLOANEXAR")
    private Boolean soloAnexar;

    /**
     * Atributo privado "reglaAnexado" tipo String
     */
    @Column(name = "IDEREGLAANEXADO")
    private String reglaAnexado;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public DocumentoTramite() {
        documentoTramitePK = new DocumentoTramitePK();
    }

    /**
     * Sobrecarga del Constructor
     * 
     * @param tipoDoc
     *            , tipoTramite
     */
    public DocumentoTramite(Integer tipoDoc, Integer tipoTramite) {
        documentoTramitePK = new DocumentoTramitePK(tipoDoc, tipoTramite);
    }

    /**
     * 
     * @return documentoTramitePK
     */
    public DocumentoTramitePK getDocumentoTramitePK() {
        if (this.documentoTramitePK == null) {
            this.documentoTramitePK = new DocumentoTramitePK();
        }
        return this.documentoTramitePK;
    }

    /**
     * 
     * @param documentoTramitePK
     *            a fijar
     */
    public void setDocumentoTramitePK(DocumentoTramitePK documentoTramitePK) {
        this.documentoTramitePK = documentoTramitePK;
    }

    /**
     * 
     * @return tipoDoc
     */
    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    /**
     * 
     * @param tipoDoc
     *            a fijar
     */
    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    /**
     * 
     * @return tipoTramite
     */
    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    /**
     * 
     * @param tipoTramite
     *            a fijar
     */
    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * 
     * @return especifico
     */
    public Integer getEspecifico() {
        return especifico;
    }

    /**
     * 
     * @param especifico
     *            a fijar
     */
    public void setEspecifico(Integer especifico) {
        this.especifico = especifico;
    }

    /**
     * 
     * @return clasificacionDocumento
     */
    public String getClasificacionDocumento() {
        return clasificacionDocumento;
    }

    /**
     * 
     * @param clasificacionDocumento
     *            a fijar
     */
    public void setClasificacionDocumento(String clasificacionDocumento) {
        this.clasificacionDocumento = clasificacionDocumento;
    }

    /**
     * 
     * @return tipoSolicitanteRFE
     */
    public String getTipoSolicitanteRFE() {
        return tipoSolicitanteRFE;
    }

    /**
     * 
     * @param tipoSolicitanteRFE
     *            a fijar
     */
    public void setTipoSolicitanteRFE(String tipoSolicitanteRFE) {
        this.tipoSolicitanteRFE = tipoSolicitanteRFE;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * 
     * @return soloAnexar
     */
    public Boolean getSoloAnexar() {
        return soloAnexar;
    }

    /**
     * 
     * @param soloAnexar
     *            a fijar
     */
    public void setSoloAnexar(Boolean soloAnexar) {
        this.soloAnexar = soloAnexar;
    }

    /**
     * 
     * @return reglaAnexado
     */
    public String getReglaAnexado() {
        return reglaAnexado;
    }

    /**
     * 
     * @param reglaAnexado
     *            a fijar
     */
    public void setReglaAnexado(String reglaAnexado) {
        this.reglaAnexado = reglaAnexado;
    }

    /**
     * Metod hashCode return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (documentoTramitePK != null ? documentoTramitePK.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DocumentoTramite)) {
            return false;
        }
        DocumentoTramite other = (DocumentoTramite) object;
        if (this.documentoTramitePK == null || other.documentoTramitePK == null) {
            return false;
        }
        else if (!this.documentoTramitePK.equals(other.documentoTramitePK)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.DocumentoTramite[documentoTramitePK=" +
     *         documentoTramitePK + "]";
     */
    public String toString() {
        return "entity.DocumentoTramite[documentoTramitePK=" + documentoTramitePK + "]";
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden
     *            the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}
