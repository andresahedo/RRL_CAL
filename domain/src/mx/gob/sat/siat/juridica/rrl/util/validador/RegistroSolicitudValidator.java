/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.util.validador;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.util.validator.BaseValidator;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class RegistroSolicitudValidator extends BaseValidator {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 7017209508163375599L;

    /**
     * Metodo para validar documentos
     * 
     * @param documentos
     * @param listaObligatorios
     * @throws ArchivoNoGuardadoException
     */
    public void validarDocumentos(List<Documento> documentos, List<DocumentoTramite> listaObligatorios)
            throws ArchivoNoGuardadoException {
        List<DocumentoTramite> documentosNoAgregados = new ArrayList<DocumentoTramite>();
        boolean encontrado = false;

        for (DocumentoTramite documentoCombo : listaObligatorios) {
            encontrado = false;
            for (Documento documento : documentos) {
                if (documentoCombo.getTipoDoc().getIdTipoDocumento().equals(documento.getIdTipoDocumento())) {
                    encontrado = true;
                    continue;
                }
            }
            if (encontrado) {
                continue;
            }
            documentosNoAgregados.add(documentoCombo);
        }
        if (!documentosNoAgregados.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (DocumentoTramite doc : documentosNoAgregados) {
                sb.append(doc.getTipoDoc().getNombre());
                sb.append(". ");

            }
            throw new ArchivoNoGuardadoException(sb.toString());
        }
    }

}
