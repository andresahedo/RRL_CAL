package mx.gob.sat.siat.juridica.cal.web.util;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.util.validator.BaseValidator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "registroSolicitudCALCommonValidator")
@NoneScoped
public class RegistroSolicitudCALCommonValidator extends BaseValidator {

    /**
     * 
     */
    private static final long serialVersionUID = 6916134158372303506L;

    public List<String> validarDocumentosAnexadosFaltantes(List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> listaDocsRequeridos) {
        List<String> lista = new ArrayList<String>();
        for (DocumentoDTO documentoCombo : listaDocsRequeridos) {
            lista.add(documentoCombo.getTipoDocumento());
        }
        if (listaDocumentos != null && listaDocumentos.size() > 0) {

            List<String> listaAnexados = new ArrayList<String>();

            for (DocumentoDTO documento : listaDocumentos) {
                listaAnexados.add(documento.getTipoDocumento());
            }

            lista.removeAll(listaAnexados);
            if (lista.isEmpty()) {
                lista = null;
            }
        }
        return lista;
    }
}
