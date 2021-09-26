package mx.gob.sat.siat.juridica.nube.azure.service.impl;

import mx.gob.sat.siat.juridica.nube.azure.repository.AzureClaroRepository;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureClaroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("azureClaroService")
public class AzureClaroServiceImpl extends AzureServiceImpl implements AzureClaroService {

    /**
     * 
     */
    private static final long serialVersionUID = 4895957095956853625L;

    @Autowired
    private AzureClaroRepository azureRepository;

    @Value("${azure.claro.conecction.container}")
    private String container;

    @Value("${azure.claro.conecction.url}")
    private String url;

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AzureClaroRepository getAzureRepository() {
        return azureRepository;
    }

    public void setAzureRepository(AzureClaroRepository azureRepository) {
        this.azureRepository = azureRepository;
    }

}
