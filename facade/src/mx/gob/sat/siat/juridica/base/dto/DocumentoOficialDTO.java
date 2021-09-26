/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DTO que representa los datos de los documentos oficiales
 * 
 * @author Softtek
 * 
 */
public class DocumentoOficialDTO extends BaseModel implements DocumentoNubeDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3211500769589859964L;
    /**
     * Contiene la clave del tipo de documento oficial.
     */
    private String cveTipoDocumento;
    /**
     * Contiene el ID del requerimiento.
     */
    private long idRequerimiento;
    /**
     * Contiene la clave del documento oficial.
     */
    private long idDocumentoOficial;
    /**
     * Contiene la descripci&oacute;n del tipo de documento oficial.
     */
    private String descripcionTipoDocumento;
    /**
     * Contiene el nombre del archivo.
     */
    private String nombreArchivo;
    /**
     * Contiene la ruta en donde se guarda el archivo.
     */
    private String ruta;

    /**
     * Contiene la fecha en que se adjunta el archivo.
     */
    private Date fechaCreacion;
    /**
     * Contiene la lectura de bytes del archivo.
     */
    private transient InputStream file;
    /**
     * Contiene el Hash del documento oficial.
     */
    private String hashDocumento;

    private String rutaAzure;
    private String rutaDescarga;
    private Long tamanioArchivo;
    private String cadenaTamanioArchivo;
    private String estadoDocumentoOficial;
    /**
     * Contiene la clave del estado del documento en estado generado
     * para diferenciar los documentos firmados en generados por el
     * sistema y adjuntados.
     */
    private String numFolioOficio;
    
    private Date fechaNotificacion;
    private Date fechaAudiencia;
    
    private boolean editarDocumSelect;

    /**
     * Constructor
     */
    public DocumentoOficialDTO() {}

    /**
     * 
     * @return cveTipoDocumento
     */
    public String getCveTipoDocumento() {
        return cveTipoDocumento;
    }

    /**
     * 
     * @param cveTipoDocumento
     *            a fijar
     */
    public void setCveTipoDocumento(String cveTipoDocumento) {
        this.cveTipoDocumento = cveTipoDocumento;
    }

    /**
     * 
     * @return idRequerimiento
     */
    public long getIdRequerimiento() {
        return idRequerimiento;
    }

    /**
     * 
     * @param idRequerimiento
     *            a fijar
     */
    public void setIdRequerimiento(long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    /**
     * 
     * @return descripcionTipoDocumento
     */
    public String getDescripcionTipoDocumento() {
        return descripcionTipoDocumento;
    }

    /**
     * 
     * @param descripcionTipoDocumento
     *            a fijar
     */
    public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
        this.descripcionTipoDocumento = descripcionTipoDocumento;
    }

    /**
     * 
     * @return nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * 
     * @param nombreArchivo
     *            a fijar
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * 
     * @return ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * 
     * @param ruta
     *            a fijar
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
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
     * @return file
     */
    public InputStream getFile() {
        return file;
    }

    /**
     * 
     * @param file
     *            a fijar
     */
    public void setFile(InputStream file) {
        this.file = file;
    }

    /**
     * 
     * @return idDocumentoOficial
     */
    public long getIdDocumentoOficial() {
        return idDocumentoOficial;
    }

    /**
     * 
     * @param idDocumentoOficial
     *            a fijar
     */
    public void setIdDocumentoOficial(long idDocumentoOficial) {
        this.idDocumentoOficial = idDocumentoOficial;
    }

    /**
     * 
     * @return hashDocumento
     */
    public String getHashDocumento() {
        return hashDocumento;
    }

    /**
     * 
     * @param hashDocumento
     *            a fijar
     */
    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public String getRutaAzure() {
        return rutaAzure;
    }

    public void setRutaAzure(String rutaAzure) {
        this.rutaAzure = rutaAzure;
    }

    public String getRutaDescarga() {
        return rutaDescarga;
    }

    public void setRutaDescarga(String rutaDescarga) {
        this.rutaDescarga = rutaDescarga;
    }

    public Long getTamanioArchivo() {
        return tamanioArchivo;
    }

    public void setTamanioArchivo(Long tamanioArchivo) {
        this.tamanioArchivo = tamanioArchivo;
    }

    public String getCadenaTamanioArchivo() {
        return cadenaTamanioArchivo;
    }

    public void setCadenaTamanioArchivo(String cadenaTamanioArchivo) {
        this.cadenaTamanioArchivo = cadenaTamanioArchivo;
    }

    @Override
    public String getNombreDocumento() {
        return getNombreArchivo();
    }

    public String getEstadoDocumentoOficial() {
        return estadoDocumentoOficial;
    }

    public void setEstadoDocumentoOficial(String estadoDocumentoOficial) {
        this.estadoDocumentoOficial = estadoDocumentoOficial;
    }

    public void setEstadoDocumentoOficial(EstadoDocumento estadoDocumentoOficial) {
        this.estadoDocumentoOficial = estadoDocumentoOficial.getClave();
    }

    /**
     * @return the numFolioOficio
     */
    public String getNumFolioOficio() {
        return numFolioOficio;
    }

    /**
     * @param numFolioOficio
     *            the numFolioOficio to set
     */
    public void setNumFolioOficio(String numFolioOficio) {
        this.numFolioOficio = numFolioOficio;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
    }

    public Date getFechaAudiencia() {
        return fechaAudiencia != null ? (Date) fechaAudiencia.clone() : null;
    }

    public void setFechaAudiencia(Date fechaAudiencia) {
        this.fechaAudiencia = fechaAudiencia != null ? (Date) fechaAudiencia.clone() : null;
    }

    public boolean isEditarDocumSelect() {
        return editarDocumSelect;
    }

    public void setEditarDocumSelect(boolean editarDocumSelect) {
        this.editarDocumSelect = editarDocumSelect;
    }

    @Override
    public String toString() {
        return "DocumentoOficialDTO [cveTipoDocumento=" + cveTipoDocumento + ", idRequerimiento=" + idRequerimiento
                + ", idDocumentoOficial=" + idDocumentoOficial + ", descripcionTipoDocumento="
                + descripcionTipoDocumento + ", nombreArchivo=" + nombreArchivo + ", ruta=" + ruta + ", fechaCreacion="
                + fechaCreacion + ", hashDocumento=" + hashDocumento + ", rutaAzure=" + rutaAzure + ", rutaDescarga="
                + rutaDescarga + ", tamanioArchivo=" + tamanioArchivo + ", cadenaTamanioArchivo=" + cadenaTamanioArchivo
                + ", estadoDocumentoOficial=" + estadoDocumentoOficial + ", numFolioOficio=" + numFolioOficio
                + ", fechaNotificacion=" + fechaNotificacion + ", fechaAudiencia=" + fechaAudiencia
                + ", editarDocumSelect=" + editarDocumSelect + "]";
    }
}
