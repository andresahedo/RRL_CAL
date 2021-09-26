package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum ProcesosBPM {

    RECURSOS_REVOCACION("25.27b7f0a9-d50a-4f9f-823c-abd94695078b",
            "2066.e4e05fa1-62ec-4975-8471-00bf0ea0f66e",
            "BandejaTareasSIAT"), CONSULTAS_Y_AUTORIZACIONES("25.ed86e8df-7cd7-4066-b1b1-1d1be1401dca",
            "2066.e4e05fa1-62ec-4975-8471-00bf0ea0f66e",
            "BandejaTareasSIAT");

    private String bpdId;

    private String processAppId;

    private String nombreConsulta;

    private ProcesosBPM(String bpdId, String processAppId, String nombreConsulta) {
        this.bpdId = bpdId;
        this.processAppId = processAppId;
    }

    public String getBpdId() {
        return bpdId;
    }

    public void setBpdId(String bpdId) {
        this.bpdId = bpdId;
    }

    public String getProcessAppId() {
        return processAppId;
    }

    public void setProcessAppId(String processAppId) {
        this.processAppId = processAppId;
    }

    public String getNombreConsulta() {
        return nombreConsulta;
    }

    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
    }

}
