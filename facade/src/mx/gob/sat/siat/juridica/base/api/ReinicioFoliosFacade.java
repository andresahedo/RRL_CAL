package mx.gob.sat.siat.juridica.base.api;

import java.io.Serializable;

public interface ReinicioFoliosFacade extends Serializable {
Integer calcularFolio();
void reiniciarFolios();
boolean isAdministracionVisible();
}
