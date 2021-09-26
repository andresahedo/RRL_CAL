/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.AnexarDocumentosFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bussines que implementan la l&oacute;gica de negocio para la
 * obtenci&oacute;n de los documentos de la solicitud
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "seleccionDocumentosBussines")
@NoneScoped
public class SeleccionDocumentosBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 2785506259028095277L;

    /** Facade para obtener documentos */
    @ManagedProperty("#{anexarDocumentosFacade}")
    private AnexarDocumentosFacade anexarDocumentosFacade;

    /**
     * M&eacute;todo para obtener los documentos obligatorios
     * asociados a la solicitud
     */
    public List<DocumentoDTO> listaDocObligatorios(Integer idTipoTramite) {
        return getAnexarDocumentosFacade().getDocumentosObligatorios(idTipoTramite);
    }

    /**
     * M&eacute;todo para obtener los documentos opcionales asociados
     * a la solicitud
     */
    public List<DocumentoDTO> listaDocOpcionales(Integer idTipoTramite) {
        return getAnexarDocumentosFacade().getDocumentosOpcionales(idTipoTramite);
    }

    /**
     * 
     * @return anexarDocumentosFacade
     */
    public AnexarDocumentosFacade getAnexarDocumentosFacade() {
        return anexarDocumentosFacade;
    }

    /**
     * 
     * @param anexarDocumentosFacade
     *            a fijar
     */
    public void setAnexarDocumentosFacade(AnexarDocumentosFacade anexarDocumentosFacade) {
        this.anexarDocumentosFacade = anexarDocumentosFacade;
    }
}
