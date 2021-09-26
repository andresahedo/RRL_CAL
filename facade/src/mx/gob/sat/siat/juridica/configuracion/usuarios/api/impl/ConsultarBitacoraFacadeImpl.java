package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.BitacoraAU;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.EnumeracionServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.comun.service.ObtenerUnidadesAdministrativasService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConsultarBitacoraFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.BitacoraDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroBitacoraAGDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.BitacoraAUDTOTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.BitacorAUService;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.EnumeracionDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("consultarBitacoraFacade")
public class ConsultarBitacoraFacadeImpl extends BaseCloudFacadeImpl implements ConsultarBitacoraFacade {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -7943983871093209178L;

    @Autowired
    private transient ObtenerUnidadesAdministrativasService obtenerUnidadesAdministrativasService;

    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient BitacoraAUDTOTransformer bitacoraTransformer;

    @Autowired
    private transient BitacorAUService bitacorAUService;

    @Autowired
    private EnumeracionServices enumeracionServices;

    @Autowired
    private EnumeracionDTOTransformer enumeracionDTOTransformer;

    @Override
    public List<CatalogoDTO> obtenerUnidadesAdministrativas() {
        return catalogoDTOTransformer.transformarDTO(obtenerUnidadesAdministrativasService
                .obtenerCatalogoUnidadesAdministrativas());
    }

    @Override
    public List<BitacoraDTO> obtenerDatosBitacora(FiltroBitacoraAGDTO filtroBitacora) {
        List<BitacoraDTO> bitacoraDTO = new ArrayList<BitacoraDTO>();

        BitacoraAU modelBitacora = new BitacoraAU();
        UnidadAdministrativa modelUnidadAdmin = new UnidadAdministrativa();
        modelUnidadAdmin.setClave(filtroBitacora.getIdUniAdmin());
        modelBitacora.setUnidadAdministrativa(modelUnidadAdmin);
        modelBitacora.setNumEmpleadoAA(filtroBitacora.getNumeroEmpAplicado() != null ? filtroBitacora
                .getNumeroEmpAplicado().toString() : null);
        modelBitacora.setRfcAA(filtroBitacora.getRfcAplicadoA() != null && !filtroBitacora.getRfcAplicadoA().equals("")
                ? filtroBitacora.getRfcAplicadoA() : null);
        modelBitacora.setIdAplicadoA(filtroBitacora.getAplicadoA() != null && !filtroBitacora.getAplicadoA().equals("")
                ? filtroBitacora.getAplicadoA() : null);
        modelBitacora.setNumEmpleadoRP(filtroBitacora.getNumeroEmpRealiza() != null ? filtroBitacora
                .getNumeroEmpRealiza().toString() : null);
        modelBitacora.setRfcRP(filtroBitacora.getRfcRealizaPor() != null
                && !filtroBitacora.getRfcRealizaPor().equals("") ? filtroBitacora.getRfcRealizaPor() : null);
        modelBitacora.setIdRealizadoPor(filtroBitacora.getRealizadoPor() != null
                && !filtroBitacora.getRealizadoPor().equals("") ? filtroBitacora.getRealizadoPor() : null);

        List<BitacoraAU> listaBitacoraAU =
                bitacorAUService.obtenerDatosBitacora(modelBitacora, filtroBitacora.getFechaInicial(),
                        filtroBitacora.getFechaFinal());
        for (BitacoraAU bitacora : listaBitacoraAU) {
            bitacoraDTO.add(bitacoraTransformer.transformarDTO(bitacora));
        }
        return bitacoraDTO;
    }

    @Override
    public List<CatalogoDTO> obtenerComboSeleccion() {
        return enumeracionDTOTransformer.transformarDTO(enumeracionServices
                .obtenerEnumeracionPorId(CatalogoConstantes.ENU_ACTOR_BITACORA));
    }

    @Override
    public List<CatalogoDTO> obtenerComboSeleccionAplica() {
        return enumeracionDTOTransformer.transformarDTO(enumeracionServices
                .obtenerEnumeracionPorId(CatalogoConstantes.ENU_ACTOR_BITACORA1));
    }

    @Override
    public List<CatalogoDTO> obtenerUnidadAdministrativa(String cveUnidad) {
        List<UnidadAdministrativa> unidad = new ArrayList<UnidadAdministrativa>();
        unidad.add(unidadAdministrativaServices.obtenerUnidadPorId(cveUnidad));
        return catalogoDTOTransformer.transformarDTO(unidad);
    }

    @Override
    public String obtenerRangoDeDias() {
        return enumeracionServices.obtenerParametroByEnum(CatalogoConstantes.DIAS_CONSULTA_BITACORA);
    }

}
