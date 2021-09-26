package mx.gob.sat.siat.juridica.base.api;

import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;

import java.util.List;

public interface TurnarRecursoRevocacionFacade extends BaseFacade {

    List<AbogadoDTO> obtenerAbogados(String numAsunto);

    TramiteDTO obtenerEstadoProcesal(String numAasunto);

    void enviarCorreo(String numeroAsunto, Long idPersona, String nombreTarea);

    String getIdeTareaOrigen();

    List<CatalogoDTO> obtenerMotivosRechazo();

}
