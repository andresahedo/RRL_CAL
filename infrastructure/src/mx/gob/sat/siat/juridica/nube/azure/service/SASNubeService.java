package mx.gob.sat.siat.juridica.nube.azure.service;

import com.microsoft.windowsazure.services.core.storage.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author Softtek
 *
 */
public interface SASNubeService {
    void upload(InputStream file, String idStorage, String sas) throws StorageException, IOException,
            URISyntaxException;

}
