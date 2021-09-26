package mx.gob.sat.siat.juridica.ara.dto;

import mx.gob.sat.sgi.SgiCripto.ara.util.ByteCert;

public class CertificadoDTO {
    private String estado;
    private String tipoCert;
    private String vigenciaFinal;
    private String vigenciaInicial;
    private ByteCert aCertificado;

    public CertificadoDTO() {
    }

    /**
     * @return the Estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the Estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**estado
     * @return the TipoCert
     */
    public String getTipoCert() {
        return tipoCert;
    }

    /**
     * @param tipoCert the TipoCert to set
     */
    public void setTipoCert(String tipoCert) {
        this.tipoCert = tipoCert;
    }

    /**
     * @return the VigenciaFinal
     */
    public String getVigenciaFinal() {
        return vigenciaFinal;
    }

    /**
     * @param vigenciaFinal the VigenciaFinal to set
     */
    public void setVigenciaFinal(String vigenciaFinal) {
        this.vigenciaFinal = vigenciaFinal;
    }

    /**
     * @return the VigenciaInicial
     */
    public String getVigenciaInicial() {
        return vigenciaInicial;
    }

    /**
     * @param vigenciaInicial the VigenciaInicial to set
     */
    public void setVigenciaInicial(String vigenciaInicial) {
        this.vigenciaInicial = vigenciaInicial;
    }

    /**
     * @return the aCertificado
     */
    public ByteCert getaCertificado() {
        return aCertificado;
    }

    /**
     * @param aCertificado the aCertificado to set
     */
    public void setaCertificado(ByteCert aCertificado) {
        this.aCertificado = aCertificado;
    }
}
