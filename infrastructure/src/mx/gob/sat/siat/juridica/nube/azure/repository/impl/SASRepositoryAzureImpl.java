package mx.gob.sat.siat.juridica.nube.azure.repository.impl;

import com.microsoft.windowsazure.services.blob.client.CloudBlobClient;
import com.microsoft.windowsazure.services.blob.client.CloudBlobContainer;
import com.microsoft.windowsazure.services.blob.client.CloudBlockBlob;
import com.microsoft.windowsazure.services.core.storage.StorageException;
import mx.gob.sat.siat.juridica.nube.azure.repository.SASRepositoryAzure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Repository
public class SASRepositoryAzureImpl implements SASRepositoryAzure {

    protected static final Logger LOG = LoggerFactory.getLogger(SASRepositoryAzureImpl.class);

    private CloudBlobContainer setCloudBlobContainer(String acount, String sas) throws URISyntaxException,
            StorageException {
        CloudBlobContainer cloudBlobContainer;
        LOG.debug("url [{}] sas [{}] ", acount, sas);
        URI baseuri = new URI(acount);
        CloudBlobClient blobclient = new CloudBlobClient(baseuri);
        cloudBlobContainer = new CloudBlobContainer(new URI(sas), blobclient);
        return cloudBlobContainer;
    }

    @Override
    public void upload(InputStream file, String idStorage, String acount, String sas) throws StorageException,
            IOException, URISyntaxException {

        CloudBlobContainer cloudBlobContainer = setCloudBlobContainer(acount, sas);
        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(idStorage);
        LOG.debug("size {} idStorage {} ", file.available(), idStorage);
        blob.upload(file, file.available());
    }
}
