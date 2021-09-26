/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.util.validador;

import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to implement validations into generar resolucion flow from
 * juridica.
 * 
 * @author Softtek antonio.flores
 * @date: 03/07/2014
 */
@ManagedBean(name = "generarResolucionValidator")
@NoneScoped
public class GenerarResolucionValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 5805163596464074842L;

    private String campo;

    private List<String> camposMiss;

    /**
     * 
     * @return
     */
    public boolean validaNumNoDecimal(Object campoRequerido) {
        boolean datosOk = true;

        return datosOk;
    }

    /**
     * 
     * @return
     */
    public boolean validaCamposRequeridosAgregar(Object campoRequerido) {
        boolean datosOk = true;
        if (campoRequerido == null) {
            datosOk = false;
        }

        if (campoRequerido instanceof String) {
            if (((String) campoRequerido).trim().isEmpty()) {
                datosOk = false;
            }
        }

        return datosOk;
    }

    /**
     * Method to validate if required field were fill by user into
     * resolucion flow.
     * 
     * @param tipoObjeto
     * @return
     */
    public boolean validateDatosResolucion(ResolucionDatosGeneradosDTO resolucionCALDTO, boolean tipo) {
        boolean requieredOk = true;
        camposMiss = new ArrayList<String>();
        if (tipo) {

            if (resolucionCALDTO.getFechaSesion() == null) {
                requieredOk = false;
                getCamposMiss().add("Fecha de sesi�n");
            }
            else {
                Date fechaSession = resolucionCALDTO.getFechaSesion();
                Date fechaActual = new Date();
                if (fechaActual.before(fechaSession)) {
                    return false;
                }
            }

        }
        if (!requieredOk) {
            buildCampos();
        }

        return requieredOk;
    }

    /**
     * 
     */
    private void buildCampos() {
        StringBuilder valores = new StringBuilder();
        if (!getCamposMiss().isEmpty()) {
            for (String campos : getCamposMiss()) {
                valores.append(campos);
                valores.append(",");
            }
        }
        valores.deleteCharAt(valores.length() - 1);
        this.setCampo(valores.toString());
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo
     *            the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @return the camposMiss
     */
    public List<String> getCamposMiss() {
        return camposMiss;
    }

    /**
     * @param camposMiss
     *            the camposMiss to set
     */
    public void setCamposMiss(List<String> camposMiss) {
        this.camposMiss = camposMiss;
    }

    public boolean validaCaracteres(String numeroOficio)  {
        boolean requieredOk = true;
        Pattern pat = Pattern.compile("^[a-zA-Z0-9-]*$");
        Matcher mat = pat.matcher(numeroOficio);
        if (!mat.matches()) {
            requieredOk = false;
        }
        return requieredOk;
    }

}
