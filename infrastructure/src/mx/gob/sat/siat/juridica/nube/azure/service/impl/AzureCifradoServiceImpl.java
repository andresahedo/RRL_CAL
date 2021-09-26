package mx.gob.sat.siat.juridica.nube.azure.service.impl;

import mx.gob.sat.siat.juridica.nube.azure.repository.AzureCifradoRepository;
import mx.gob.sat.siat.juridica.nube.azure.service.AzureCifradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("azureCifradoService")
public class AzureCifradoServiceImpl extends AzureServiceImpl implements AzureCifradoService {

    /**
     * 
     */
    private static final long serialVersionUID = 4895957095956853625L;

    @Autowired
    private AzureCifradoRepository azureRepository;

    @Value("${azure.cifrado.conecction.container}")
    private String container;

    @Value("${azure.cifrado.conecction.url}")
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

    public AzureCifradoRepository getAzureRepository() {
        return azureRepository;
    }

    public void setAzureRepository(AzureCifradoRepository azureRepository) {
        this.azureRepository = azureRepository;
    }

}
