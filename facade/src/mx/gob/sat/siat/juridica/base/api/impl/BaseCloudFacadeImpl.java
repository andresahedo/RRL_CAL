package mx.gob.sat.siat.juridica.base.api.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.microsoft.windowsazure.services.core.storage.StorageException;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureCifradoService;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureClaroService;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureService;
import mx.gob.sat.siat.juridica.nube.azure.service.CifradoService;

public abstract class BaseCloudFacadeImpl extends BaseFacadeImpl implements BaseCloudFacade {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseCloudFacadeImpl.class);
    /**
     * 
     */
    private static final long serialVersionUID = 3438982787855442759L;
    @Autowired
    private transient AzureCifradoService azureCifradoService;
    @Autowired
    private transient CifradoService cifradoService;
    @Autowired
    private transient AzureClaroService azureClaroService;
    @Autowired
    private transient DocumentosServices documentosServices;
    @Value("${azure.app.url}")
    private String wsUrl;

    @Override
    public String getCadenaSASUpload() throws InvalidKeyException, URISyntaxException, StorageException {
        return azureCifradoService.stringContainerWrite();
    }

    @Override
    public String getCadenaSasDownload(String idStorage)
            throws InvalidKeyException, URISyntaxException, StorageException, BaseBussinessException {
        StringBuilder sb = new StringBuilder(wsUrl);
        String azureID = idStorage.split("-")[0];
        String respuesta = "";
        sb.append(idStorage);
        try {
            if (!azureClaroService.checkExist(azureID)) {
                if (azureClaroService.sendGet(sb.toString()) == AzureService.RESPONSE_OK) {
                    respuesta = azureClaroService.stringBlobRead(azureID);
                }
            } else {
                respuesta = azureClaroService.stringBlobRead(azureID);
            }
        } catch (IOException ex) {

            throw new BaseBussinessException("Problema en la respuesta del contenedor", ex);
        }

        return respuesta;
    }

    @Override
    public String getCadenaTamanioArchivo(long tamanioBytes) {
        return documentosServices.obtenerCadenaTamanioArchivo(tamanioBytes);
    }

    public void checkCifradoExist(String idStorage)
            throws InvalidKeyException, URISyntaxException, StorageException, BaseBussinessException {
        LOGGER.debug("idStorage: {}", idStorage);
        if (!azureCifradoService.checkExist(idStorage)) {
            throw new BaseBussinessException("No existe el archivo en el contenedor");
        }
    }

    @SuppressWarnings("resource")
    @Override
    public byte[] descargarDocumentoCifrado(String idStorage) {
        LOGGER.debug("Decodificando el archivo idStorage: {}", idStorage);
        byte[] stream = null;
        OutputStream outputStream = null;
        String azureID = idStorage.split("-")[0];
        try {
            if (azureCifradoService.checkExist(azureID)) {
                outputStream = new ByteArrayOutputStream();
                outputStream = azureCifradoService.download(outputStream, azureID);
                String key = azureCifradoService.getKey(azureID);
                stream = cifradoService.decifrarAES(((ByteArrayOutputStream) outputStream).toByteArray(), key);
            }
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
        } catch (StorageException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return stream;
    }

    @Override
    public String subirArchivo(InputStream archivo, String idStorage) {
        String hashArchivo = null;
        try {
            byte[] contenido = IOUtils.toByteArray(archivo);
            hashArchivo = cifradoService.genrerarHASHAchivo(contenido);
            contenido = cifradoService.cifrarAES(contenido, idStorage);
            ByteArrayInputStream bai = new ByteArrayInputStream(contenido);
            if (!azureCifradoService.checkExist(idStorage)) {
                azureCifradoService.upload(bai, idStorage);
            }
            bai.close();
        } catch (StorageException e) {
            getLogger().error(e.getMessage());
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (URISyntaxException e) {
            getLogger().error(e.getMessage());
        }
        return hashArchivo;
    }

    @Override
    public boolean validaIdStorage(String idStorage) {
        Boolean valida = true;
        try {
            valida = azureCifradoService.checkExist(idStorage);
        } catch (URISyntaxException e) {
            getLogger().error(e.getMessage());
        } catch (StorageException e) {
            getLogger().error(e.getMessage());
        }
        return valida.booleanValue();
    }
}
