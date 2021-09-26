package mx.gob.sat.siat.juridica.base.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DocumentoDTO extends BaseModel implements DocumentoNubeDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8735044795244868252L;

    private Integer idTipoDocumento;
    private long idDocumentoSolicitud;
    private String tipoDocumento;
    private String nombre;
    private String ruta;
    private Date fechaRecepcion;
    private transient InputStream file;
    private String hashDocumento;
    private String descripcion;
    private String rutaAzure;
    private String rutaDescarga;
    private Long tamanioArchivo;
    private boolean complementario;
    private String cadenaTamanioArchivo;
    private String tooltipDescripcion;
    private Long folio;
    private Date fechaApertura;

    public DocumentoDTO() {

    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public long getIdDocumentoSolicitud() {
        return idDocumentoSolicitud;
    }

    public void setIdDocumentoSolicitud(long idDocumentoSolicitud) {
        this.idDocumentoSolicitud = idDocumentoSolicitud;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion != null ? (Date) fechaRecepcion.clone() : null;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        if (fechaRecepcion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaRecepcion);
            this.fechaRecepcion = cal.getTime();
        }
        else {
            this.fechaRecepcion = null;
        }
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getHashDocumento() {
        return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    /**
     * @return the complementario
     */
    public boolean isComplementario() {
        return complementario;
    }

    /**
     * @param complementario
     *            the complementario to set
     */
    public void setComplementario(boolean complementario) {
        this.complementario = complementario;
    }

    public String getCadenaTamanioArchivo() {
        return cadenaTamanioArchivo;
    }

    public void setCadenaTamanioArchivo(String cadenaTamanioArchivo) {
        this.cadenaTamanioArchivo = cadenaTamanioArchivo;
    }

    @Override
    public String getNombreDocumento() {
        return getNombre();
    }

    public String getTooltipDescripcion() {
        return tooltipDescripcion;
    }

    public void setTooltipDescripcion(String tooltipDescripcion) {
        this.tooltipDescripcion = tooltipDescripcion;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }
}
