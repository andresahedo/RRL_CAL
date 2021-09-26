package mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("SELECT DISTINCT tramite.IDSERVICIO as SERVICIO, tramite.DESCSERVICIO as DESCRIPCIONSERVICIO, "
        + "ttramite.BLNACTIVO as BLNACTIVO, ttramite.IDUNIADMIN as IDUNIADMIN "
        + "FROM RVCC_TIPOTRAMITE tramite LEFT JOIN RVCA_TIPOTRAMITE_UNIADMIN ttramite "
        + "ON tramite.IDTIPOTRAMITE = ttramite.IDTIPOTRAMITE")
public class TipoTramiteConfigurar extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3595782366670473610L;

    @Id
    @Column(name = "SERVICIO")
    private String idServicio;
    @Column(name = "IDUNIADMIN")
    private String idUnidadAdministrativa;
    @Column(name = "BLNACTIVO")
    private Integer blnSeleccionado;
    @Column(name = "DESCRIPCIONSERVICIO")
    private String descripcionServicio;

    /**
     * @return the idServicio
     */
    public String getIdServicio() {
        return idServicio;
    }

    /**
     * @param idServicio
     *            the idServicio to set
     */
    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    /**
     * @return the idUnidadAdministrativa
     */
    public String getIdUnidadAdministrativa() {
        return idUnidadAdministrativa;
    }

    /**
     * @param idUnidadAdministrativa
     *            the idUnidadAdministrativa to set
     */
    public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
        this.idUnidadAdministrativa = idUnidadAdministrativa;
    }

    /**
     * @return the blnSeleccionado
     */
    public Integer getBlnSeleccionado() {
        return blnSeleccionado;
    }

    /**
     * @param blnSeleccionado
     *            the blnSeleccionado to set
     */
    public void setBlnSeleccionado(Integer blnSeleccionado) {
        this.blnSeleccionado = blnSeleccionado;
    }

    /**
     * @return the descripcionServicio
     */
    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    /**
     * @param descripcionServicio
     *            the descripcionServicio to set
     */
    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

}
