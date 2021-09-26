package mx.gob.sat.siat.juridica.nube.azure.service;

import com.microsoft.windowsazure.services.core.storage.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public interface AzureService {

    int RESPONSE_OK = 200;

    void upload(InputStream file, String idStorage) throws StorageException, IOException, URISyntaxException;

    void uploadByBlock(InputStream file, String idStorage) throws StorageException, IOException, URISyntaxException;

    boolean delete(String idStorage) throws URISyntaxException, StorageException;

    OutputStream download(OutputStream ops, String idStorage) throws URISyntaxException, StorageException, IOException;

    String getSASContainerRead() throws InvalidKeyException, URISyntaxException, StorageException;

    String getSASBlobRead(String idStorage) throws InvalidKeyException, URISyntaxException, StorageException;

    String getSASContainerWrite() throws InvalidKeyException, URISyntaxException, StorageException;

    String getSASBlobWrite(String idStorage) throws InvalidKeyException, URISyntaxException, StorageException;

    String stringBlobRead(String idStorage) throws URISyntaxException, StorageException, InvalidKeyException;

    String stringContainerRead() throws URISyntaxException, StorageException, InvalidKeyException;

    String stringBlobWrite(String idStorage) throws URISyntaxException, StorageException, InvalidKeyException;

    String stringContainerWrite() throws URISyntaxException, StorageException, InvalidKeyException;

    boolean checkExist(String idStorage) throws URISyntaxException, StorageException;

    int sendGet(String url) throws IOException;
    
    String getKey(String idStorage) throws URISyntaxException, StorageException;
}
