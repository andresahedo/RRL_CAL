/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;

import javax.persistence.*;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCA_TIPOTRAMITE_UNIADMIN")
public class TipoTramiteUnidadAdministrativaCatalogo extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTIPOTRAMITEUNIADMIN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_TIPOTRAMITE_UNIADMIN")
    @SequenceGenerator(name = "RVCQ_TIPOTRAMITE_UNIADMIN", sequenceName = "RVCQ_TIPOTRAMITE_UNIADMIN",
            allocationSize = 1)
    private Integer idTipoTramiteUniAdmin;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTIPOTRAMITE", referencedColumnName = "IDTIPOTRAMITE", updatable = false)
    private TipoTramite tipoTramite;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUNIADMIN", referencedColumnName = "IDUNIADMIN", updatable = false)
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "ideTipoTramiteUniAdmin" tipo String
     */
    @Column(name = "IDETIPOTRAMITEUNIADMIN", insertable = false, updatable = false)
    private String ideTipoTramiteUniAdmin;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public TipoTramiteUnidadAdministrativaCatalogo() {}

    /**
     * 
     * @return tipoTramite
     */
    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    /**
     * 
     * @param tipoTramite
     *            a fijar
     */
    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * 
     * @return unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * 
     * @param unidadAdministrativa
     *            a fijar
     */
    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * 
     * @return ideTipoTramiteUniAdmin
     */
    public String getIdeTipoTramiteUniAdmin() {
        return ideTipoTramiteUniAdmin;
    }

    /**
     * 
     * @param ideTipoTramiteUniAdmin
     *            a fijar
     */
    public void setIdeTipoTramiteUniAdmin(String ideTipoTramiteUniAdmin) {
        this.ideTipoTramiteUniAdmin = ideTipoTramiteUniAdmin;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the idTipoTramiteUniAdmin
     */
    public Integer getIdTipoTramiteUniAdmin() {
        return idTipoTramiteUniAdmin;
    }

    /**
     * @param idTipoTramiteUniAdmin
     *            the idTipoTramiteUniAdmin to set
     */
    public void setIdTipoTramiteUniAdmin(Integer idTipoTramiteUniAdmin) {
        this.idTipoTramiteUniAdmin = idTipoTramiteUniAdmin;
    }
}
