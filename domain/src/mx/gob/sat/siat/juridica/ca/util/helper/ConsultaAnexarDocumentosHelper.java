/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.helper;

/*
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class ConsultaAnexarDocumentosHelper extends BaseHelper {
    /**
     * Inyeccion de Dao de enumeracion
     */
    @Autowired
    private EnumeracionDao enumeracionDao;

    /**
     * Inyeccion de Dao de remision
     */
    @Autowired
    private RemisionDao remisionDao;

    @Autowired
    private DiasHabilesHelper diasHabilesHelper;
    
    @Autowired
    private DocumentoDao documentoDao;
    
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    /**
     * 
     * @author softtek
     */
    private static final long serialVersionUID = 4825846358048096986L;

    /**
     * Metodo que valida segun la fecha en que se firmo la promocion y
     * en la que se anexo el documento de anuncio de pruebas, asi como
     * los requerimientos atendidos y no atendidos que documentos
     * puede anexar.
     * 
     * @param tramite
     * @param listaRequerimientos
     * @param listaDocumentos
     * @return
     */
    public String validaDocumentosDisponibles(Tramite tramite, List<Requerimiento> listaRequerimientos,
            List<DocumentoSolicitud> listaDocumentos) {
        String reglaAnexado = "";
        Date date = new Date();
        String fechaRegProm =
                enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_HABILES_SIG_REG_PROM.getClave());
        String fechaRegDocPruebas =
                enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_HABILES_SIG_REG_DOC_APA.getClave());

        Date fechaCalculadaTramite =
                diasHabilesHelper.obtenerFechaDiasHabiles(tramite.getFechaInicioTramite(),
                        (Integer.valueOf(fechaRegProm)));

        boolean remisionExterna = false;
        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(tramite.getNumeroAsunto());
        if (remision != null) {
            UnidadAdministrativa unidadAdministrativa = remision.getUnidadAdminNueva();

            if (unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                    .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                    || unidadAdministrativa.getIdeTipoUnidadAdministrativa().getClave()
                            .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave())) {
                remisionExterna = true;
            }
        }

        // Regla sobre un documento que podra adjuntarse, cuando
        // el asunto
        // de RRL este en Promovido o Estudio y
        // aun no tenga Requerimientos emitidos.

        if (tramite.getEstadoTramite().getClave().equals(EstadoTramite.PROMOVIDO.getClave())
                || tramite.getEstadoTramite().getClave().equals(EstadoTramite.EN_ESTUDIO.getClave())) {
            // Se valida si tiene Requerimientos
            if (listaRequerimientos == null || listaRequerimientos.isEmpty()) {
                // Se valida si ya pasaron los dias habiles desde
                // que se firmo la promocion.
                if (date.before(fechaCalculadaTramite)) {
                    reglaAnexado = "REANX.APERA";
                    for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                        if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {

                            Date fechaCalculadaDoc =
                                    diasHabilesHelper.obtenerFechaDiasHabiles(documentoSolicitud.getFechaAsociacion(),
                                            (Integer.valueOf(fechaRegDocPruebas)));
                            // Regla sobre un documento que podra
                            // adjuntarse, cuando el asunto RRL
                            // este en
                            // Estudio, le hayan emitido
                            // requerimientos
                            // y se encuentren atendidos o no
                            // atendidos y
                            // solo si aun no ha transcurrido el
                            // numero de
                            // dias habiles a partir del dia
                            // habil siguiente de la fecha de
                            // recepcion del
                            // Anuncio de Pruebas Adicionales.
                            if (!date.before(fechaCalculadaDoc)) {
                                reglaAnexado = "REANX.APERP";
                            }
                        }
                    }

                }
                else {
                    // Si ya pasaron los dias habiles programados
                    // desde que se firmo la promocion
                    reglaAnexado = "REANX.APENR";

                    for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                        if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {
                            Date fechaCalculadaDoc =
                                    diasHabilesHelper.obtenerFechaDiasHabiles(documentoSolicitud.getFechaAsociacion(),
                                            (Integer.valueOf(fechaRegDocPruebas)));

                            // Se valida si ya pasaron los dias
                            // habiles desde que se anexo el
                            // anuncio de pruebas
                            if (date.before(fechaCalculadaDoc)) {
                                reglaAnexado = "REANX.APENA";
                            }
                        }
                    }

                }

            }
            else {
                // Cuando si tiene Requerimientos
                for (Requerimiento requerimiento : listaRequerimientos) {

                    if (requerimiento.getEstadoRequerimiento().getClave()
                            .equals(EstadoRequerimiento.NO_ATENDIDO.getClave())) {
                        if (date.before(fechaCalculadaTramite)) {
                            reglaAnexado = "REANX.AENAP";
                            for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                                if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                        || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {
                                    Date fechaCalculadaDoc =
                                            diasHabilesHelper.obtenerFechaDiasHabiles(
                                                    documentoSolicitud.getFechaAsociacion(),
                                                    (Integer.valueOf(fechaRegDocPruebas)));

                                    // Se valida si ya pasaron los
                                    // dias habiles desde que se
                                    // anexo el anuncio de pruebas
                                    if (!date.before(fechaCalculadaDoc)) {
                                        reglaAnexado = "REANX.AERNR";
                                    }
                                }
                            }

                        }
                        else {
                            for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                                if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                        || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {
                                    Date fechaCalculadaDoc =
                                            diasHabilesHelper.obtenerFechaDiasHabiles(
                                                    documentoSolicitud.getFechaAsociacion(),
                                                    (Integer.valueOf(fechaRegDocPruebas)));
                                    // Se valida si ya pasaron los
                                    // dias habiles desde que se
                                    // anexo el anuncio de pruebas
                                    if (date.before(fechaCalculadaDoc)) {
                                        reglaAnexado = "REANX.PERSA";
                                    }
                                }
                            }

                        }
                    }
                    else {

                        if (date.before(fechaCalculadaTramite)) {
                            reglaAnexado = "REANX.AERRA";
                            for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                                if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                        || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {
                                    Date fechaCalculadaDoc =
                                            diasHabilesHelper.obtenerFechaDiasHabiles(
                                                    documentoSolicitud.getFechaAsociacion(),
                                                    (Integer.valueOf(fechaRegDocPruebas)));

                                    // Se valida si ya pasaron los
                                    // dias habiles desde que se
                                    // anexo el anuncio de pruebas
                                    if (!date.before(fechaCalculadaDoc)) {
                                        reglaAnexado = "REANX.AERRP";
                                    }
                                }
                            }

                        }
                        else {

                            reglaAnexado = "REANX.AERA";

                            for (DocumentoSolicitud documentoSolicitud : listaDocumentos) {

                                if (documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.SEIS
                                        || documentoSolicitud.getIdTipoDocumento() == NumerosConstantes.CIENTO_CATORCE) {
                                    Date fechaCalculadaDoc =
                                            diasHabilesHelper.obtenerFechaDiasHabiles(
                                                    documentoSolicitud.getFechaAsociacion(),
                                                    (Integer.valueOf(fechaRegDocPruebas)));

                                    // Se valida si ya pasaron los
                                    // dias habiles desde que se
                                    // anexo el anuncio de pruebas
                                    if (date.before(fechaCalculadaDoc)) {
                                        reglaAnexado = "REANX.AERNA";
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }
        else if (!tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO.getClave())
                && !tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO_NOTIFICADO.getClave())
                && !remisionExterna) {
            reglaAnexado = "REANX.DESISTIMIENTO";

        }

        return reglaAnexado;
    }
    
    public void documentosExclusivoFondo(String idTipoTramite,Tramite tramite,String numAsunto,List<DocumentoTramite> lstDocTra){
        if(lstDocTra!=null){
            if(tramite!=null){
                if (!tramite.getEstadoTramite().getClave().equals(EstadoTramite.PROMOVIDO.getClave())
                        && !tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO.getClave())
                        && !tramite.getEstadoTramite().getClave().equals(EstadoTramite.RESUELTO_NOTIFICADO.getClave())
                        && !tramite.getEstadoTramite().getClave().equals(EstadoTramite.REMITIDO.getClave())) {
                        DocumentoOficial objDocOf=documentoDao.obtenerUltimoDocumentoOficialesAnexadosPorTipo(numAsunto, TipoDocumentoOficial.OFICIO_ADMISION_CON_AUDIENCIA.getClave());
                        if(objDocOf!=null){
                            int lnDias=diasHabilesHelper.calcularDiasTranscurridos(new Date(),objDocOf.getFechaAudiencia());
                            String lsDiasHab=enumeracionDao.obtenerParametroByEnum(EnumeracionParametro.DIAS_HAB_CAMBIO_FEC_AUDIENCIA.getClave());
                            if(lnDias>Integer.valueOf(lsDiasHab)){
                                lstDocTra.addAll(registroRecursoRevocacionDAO.buscarDocumentosTramitePorTipoTramite(idTipoTramite, "REANX.EXFON"));
                            }
                        }
                    }
            }
        }
    }
}
