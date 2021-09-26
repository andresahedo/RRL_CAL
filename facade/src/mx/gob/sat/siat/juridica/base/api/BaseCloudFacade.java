package mx.gob.sat.siat.juridica.base.api;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public interface BaseCloudFacade extends BaseFacade {

    String getCadenaSASUpload() throws InvalidKeyException, URISyntaxException, StorageException;

    String getCadenaSasDownload(String paramString) throws InvalidKeyException, URISyntaxException,
            StorageException, BaseBussinessException;

    String getCadenaTamanioArchivo(long tamanioBytes);
    
    void checkCifradoExist(String idStorage) throws InvalidKeyException, URISyntaxException,
             StorageException, BaseBussinessException;

    byte[] descargarDocumentoCifrado(String idStorage);

    String subirArchivo(InputStream inputStream, String idStorage);
    
    boolean validaIdStorage(String idStorage);
    
}
