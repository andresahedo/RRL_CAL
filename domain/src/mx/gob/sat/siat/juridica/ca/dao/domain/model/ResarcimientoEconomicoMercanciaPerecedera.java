package mx.gob.sat.siat.juridica.ca.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESCOMPOCISION_ANIMALES)
public class ResarcimientoEconomicoMercanciaPerecedera extends
      SolicitudConsultaAutorizacion {

   /**
    * Numero de serie
    */
   private static final long serialVersionUID = 8080871268282216411L;

   /**
   * Atributo privado "tipoTramite" tipo TipoTramite
   */
  @Transient
  private TipoTramite tipoTramite = new TipoTramite(
          Integer.valueOf(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESCOMPOCISION_ANIMALES));

  /**
   * Atributo privado "discriminatorValue" tipo String
   */
  @Transient
  private String discriminatorValue = DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESCOMPOCISION_ANIMALES;

  /**
   * @return return tipoTramite
   */
  @Override
  public TipoTramite getTipoTramite() {
      return tipoTramite;
  }

  /**
   * @return discriminatorValue
   */
  @Override
  public String getDiscriminatorValue() {
      return discriminatorValue;
  }
}
