/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.util.constante.Runtime;
import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Modelo de datos de documentos oficiales.
 * 
 * @author Softtek
 * 
 */
public class DocumentoOficialDataModel extends ListDataModel<DocumentoOficialDTO> implements
        SelectableDataModel<DocumentoOficialDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -127129566671230431L;

    /**
     * Propiedad para llevar a cabo un registro de eventos.
     */
    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    /**
     * Constructor
     */
    public DocumentoOficialDataModel(List<DocumentoOficialDTO> listaDocumentoOficialDTO) {
        super((Runtime.getInstance().isFielRequired())?listaDocumentoOficialDTO:estres(listaDocumentoOficialDTO));
    }

   private static List<DocumentoOficialDTO> estres(List<DocumentoOficialDTO> listaDocumentoOficialDTO){
               if(listaDocumentoOficialDTO!=null && listaDocumentoOficialDTO.isEmpty()){
           DocumentoOficialDTO docOfDTO = new DocumentoOficialDTO();
           docOfDTO.setNombreArchivo("estres");
           docOfDTO.setFechaCreacion(new Date());
           docOfDTO.setIdDocumentoOficial(1);
           docOfDTO.setRutaAzure("AERF770708T57.1426036676396");
           docOfDTO.setHashDocumento("bdd74a9c7a29820a4001e885bc151190");
           docOfDTO.setTamanioArchivo(new Long(NumerosConstantes.NOVENTA_Y_SEIS_MIL_CINCUENTA_Y_UNO));
           docOfDTO.setCadenaTamanioArchivo("1");
           listaDocumentoOficialDTO.add(docOfDTO);
       }
       return listaDocumentoOficialDTO;
    }

    /**
     * M&eacute;todo para obtener el documento oficial.
     */
    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public DocumentoOficialDTO getRowData(String arg0) {
        List<DocumentoOficialDTO> listaDocumentoOficialDTO = (List<DocumentoOficialDTO>) getWrappedData();

        for (DocumentoOficialDTO documento : listaDocumentoOficialDTO) {
            if (documento.getCveTipoDocumento().toString().equals(arg0)) {
                return documento;
            }
        }

        return null;
    }

    /**
     * M&eacute;todo para obtener la clave del tipo de documento
     * oficial.
     */
    @Override
    public Object getRowKey(DocumentoOficialDTO documento) {
        if (documento != null && documento.getCveTipoDocumento() != null) {
            return documento.getCveTipoDocumento();
        }
        else {
            return "";
        }
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    

}
