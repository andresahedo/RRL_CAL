package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Value;

import com.microsoft.windowsazure.services.core.storage.StorageException;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoNubeDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.base.web.util.validador.PDFValidator;
import mx.gob.sat.siat.juridica.nube.util.constante.NubeConstantes;

public abstract class BaseCloudController<T extends DocumentoNubeDTO> extends BaseControllerBean {

    private static final long serialVersionUID = 2818148205498682307L;

    /* inicia integracion de nube */
    @Value("${azure.cifrado.conecction.urlConecction}")
    private String storageCifradoConnectionString;

    @Value("${azure.cifrado.conecction.container}")
    private String storageCifradoContainer;

    @Value("${azure.claro.conecction.urlConecction}")
    private String storageClaroConnectionString;

    @Value("${azure.claro.conecction.container}")
    private String storageClaroContainer;

    private static final String DECRIFARDOCUMENTO = "/DecifrarDocumentoServlet/document.pdf";
    private String nombreDocumento;
    private String idAzure;
    private String sasURL;
    private String hashDocumento;
    private String rutaVisor;
    private String idAzureDescarga;
    private Long tamanioDocumento;
    private UploadedFile archivo;
    private DocumentoDTO documentoDTOSelected;
    private DocumentoOficialDTO documentoOficialSelected;
    private boolean banderaDocumentoOficial;
    private boolean banderaDocumentoPromocion;
    private List<DocumentoOficialDTO> listaDocumentosOficialDTO = new ArrayList<DocumentoOficialDTO>();
    private List<DocumentoDTO> listaDocumentosDTO = new ArrayList<DocumentoDTO>();
    private List<DocumentoDTO> listaDocumentosOpcionalesDTO = new ArrayList<DocumentoDTO>();
    private boolean bolDocOpcDto = true;
    private boolean bolDocDto = true;
    private boolean bolDocDtoOficial = true;

    private Logger log = Logger.getLogger(BaseCloudController.class);

    public String generaRutaDescarga(String idStorage)
            throws InvalidKeyException, URISyntaxException, StorageException, BaseBussinessException {
        return getBusinessBean().generaRutaDescarga(idStorage);
    }

    public DocumentoDTO obtenerDocumento(String id) {
        DocumentoDTO documento = null;
        String[] ids = id.split(",");
        if (ids != null && ids.length > 0) {
            documento = obtenerDocumentoDTO(Integer.valueOf(ids[0]), ids[1]);
        }
        return documento;

    }

    /**
     * M&eacute;todo para adjuntar un documento
     */
    public DocumentoOficialDTO obtenerDocumentoOficialDTO(TipoDocumentoOficial tipoDocumento) {
        return obtenerDocumentoOficialDTO(tipoDocumento.getClave(), tipoDocumento.getDescripcion());

    }

    public void generaRutaDescargaEventDocumento() {
        String rutaVsr = null;
        try {
            rutaVsr = getBusinessBean().generaRutaDescarga(
                    getDocumentoDTOSelected().getRutaAzure() + "-" + getDocumentoDTOSelected().getHashDocumento());
        } catch (InvalidKeyException e1) {
            log.error("", e1);
        } catch (URISyntaxException e1) {
            log.error("", e1);
        } catch (StorageException e1) {
            log.error("", e1);
        } catch (Exception e1) {
            log.error("", e1);
        }

        setRutaVisor(rutaVsr);
    }

    public void decifrarDocumento() {
        String rutaVsr;
        StringBuilder sb = new StringBuilder();
        if (getDocumentoDTOSelected() != null && banderaDocumentoPromocion) {
            sb.append(getDocumentoDTOSelected().getRutaAzure());
            sb.append("-");
            sb.append(getDocumentoDTOSelected().getHashDocumento());
            setDocumentoOficialSelected(null);
        }
        if (getDocumentoOficialSelected() != null && banderaDocumentoOficial) {
            sb.append(getDocumentoOficialSelected().getRutaAzure());
            sb.append("-");
            sb.append(getDocumentoOficialSelected().getHashDocumento());
            setDocumentoDTOSelected(null);
        }
        rutaVsr = sb.toString();

        byte[] archivoDecifrado = getBusinessBean().descargarDocumentoCifrado(rutaVsr);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("documento", archivoDecifrado);
    }

    public String getUrlDescargaSelected() {
        return DECRIFARDOCUMENTO;
    }
    
    public void fileUploadListenerGrowl(FileUploadEvent e) {
        setArchivo(e.getFile());
        String nombre = e.getFile().getFileName();
        Charset utf8charset = Charset.forName("UTF-8");
        byte[] utf8bytes = e.getFile().getFileName().getBytes();
        nombre = new String (utf8bytes, utf8charset );
        setNombreDocumento(nombre);
        getLogger().debug("Nombre del archivo en el get:" + getNombreDocumento());
        String validacion = validarArchivo();
        setTamanioDocumento((long) 0D);
        validacion = subirArchivo(validacion);
        FacesMessage msg;
        if (validacion != null) {
            setIdAzure("ERROR");
            setNombreDocumento(validacion);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",validacion);
        }else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Documento adjuntado.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }

    
    public void fileUploadListener(FileUploadEvent e) {
        setArchivo(e.getFile());
        String nombre = e.getFile().getFileName();
        Charset utf8charset = Charset.forName("UTF-8");
        byte[] utf8bytes = e.getFile().getFileName().getBytes();
        nombre = new String (utf8bytes, utf8charset );
        setNombreDocumento(nombre);
        getLogger().debug("Nombre del archivo en el get:" + getNombreDocumento());
        String validacion = validarArchivo();
        setTamanioDocumento((long) 0D);
        validacion = subirArchivo(validacion);
        if (validacion != null) {
            setIdAzure("ERROR");
            setNombreDocumento(validacion);
        }
    }

    public String subirArchivo(String validacion) {
        if(validacion == null) {
            String idStorage = this.getUserProfile().getRfc() + "." + new Date().getTime();
            try {
                setHashDocumento(getBusinessBean().subirArchivo(archivo.getInputstream(), idStorage));
                getLogger().error("Subida del documento correctos");
            } catch (IOException e) {
                getLogger().error("Ocurrio un error al subir el archivo:", e.getMessage());
                validacion = "Ocurri\u00F3 un error al subir el archivo";
            }
            setIdAzure(idStorage);
            setNombreDocumento(getNombreDocumento());
            setTamanioDocumento(getArchivo().getSize());
            setArchivo(null);
        }
        return validacion;
    }

    private String validarArchivo() {
        String validacion = null;
        PDFValidator pdfValidator = new PDFValidator();
        InputStream contenido = null;
        try {
            contenido = getArchivo().getInputstream();
            if (pdfValidator.validateBlankPages(contenido) != NumerosConstantes.CERO) {
                validacion =  pdfValidator.validateBlankPages(contenido) == NumerosConstantes.CUATRO ? "No es posible adjuntar archivos protegidos por contraseña" : "No es posible adjuntar archivos que contengan p\\u00E1ginas en blanco.";
                getLogger().error("Error al subir un documento:"+validacion);
            } else if (pdfValidator.nameValidator(getNombreDocumento(), getNombresUsados()) != NumerosConstantes.CERO) {
                validacion = "No se permite adjuntar archivos con el mismo nombre.";
                getLogger().error("Error al subir un documento. Mismo Nombre");
            } else if (getNombreDocumento() == null ||  getNombreDocumento().isEmpty()) {
                validacion = "S\u00F3lo es posible adjuntar un archivo a la vez";
                getLogger().error("Solo se permite subir un documento a la vez.");
            } else {
                validacion = null;
                getLogger().error("Validacion del documento correcta");
            }
        } catch (IOException e) {
            getLogger().error("Ocurrrio un error de validacion IOException el archivo:" + e.getMessage());
            validacion = "Ocurri\u00F3 un error al subir el archivo";
        }
        return validacion;
    }

    public DocumentoOficialDTO obtenerDocumentoOficialDTO(String clave, String descripcion) {

        DocumentoOficialDTO documento = new DocumentoOficialDTO();
        documento.setNombreArchivo(getNombreDocumento() != null ? getNombreDocumento() : "S\u00F3lo es posible adjuntar un archivo a la vez");
        documento.setFechaCreacion(new Date());
        documento.setRutaAzure(getNombreDocumento() != null ? getIdAzure() : "ERROR");
        documento.setEstadoDocumentoOficial(EstadoDocumento.ANEXADO);
        documento.setCveTipoDocumento(clave);
        documento.setHashDocumento(getHashDocumento());
        documento.setTamanioArchivo(getTamanioDocumento());
        documento.setCadenaTamanioArchivo(getBusinessBean().getCadenaTamanioArchivo(getTamanioDocumento()));
        documento.setDescripcionTipoDocumento(descripcion);
        documento.setEditarDocumSelect(false);
        setNombreDocumento(null);
        setIdAzure(null);
        setTamanioDocumento(null);
        setHashDocumento(null);
        return documento;
    }

    public DocumentoDTO obtenerDocumentoDTO(Integer tipoDocumento, String descripcionTipoDocumento) {
        DocumentoDTO documento = new DocumentoDTO();

        documento.setIdTipoDocumento(tipoDocumento);
        documento.setTipoDocumento(descripcionTipoDocumento);
        documento.setNombre(getNombreDocumento());
        documento.setRutaAzure(getIdAzure());
        documento.setFechaRecepcion(new Date());
        documento.setTamanioArchivo(getTamanioDocumento());
        documento.setCadenaTamanioArchivo(getBusinessBean().getCadenaTamanioArchivo(getTamanioDocumento()));
        documento.setHashDocumento(getHashDocumento());
        setNombreDocumento(null);
        setIdAzure(null);
        setTamanioDocumento(null);
        setHashDocumento(null);
        return documento;
    }

    public void activaBanderaDocumentoOficial() {
        setBanderaDocumentoOficial(true);
        setBanderaDocumentoPromocion(false);
        decifrarDocumento();
    }

    public void activaBanderaDocumentoPromocion() {
        setBanderaDocumentoPromocion(true);
        setBanderaDocumentoOficial(false);
        decifrarDocumento();
    }

    public InputStream descargaDocumentoDto(DocumentoDTO documento) {
        String idStorage = documento.getRutaAzure() + "-" + documento.getHashDocumento();
        return new ByteArrayInputStream(getBusinessBean().descargarDocumentoCifrado(idStorage));
    }

    public void descargarResueltosOpcionales(AjaxBehaviorEvent changeEvent) {
        setBolDocOpcDto(listaDocumentosOpcionalesDTO.size() == 0);
    }

    public byte[] generarZipOpcionales(Map<Integer, String> mapaRutas) {
        Map<String, InputStream> mapaDocumentos = new HashMap<String, InputStream>();
        for (Entry<Integer, String> documento : mapaRutas.entrySet()) {
            mapaDocumentos.put(documento.getValue(),
                    descargaDocumentoDto(listaDocumentosOpcionalesDTO.get(documento.getKey())));
        }
        return generarZip(mapaDocumentos);
    }

    public void descargarResueltosDTO() {
        setBolDocDto(listaDocumentosDTO.size() == 0);
    }

    public byte[] generarZipResueltos(Map<Integer, String> mapaRutas) {
        Map<String, InputStream> mapaDocumentos = new HashMap<String, InputStream>();
        for (Entry<Integer, String> documento : mapaRutas.entrySet()) {
            mapaDocumentos.put(documento.getValue(), descargaDocumentoDto(listaDocumentosDTO.get(documento.getKey())));
        }
        return generarZip(mapaDocumentos);
    }

    public void descargarResueltosOficiales(AjaxBehaviorEvent changeEvent) {
        setBolDocDtoOficial(listaDocumentosOficialDTO.size() == 0);
    }

    public InputStream descargaDocumentoOficial(DocumentoOficialDTO documento) {
        String idStorage = documento.getRutaAzure() + "-" + documento.getHashDocumento();
        return new ByteArrayInputStream(getBusinessBean().descargarDocumentoCifrado(idStorage));
    }

    public byte[] generarZipOficial(Map<Integer, String> mapaRutas) {
        Map<String, InputStream> mapaDocumentos = new HashMap<String, InputStream>();
        for (Entry<Integer, String> documento : mapaRutas.entrySet()) {
            mapaDocumentos.put(documento.getValue(),
                    descargaDocumentoOficial(listaDocumentosOficialDTO.get(documento.getKey())));
        }
        return generarZip(mapaDocumentos);
    }

    public byte[] generarZip(Map<String, InputStream> mapaDocumentos) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        for (Entry<String, InputStream> documento : mapaDocumentos.entrySet()) {
            try {
                byte[] buf = new byte[NumerosConstantes.BYTES];
                InputStream fis = documento.getValue();
                ZipEntry entry = new ZipEntry(documento.getKey());
                zos.putNextEntry(entry);
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                fis.close();
                zos.closeEntry();
            } catch (MalformedURLException e) {
                getLogger().error("Ocurrio un error al comprimir el archivo:" + e);
            } catch (IOException e) {
                getLogger().error("Ocurrio un error al comprimir el archivo:" + e);
            }
        }
        try {
            zos.finish();
            zos.close();
        } catch (IOException e) {
            getLogger().error("Ocurrio un error al generar el archivo zip:" + e);
        }
        return baos.toByteArray();
    }

    public abstract BaseCloudBusiness getBusinessBean();

    public String getStorageCifradoConnectionString() {
        return storageCifradoConnectionString;
    }

    public void setStorageCifradoConnectionString(String storageCifradoConnectionString) {
        this.storageCifradoConnectionString = storageCifradoConnectionString;
    }

    public String getStorageCifradoContainer() {
        return storageCifradoContainer;
    }

    public void setStorageCifradoContainer(String storageCifradoContainer) {
        this.storageCifradoContainer = storageCifradoContainer;
    }

    public String getStorageClaroConnectionString() {
        return storageClaroConnectionString;
    }

    public void setStorageClaroConnectionString(String storageClaroConnectionString) {
        this.storageClaroConnectionString = storageClaroConnectionString;
    }

    public String getStorageClaroContainer() {
        return storageClaroContainer;
    }

    public void setStorageClaroContainer(String storageClaroContainer) {
        this.storageClaroContainer = storageClaroContainer;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getIdAzure() {
        return idAzure;
    }

    public void setIdAzure(String idAzure) {
        this.idAzure = idAzure;
    }

    public String getSasURL() {
        return sasURL;
    }

    public void setSasURL(String sasURL) {
        this.sasURL = sasURL;
    }

    public String getHashDocumento() {
        return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public String getRutaVisor() {
        return rutaVisor;
    }

    public void setRutaVisor(String rutaVisor) {
        this.rutaVisor = rutaVisor;
    }

    public String getIdAzureDescarga() {
        return idAzureDescarga;
    }

    public void setIdAzureDescarga(String idAzureDescarga) {
        this.idAzureDescarga = idAzureDescarga;
    }

    public DocumentoDTO getDocumentoDTOSelected() {
        return documentoDTOSelected;
    }

    public void setDocumentoDTOSelected(DocumentoDTO documentoDTOSelected) {
        this.documentoDTOSelected = documentoDTOSelected;
        if (documentoDTOSelected != null) {
            activaBanderaDocumentoPromocion();
        }
    }

    public DocumentoOficialDTO getDocumentoOficialSelected() {
        return documentoOficialSelected;
    }

    public void setDocumentoOficialSelected(DocumentoOficialDTO documentoOficialSelected) {
        this.documentoOficialSelected = documentoOficialSelected;
        if (documentoOficialSelected != null) {
            activaBanderaDocumentoOficial();
        }
    }

    public Long getTamanioDocumento() {
        return tamanioDocumento;
    }

    public void setTamanioDocumento(Long tamanioDocumento) {
        this.tamanioDocumento = tamanioDocumento;
    }

    public boolean isBanderaDocumentoOficial() {
        return banderaDocumentoOficial;
    }

    public void setBanderaDocumentoOficial(boolean banderaDocumentoOficial) {
        this.banderaDocumentoOficial = banderaDocumentoOficial;
    }

    public boolean isBanderaDocumentoPromocion() {
        return banderaDocumentoPromocion;
    }

    public void setBanderaDocumentoPromocion(boolean banderaDocumentoPromocion) {
        this.banderaDocumentoPromocion = banderaDocumentoPromocion;
    }

    /**
     * Devuelve el ancho en pixeles para definir el estilo de los dataTables que
     * muestran los archivos que se adjuntan. Es necesario ya que en IE se
     * ensancha/encoge la tabla si no se define esta propiedad al ir agregando
     * documento.
     * 
     * @return Ancho en pixeles, definido por la constante
     *         VistaConstantes.ANCHO_GRID_DOCUMENTOS_PX
     */
    public int getAnchoGridDocumentosPx() {
        return VistaConstantes.ANCHO_GRID_DOCUMENTOS_PX;
    }

    public String getNombresUsados() {
        StringBuilder sb = new StringBuilder();
        for (DocumentoNubeDTO documento : getListaDocumentosAdjuntados()) {
            sb.append(documento.getNombreDocumento());
            sb.append(NubeConstantes.SEPARADOR_NOMBRE_ARCHIVOS_ADJUNTAR);
        }
        return sb.toString();
    }

    public abstract List<T> getListaDocumentosAdjuntados();

    public List<DocumentoDTO> getListaDocumentosDTO() {
        return listaDocumentosDTO;
    }

    public void setListaDocumentosDTO(List<DocumentoDTO> listaDocumentosDTO) {
        this.listaDocumentosDTO = listaDocumentosDTO;
    }

    public List<DocumentoOficialDTO> getListaDocumentosOficialDTO() {
        return listaDocumentosOficialDTO;
    }

    public void setListaDocumentosOficialDTO(List<DocumentoOficialDTO> listaDocumentosOficialDTO) {
        this.listaDocumentosOficialDTO = listaDocumentosOficialDTO;
    }

    public boolean isBolDocDto() {
        return bolDocDto;
    }

    public void setBolDocDto(boolean bolDocDto) {
        this.bolDocDto = bolDocDto;
    }

    public boolean isBolDocDtoOficial() {
        return bolDocDtoOficial;
    }

    public void setBolDocDtoOficial(boolean bolDocDtoOficial) {
        this.bolDocDtoOficial = bolDocDtoOficial;
    }

    public List<DocumentoDTO> getListaDocumentosOpcionalesDTO() {
        return listaDocumentosOpcionalesDTO;
    }

    public void setListaDocumentosOpcionalesDTO(List<DocumentoDTO> listaDocumentosOpcionalesDTO) {
        this.listaDocumentosOpcionalesDTO = listaDocumentosOpcionalesDTO;
    }

    public boolean isBolDocOpcDto() {
        return bolDocOpcDto;
    }

    public void setBolDocOpcDto(boolean bolDocOpcDto) {
        this.bolDocOpcDto = bolDocOpcDto;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

}
