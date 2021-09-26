package mx.gob.sat.siat.juridica.nube.azure.repository;

import com.microsoft.windowsazure.services.core.storage.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public interface SASRepositoryAzure {

    void upload(InputStream file, String idStorage, String acount, String sas) throws StorageException, IOException,
            URISyntaxException;

}
