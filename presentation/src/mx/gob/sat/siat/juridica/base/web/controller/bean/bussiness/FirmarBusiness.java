/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.FirmarFacade;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.Serializable;

/**
 * Bussines que implementa la logica de negocio para firmar.
 * 
 * @author softtek
 * 
 */
@NoneScoped
@ManagedBean
public class FirmarBusiness implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6933046869261495568L;

    /**
     * Facade para la firma
     */
    @ManagedProperty("#{firmarFacade}")
    private transient FirmarFacade firmarFacade;

    /**
     * Metodo que guarda la firma.
     * 
     * @param firmaDTO
     */
    public void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud) {
        getFirmarFacade().guardarFirma(firmaDTO, idSolicitud);
    }

    /**
     * 
     * @return firmarFacade
     */
    public FirmarFacade getFirmarFacade() {
        return firmarFacade;
    }

    /**
     * 
     * @param firmarFacade
     *            el/la firmarFacade a fijar.
     */
    public void setFirmarFacade(FirmarFacade firmarFacade) {
        this.firmarFacade = firmarFacade;
    }

    public void guardarFirmaAutorizarRequerimiento(FirmaDTO firma, Long idRequerimiento) {
        getFirmarFacade().guardarFirmaAutorizarRequerimento(firma, idRequerimiento);

    }

}
