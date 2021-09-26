package mx.gob.sat.siat.juridica.nube.azure.repository.impl;

import com.microsoft.windowsazure.services.blob.client.*;
import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.core.storage.StorageException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import mx.gob.sat.siat.juridica.nube.azure.repository.AzureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public abstract class AzureRepositoryImpl implements AzureRepository, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8880095962285735767L;
    protected static final Logger LOG = LoggerFactory.getLogger(AzureRepositoryImpl.class);
            private static final int MIL_VEINTICUATRO = 1024;
            private static final int DOSCIENTOS_CINCUENTA = 250;

    public abstract String getContainer();

    public abstract String getStorageConnectionString();

    private CloudBlobContainer cloudBlobContainer;

    public void setCloudBlobContainer(CloudBlobContainer cbc) {
        this.cloudBlobContainer = cbc;
    }

    public CloudBlobContainer getCloudBlobContainer() {
        return cloudBlobContainer;
    }

    @PostConstruct
    public void init() {
        LOG.debug("url [{}] contenedor [{}] ", getStorageConnectionString(), getContainer());
        CloudStorageAccount account;
        CloudBlobClient serviceClient;

        try {
            account = CloudStorageAccount.parse(getStorageConnectionString());
            serviceClient = account.createCloudBlobClient();
            setCloudBlobContainer(serviceClient.getContainerReference(getContainer()));
            getCloudBlobContainer().createIfNotExist();

            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);
            cloudBlobContainer.uploadPermissions(containerPermissions);
        }
        catch (InvalidKeyException e) {
            LOG.error("Error", e);
        }
        catch (URISyntaxException e) {
            LOG.error("Error", e);
        }
        catch (StorageException e) {
            LOG.error("Error", e);
        }

    }

    public void upload(InputStream file, String idStorage) throws StorageException, IOException, URISyntaxException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
        LOG.debug("size {} idStorage {} ", file.available(), idStorage);

        blob.upload(file, file.available());
    }

    public boolean checkExist(String idStorage) throws URISyntaxException, StorageException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);

        return blob.exists();
    }

    public void uploadByBlock(InputStream file, String idStorage) throws StorageException, IOException,
            URISyntaxException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
     // 250 kb
        int pageSize = DOSCIENTOS_CINCUENTA * MIL_VEINTICUATRO; 
        byte[] block = new byte[pageSize];
        InputStream is = file;
        List<BlockEntry> idBlocks = new ArrayList<BlockEntry>();
        int readed;
        int id = 0;
        while ((readed = is.read(block)) > 0) {
            String aux = String.format("%7d", id).replace(' ', '0');
            String blockId = Base64.encode(String.valueOf(aux).getBytes(Charset.forName("UTF-8")));
            LOG.debug("leido {} longitud codigo {} ", readed, blockId.length());
            LOG.debug("id  {} clave {}", aux, blockId);
            idBlocks.add(new BlockEntry(blockId, BlockSearchMode.UNCOMMITTED));
            ByteArrayInputStream bas = new ByteArrayInputStream(block);
            LOG.debug("leido {} longitud stream {}", readed, bas.available());
            blob.uploadBlock(blockId, bas, readed);
            id++;

        }

        blob.commitBlockList(idBlocks);

    }

    public OutputStream download(OutputStream ops, String idStorage) throws URISyntaxException, StorageException,
            IOException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
        blob.download(ops);
        return ops;
    }

    public String getSASContainer(SharedAccessBlobPolicy policy) throws InvalidKeyException, URISyntaxException,
            StorageException {
        BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
        containerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);
        getCloudBlobContainer().uploadPermissions(containerPermissions);

        return getCloudBlobContainer().generateSharedAccessSignature(policy, null);
    }

    public String getSASBlob(String idStorage, SharedAccessBlobPolicy policy) throws InvalidKeyException,
            URISyntaxException, StorageException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
        String sas = blob.generateSharedAccessSignature(policy, null);
        blob.uploadProperties();
        return sas;
    }

    public boolean delete(String idStorage) throws URISyntaxException, StorageException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
        return blob.deleteIfExists();
    }
    
    public String getKey(String idStorage) throws URISyntaxException, StorageException {
        CloudBlockBlob blob = getCloudBlobContainer().getBlockBlobReference(idStorage);
        return blob.getName();
    }

}
