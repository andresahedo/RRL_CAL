package mx.gob.sat.siat.juridica.nube.azure.repository.impl;

import mx.gob.sat.siat.juridica.nube.azure.repository.AzureCifradoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("azureCifradoRepository")
public class AzureCifradoRepositoryImpl extends AzureRepositoryImpl implements AzureCifradoRepository {

    /**
     * 
     */
    private static final long serialVersionUID = 3553064786901137137L;

    @Value("${azure.cifrado.conecction.urlConecction}")
    private String storageConnectionString;

    @Value("${azure.cifrado.conecction.container}")
    private String container;

    public String getStorageConnectionString() {
        return storageConnectionString;
    }

    public void setStorageConnectionString(String storageConnectionString) {
        this.storageConnectionString = storageConnectionString;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

}
