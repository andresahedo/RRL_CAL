package mx.gob.sat.siat.juridica.nube.azure.service.impl;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import mx.gob.sat.siat.juridica.nube.azure.helper.AzureHelper;
import mx.gob.sat.siat.juridica.nube.azure.repository.AzureRepository;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.Calendar;

@Service("azureService")
public abstract class AzureServiceImpl implements AzureService, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4895957095956853625L;

            private static final int DOCE = 12;

    /**
     * Instancia para el registro de eventos
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AzureHelper azureHelper;

    @Override
    public void upload(InputStream file, String idStorage) throws StorageException, IOException, URISyntaxException {
        getAzureRepository().upload(file, idStorage);

    }

    @Override
    public void uploadByBlock(InputStream file, String idStorage) throws StorageException, IOException,
            URISyntaxException {
        getAzureRepository().uploadByBlock(file, idStorage);

    }

    @Override
    public boolean delete(String idStorage) throws URISyntaxException, StorageException {
        return getAzureRepository().delete(idStorage);
    }

    @Override
    public OutputStream download(OutputStream ops, String idStorage) throws URISyntaxException, StorageException,
            IOException {
        return getAzureRepository().download(ops, idStorage);
    }

    @Override
    public String stringBlobRead(String idStorage) throws URISyntaxException, StorageException, InvalidKeyException {
        String sas = getSASBlobRead(idStorage);
        StringBuffer sb = new StringBuffer();
        sb.append(getURLDownload());
        sb.append("/");
        sb.append(idStorage);
        sb.append("?");
        sb.append(sas);
        return sb.toString();
    }

    @Override
    public String stringBlobWrite(String idStorage) throws URISyntaxException, StorageException, InvalidKeyException {
        String sas = getSASBlobWrite(idStorage);
        StringBuffer sb = new StringBuffer();
        sb.append(getURLDownload());
        sb.append("/");
        sb.append(idStorage);
        sb.append("?");
        sb.append(sas);
        return sb.toString();
    }

    @Override
    public String stringContainerRead() throws URISyntaxException, StorageException, InvalidKeyException {
        String sas = getSASContainerRead();
        StringBuffer sb = new StringBuffer();
        sb.append(getURLDownload());
        sb.append("?");
        sb.append(sas);
        return sb.toString();
    }

    @Override
    public String stringContainerWrite() throws URISyntaxException, StorageException, InvalidKeyException {
        String sas = getSASContainerWrite();
        StringBuffer sb = new StringBuffer();
        sb.append(getURLDownload());
        sb.append("?");
        sb.append(sas);
        return sb.toString();
    }

    @Override
    public String getSASContainerRead() throws InvalidKeyException, URISyntaxException, StorageException {
        return getAzureRepository().getSASContainer(
                getAzureHelper().generateSharedAccessBlobPolicyRead(Calendar.HOUR, DOCE));
    }

    @Override
    public String getSASContainerWrite() throws InvalidKeyException, URISyntaxException, StorageException {
        return getAzureRepository().getSASContainer(
                getAzureHelper().generateSharedAccessBlobPolicyWrite(Calendar.HOUR, DOCE));
    }

    @Override
    public String getSASBlobRead(String idStorage) throws InvalidKeyException, URISyntaxException, StorageException {
        return getAzureRepository().getSASBlob(idStorage,
                getAzureHelper().generateSharedAccessBlobPolicyRead(Calendar.HOUR, DOCE));
    }

    @Override
    public String getSASBlobWrite(String idStorage) throws InvalidKeyException, URISyntaxException, StorageException {
        return getAzureRepository().getSASBlob(idStorage,
                getAzureHelper().generateSharedAccessBlobPolicyWrite(Calendar.HOUR, DOCE));
    }

    @Override
    public boolean checkExist(String idStorage) throws URISyntaxException, StorageException {
        return getAzureRepository().checkExist(idStorage);
    }

    public AzureHelper getAzureHelper() {
        return azureHelper;
    }

    public abstract AzureRepository getAzureRepository();

    private String getURLDownload() {
        return getUrl() + "/" + getContainer();
    }

    public int sendGet(String url) throws IOException {
        String charSet = "UTF-8";
        URL obj = new URL(url.replace("Ñ", "%C3%91").replace("&", "%26"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        logger.debug("\nSending 'GET' request to URL : " + url);
        logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName(charSet)));
        con.disconnect();
        String inputLine;
        StringBuffer response = new StringBuffer();
        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }finally {
            in.close();
        }
        

        logger.debug(response.toString());
        return responseCode;

    }
    
    @Override
    public String getKey(String idStorage) throws URISyntaxException, StorageException{
        return getAzureRepository().getKey(idStorage);
    }

    public abstract String getContainer();

    public abstract String getUrl();

}
