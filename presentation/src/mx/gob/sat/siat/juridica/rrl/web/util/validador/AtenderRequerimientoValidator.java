/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.util.validador;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.web.util.validador.BaseValidator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.List;

@ManagedBean
@NoneScoped
public class AtenderRequerimientoValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 770265763999997233L;

    public boolean validarDocumentosAnexados(List<DocumentoDTO> listaDocumentosOficiales) {
        if (listaDocumentosOficiales != null && listaDocumentosOficiales.size() > 0) {
            return true;
        }
        return false;
    }

}
