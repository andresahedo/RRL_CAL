package mx.gob.sat.siat.juridica.base.util.helper;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

@Component
public class DocumentosHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4474316208540047444L;

    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    public String obtenerCadenaTamanioArchivo(long tamanioBytes) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = NumerosConstantes.SIZE_KB;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTera = sizeGb * sizeKb;

        if (tamanioBytes < sizeMb) {
            return df.format(tamanioBytes / sizeKb) + " Kb";
        }
        else if (tamanioBytes < sizeGb) {
            return df.format(tamanioBytes / sizeMb) + " Mb";
        }
        else if (tamanioBytes < sizeTera) {
            return df.format(tamanioBytes / sizeGb) + " Gb";
        }
        return "";
    }

    public void complementarDocumentos(Long idSolicitud) {
        List<DocumentoSolicitud> docs = registroRecursoRevocacionDAO.obtenerDocumentoSolicitud(idSolicitud.toString());
        for (DocumentoSolicitud doc : docs) {

            doc.setComplementario(false);
            registroRecursoRevocacionDAO.guardaDocumento(doc);
            registroRecursoRevocacionDAO.guardaDocumentoSolicitud(doc.getDocumento());
        }

    }
}
