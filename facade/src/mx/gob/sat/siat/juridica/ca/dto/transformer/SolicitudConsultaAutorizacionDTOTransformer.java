package mx.gob.sat.siat.juridica.ca.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.MediosDefensa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.DomicilioSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.*;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Se debe agregar las nuevas clases para las nuevas modalidades Hidrocarburos P96156
 * */

@Component
public class SolicitudConsultaAutorizacionDTOTransformer extends
        DTOTransformer<SolicitudConsultaAutorizacionDTO, SolicitudConsultaAutorizacion> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public SolicitudConsultaAutorizacion transformarDTO(SolicitudConsultaAutorizacionDTO solicitud) {

        SolicitudConsultaAutorizacion solicitudCA = null;
        if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            solicitudCA = new SolicitudClasificacionArancelaria();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_REGULACION_DE_VEHICULOS)) {
            solicitudCA = new SolicitudRegularizacionVehiculos();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_MATERIAL_ADUANERA_Y_COMERCIO_EXTERIOR)) {
            solicitudCA = new SolicitudConsultasMateriaAduaneraComercioExterior();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_CODIGO_FISCAL_FEDERACION)) {
            solicitudCA = new SolicitudConsultaMateriaCodigoFiscalFederacion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_IVA)) {
            solicitudCA = new ConsultaLeyIVA();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_ISR)) {
            solicitudCA = new SolicitudConsultaLeyISR();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_ADUANERA)) {
            solicitudCA = new SolicitudConsultaMateriaLeyAduanera();
        }
        else if (solicitud.getTipoTramite()
                .equals(DiscriminadorConstants.T2_CONTINUAR_EMITIENDO_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new SolicitudEmitiendoMonederosElectronicos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_NO_PAGO_IVA_IMPORTACION_MERCANCIAS)) {
            solicitudCA = new SolicitudNoPagoIvaImportacionMercancias();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_DESTRUCCION_CAMBIO_REGIMEN_MERCANCIAS_DESTRUIDAS_DANIOS_PAIS)) {
            solicitudCA = new SolicitudDestruccionRegimenMercanciasDestruidas();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_DONACION_DESPERDICIOS_EMPRESAS_IMMEX)) {
            solicitudCA = new SolicitudDonacionDesperdiciosEmpresasIMME();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SEGURIDAD_NACIONAL)) {
            solicitudCA = new SolicitudSeguridadNacional();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACION_NO_DEDUCCION_COSTO_ADQUISICION)) {
            solicitudCA = new SolicitudAutorizacionNoDisminucionCostoAdquisicion();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_TRANSLADO_MERCANCIAS_INTERIOR_PAIS_INDUSTRIA_AUTOMOTRIZ)) {
            solicitudCA = new SolicitudTrasladoMercanciasIndustriaAutomotriz();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PRORROGA_PLAZOS_IMPORTACION_EXPORTACION)) {
            solicitudCA = new SolicitudProrrogasPlazosImportacionExportacion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_DUDUCCION_PERDIDAS_TITULOS)) {
            solicitudCA = new SolicitudAutorizacionDeduccionPerdidas();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RECTIFICACION_PEDIMENTOS)) {
            solicitudCA = new SolicitudRectificacionPedimentos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_IMPORTACION_UNICA_SIN_PADRON)) {
            solicitudCA = new SolicitudImportacionSinInscripcionPadron();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_IMPORTACION_UNICA_SIN_CONCLUIR_TRAMITE_INSCRIPCION)) {
            solicitudCA = new SolicitudImportacionSinConcluirTramiteInscripcion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CANDADOS_OFICIALES)) {
            solicitudCA = new SolicitudCandadosOficiales();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MENSAJE_CASA_ART61_FACCIONVII_LA)) {
            solicitudCA = new SolicitudMenajeCasaArt61();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_IMPORTACION_DEFINITIVA_VEHICULOS_ESPECIALES_ADAPTADOS)) {
            solicitudCA = new SolicitudImportacionDefinitivaVehiculosEspeciales();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_DONACION_FISCO_FEDERAL_ART147_RLA)) {
            solicitudCA = new SolicitudDonacionFiscoFederalArt147();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_IMPUESTO_ESPECIAL_PRODUCTOS_SERVICIOS)) {
            solicitudCA = new SolicitudConsultaImpuestoEspecialProductosServicios();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RETORNO_SEGURO)) {
            solicitudCA = new SolicitudRetornoSeguro();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_DONACION_EXTRANJERO_ART6_FRACCIONXVII_LEY_ADUANERA)) {
            solicitudCA = new SolicitudDonacionesExtranjero();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RESARCIMIENTO)) {
            solicitudCA = new SolicitudResarcimiento();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_IMPUESTO_ESPECIAL_PRODUCTOS_SERVICIOS_IMPORTACIONES)) {
            solicitudCA = new SolicitudConsultaImpuestoEspecialProductosServiciosImportaciones();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PROVEEDOR_CERTIFICACION_CFDI)) {
            solicitudCA = new SolicitudProveedorCertificacionCFDI();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_CENTRO_DESTRUCCION_VEHICULOS)) {
            solicitudCA = new SolicitudAutorizacionCentrosDestruccionVehiculos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_FUSION)) {
            solicitudCA = new SolicitudAutorizacionFusion();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CLAVE_PRODUCIR_IMPORTAR_NUEVAS_MARCAS_TABACO_LABRADOS)) {
            solicitudCA = new SolicitudImportacionNuevasMarcasTabacos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new SolicitudMonederosElectronicos();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_PRORROGAS_IMPORTACION_EXPORTACION_CUADERNOS_ATA)) {
            solicitudCA = new SolicitudProrrogasImportacionExportacionCuadernosATA();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PRORRGOAS_IMMEX_CANCELADO)) {
            solicitudCA = new SolicitudProrrogasIMMEXVencido();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new AvisoRenovacionMonederoElectronico();
        }
        /* inicia bloque: a este nivel se tendr�a que insertar las nuevas modalidades para Hidrocarburos, como else if, y continuar con el flujo como esta */
        
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_PRECIOS_DE_TRANSFERENCIA)){
                solicitudCA = new ConsultasMateriaPreciosTransferencia();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_A_LOS_DEPOSITOS_EN_EFECTIVO)){
                solicitudCA = new ConsultasMateriaLeyImpuestoDepositosEfectivo();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_EMPRESARIAL_A_TASA_UNICA)){
                solicitudCA = new ConsultasMateriaLeyImpuestoEmpresarialTasaUnica();
        } 
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DE_INGRESOS_SOBRE_HIDROCARBUROS)){            
                solicitudCA = new ConsultasMateriaLeyIngresosHidrocarburos();
        } 
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_FUSION_DENTRO5ANIOS_POSTERIORES_FUSION_O_ESCISION)){
                solicitudCA = new AutorizacionFusionDentro5AniosPostetioresFusionOEscision();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_PARA_LA_DETERMINACION_DE_UN_REGIMEN_FISCAL)){            
                solicitudCA = new AutorizacionDeterminacionRegimenFiscal();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_BAPAS)){            
                solicitudCA = new AutorizacionesBAPAs();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_MAPS)){
                solicitudCA = new AutorizacionesMAPs();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_DIFERIMIENTO_DE_IMPUESTO_161_LISR)){            
                solicitudCA = new AutorizacionDiferimientoImpuesto161LISR();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOLICITUD_DE_RESOLUCION_PARA_DEDUCIR_INTERESES_POR_DEUDAS_ENTRE_PARTES_RELACIONADAS)) {
                solicitudCA = new SolicitudResolucionDeducirInteresesDeudasEntrePartesRelacionadas();
        }
           /* termina bloque: nuevas modalidades para Hidrocarburos */
        else {
            solicitudCA = new SolicitudAutorizacionesMateriaImpuestosInternos();
        }

        solicitudCA.setCveUsuarioCapturista(solicitud.getSolicitante().getRfc());
        solicitudCA.setFechaCreacion(new Date());
        solicitudCA.setFechaEstatus(new Date());
        solicitudCA.setFechaActualizacion(new Date());
        solicitudCA.setFechaInicioTramite(new Date());
        solicitudCA.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);

        solicitudCA.setUnidadAdminBalanceo(solicitud.getClaveUnidadEmisora());
        solicitudCA.setUnidadAdministrativaRepresentacionFederal(new UnidadAdministrativa(solicitud
                .getClaveUnidadEmisora()));
        solicitudCA.setMonto(new BigDecimal(solicitud.getMonto().toString()));
        solicitudCA.setGranContribuyente(Integer.valueOf(solicitud.getGranContribuyete()));
        solicitudCA.setDescribirActividad(solicitud.getActividadInteresado());
        solicitudCA.setDescribirHecho(solicitud.getHechosCircunstancias());
        solicitudCA.setDescribirRazon(solicitud.getRazonNegocio());
        if (solicitud.getManifiesto1()) {
            solicitudCA.setMedioDefensa(1);
            solicitudCA.setMedioDefensaHelper(transformarMediosDefensa(solicitud));

        }
        else {
            solicitudCA.setMedioDefensa(0);
        }

        if (solicitud.getManifiesto2()) {
            solicitudCA.setSujetoEjercicio(1);
            solicitudCA.setDescribirPeriodoContribucion(solicitud.getPeriodosContribuciones());
            solicitudCA.setDentroPlazo(1);
        }
        else {
            solicitudCA.setSujetoEjercicio(0);
            solicitudCA.setDentroPlazo(0);
        }
        solicitudCA.setSolicitanteHelper(transformarSolicitante(solicitud));
        solicitudCA.setPersonaORNHelper(transformarPersonaOirRecibirNotificaciones(solicitud));
        solicitudCA.setPersonaExtranjeroHelper(transformarResidenteExtranjero(solicitud));

        return solicitudCA;
    }

    public SolicitudConsultaAutorizacion transformarCALDTO(SolicitudCALDTO solicitud) {

        SolicitudConsultaAutorizacion solicitudCA = null;
        if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            solicitudCA = new SolicitudClasificacionArancelaria();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_REGULACION_DE_VEHICULOS)) {
            solicitudCA = new SolicitudRegularizacionVehiculos();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_MATERIAL_ADUANERA_Y_COMERCIO_EXTERIOR)) {
            solicitudCA = new SolicitudConsultasMateriaAduaneraComercioExterior();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_CODIGO_FISCAL_FEDERACION)) {
            solicitudCA = new SolicitudConsultaMateriaCodigoFiscalFederacion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_IVA)) {
            solicitudCA = new ConsultaLeyIVA();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_ISR)) {
            solicitudCA = new SolicitudConsultaLeyISR();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_LEY_ADUANERA)) {
            solicitudCA = new SolicitudConsultaMateriaLeyAduanera();
        }
        else if (solicitud.getTipoTramite()
                .equals(DiscriminadorConstants.T2_CONTINUAR_EMITIENDO_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new SolicitudEmitiendoMonederosElectronicos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_NO_PAGO_IVA_IMPORTACION_MERCANCIAS)) {
            solicitudCA = new SolicitudNoPagoIvaImportacionMercancias();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_DESTRUCCION_CAMBIO_REGIMEN_MERCANCIAS_DESTRUIDAS_DANIOS_PAIS)) {
            solicitudCA = new SolicitudDestruccionRegimenMercanciasDestruidas();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_DONACION_DESPERDICIOS_EMPRESAS_IMMEX)) {
            solicitudCA = new SolicitudDonacionDesperdiciosEmpresasIMME();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SEGURIDAD_NACIONAL)) {
            solicitudCA = new SolicitudSeguridadNacional();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACION_NO_DEDUCCION_COSTO_ADQUISICION)) {
            solicitudCA = new SolicitudAutorizacionNoDisminucionCostoAdquisicion();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACION_REDUCCION_GASTOS_USO_GOCE_TEMPORAL)) {
            solicitudCA = new SolicitudAutorizacionPagosUsoGoceTemporal();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_TRANSLADO_MERCANCIAS_INTERIOR_PAIS_INDUSTRIA_AUTOMOTRIZ)) {
            solicitudCA = new SolicitudTrasladoMercanciasIndustriaAutomotriz();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PRORROGA_PLAZOS_IMPORTACION_EXPORTACION)) {
            solicitudCA = new SolicitudProrrogasPlazosImportacionExportacion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_DUDUCCION_PERDIDAS_TITULOS)) {
            solicitudCA = new SolicitudAutorizacionDeduccionPerdidas();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RECTIFICACION_PEDIMENTOS)) {
            solicitudCA = new SolicitudRectificacionPedimentos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_IMPORTACION_UNICA_SIN_PADRON)) {
            solicitudCA = new SolicitudImportacionSinInscripcionPadron();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_IMPORTACION_UNICA_SIN_CONCLUIR_TRAMITE_INSCRIPCION)) {
            solicitudCA = new SolicitudImportacionSinConcluirTramiteInscripcion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CANDADOS_OFICIALES)) {
            solicitudCA = new SolicitudCandadosOficiales();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MENSAJE_CASA_ART61_FACCIONVII_LA)) {
            solicitudCA = new SolicitudMenajeCasaArt61();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_IMPORTACION_DEFINITIVA_VEHICULOS_ESPECIALES_ADAPTADOS)) {
            solicitudCA = new SolicitudImportacionDefinitivaVehiculosEspeciales();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_DONACION_FISCO_FEDERAL_ART147_RLA)) {
            solicitudCA = new SolicitudDonacionFiscoFederalArt147();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_IMPUESTO_ESPECIAL_PRODUCTOS_SERVICIOS)) {
            solicitudCA = new SolicitudConsultaImpuestoEspecialProductosServicios();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RETORNO_SEGURO)) {
            solicitudCA = new SolicitudRetornoSeguro();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_DONACION_EXTRANJERO_ART6_FRACCIONXVII_LEY_ADUANERA)) {
            solicitudCA = new SolicitudDonacionesExtranjero();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RESARCIMIENTO)) {
            solicitudCA = new SolicitudResarcimiento();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CONSULTA_IMPUESTO_ESPECIAL_PRODUCTOS_SERVICIOS_IMPORTACIONES)) {
            solicitudCA = new SolicitudConsultaImpuestoEspecialProductosServiciosImportaciones();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PROVEEDOR_CERTIFICACION_CFDI)) {
            solicitudCA = new SolicitudProveedorCertificacionCFDI();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_CENTRO_DESTRUCCION_VEHICULOS)) {
            solicitudCA = new SolicitudAutorizacionCentrosDestruccionVehiculos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_FUSION)) {
            solicitudCA = new SolicitudAutorizacionFusion();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_CLAVE_PRODUCIR_IMPORTAR_NUEVAS_MARCAS_TABACO_LABRADOS)) {
            solicitudCA = new SolicitudImportacionNuevasMarcasTabacos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new SolicitudMonederosElectronicos();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_PRORROGAS_IMPORTACION_EXPORTACION_CUADERNOS_ATA)) {
            solicitudCA = new SolicitudProrrogasImportacionExportacionCuadernosATA();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_PRORRGOAS_IMMEX_CANCELADO)) {
            solicitudCA = new SolicitudProrrogasIMMEXVencido();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new AvisoRenovacionMonederoElectronico();
        }
        /* inicia bloque: a este nivel se tendr�a que insertar las nuevas modalidades para Hidrocarburos, como else if, y continuar con el flujo como esta */
        
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_PRECIOS_DE_TRANSFERENCIA)){
                solicitudCA = new ConsultasMateriaPreciosTransferencia();
        } 
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_A_LOS_DEPOSITOS_EN_EFECTIVO)){
                solicitudCA = new ConsultasMateriaLeyImpuestoDepositosEfectivo();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_EMPRESARIAL_A_TASA_UNICA)){
                solicitudCA = new ConsultasMateriaLeyImpuestoEmpresarialTasaUnica();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DE_INGRESOS_SOBRE_HIDROCARBUROS)){            
                 solicitudCA = new ConsultasMateriaLeyIngresosHidrocarburos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_FUSION_DENTRO5ANIOS_POSTERIORES_FUSION_O_ESCISION)){
                solicitudCA = new AutorizacionFusionDentro5AniosPostetioresFusionOEscision();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_PARA_LA_DETERMINACION_DE_UN_REGIMEN_FISCAL)){            
                solicitudCA = new AutorizacionDeterminacionRegimenFiscal();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_BAPAS)){            
                solicitudCA = new AutorizacionesBAPAs();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_MAPS)){
                solicitudCA = new AutorizacionesMAPs();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_DIFERIMIENTO_DE_IMPUESTO_161_LISR)){            
                solicitudCA = new AutorizacionDiferimientoImpuesto161LISR();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOLICITUD_DE_RESOLUCION_PARA_DEDUCIR_INTERESES_POR_DEUDAS_ENTRE_PARTES_RELACIONADAS)) {
                solicitudCA = new SolicitudResolucionDeducirInteresesDeudasEntrePartesRelacionadas();
            }      
           /* termina bloque: nuevas modalidades para Hidrocarburos */
            
        else {
            solicitudCA = new SolicitudAutorizacionesMateriaImpuestosInternos();
        }

        solicitudCA.setCveUsuarioCapturista(solicitud.getSolicitante().getRfc());
        solicitudCA.setFechaCreacion(new Date());
        solicitudCA.setFechaEstatus(new Date());
        solicitudCA.setFechaActualizacion(new Date());
        solicitudCA.setFechaInicioTramite(new Date());
        solicitudCA.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);

        solicitudCA.setUnidadAdminBalanceo(solicitud.getClaveUnidadEmisora());
        solicitudCA.setUnidadAdministrativaRepresentacionFederal(new UnidadAdministrativa(solicitud
                .getClaveUnidadEmisora()));
        solicitudCA.setMonto(solicitud.getMontoOperacion());
        solicitudCA.setGranContribuyente(Integer.valueOf(solicitud.getGranContribuyete()));
        solicitudCA.setDescribirActividad(solicitud.getActividadInteresado());
        solicitudCA.setDescribirHecho(solicitud.getHechosCircunstancias());
        solicitudCA.setDescribirRazon(solicitud.getRazonesNegocio());
        solicitudCA.setSolicitanteHelper(transformarSolicitanteCAL(solicitud));
        solicitudCA.setPersonaONHelperList(transformarPONCAL(solicitud));
        solicitudCA.setPersonaResExtHelperList(transformarPRECAL(solicitud));
        if (solicitud.getHechosCircunstanciasPrevPlanteadas() != null
                && solicitud.getHechosCircunstanciasPrevPlanteadas().equals("1")) {
            solicitudCA.setHecho(1);
            solicitudCA.setAutoridadHelper(transformarAutoridad(solicitud));
        }
        else {
            solicitudCA.setHecho(0);
        }
        if (solicitud.getHechosCircunstanciasMatMedios() != null
                && solicitud.getHechosCircunstanciasMatMedios().equals("1")) {
            solicitudCA.setMedioDefensa(1);
            solicitudCA.setMedioDefensaHelper(transformarMediosDefensa(solicitud));
        }
        else {
            solicitudCA.setMedioDefensa(0);
        }
        if (solicitud.getContribuyenteSujetoEjercicio() != null
                && solicitud.getContribuyenteSujetoEjercicio().equals("1")) {
            solicitudCA.setSujetoEjercicio(1);
            solicitudCA.setDescribirPeriodoContribucion(solicitud.getPeriodosContribuciones());
            solicitudCA.setDentroPlazo(Integer.valueOf(solicitud.getPlazo()));
            solicitudCA.setAutoridadSujetoHelper(transformarAutoridadSujeto(solicitud));
        }

        return solicitudCA;
    }

    public Solicitante transformarSolicitanteCAL(SolicitudCALDTO solicitud) {
        Solicitante solicitante = new Solicitante();
        DomicilioSolicitud domicilio = new DomicilioSolicitud();
        solicitante.setApellidoMaterno(solicitud.getSolicitante().getApellidoMaterno());
        solicitante.setApellidoPaterno(solicitud.getSolicitante().getApellidoPaterno());
        solicitante.setNombre(solicitud.getSolicitante().getNombre());
        solicitante.setRfc(solicitud.getSolicitante().getRfc());
        solicitante.setEstadoContribuyente(solicitud.getSolicitante().getEstadoContribuyente());
        domicilio.setCalle(solicitud.getSolicitante().getDomicilio().getCalle());
        domicilio.setNumeroExterior(solicitud.getSolicitante().getDomicilio().getNumeroExterior());
        domicilio.setNumeroInterior(solicitud.getSolicitante().getDomicilio().getNumeroInterior());
        domicilio.setTelefono(solicitud.getSolicitante().getDomicilio().getTelefono());

        solicitante.setDomicilio(domicilio);

        return solicitante;

    }

    public List<PersonaOirRecibirNotificaciones> transformarPONCAL(SolicitudCALDTO solicitud) {
        List<PersonaOirRecibirNotificaciones> listaPON = new ArrayList<PersonaOirRecibirNotificaciones>();
        PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones = null;
        for (PersonaOirNotificacionesDTO personaOirNotificacionesDTO : solicitud.getListaPersonasNotificaciones()) {
            personaOirRecibirNotificaciones = new PersonaOirRecibirNotificaciones();
            personaOirRecibirNotificaciones.setNombre(personaOirNotificacionesDTO.getNombre());
            personaOirRecibirNotificaciones.setApellidoPaterno(personaOirNotificacionesDTO.getPaterno());
            personaOirRecibirNotificaciones.setApellidoMaterno(personaOirNotificacionesDTO.getMaterno());
            personaOirRecibirNotificaciones.setRfc(personaOirNotificacionesDTO.getRfc());
            personaOirRecibirNotificaciones.setEstadoContribuyente(personaOirNotificacionesDTO.getEstadoContribuyente());
            personaOirRecibirNotificaciones.setTelefono(personaOirNotificacionesDTO.getTelefono());
            listaPON.add(personaOirRecibirNotificaciones);
        }
        return listaPON;
    }

    public List<PersonaResidenteExtranjero> transformarPRECAL(SolicitudCALDTO solicitud) {
        List<PersonaResidenteExtranjero> listaPRE = new ArrayList<PersonaResidenteExtranjero>();
        PersonaResidenteExtranjero personaResidenteExtranjero = null;
        for (PersonaInvolucradaDTO personaInvolucradaDTO : solicitud.getListaPersonasInvolucradas()) {
            personaResidenteExtranjero = new PersonaResidenteExtranjero();
            personaResidenteExtranjero.setNombre(personaInvolucradaDTO.getNombre());
            personaResidenteExtranjero.setApellidoPaterno(personaInvolucradaDTO.getPaterno());
            personaResidenteExtranjero.setApellidoMaterno(personaInvolucradaDTO.getMaterno());
            personaResidenteExtranjero.setRfc(personaInvolucradaDTO.getRfc());
            personaResidenteExtranjero.setRazonSocial(personaInvolucradaDTO.getRazonSocial());
            personaResidenteExtranjero.setDomicilio(new DomicilioSolicitud());
            personaResidenteExtranjero.getDomicilio().setCalle(personaInvolucradaDTO.getDireccion());
            listaPRE.add(personaResidenteExtranjero);
        }
        return listaPRE;
    }

    public MedioDefensa transformarMediosDefensa(SolicitudCALDTO solicitud) {
        MedioDefensa medioDefensa = new MedioDefensa();
        medioDefensa.setTipoMedioDefensa(MediosDefensa.parse(solicitud.getClaveMedioDefensa()));
        medioDefensa.setNumeroAsunto(solicitud.getNumeroAsunto());

        medioDefensa.setDescAutoridad(solicitud.getDescripcionAutoridad());
        return medioDefensa;
    }

    public Autoridad transformarAutoridad(SolicitudCALDTO solicitud) {
        Autoridad autoridad = new Autoridad();
        autoridad.setIdTipoAutoridad(TipoAutoridad.AUTORIDAD_HCH.getClave());
        autoridad.setIdUniAdmin(solicitud.getClaveAutoridad());
        return autoridad;
    }

    public Autoridad transformarAutoridadSujeto(SolicitudCALDTO solicitud) {
        Autoridad autoridad = new Autoridad();
        autoridad.setIdTipoAutoridad(TipoAutoridad.AUTORIDAD_SUJETO.getClave());
        autoridad.setIdUniAdmin(solicitud.getClaveAutoridadRevisando());
        return autoridad;
    }

    public Solicitante transformarSolicitante(SolicitudConsultaAutorizacionDTO solicitud) {
        Solicitante solicitante = new Solicitante();
        DomicilioSolicitud domicilio = new DomicilioSolicitud();
        solicitante.setApellidoMaterno(solicitud.getSolicitante().getApellidoMaterno());
        solicitante.setApellidoPaterno(solicitud.getSolicitante().getApellidoPaterno());
        solicitante.setNombre(solicitud.getSolicitante().getNombre());
        solicitante.setRfc(solicitud.getSolicitante().getRfc());

        domicilio.setCalle(solicitud.getSolicitante().getDomicilio().getCalle());
        domicilio.setNumeroExterior(solicitud.getSolicitante().getDomicilio().getNumeroExterior());
        domicilio.setNumeroInterior(solicitud.getSolicitante().getDomicilio().getNumeroInterior());
        domicilio.setTelefono(solicitud.getSolicitante().getDomicilio().getTelefono());

        solicitante.setDomicilio(domicilio);

        return solicitante;

    }

    public MedioDefensa transformarMediosDefensa(SolicitudConsultaAutorizacionDTO solicitud) {
        if (solicitud.getManifiesto1()) {
            MedioDefensa medioDefensa = new MedioDefensa();
            medioDefensa.setTipoMedioDefensa(MediosDefensa.parse(solicitud.getClaveMedioDefensa()));
            medioDefensa.setNumeroAsunto(solicitud.getNumeroAsunto());

            return medioDefensa;
        }
        return null;
    }

    public DatosBandejaTareaDTO transformarTramiteBandeja(Tramite tramite) {

        DatosBandejaTareaDTO datosBandeja = new DatosBandejaTareaDTO();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        datosBandeja.setTipoTramite(tramite.getSolicitud().getTipoTramite().getDescripcionModalidad());
        datosBandeja.setNumeroAsunto(tramite.getNumeroAsunto());
        datosBandeja.setEstadoProcesal(tramite.getEstadoTramite().getDescripcion());
        datosBandeja.setCveEstadoProcesal(tramite.getEstadoTramite().getClave());
        datosBandeja.setFechaAsignacionStr(dateFormat.format(tramite.getSolicitud().getFechaCreacion()));
        datosBandeja.setRfcUsuarioAsignado(tramite.getSolicitud().getCveUsuarioCapturista());
        datosBandeja.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());
        datosBandeja.setTipoTramite(tramite.getSolicitud().getTipoTramite().getDescripcionModalidad());
        datosBandeja.setUrl("/resources/pages/ca/consultas/detallePromocion.jsf");
        return datosBandeja;
    }

    public PersonaOirRecibirNotificaciones transformarPersonaOirRecibirNotificaciones(
            SolicitudConsultaAutorizacionDTO solicitud) {
        PersonaOirRecibirNotificaciones persona = null;
        if ((solicitud.getRfcPAORN() != null && !solicitud.getRfcPAORN().equals(""))
                || (solicitud.getMailPAORN() != null && !solicitud.getMailPAORN().equals(""))) {
            persona = new PersonaOirRecibirNotificaciones();
            persona.setRfc(solicitud.getRfcPAORN());
            persona.setCorreoElectronico(solicitud.getMailPAORN());
        }
        return persona;
    }

    public PersonaResidenteExtranjero transformarResidenteExtranjero(SolicitudConsultaAutorizacionDTO solicitud) {
        PersonaResidenteExtranjero persona = null;
        boolean nomResidente = false;
        boolean paternoResidente = false;
        boolean maternoResidente = false;
        boolean rfcResidente = false;
        boolean direccion = false;
        boolean nombre = false;

        if (solicitud.getNombreResidente() != null && !solicitud.getNombreResidente().equals("")) {
            nomResidente = true;
        }
        if (solicitud.getApellidoPaternoResidente() != null && !solicitud.getApellidoPaternoResidente().equals("")) {
            paternoResidente = true;
        }
        if (solicitud.getApellidoMaternoResidente() != null && !solicitud.getApellidoMaternoResidente().equals("")) {
            maternoResidente = true;
        }
        if (solicitud.getRfcResidente() != null && !solicitud.getRfcResidente().equals("")) {
            rfcResidente = true;
        }
        if (solicitud.getDireccion() != null && !solicitud.getDireccion().equals("")) {
            direccion = true;
        }
        if (nomResidente || paternoResidente || maternoResidente) {
            nombre = true;
        }
        if (nombre || rfcResidente || direccion) {
            persona = new PersonaResidenteExtranjero();
            persona.setNss(solicitud.getRfcResidente());
            persona.setNombre(solicitud.getNombreResidente());
            persona.setApellidoPaterno(solicitud.getApellidoPaternoResidente());
            persona.setApellidoMaterno(solicitud.getApellidoMaternoResidente());
            if ((solicitud.getDireccion() != null && !solicitud.getDireccion().equals(""))) {
                DomicilioSolicitud domicilio = new DomicilioSolicitud();
                domicilio.setDescripcionUbicacion(solicitud.getDireccion());
                persona.setDomicilio(domicilio);
            }
        }
        return persona;
    }

    public SolicitudConsultaAutorizacionDTO transformaSolicitud(SolicitudDatosGenerales solicitud,
            MedioDefensa medioDefensa, List<PersonaSolicitud> personaSolicitud) {
        SolicitudConsultaAutorizacionDTO solicitudDTO = new SolicitudConsultaAutorizacionDTO();

        solicitudDTO.setFechaCreacion(solicitud.getFechaCreacion());
        solicitudDTO.setEstadoProcesal(solicitud.getEstadoSolicitud().getClave());
        solicitudDTO.setClaveUnidadEmisora(solicitud.getUnidadAdminBalanceo());
        solicitudDTO.setClaveUnidadEmisora(solicitud.getUnidadAdministrativaRepresentacionFederal().getClave());
        solicitudDTO.setNombreUnidadEmisora(solicitud.getUnidadAdministrativaRepresentacionFederal().getNombre());
        if (solicitud.getMonto() != null) {
            solicitudDTO.setMonto(solicitud.getMonto().toString());
        }

        solicitudDTO.setGranContribuyete(solicitud.getGranContribuyente() == 1 ? "Si" : "No");
        solicitudDTO.setActividadInteresado(solicitud.getDescribirActividad());
        solicitudDTO.setHechosCircunstancias(solicitud.getDescribirHecho());
        solicitudDTO.setRazonNegocio(solicitud.getDescribirRazon());

        if (solicitud.getMedioDefensa() == 1) {
            solicitudDTO.setManifiesto1(true);
            solicitudDTO.setDescripcionMedioDefensa(medioDefensa.getTipoMedioDefensa().getDescripcion());
            solicitudDTO.setNumeroAsunto(medioDefensa.getNumeroAsunto());

        }
        else {
            solicitudDTO.setManifiesto1(false);
        }

        if (solicitud.getSujetoEjercicio() == 1) {
            solicitudDTO.setManifiesto2(true);
            solicitudDTO.setPeriodosContribuciones(solicitud.getDescribirPeriodoContribucion());
            solicitudDTO.setPlazo("Si");
        }
        else {
            solicitudDTO.setManifiesto2(false);
            solicitudDTO.setPlazo("No");
        }

        PersonaSolicitudDTO personaSolicitudDTO = null;
        for (PersonaSolicitud personaSolicitud2 : personaSolicitud) {
            if (personaSolicitud2 instanceof Solicitante) {
                DomicilioSolicitudDTO domicilioSolicitudDTO = new DomicilioSolicitudDTO();
                personaSolicitudDTO = new PersonaSolicitudDTO();
                personaSolicitudDTO.setApellidoMaterno(personaSolicitud2.getApellidoMaterno());
                personaSolicitudDTO.setApellidoPaterno(personaSolicitud2.getApellidoPaterno());
                personaSolicitudDTO.setNombre(personaSolicitud2.getNombre());
                personaSolicitudDTO.setRfc(personaSolicitud2.getRfc());
                if (personaSolicitud2.getDomicilio() != null) {
                    domicilioSolicitudDTO.setCalle(personaSolicitud2.getDomicilio().getCalle());
                    domicilioSolicitudDTO.setNumeroExterior(personaSolicitud2.getDomicilio().getNumeroExterior());
                    domicilioSolicitudDTO.setNumeroInterior(personaSolicitud2.getDomicilio().getNumeroInterior());
                    domicilioSolicitudDTO.setTelefono(personaSolicitud2.getDomicilio().getTelefono());
                }
                personaSolicitudDTO.setDomicilio(domicilioSolicitudDTO);
                solicitudDTO.setSolicitante(personaSolicitudDTO);
            }
            else if (personaSolicitud2 instanceof PersonaOirRecibirNotificaciones) {
                solicitudDTO.setRfcPAORN(personaSolicitud2.getRfc());
                solicitudDTO.setMailPAORN(personaSolicitud2.getCorreoElectronico());
            }
            else if (personaSolicitud2 instanceof PersonaResidenteExtranjero) {
                solicitudDTO.setRfcResidente(personaSolicitud2.getNss());
                solicitudDTO.setNombreResidente(personaSolicitud2.getNombre());
                solicitudDTO.setApellidoMaternoResidente(personaSolicitud2.getApellidoMaterno());
                solicitudDTO.setApellidoPaternoResidente(personaSolicitud2.getApellidoPaterno());
                if (personaSolicitud2.getDomicilio() != null) {
                    solicitudDTO.setDireccion(personaSolicitud2.getDomicilio().getDescripcionUbicacion());
                }
            }

        }

        return solicitudDTO;
    }

}
