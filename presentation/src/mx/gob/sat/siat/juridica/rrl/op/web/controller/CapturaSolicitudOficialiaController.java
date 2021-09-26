package mx.gob.sat.siat.juridica.rrl.op.web.controller;

import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller.CapturaSolicitudController;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "solicitudControllerOficialia")
@ViewScoped
public class CapturaSolicitudOficialiaController extends CapturaSolicitudController {

    /**
     * 
     */
    private static final long serialVersionUID = -8697868478898214526L;

    private String nombrePromovente;

    @Override
    public void iniciar() {
        setListaUnidadesEmisoras(getCapturaSolicitudBussines().obtenerAutoridadesEmisoras());

        DatosSolicitudDTO datosSolicitudIDC = new DatosSolicitudDTO();

        getFlash().remove("idSolicitud");
        DatosSolicitudDTO datosSol = (DatosSolicitudDTO) getFlash().get("datosSolicitud");
        if (datosSol != null) {
            datosSolicitudIDC = datosSol;

            datosSolicitudIDC.setRfcContribuyente(datosSol.getSolicitante().getRfc());
            datosSolicitudIDC.setNombre(datosSol.getSolicitante().getNombre());
            datosSolicitudIDC.setApellidoMaterno(datosSol.getSolicitante().getApellidoMaterno());
            datosSolicitudIDC.setApellidoPaterno(datosSol.getSolicitante().getApellidoPaterno());
            datosSolicitudIDC.setCorreoElectronico(datosSol.getSolicitante().getDomicilio().getCorreoElectronico());
            datosSolicitudIDC.setRazonSocial(datosSol.getSolicitante().getRazonSocial());

            datosSolicitudIDC.setCalle(datosSol.getSolicitante().getDomicilio().getCalle());
            datosSolicitudIDC.setNumeroExterior(datosSol.getSolicitante().getDomicilio().getNumeroExterior());
            datosSolicitudIDC.setNumeroInterior(datosSol.getSolicitante().getDomicilio().getNumeroInterior());
            datosSolicitudIDC.setTelefono(datosSol.getSolicitante().getDomicilio().getTelefono());
            datosSolicitudIDC.setCodigoPostal(datosSol.getSolicitante().getDomicilio().getCodigoPostal());
            datosSolicitudIDC.setColonia(datosSol.getSolicitante().getDomicilio().getColonia());
            datosSolicitudIDC.setDelegacionMunicipio(datosSol.getSolicitante().getDomicilio().getDelegacionMunicipio());
            datosSolicitudIDC.setEstado(datosSol.getSolicitante().getDomicilio().getEstado());
            datosSolicitudIDC.setAdministracionLocal(datosSol.getSolicitante().getDomicilio().getAdministracionLocal());
            datosSolicitudIDC.setNombreAdministracionLocal(datosSol.getSolicitante().getDomicilio()
                    .getNombreAdministracionLocal());
            datosSolicitudIDC.setRepresentanteLegal(datosSol.getSolicitante().getDomicilio().getRepresentanteLegal());
            datosSolicitudIDC.setPaisCve(datosSol.getSolicitante().getDomicilio().getCvePais());
            datosSolicitudIDC.setPaisNombre(datosSol.getSolicitante().getDomicilio().getNombrePais());
            setNombrePromovente(datosSol.getSolicitante().getNombre() + " "
                    + datosSol.getSolicitante().getApellidoPaterno() + " "
                    + datosSol.getSolicitante().getApellidoMaterno());
            datosSolicitudIDC.setClaveColonia(datosSol.getSolicitante().getDomicilio().getCveColonia());
            datosSolicitudIDC.setClaveDelegacion(datosSol.getSolicitante().getDomicilio().getCveDelegacion());
            datosSolicitudIDC.setClaveEstado(datosSol.getSolicitante().getDomicilio().getCveEstado());
            datosSolicitudIDC.setCveLocalidad(datosSol.getSolicitante().getDomicilio().getCveLocalidad());

        }

        setDatosSolicitud(datosSolicitudIDC);
        getDatosSolicitud().setRolCapturista(TipoRol.OFICIAL_PARTES.getClave());

        if (getDatosSolicitud().getRazonSocial() != null) {
            if (getDatosSolicitud().getRazonSocial().equalsIgnoreCase("")) {
                setBanderaRazonSocial(false);
            }
            else {
                setBanderaRazonSocial(true);
            }

        }
        else {
            setBanderaRazonSocial(false);
        }
        setModalidadTramite(new StringBuffer());
        String modalidad =
                getCapturaSolicitudBussines().obtenerModalidadDeTramite(
                        Integer.parseInt(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA));
        getModalidadTramite().append("Recursos Administrativos-");
        getModalidadTramite().append(modalidad);
        meterId();

    }

    /**
     * Metodo para siguiente.
     * 
     * @return
     * @throws SolicitudNoGuardadaException
     * @throws Exception
     */
    public void siguiente() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put("datosSolicitud", getDatosSolicitud());
        configurableNavigationHandler.performNavigation("/resources/pages/rrl/op/registroSolicitudRRL.xhtml"
                + "?faces-redirect=true");

    }

    /**
     * Metodo para siguiente.
     * 
     * @return
     * @throws SolicitudNoGuardadaException
     * @throws Exception
     */
    public void atras() {

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation("/resources/pages/rrl/op/buscarPersonaOficialia.xhtml"
                + "?faces-redirect=true");

    }

    /**
     * @return the nombrePromovente
     */
    public String getNombrePromovente() {
        return nombrePromovente;
    }

    /**
     * @param nombrePromovente
     *            the nombrePromovente to set
     */
    public void setNombrePromovente(String nombrePromovente) {
        this.nombrePromovente = nombrePromovente;
    }

    @Override
    public String getDireccionFirma() {
        return UrlFirma.PAGINA_FIRMA_SOLICITUD_RRL_OP.toString();
    }

    public void seleccionDocumentosRegistro() {

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation("/resources/pages/rrl/op/seleccionDocumento.xhtml"
                + "?faces-redirect=true");
    }

    public void validaAccesoOficialia() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
                MenusConstantes.MENU_EMPLEADOS);

    }
}
