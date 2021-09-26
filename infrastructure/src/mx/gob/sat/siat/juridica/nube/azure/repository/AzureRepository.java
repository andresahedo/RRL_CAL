package mx.gob.sat.siat.juridica.nube.azure.repository;

import com.microsoft.windowsazure.services.blob.client.SharedAccessBlobPolicy;
import com.microsoft.windowsazure.services.core.storage.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public interface AzureRepository {

    void upload(InputStream file, String idStorage) throws StorageException, IOException, URISyntaxException;

    OutputStream download(OutputStream ops, String idStorage) throws URISyntaxException, StorageException,
            IOException;

    boolean delete(String idStorage) throws URISyntaxException, StorageException;

    void uploadByBlock(InputStream file, String idStorage) throws StorageException, IOException,
            URISyntaxException;

    String getSASContainer(SharedAccessBlobPolicy policy) throws InvalidKeyException, URISyntaxException,
            StorageException;

    String getSASBlob(String idStorage, SharedAccessBlobPolicy policy) throws InvalidKeyException, URISyntaxException,
            StorageException;

    boolean checkExist(String idStorage) throws URISyntaxException, StorageException;

    String getKey(String idStorage) throws URISyntaxException, StorageException;

}
