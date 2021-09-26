package mx.gob.sat.siat.juridica.nube.azure.service.impl;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.nube.azure.repository.SASRepositoryAzure;
import mx.gob.sat.siat.juridica.nube.azure.service.SASNubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@Service("sASNuveService")
public class SASNubeServiceImpl extends BaseSerializableBusinessServices implements SASNubeService {

    /**
     * 
     */
    private static final long serialVersionUID = 3269704307584760918L;

    @Value("${azure.claro.conecction.url}")
    private String acount;

    @Autowired
    private transient SASRepositoryAzure sASRepositoryAzure;

    public SASRepositoryAzure getsASRepositoryAzure() {
        return sASRepositoryAzure;
    }

    @Override
    public void upload(InputStream file, String idStorage, String sas) throws StorageException, IOException,
            URISyntaxException {
        getsASRepositoryAzure().upload(file, idStorage, acount, sas);

    }

}
