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
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.web.util.validador.BaseValidator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@ManagedBean(name = "anexarDocumentoValidator")
@NoneScoped
public class AnexarDocumentoValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = -2863253611688233038L;

    public boolean validarDocumentosAnexados(List<DocumentoDTO> listaDocumentos) {
        if (listaDocumentos != null && listaDocumentos.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean validarDocumentosOficiales(List<DocumentoOficialDTO> listaDoc) {
        if (listaDoc != null && listaDoc.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean validarDocumentosAnexados(List<DocumentoDTO> listaDocumentos, List<DocumentoDTO> documentosCombo) {
        if (listaDocumentos != null && listaDocumentos.size() > 0) {
            Collection<String> listaAnexados = new HashSet<String>();
            Collection<String> listaCombo = new HashSet<String>();
            for (DocumentoDTO documento : listaDocumentos) {
                listaAnexados.add(documento.getIdTipoDocumento().toString());
            }
            for (DocumentoDTO documentoCombo : documentosCombo) {
                listaCombo.add(documentoCombo.getIdTipoDocumento().toString());
            }
            listaCombo.removeAll(listaAnexados);
            if (listaCombo.isEmpty()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public List<String> validarDocumentosAnexadosFaltantes(List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> documentosCombo) {
        List<String> listaCombo = new ArrayList<String>();
        for (DocumentoDTO documentoCombo : documentosCombo) {
            listaCombo.add(documentoCombo.getTipoDocumento());
        }
        if (listaDocumentos != null && listaDocumentos.size() > 0) {

            List<String> listaAnexados = new ArrayList<String>();

            for (DocumentoDTO documento : listaDocumentos) {
                listaAnexados.add(documento.getTipoDocumento());
            }

            listaCombo.removeAll(listaAnexados);
            if (listaCombo.isEmpty()) {
                listaCombo = null;
            }

        }
        return listaCombo;
    }
}
