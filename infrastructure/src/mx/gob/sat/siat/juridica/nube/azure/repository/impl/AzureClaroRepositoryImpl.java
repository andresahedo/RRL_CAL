package mx.gob.sat.siat.juridica.nube.azure.repository.impl;

import mx.gob.sat.siat.juridica.nube.azure.repository.AzureClaroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("azureClaroRepositoryImpl")
public class AzureClaroRepositoryImpl extends AzureRepositoryImpl implements AzureClaroRepository {

    /**
     * 
     */
    private static final long serialVersionUID = 354269137913507830L;

    @Value("${azure.claro.conecction.urlConecction}")
    private String storageConnectionString;

    @Value("${azure.claro.conecction.container}")
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
