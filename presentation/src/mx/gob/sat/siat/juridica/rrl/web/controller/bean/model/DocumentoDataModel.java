/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class DocumentoDataModel extends ListDataModel<DocumentoDTO> implements SelectableDataModel<DocumentoDTO>,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3228238302205855196L;

    public DocumentoDataModel(List<DocumentoDTO> listaDocumentoDTO) {
        super(listaDocumentoDTO);
    }

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public DocumentoDTO getRowData(String arg0) {
        List<DocumentoDTO> listaDocumentoDTO = (List<DocumentoDTO>) getWrappedData();

        for (DocumentoDTO documento : listaDocumentoDTO) {
            if (documento.getIdTipoDocumento().toString().equals(arg0)) {
                return documento;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(DocumentoDTO documento) {
        if (documento != null && documento.getIdTipoDocumento() != null) {
            return documento.getIdTipoDocumento();
        }
        else {
            return "";
        }
    }

}
