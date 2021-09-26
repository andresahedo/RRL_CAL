package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public abstract class BaseCloudBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 2938081042029869544L;

    public String getCadenaSASUpload() throws InvalidKeyException, URISyntaxException, StorageException {
        return getCloudFacade().getCadenaSASUpload();
    }

    public String generaRutaDescarga(String idStorage) throws InvalidKeyException, URISyntaxException,
            StorageException, BaseBussinessException {
        return getCloudFacade().getCadenaSasDownload(idStorage);
    }

    public String getCadenaTamanioArchivo(long tamanioBytes) {
        return getCloudFacade().getCadenaTamanioArchivo(tamanioBytes);
    }
    
    public void checkCifradoExist(String idStorage) throws InvalidKeyException, URISyntaxException,
             StorageException, BaseBussinessException {
        
         getCloudFacade().checkCifradoExist(idStorage);
    }
    
    public byte[] descargarDocumentoCifrado(String idStorage) {
        return getCloudFacade().descargarDocumentoCifrado(idStorage);
    }
    
    public abstract BaseCloudFacade getCloudFacade();

    public String subirArchivo(InputStream inputStream, String idStorage) {
        return getCloudFacade().subirArchivo(inputStream,idStorage);
    }

    public boolean validaIdStorage(String idStorage) {
        return getCloudFacade().validaIdStorage(idStorage);
    }

}
