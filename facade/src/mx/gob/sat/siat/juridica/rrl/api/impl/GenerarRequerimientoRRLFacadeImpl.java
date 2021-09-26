/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.rrl.api.GenerarRequerimientoRRLFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.RequerimientoDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.AtenderSolicitudRevocacionService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade para atender la generaci&oacute;n del requerimiento.
 * 
 * @author Softtek
 * 
 */
@Component("generarRequerimientoRRLFacade")
public class GenerarRequerimientoRRLFacadeImpl extends BaseFacadeImpl implements GenerarRequerimientoRRLFacade {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -4455000616613525801L;
    /** Servicio para atender las peticiones del requerimiento */
    @Autowired
    private transient RequerimientoServices requerimientoServices;
    /** Servicio para avanzar la generaci&oacute;n del requerimiento */
    @Autowired
    private transient FirmaTareaService firmaTareaService;
    /** Objeto para transformaciones de requerimiento */
    @Autowired
    private transient RequerimientoDTOTransformer requerimientoTransformer;
    /** Objeto para transformaciones de cat&aacute;logos */
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    /** Objeto para transformaciones de tramite */
    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient AtenderSolicitudRevocacionService atenderSolicitudRevocacionService;

    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;
    
    @Autowired
    private transient AsignarTareaCorreoService asignarTareaCorreoService;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<CatalogoDTO> obtenerTiposDeRequerimiento() {
        List<EnumeracionTr> listaEnumeraciones =
                requerimientoServices.obtenerTiposDeRequerimiento(EnumeracionConstantes.TIPOS_REQUERIMIENTO);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener las autoridades a las que se
     * solicitar&aacute; el requerimiento.
     */
    @Override
    public List<CatalogoDTO> obtenerAutoridadesRRL() {
        List<UnidadAdministrativa> listaUnidades = requerimientoServices.obtenerUnidadesTodas();
        return catalogoDTOTransformer.transformarDTO(listaUnidades);
    }

    /**
     * M&eacute;todo para obtener los autorizadores del requerimiento.
     */
    public List<CatalogoDTO> obtenerAutorizadores(String numeroAsunto) {
        List<Persona> listaAutorizadores = requerimientoServices.obtenerAutorizadores(numeroAsunto);
        return catalogoDTOTransformer.transformarPersonas(listaAutorizadores);
    }

    /**
     * M&eacute;todo para preparar la generaci&oacute;n del
     * requerimiento.
     */
    public RequerimientoDTO prepararRequerimiento(String numeroAsunto, UserProfileDTO userProfile) {
        RequerimientoDTO requerimiento = new RequerimientoDTO();
        requerimiento.setTramite(tramiteDTOTransformer.transformarDTO(requerimientoServices
                .buscarTramitePorAsunto(numeroAsunto)));
        if (userProfile != null) {
            requerimiento.setNombrePersona(userProfile.getNombreCompleto());
            requerimiento.setRfc(userProfile.getRfc());
        }
        return requerimiento;
    }

    /**
     * M&eacute;todo para guardar el requerimiento.
     * @throws BusinessException 
     */
    @Override
    public void generarRequerimiento(String rfcAutorizador, List<RequerimientoDTO> requerimientos,
            DatosBandejaTareaDTO datosBandeja, String rfc) throws BusinessException {


        List<Requerimiento> requerimientosGuardar = new ArrayList<Requerimiento>();
        for (RequerimientoDTO requerimiento : requerimientos) {

            Requerimiento req = requerimientoTransformer.transformarGenerarRequerimiento(requerimiento);
            req.setEstadoRequerimiento(EstadoRequerimiento.GENERADO);
            req.setTramite(new Tramite(requerimiento.getTramite().getNumeroAsunto()));
            req.setIdUsuario(rfc);
            req.setTipoRequerimientoBpm(req.getTipoRequerimiento());
            requerimientosGuardar.add(req);

        }
        List<RequerimientoTarea> requerimientosGenerados = requerimientoServices.guardarRequerimientos(requerimientosGuardar);
        getLogger().debug("rfcAutorizador {}", rfcAutorizador);
        getLogger().debug("rfc {}", rfc);
        firmaTareaService.enviarTareaRequerimiento(datosBandeja.getNumeroAsunto(), datosBandeja.getIdTareaUsuario().toString(), rfcAutorizador, rfc, requerimientosGenerados);
        asignarTareaCorreoService.enviarCorreo(datosBandeja.getNumeroAsunto(), rfcAutorizador, "Autorizar Requerimiento");

    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosComplementarios(Long idSolicitud) {

        return documentoDTOTransformer.tranformarDocSolicitud(atenderSolicitudRevocacionService
                .obtenerDocumentosComplementarios(idSolicitud));
    }

}
