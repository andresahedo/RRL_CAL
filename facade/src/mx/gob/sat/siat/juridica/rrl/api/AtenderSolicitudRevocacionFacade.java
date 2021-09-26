package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.*;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionAbogadoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;

import java.util.List;
import java.util.Map;

public interface AtenderSolicitudRevocacionFacade extends BaseFacade {

    ResolucionAbogadoDTO obtenerInformacionFuncionario(SolicitudDTO solicitud, TramiteDTO tramiteDTO);

    ResolucionAbogadoDTO consultarSolicitud(SolicitudDTO solicitud);

    ResolucionAbogadoDTO mostrarResolucion(ResolucionAbogadoDTO resolucion);

    void guardar(ResolucionAbogadoDTO resolucionAbogadoDTO);

    void autorizarSolicitud(ResolucionAbogadoDTO resolucionAbogadoDTO);

    void firmar(ResolucionAbogadoDTO resolucionAbogadoDTO);

    List<CatalogoDTO> obtenerCatalogoConcepto();

    List<CatalogoDTO> obtenerCatalogoRecurso();

    List<CatalogoDTO> obtenerCatalogoCaracteristicas();

    List<UnidadAdministrativaDTO> obtenerCatalogoUnidadAdministrativa();

    List<AgraviosDTO> obtenerCatalogoAgravio();

    List<CatalogoDTO> obtenerCatalogoSentido();
    
    Map<String,CatalogoDTO> obtenerSentidosResolucionPerdedores();

    ResolucionImpugnadaDTO verificarExistencia(ResolucionImpugnadaDTO abogadoDTO);

    List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto);

    boolean validaBanderaGuardar(String numFolioResolucion);

    List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud);

    String getIdeTareaOrigen();

    TramiteDTO validarMedioDefensaResolucion(ResolucionImpugnadaDTO resolucionDTO);

    void actualizaDatosBP(int idInstancia, String numAsunto, String rfcUsuario);

    String obtenerObservacionTurnado(String numeroAsunto);
    
    List<UnidadAdministrativaDTO> obtenerCatalogoUnidadAdministrativaVig();

}
