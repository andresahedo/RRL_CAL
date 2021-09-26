package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.AccionesBitacoraConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BitacoraAUDTOTransformer extends DTOTransformer<BitacoraAU, BitacoraDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private PersonaServices personaServices;

    @Autowired
    private UnidadAdministrativaServices unidadService;

    @Autowired
    private TipoTramiteServices tipoTramiteService;

    public BitacoraDTO transformarDTO(BitacoraAU bitacora) {

        BitacoraDTO bitacoraDTO = new BitacoraDTO();

        bitacoraDTO.setDescripcion(bitacora.getDescripcion());
        bitacoraDTO.setFechaAccion(bitacora.getFechaAccion());
        bitacoraDTO.setIdAccion(bitacora.getIdAccion());
        bitacoraDTO.setIdAplicadoA(bitacora.getIdAplicadoA());
        bitacoraDTO.setIdBitacora(bitacora.getIdBitacora());
        bitacoraDTO.setIdRealizadoPor(bitacora.getIdRealizadoPor());
        bitacoraDTO.setIdRol(bitacora.getIdRol());
        bitacoraDTO.setIdTipoTramite(bitacora.getTipoTramite() != null ? bitacora.getTipoTramite().getIdTipoTramite()
                : null);
        bitacoraDTO.setIdTramite(bitacora.getTramite() != null ? bitacora.getTramite().getNumeroAsunto() : null);
        bitacoraDTO.setIdUniAdmin(bitacora.getUnidadAdministrativa() != null ? bitacora.getUnidadAdministrativa()
                .getClave() : null);
        bitacoraDTO.setModalidad(bitacora.getIdModalidad());
        if (bitacoraDTO.getIdRealizadoPor().equals(AccionesBitacoraConstants.ACCION_REALIZA_SISTEMA)) {
            bitacoraDTO.setNomEmpRealiza("SISTEMA");
        }
        else {
            bitacoraDTO.setNomEmpRealiza(bitacora.getNombreRP());
        }
        bitacoraDTO.setNomEmpAplicadoA(bitacora.getNombreAA());
        bitacoraDTO.setNomUniAdmin(bitacora.getUnidadAdministrativa() != null ? bitacora.getUnidadAdministrativa()
                .getNombre() : null);
        bitacoraDTO.setNumEmpAplicadoA(bitacora.getNumEmpleadoAA());
        bitacoraDTO.setNumEmpRealiza(bitacora.getNumEmpleadoRP());
        bitacoraDTO.setRfcAplicadoA(bitacora.getRfcAA());
        bitacoraDTO.setRfcEmpleadoRealiza(bitacora.getRfcRP());
        bitacoraDTO.setAccion(AccionesBitacora.parse(bitacora.getIdAccion()).getDescripcion());

        return bitacoraDTO;
    }

    public BitacoraAU tranformar(BitacoraDTO bitacoraDTO) {
        BitacoraAU bitacora = new BitacoraAU();

        Persona personaRealiza = personaServices.obtenerPersonaPorRFC(bitacoraDTO.getRfcEmpleadoRealiza());

        // UnidadAdministrativa
        if (bitacoraDTO.getIdUniAdmin() != null) {
            UnidadAdministrativa unidad = unidadService.obtenerUnidadPorId(bitacoraDTO.getIdUniAdmin());
            bitacora.setNombreUnidadAdmin(unidad.getNombre());
            bitacora.setUnidadAdministrativa(unidad);
        }

        if (bitacoraDTO.getIdTipoTramite() != null) {
            TipoTramite tipoTramite = tipoTramiteService.obtenerTipoTramiteId(bitacoraDTO.getIdTipoTramite());
            bitacora.setTipoTramite(tipoTramite);
        }
        // personaAPlica
        if (bitacoraDTO.getRfcAplicadoA() != null) {
            Persona personaAPlica = personaServices.buscarPersonaPorRFC(bitacoraDTO.getRfcAplicadoA());
            bitacora.setNombreAA(personaAPlica.getNombre() + " " + personaAPlica.getApellidoPaterno() + " "
                    + personaAPlica.getApellidoMaterno());
            bitacora.setNumEmpleadoAA(personaAPlica.getNumeroEmpleado().toString());
            bitacora.setRfcAA(personaAPlica.getRfc());
        }

        // personaRealiza
        bitacora.setNombreRP(personaRealiza.getNombre() + " " + personaRealiza.getApellidoPaterno() + " "
                + personaRealiza.getApellidoMaterno());
        bitacora.setNumEmpleadoRP(personaRealiza.getNumeroEmpleado().toString());
        bitacora.setRfcRP(personaRealiza.getRfc());
        bitacora.setIdRol(bitacoraDTO.getIdRol());

        // Otros
        bitacora.setFechaAccion(new Date());
        bitacora.setIdAplicadoA(bitacoraDTO.getIdAplicadoA());
        bitacora.setIdRealizadoPor(bitacoraDTO.getIdRealizadoPor());
        bitacora.setIdAccion(bitacoraDTO.getIdAccion());

        // Detalles del tramite
        if (bitacoraDTO.getIdTramite() != null) {
            bitacora.getTramite().setNumeroAsunto(bitacoraDTO.getIdTramite());
        }
        bitacora.setIdModalidad(bitacoraDTO.getModalidad());
        bitacora.setDescripcion(bitacoraDTO.getDescripcion());

        return bitacora;
    }
}
