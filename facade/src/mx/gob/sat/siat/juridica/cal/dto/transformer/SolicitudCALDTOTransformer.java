package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.DomicilioSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.service.DomicilioServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.*;
import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
 * Se debe agregar las nuevas clases para las nuevas modalidades Hidrocarburos P96156
 * */

@Component
public class SolicitudCALDTOTransformer extends DTOTransformer<SolicitudCALDTO, SolicitudConsultaAutorizacion> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private transient DomicilioServices domicilioServices;

    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    @Override
    public SolicitudConsultaAutorizacion transformarDTO(SolicitudCALDTO solicitud) {

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
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_LEY_TENENCIA_VEHICULO)) {
            solicitudCA = new SolicitudConsultasLeyTenenciaVehiculo();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_IMPUESTO_AUTOS_NUEVOS)) {
            solicitudCA = new SolicitudConsultaImpuestoAutosNuevos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_COORDINACION_FISCAL)) {
            solicitudCA = new SolicitudConsultaCoordinacionFiscal();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_DERECHOS)) {
            solicitudCA = new SolicitudConsultaMateriaDerechos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_DECRETOS_PROGRAMAS)) {
            solicitudCA = new SolicitudConsultaDecretosProgramas();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_RESOLUCIONES_GENERALES)) {
            solicitudCA = new SolicitudConsultaResolucionesGenerales();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTA_MATERIA_IMPUESTO_INTERNOS)) {
            solicitudCA = new SolicitudConsultaImpuestoInternos();
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
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_LIBERACION_PAGO_EROGACIONES)) {
            solicitudCA = new SolicitudAutorizacionesMateriaImpuestosInternos();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_DEDUCIR_PAGOS_AVIONES)) {
            solicitudCA = new SolicitudAutorizacionesDeducirPagosAviones();
        }
        else if (solicitud.getTipoTramite()
                .equals(DiscriminadorConstants.T2_AUTORIZACIONES_DEDUCIR_PAGOS_EMBARCACIONES)) {
            solicitudCA = new SolicitudAutorizacionesDeducirPagosEmbarcaciones();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACIONES_DEDUCIR_PAGOS_CASA_HABITACION)) {
            solicitudCA = new SolicitudAutorizacionesDeducirPagosCasaHabitacion();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_DEDUCIR_PAGOS_COMEDORES)) {
            solicitudCA = new SolicitudAutorizacionesDeducirPagosComedores();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACIONES_PROVEEDOR_SERVICIO_AUTORIZADO_PSA)) {
            solicitudCA = new SolicitudAutorizacionesProveedorServicioAutorizado();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_ORGANO_VERIFICADOR_OV)) {
            solicitudCA = new SolicitudAutorizacionesOrganoVerificador();
        }
        else if (solicitud.getTipoTramite().equals(
                DiscriminadorConstants.T2_AUTORIZACIONES_ENAJENAR_ACCIONES_COSTO_FISCAL)) {
            solicitudCA = new SolicitudAutorizacionesEnajenarAcciones();
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
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOLICITUD_CANCELACION_VEHICULOS_USADOS)) {
            solicitudCA = new SolicitudCancelaAutorizacionOperaDestruirVehiculosUsados();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESTRUCCION_MERCANCIAS)) {
            solicitudCA = new ResarcimientoEconomicoExtravioDestruccionMercancias();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_DESCOMPOCISION_ANIMALES)) {
            solicitudCA = new ResarcimientoEconomicoMercanciaPerecedera();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_RESARCIMIENTO_ECONOMICO_ESPECIE)) {
            solicitudCA = new ResarcimientoEconomicoEspecie();
        }
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_FRANQUICIA_DIPLOMATICA_VEHICULOS)) {
            solicitudCA = new FranquiciaDiplomaticaVehiculos();
        }	 // inicio
        else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_ORGANO_VERIFICADOR)) {
            solicitudCA = new SolicitudAvisoRenovarOrganoVerificador();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_PROVEEDOR_SERVICIO)) {
            solicitudCA = new SolicitudAvisoRenovarProveedorServicio();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_MONEDEROS_VALESDESPENSA)) {
            solicitudCA = new SolicitudAvisoRenovarMonederoVale();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_RENOVACION_MONEDEROS_COMBUSTIBLES)) {
            solicitudCA = new SolicitudAvisoRenovarMonederoCombustible();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_ACTUALIZACION_MONEDEROS_COMBUSTIBLES)) {
            solicitudCA = new SolicitudAvisoActualizarMonederoComustible();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_CERTIFICACION_PROVEEDOR_SERVICIO)) {
            solicitudCA = new SolicitudAvisoCertificarProveedorServicio();
        } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AVISO_ACTUALIZACION_MONEDEROS_ELECTRONICOS)) {
            solicitudCA = new SolicitudAvisoActualizarMonederoElectronico();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_APLICAR_REGIMEN_OPCIONAL_GRUPOS_SOCIEDADES)) {
            solicitudCA = new AutorizacionAplicarRegimenOpcionalGruposSociedades();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOL_AUTO_SOCIEDADES_FINANCIERAS_OBJ_MULTIPLE_NV_CREACION)) {
            solicitudCA = new SolAutoSociedadesFinancierasObjMultipleNvCreacion();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOL_AUTO_DIFER_PAGO_ISR_DERIV_REESTRUC_REF_ART161_LEY_ISR)) {
            solicitudCA = new SolAutoDiferPagoISRDerivReestrucRefArt161LeyISR();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOL_AUT_NO_APL_DIS_TI_VI_CA_I_LEY_ISR_ING_PAS_GEN_ENT_FIG)) {
            solicitudCA = new SolAutNoAplDisTiVICaILeyISRIngPasGenEntFig();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTO_EXC_INV_ACT_NV_LIMIT_ESTA_REQ_FIDEI_INVER_ENER_INFRA)) {
            solicitudCA = new AutoExcInvActNvLimitEstaReqFideiInverEnerInfra();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_OPERAR_ORGANO_CERTIFICADOR)) {
            solicitudCA = new AutorizacionOperarOrganoCertificador();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_SOL_DEJAR_SIN_EFECTOS_AUTOR_OPERAR_ORGANO_CERTIFICADOR)) {
            solicitudCA = new SolDejarSinEfectosAutorOperarOrganoCertificador();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20270)) {
            solicitudCA = new Modalidad20270();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20271)) {
            solicitudCA = new Modalidad20271();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20272)) {
            solicitudCA = new Modalidad20272();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20273)) {
            solicitudCA = new Modalidad20273();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20274)) {
            solicitudCA = new Modalidad20274();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20275)) {
            solicitudCA = new Modalidad20275();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20276)) {
            solicitudCA = new Modalidad20276();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20277)) {
            solicitudCA = new Modalidad20277();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20278)) {
            solicitudCA = new Modalidad20278();
        }else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_MODALIDAD_20279)) {
            solicitudCA = new Modalidad20279();
        }
            /* inicia bloque: a este nivel se debe insertar las nuevas modalidades para hidrocarburos, como else if */  
         else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_PRECIOS_DE_TRANSFERENCIA)){
                solicitudCA = new ConsultasMateriaPreciosTransferencia();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_A_LOS_DEPOSITOS_EN_EFECTIVO)){
             solicitudCA = new ConsultasMateriaLeyImpuestoDepositosEfectivo();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DEL_IMPUESTO_EMPRESARIAL_A_TASA_UNICA)){
            solicitudCA = new ConsultasMateriaLeyImpuestoEmpresarialTasaUnica();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_CONSULTAS_EN_MATERIA_DE_LEY_DE_INGRESOS_SOBRE_HIDROCARBUROS)){            
            solicitudCA = new ConsultasMateriaLeyIngresosHidrocarburos();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_FUSION_DENTRO5ANIOS_POSTERIORES_FUSION_O_ESCISION)){
            solicitudCA = new AutorizacionFusionDentro5AniosPostetioresFusionOEscision();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_PARA_LA_DETERMINACION_DE_UN_REGIMEN_FISCAL)){            
            solicitudCA = new AutorizacionDeterminacionRegimenFiscal();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_BAPAS)){
            solicitudCA = new AutorizacionesBAPAs();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACIONES_MAPS)){
             solicitudCA = new AutorizacionesMAPs();
         } else if (solicitud.getTipoTramite().equals(DiscriminadorConstants.T2_AUTORIZACION_DIFERIMIENTO_DE_IMPUESTO_161_LISR)){            
            solicitudCA = new AutorizacionDiferimientoImpuesto161LISR();
         } else   {
            solicitudCA = new SolicitudResolucionDeducirInteresesDeudasEntrePartesRelacionadas();
        }
        if (solicitudCA != null) {
            if (solicitud.getIdSolicitud() != null) {
                solicitudCA.setIdSolicitud(solicitud.getIdSolicitud());
                solicitudCA.setFechaCreacion(solicitud.getFechaCreacion());
            }
            else {
                solicitudCA.setFechaCreacion(new Date());
            }
            solicitudCA.setCveUsuarioCapturista(solicitud.getSolicitante().getRfc());
            solicitudCA.setFechaEstatus(new Date());
            solicitudCA.setFechaActualizacion(new Date());
            solicitudCA.setFechaInicioTramite(new Date());
            solicitudCA.setEstadoSolicitud(EstadoSolicitud.REGISTRADA);
            solicitudCA.setCveRolCapturista(solicitud.getCveRolCapturista());
            solicitudCA.setUnidadAdminBalanceo(solicitud.getClaveUnidadEmisora());
            solicitudCA.setUnidadAdministrativaRepresentacionFederal(new UnidadAdministrativa(solicitud
                    .getClaveUnidadEmisora()));
            solicitudCA.setMonto(new BigDecimal(solicitud.getMontoOperacion().toString()));
            solicitudCA.setGranContribuyente(solicitud.getGranContribuyete() != null ? Integer.valueOf(solicitud
                    .getGranContribuyete()) : null);
            solicitudCA.setDescribirActividad(solicitud.getActividadInteresado());
            solicitudCA.setDescribirHecho(solicitud.getHechosCircunstancias());
            solicitudCA.setDescribirRazon(solicitud.getRazonesNegocio());
            solicitudCA.setSolicitanteHelper(transformarSolicitanteCAL(solicitud));
            solicitudCA.setRepresentanteLegalHelper(null);
            solicitudCA.setPersonaONHelperList(transformarPONCAL(solicitud));
            solicitudCA.setPersonaResExtHelperList(transformarPRECAL(solicitud));
            solicitudCA.setFraccionDudaHelperList(transformarFraccionDuda(solicitud));
            if (solicitud.getNumeroFolio() != null) {
                solicitudCA.setFolio(Long.parseLong(solicitud.getNumeroFolio()));
            }
            solicitudCA.setFechaApertura(solicitud.getFechaApertura());
            if (solicitud.getHechosCircunstanciasPrevPlanteadas() != null
                    && solicitud.getHechosCircunstanciasPrevPlanteadas().equals("1")) {
                solicitudCA.setHecho(1);
                solicitudCA.setAutoridadHelper(transformarAutoridad(solicitud));
                solicitudCA.setFechHecho(solicitud.getFechaPromocion());
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
                if (solicitud.getIdMedioDefensa() != null) {
                    solicitudCA.setMedioDefensaHelper(transformarMediosDefensaEliminado(solicitud));
                }
            }
            if (solicitud.getContribuyenteSujetoEjercicio() != null
                    && solicitud.getContribuyenteSujetoEjercicio().equals("1")) {
                solicitudCA.setSujetoEjercicio(1);
                solicitudCA.setDescribirPeriodoContribucion(solicitud.getPeriodosContribuciones());
                solicitudCA.setDentroPlazo(solicitud.getPlazo() != null ? Integer.valueOf(solicitud.getPlazo()) : null);
                solicitudCA.setAutoridadSujetoHelper(transformarAutoridadSujeto(solicitud));
            }
            else {
                solicitudCA.setSujetoEjercicio(0);
            }
            if (solicitud.getTipoClasificacion() != null) {
                solicitudCA.setIdeClasifArancelaria(solicitud.getTipoClasificacion());
                solicitudCA.setDescNombreMercancia(solicitud.getDescripcionMercancia());
                if (TipoClasificacionArancelaria.CLASIFICACION_CLAS.getClave().equals(solicitud.getTipoClasificacion())) {
                    solicitudCA.setFraccionAplicable(solicitud.getFraccionAplicable());
                }
            }
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
        solicitante.setRazonSocial(solicitud.getSolicitante().getRazonSocial());
        solicitante.setTelefono(solicitud.getSolicitante().getDomicilio().getTelefono());
        solicitante.setCorreoElectronico(solicitud.getSolicitante().getDomicilio().getCorreoElectronico());
        solicitante.setBlnActivo(true);
        solicitante.setPersonaMoral(solicitud.getSolicitante().getRazonSocial() != null);
        domicilio.setTelefono(solicitud.getSolicitante().getDomicilio().getTelefono());
        domicilio.setCalle(solicitud.getSolicitante().getDomicilio().getCalle());
        domicilio.setNumeroExterior(solicitud.getSolicitante().getDomicilio().getNumeroExterior());
        domicilio.setNumeroInterior(solicitud.getSolicitante().getDomicilio().getNumeroInterior());
        domicilio.setClaveAdministracionLocalRecaudadora(solicitud.getSolicitante().getDomicilio()
                .getAdministracionLocal());
        domicilio.setCodigoPostal(solicitud.getSolicitante().getDomicilio().getCodigoPostal());
        if (solicitud.getSolicitante().getDomicilio().getCveEstado() != null) {
            domicilio.setEntidadFederativa(domicilioServices.buscarEntidadByClave(solicitud.getSolicitante()
                    .getDomicilio().getCveEstado()));
        }
        if (solicitud.getSolicitante().getDomicilio().getCveColonia() != null) {
            domicilio.setColonia(domicilioServices.buscarColoniaByClave(solicitud.getSolicitante().getDomicilio()
                    .getCveColonia()));
        }
        if (solicitud.getSolicitante().getDomicilio().getCveDelegacion() != null) {
            domicilio.setDelegacionMunicipio(domicilioServices.buscarDelegacionByClave(solicitud.getSolicitante()
                    .getDomicilio().getCveDelegacion()));
        }
        if (solicitud.getSolicitante().getDomicilio().getCvePais() != null) {
            domicilio.setPais(domicilioServices.buscarPaisByClave(solicitud.getSolicitante().getDomicilio()
                    .getCvePais()));
        }
        if (solicitud.getSolicitante().getDomicilio().getCveLocalidad() != null) {
            domicilio.setLocalidad(domicilioServices.buscarLocalidadByClave(solicitud.getSolicitante().getDomicilio()
                    .getCveLocalidad()));
        }
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
            if (!personaOirNotificacionesDTO.isNuevo()) {
                personaOirRecibirNotificaciones.setIdPersonaSolicitud(personaOirNotificacionesDTO.getIdPersona());
            }
            personaOirRecibirNotificaciones.setBlnActivo(true);
            if (personaOirNotificacionesDTO.getDireccion() != null) {
                personaOirRecibirNotificaciones.setDomicilio(new DomicilioSolicitud());
                personaOirRecibirNotificaciones.getDomicilio().setDescripcionUbicacion(
                        personaOirNotificacionesDTO.getDireccion());
            }
            listaPON.add(personaOirRecibirNotificaciones);
        }
        return listaPON;
    }

    public List<PersonaResidenteExtranjero> transformarPRECAL(SolicitudCALDTO solicitud) {
        List<PersonaResidenteExtranjero> listaPRE = new ArrayList<PersonaResidenteExtranjero>();
        PersonaResidenteExtranjero personaResidenteExtranjero = null;
        for (PersonaInvolucradaDTO personaInvolucradaDTO : solicitud.getListaPersonasInvolucradas()) {
            personaResidenteExtranjero = new PersonaResidenteExtranjero();
            Boolean personaMoral =
                    (personaInvolucradaDTO.getTipoPersona() != null && personaInvolucradaDTO.getTipoPersona().equals(
                            "1")) ? true : false;
            personaResidenteExtranjero.setPersonaMoral(personaMoral);
            personaResidenteExtranjero.setNombre(personaInvolucradaDTO.getNombre());
            personaResidenteExtranjero.setApellidoPaterno(personaInvolucradaDTO.getPaterno());
            personaResidenteExtranjero.setApellidoMaterno(personaInvolucradaDTO.getMaterno());
            personaResidenteExtranjero.setNss(personaInvolucradaDTO.getRfc());
            personaResidenteExtranjero.setRazonSocial(personaInvolucradaDTO.getRazonSocial());
            personaResidenteExtranjero.setDomicilio(new DomicilioSolicitud());
            personaResidenteExtranjero.getDomicilio().setDescripcionUbicacion(personaInvolucradaDTO.getDireccion());
            personaResidenteExtranjero.setBlnActivo(true);
            personaResidenteExtranjero.setBooleanExtranjero(personaInvolucradaDTO.isExtranjero());
            if (!personaInvolucradaDTO.isNuevo()) {
                personaResidenteExtranjero.setIdPersonaSolicitud(personaInvolucradaDTO.getIdPersona());
            }
            listaPRE.add(personaResidenteExtranjero);
        }
        return listaPRE;
    }

    public List<FraccionDuda> transformarFraccionDuda(SolicitudCALDTO solicitud) {
        List<FraccionDuda> listaFraccionDuda = new ArrayList<FraccionDuda>();
        FraccionDuda fraccionDuda = null;
        for (FraccionArancelariaDudaDTO fraccionDudaDTO : solicitud.getListaFraccionDuda()) {
            fraccionDuda = new FraccionDuda();
            fraccionDuda.setFraccionDuda(fraccionDudaDTO.getFraccionArancelaria());
            if (!fraccionDudaDTO.isNuevo()) {
                fraccionDuda.setIdFraccionDuda(fraccionDudaDTO.getIdFraccionArancelaria());
            }
            fraccionDuda.setBlnActivo(true);
            listaFraccionDuda.add(fraccionDuda);
        }
        return listaFraccionDuda;
    }

    public MedioDefensa transformarMediosDefensa(SolicitudCALDTO solicitud) {
        MedioDefensa medioDefensa = new MedioDefensa();
        if (solicitud.getClaveMedioDefensa() != null) {
            medioDefensa.setTipoMedioDefensa(MediosDefensa.parse(solicitud.getClaveMedioDefensa()));
        }
        medioDefensa.setNumeroAsunto(solicitud.getNumeroAsunto());
        medioDefensa.setSentidoResolucion(solicitud.getClaveSentidoResolucion());
        medioDefensa.setDescAutoridad(solicitud.getDescripcionAutoridad());
        medioDefensa.setBlnActivo(true);
        if (solicitud.getIdMedioDefensa() != null) {
            medioDefensa.setIdMedioDefensa(solicitud.getIdMedioDefensa());
        }
        return medioDefensa;
    }

    public MedioDefensa transformarMediosDefensaEliminado(SolicitudCALDTO solicitud) {
        MedioDefensa medioDefensa = new MedioDefensa();
        if (solicitud.getClaveMedioDefensa() != null) {
            medioDefensa.setTipoMedioDefensa(MediosDefensa.parse(solicitud.getClaveMedioDefensa()));
        }
        medioDefensa.setNumeroAsunto(solicitud.getNumeroAsunto());
        medioDefensa.setSentidoResolucion(solicitud.getClaveSentidoResolucion());
        medioDefensa.setDescAutoridad(solicitud.getDescripcionAutoridad());
        medioDefensa.setIdMedioDefensa(solicitud.getIdMedioDefensa());
        medioDefensa.setBlnActivo(false);
        solicitud.setIdMedioDefensa(null);
        return medioDefensa;
    }

    public Autoridad transformarAutoridad(SolicitudCALDTO solicitud) {
        Autoridad autoridad = null;
        if (solicitud.getClaveAutoridad() != null && !solicitud.getClaveAutoridad().isEmpty()) {
            autoridad = new Autoridad();
            autoridad.setIdTipoAutoridad(TipoAutoridad.AUTORIDAD_HCH.getClave());
            autoridad.setIdUniAdmin(solicitud.getClaveAutoridad());
        }
        return autoridad;
    }

    public Autoridad transformarAutoridadSujeto(SolicitudCALDTO solicitud) {
        Autoridad autoridad = null;
        if (solicitud.getClaveAutoridadRevisando() != null
                && solicitud.getClaveAutoridadRevisando().trim().length() > 0) {
            autoridad = new Autoridad();
            autoridad.setIdTipoAutoridad(TipoAutoridad.AUTORIDAD_SUJETO.getClave());
            autoridad.setIdUniAdmin(solicitud.getClaveAutoridadRevisando());
        }
        return autoridad;
    }

    public Solicitante transformarSolicitante(SolicitudConsultaAutorizacionDTO solicitud) {
        Solicitante solicitante = new Solicitante();
        DomicilioSolicitud domicilio = new DomicilioSolicitud();
        solicitante.setApellidoMaterno(solicitud.getSolicitante().getApellidoMaterno());
        solicitante.setApellidoPaterno(solicitud.getSolicitante().getApellidoPaterno());
        solicitante.setNombre(solicitud.getSolicitante().getNombre());
        solicitante.setRfc(solicitud.getSolicitante().getRfc());
        solicitante.setEstadoContribuyente(solicitud.getSolicitante().getEstadoContribuyente());
        solicitante.setRazonSocial(solicitud.getSolicitante().getRazonSocial());
        solicitante.setPersonaMoral(solicitud.getSolicitante().getRazonSocial() != null);
        domicilio.setCalle(solicitud.getSolicitante().getDomicilio().getCalle());
        domicilio.setNumeroExterior(solicitud.getSolicitante().getDomicilio().getNumeroExterior());
        domicilio.setNumeroInterior(solicitud.getSolicitante().getDomicilio().getNumeroInterior());
        domicilio.setTelefono(solicitud.getSolicitante().getDomicilio().getTelefono());
        domicilio.setClaveAdministracionLocalRecaudadora(solicitud.getSolicitante().getDomicilio()
                .getAdministracionLocal());
        solicitante.setDomicilio(domicilio);
        return solicitante;
    }

    public MedioDefensa transformarMediosDefensa(SolicitudConsultaAutorizacionDTO solicitud) {
        if (solicitud.getManifiesto1()) {
            MedioDefensa medioDefensa = new MedioDefensa();
            medioDefensa.setTipoMedioDefensa(solicitud.getClaveMedioDefensa() != null ? MediosDefensa.parse(solicitud
                    .getClaveMedioDefensa()) : null);
            medioDefensa.setNumeroAsunto(solicitud.getNumeroAsunto());
            medioDefensa.setSentidoResolucion(solicitud.getClaveSentidoResolucion());
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

    public SolicitudCALDTO transformaSolicitud(SolicitudConsultaAutorizacion solicitud, MedioDefensa medioDefensa,
            List<PersonaSolicitud> personaSolicitud, List<FraccionDuda> fraccionesDuda, Autoridad autoridadDeHechos,
            Autoridad autoridadSujetoEjercicio) {
        SolicitudCALDTO solicitudDTO = new SolicitudCALDTO();

        try {
            if (solicitud.getFechaApertura() != null) {
                solicitudDTO.setFechaApertura(solicitud.getFechaApertura());
                solicitudDTO.setNumeroFolio(String.valueOf(solicitud.getFolio()));
            }
            solicitudDTO.setFechaCreacion(solicitud.getFechaCreacion());
            solicitudDTO.setClaveUnidadEmisora(solicitud.getUnidadAdminBalanceo());
            solicitudDTO.setClaveUnidadEmisora(solicitud.getUnidadAdministrativaRepresentacionFederal().getClave());
            solicitudDTO.setTipoTramite(solicitud.getTipoTramiteSolicitud() != null ? solicitud
                    .getTipoTramiteSolicitud().getIdTipoTramite().toString() : null);

            solicitudDTO.setGranContribuyete(solicitud.getGranContribuyente() != null ? solicitud
                    .getGranContribuyente().toString() : null);
            if (solicitud.getMonto() != null) {
                solicitudDTO.setMontoOperacion(solicitud.getMonto());
            }
            solicitudDTO.setActividadInteresado(solicitud.getDescribirActividad());
            solicitudDTO.setHechosCircunstancias(solicitud.getDescribirHecho());
            solicitudDTO.setRazonesNegocio(solicitud.getDescribirRazon());

            solicitudDTO.setHechosCircunstanciasPrevPlanteadas(solicitud.getHecho() != null ? solicitud.getHecho()
                    .toString() : null);
            if (solicitud.getHecho() != null && solicitud.getHecho() == 1) {
                solicitudDTO.setClaveAutoridad(autoridadDeHechos != null ? autoridadDeHechos.getIdUniAdmin() : null);
                solicitudDTO.setFechaPromocion(solicitud.getFechHecho()); 

            }

            solicitudDTO.setHechosCircunstanciasMatMedios(solicitud.getMedioDefensa().toString());
            if (medioDefensa != null) {
                solicitudDTO.setIdMedioDefensa(medioDefensa.getIdMedioDefensa());
                solicitudDTO.setClaveMedioDefensa(medioDefensa.getTipoMedioDefensa() != null ? medioDefensa
                        .getTipoMedioDefensa().getClave() : null);
                solicitudDTO.setNumeroAsunto(medioDefensa.getNumeroAsunto()); 

                solicitudDTO.setClaveSentidoResolucion(medioDefensa.getSentidoResolucion() != null ? medioDefensa
                        .getSentidoResolucion() : ""); 

                solicitudDTO.setDescripcionAutoridad(medioDefensa.getDescAutoridad()); 

            }

            solicitudDTO.setContribuyenteSujetoEjercicio(solicitud.getSujetoEjercicio() != null ? solicitud
                    .getSujetoEjercicio().toString() : null);
            if (solicitud.getSujetoEjercicio() != null && solicitud.getSujetoEjercicio() == 1) {
                solicitudDTO.setPeriodosContribuciones(solicitud.getDescribirPeriodoContribucion()); 

                solicitudDTO
                        .setPlazo(solicitud.getDentroPlazo() != null ? solicitud.getDentroPlazo().toString() : null); 
                solicitudDTO.setClaveAutoridadRevisando(autoridadSujetoEjercicio != null ? autoridadSujetoEjercicio
                        .getIdUniAdmin() : null); 

            }

            if (solicitud.getIdeClasifArancelaria() != null) {
                solicitudDTO.setTipoClasificacion(solicitud.getIdeClasifArancelaria());
                solicitudDTO.setDescripcionMercancia(solicitud.getDescNombreMercancia());
                solicitudDTO.setFraccionAplicable(solicitud.getFraccionAplicable());
                if (fraccionesDuda != null && !fraccionesDuda.isEmpty()) {
                    List<FraccionArancelariaDudaDTO> fraccionesDudaDTO = new LinkedList<FraccionArancelariaDudaDTO>();
                    for (FraccionDuda fd : fraccionesDuda) {
                        if (fd.getFraccionDuda() != null) {
                            FraccionArancelariaDudaDTO fddto = new FraccionArancelariaDudaDTO();
                            fddto.setFraccionArancelaria(fd.getFraccionDuda().toString());
                            fddto.setIdFraccionArancelaria(fd.getIdFraccionDuda());
                            fddto.setNuevo(false);
                            fraccionesDudaDTO.add(fddto);
                        }
                    }
                    solicitudDTO.setListaFraccionDuda(fraccionesDudaDTO);
                }
            }

            PersonaSolicitudDTO personaSolicitudDTO = null;
            List<PersonaOirNotificacionesDTO> listaPersonasOirNotificaciones =
                    new LinkedList<PersonaOirNotificacionesDTO>();
            List<PersonaInvolucradaDTO> listaPersonasInvolucradas = new LinkedList<PersonaInvolucradaDTO>();
            for (PersonaSolicitud personaSolicitud2 : personaSolicitud) {
                if (personaSolicitud2 instanceof Solicitante) {
                    DomicilioSolicitudDTO domicilioSolicitudDTO = new DomicilioSolicitudDTO();
                    personaSolicitudDTO = new PersonaSolicitudDTO();
                    personaSolicitudDTO.setApellidoMaterno(personaSolicitud2.getApellidoMaterno());
                    personaSolicitudDTO.setApellidoPaterno(personaSolicitud2.getApellidoPaterno());
                    personaSolicitudDTO.setNombre(personaSolicitud2.getNombre());
                    personaSolicitudDTO.setRfc(personaSolicitud2.getRfc());
                    personaSolicitudDTO.setEstadoContribuyente(personaSolicitud2.getEstadoContribuyente());

                    personaSolicitudDTO.setRazonSocial(personaSolicitud2.getRazonSocial());
                    if (personaSolicitud2.getDomicilio() != null) {
        
                            domicilioSolicitudDTO.setCalle(personaSolicitud2.getDomicilio().getCalle());
                            domicilioSolicitudDTO.setNumeroExterior(personaSolicitud2.getDomicilio().getNumeroExterior());
                            domicilioSolicitudDTO.setNumeroInterior(personaSolicitud2.getDomicilio().getNumeroInterior());
                            domicilioSolicitudDTO.setTelefono(personaSolicitud2.getDomicilio().getTelefono());
                            domicilioSolicitudDTO.setCodigoPostal(personaSolicitud2.getDomicilio().getCodigoPostal());
                            domicilioSolicitudDTO.setColonia(personaSolicitud2.getDomicilio().getColonia() != null ? 
                                                    personaSolicitud2.getDomicilio().getColonia().getNombre() : null);
                            
                            domicilioSolicitudDTO.setCveColonia(personaSolicitud2.getDomicilio().getColonia() != null ? 
                                                    personaSolicitud2.getDomicilio().getColonia().getClave() : null);
                            domicilioSolicitudDTO.setDelegacionMunicipio(personaSolicitud2.getDomicilio().getDelegacionMunicipio() != null ? 
                                                    personaSolicitud2.getDomicilio().getDelegacionMunicipio().getNombre() : null);
                            domicilioSolicitudDTO.setCveDelegacion(personaSolicitud2.getDomicilio().getDelegacionMunicipio() != null ? 
                                                    personaSolicitud2.getDomicilio().getDelegacionMunicipio().getClave() : null);
                            
                            domicilioSolicitudDTO.setEstado(personaSolicitud2.getDomicilio().getEntidadFederativa() != null ? personaSolicitud2
                                    .getDomicilio().getEntidadFederativa().getNombre() : "");
                            domicilioSolicitudDTO.setCveEstado(personaSolicitud2.getDomicilio().getEntidadFederativa() != null ? personaSolicitud2
                                    .getDomicilio().getEntidadFederativa().getClave() : null);
                            domicilioSolicitudDTO.setCvePais(personaSolicitud2.getDomicilio().getPais() != null ? personaSolicitud2.getDomicilio()
                                    .getPais().getClave() : "");
                            domicilioSolicitudDTO.setNombrePais(personaSolicitud2.getDomicilio().getPais() != null ? personaSolicitud2.getDomicilio()
                                    .getPais().getNombre() : "");
                            if (personaSolicitud2.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                                domicilioSolicitudDTO.setAdministracionLocal(personaSolicitud2.getDomicilio()
                                        .getClaveAdministracionLocalRecaudadora());
                                domicilioSolicitudDTO.setNombreAdministracionLocal(unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                                        personaSolicitud2.getDomicilio().getClaveAdministracionLocalRecaudadora()).getNombre());
                            }
                        
                    }
                    personaSolicitudDTO.setDomicilio(domicilioSolicitudDTO);
                    solicitudDTO.setSolicitante(personaSolicitudDTO);
                }
                else if (personaSolicitud2 instanceof PersonaOirRecibirNotificaciones) {
                    PersonaOirNotificacionesDTO paorn = new PersonaOirNotificacionesDTO();
                    paorn.setIdPersona(personaSolicitud2.getIdPersonaSolicitud());
                    paorn.setNombre(personaSolicitud2.getNombre());
                    paorn.setPaterno(personaSolicitud2.getApellidoPaterno());
                    paorn.setMaterno(personaSolicitud2.getApellidoMaterno());
                    paorn.setRfc(personaSolicitud2.getRfc());
                    paorn.setEstadoContribuyente(personaSolicitud2.getEstadoContribuyente());

                    paorn.setTelefono(personaSolicitud2.getTelefono());
                    paorn.setNuevo(false);
                    paorn.setDireccion(personaSolicitud2.getDomicilio() != null ? personaSolicitud2.getDomicilio()
                            .getDescripcionUbicacion() : null);
                    listaPersonasOirNotificaciones.add(paorn);
                }
                else if (personaSolicitud2 instanceof PersonaResidenteExtranjero) {
                    PersonaInvolucradaDTO pinv = new PersonaInvolucradaDTO();
                    pinv.setIdPersona(personaSolicitud2.getIdPersonaSolicitud());
                    pinv.setNombre(personaSolicitud2.getNombre());
                    pinv.setPaterno(personaSolicitud2.getApellidoPaterno());
                    pinv.setMaterno(personaSolicitud2.getApellidoMaterno());
                    pinv.setRfc(personaSolicitud2.getNss());
                    pinv.setEstadoContribuyente(personaSolicitud2.getEstadoContribuyente());
                    pinv.setRazonSocial(personaSolicitud2.getRazonSocial());
                    pinv.setDireccion(personaSolicitud2.getDomicilio() != null ? personaSolicitud2.getDomicilio()
                            .getDescripcionUbicacion() : null);
                    pinv.setTipoPersona("");
                    pinv.setExtranjero(personaSolicitud2.getBooleanExtranjero());
                    pinv.setNuevo(false);

                    listaPersonasInvolucradas.add(pinv);
                }

            }
            solicitudDTO.setListaPersonasNotificaciones(listaPersonasOirNotificaciones);
            solicitudDTO.setListaPersonasInvolucradas(listaPersonasInvolucradas);
        }
        catch (Exception e) {

            logger.error("", e);
        }

        return solicitudDTO;
    }

    public PersonaOirNotificacionesDTO transformaPersonaOirNotificaciones(
            PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones) {
        PersonaOirNotificacionesDTO paorn = new PersonaOirNotificacionesDTO();
        paorn.setIdPersona(personaOirRecibirNotificaciones.getIdPersonaSolicitud());
        paorn.setNombre(personaOirRecibirNotificaciones.getNombre());
        paorn.setPaterno(personaOirRecibirNotificaciones.getApellidoPaterno());
        paorn.setMaterno(personaOirRecibirNotificaciones.getApellidoMaterno());
        paorn.setRfc(personaOirRecibirNotificaciones.getRfc());
        paorn.setEstadoContribuyente(personaOirRecibirNotificaciones.getEstadoContribuyente());
        paorn.setTelefono(personaOirRecibirNotificaciones.getTelefono());
        paorn.setNuevo(false);
        paorn.setDireccion(personaOirRecibirNotificaciones.getDomicilio() != null ? personaOirRecibirNotificaciones
                .getDomicilio().getDescripcionUbicacion() : null);

        return paorn;
    }

    public PersonaInvolucradaDTO transformaPersonaResidenteExtrajero(
            PersonaResidenteExtranjero personaResidenteExtranjero) {
        PersonaInvolucradaDTO pinv = new PersonaInvolucradaDTO();
        pinv.setIdPersona(personaResidenteExtranjero.getIdPersonaSolicitud());
        pinv.setNombre(personaResidenteExtranjero.getNombre());
        pinv.setPaterno(personaResidenteExtranjero.getApellidoPaterno());
        pinv.setMaterno(personaResidenteExtranjero.getApellidoMaterno());
        pinv.setRfc(personaResidenteExtranjero.getNss());
        pinv.setEstadoContribuyente(personaResidenteExtranjero.getEstadoContribuyente());
        pinv.setRazonSocial(personaResidenteExtranjero.getRazonSocial());
        pinv.setDireccion(personaResidenteExtranjero.getDomicilio() != null ? personaResidenteExtranjero.getDomicilio()
                .getDescripcionUbicacion() : null);
        pinv.setTipoPersona("");
        pinv.setExtranjero(personaResidenteExtranjero.getBooleanExtranjero());
        pinv.setNuevo(false);

        return pinv;
    }

    public FraccionArancelariaDudaDTO transformaFraccionDuda(FraccionDuda fraccionDuda) {
        return new FraccionArancelariaDudaDTO(fraccionDuda.getIdFraccionDuda(), fraccionDuda.getFraccionDuda()
                .toString(), false);
    }
}
