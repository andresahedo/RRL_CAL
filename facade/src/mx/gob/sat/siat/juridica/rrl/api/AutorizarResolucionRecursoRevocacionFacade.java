package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import java.util.List;

public interface AutorizarResolucionRecursoRevocacionFacade extends BaseFacade {

    ResolucionAbogadoDTO consultarInformacionInicial(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO);

    void guardar(ResolucionAbogadoDTO resolucionAbogadoDTO);

    ResolucionImpugnadaDTO validarExistencia(ResolucionImpugnadaDTO abogadoDTO);

    List<DocumentoDTO> obtenerDocumentosComplementariosAutorizacion(Long idSolicitud);

    void rechazarResolucion(String numAsunto, String idTarea, String rfc, Long idSolicitud);

}
