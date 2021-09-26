/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(value = "20000")
public abstract class SolicitudConsultaAutorizacion extends SolicitudDatosGenerales {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 9122653440167006456L;

    /**
     * Atributo privado "solicitanteHelper" tipo Solicitante
     */
    @Transient
    private Solicitante solicitanteHelper;

    /**
     * Atributo privado "representanteLegalHelper" tipo
     * RepresentanteLegal
     */
    @Transient
    private RepresentanteLegal representanteLegalHelper;

    /**
     * Atributo privado "medioDefensaHelper" tipo MedioDefensa
     */
    @Transient
    private MedioDefensa medioDefensaHelper;

    /**
     * Atributo privado "personaORNHelper" tipo
     * PersonaOirRecibirNotificaciones
     */
    @Transient
    private PersonaOirRecibirNotificaciones personaORNHelper;

    /**
     * Atributo privado "personaExtranjeroHelper" tipo
     * PersonaResidenteExtranjero
     */
    @Transient
    private PersonaResidenteExtranjero personaExtranjeroHelper;

    @Transient
    private Autoridad autoridadHelper;

    @Transient
    private Autoridad autoridadSujetoHelper;

    /**
     * Atributo privado "personaORNHelper" tipo
     * PersonaOirRecibirNotificaciones
     */
    @Transient
    private List<PersonaOirRecibirNotificaciones> personaONHelperList;

    /**
     * Atributo privado "personaExtranjeroHelper" tipo
     * PersonaResidenteExtranjero
     */
    @Transient
    private List<PersonaResidenteExtranjero> personaResExtHelperList;

    @Transient
    private List<FraccionDuda> fraccionDudaHelperList;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName="IDSOLICITUD", updatable = false)
    private List<EstadoManifiesto> estadosManifiesto;
    
    public SolicitudConsultaAutorizacion() {
        super();
        estadosManifiesto = new ArrayList<EstadoManifiesto>();
    }

    public List<EstadoManifiesto> getEstadosManifiesto() {
        return estadosManifiesto;
    }

    public void setEstadosManifiesto(List<EstadoManifiesto> estadosManifiesto) {
        this.estadosManifiesto = estadosManifiesto;
    }

    public void addEstadoManifiesto(Manifiesto manifiesto, boolean aceptado) {
        EstadoManifiesto estadoManifiesto = new EstadoManifiesto();

        EstadoManifiestoPK estadoManifiestoPK = new EstadoManifiestoPK();
        estadoManifiestoPK.setIdManifiesto(manifiesto.getIdManifiesto());
        estadoManifiestoPK.setIdSolicitud(getIdSolicitud());

        estadoManifiesto.setEstadoManifiestoPK(estadoManifiestoPK);
        estadoManifiesto.setAceptado(aceptado);
        estadoManifiesto.setManifiesto(manifiesto);

        estadosManifiesto.add(estadoManifiesto);
    }

    /**
     * 
     * @return solicitanteHelper
     */
    public Solicitante getSolicitanteHelper() {
        return solicitanteHelper;
    }

    /**
     * 
     * @param solicitanteHelper
     *            a fijar
     */
    public void setSolicitanteHelper(Solicitante solicitanteHelper) {
        this.solicitanteHelper = solicitanteHelper;
    }

    /**
     * 
     * @return representanteLegalHelper
     */
    public RepresentanteLegal getRepresentanteLegalHelper() {
        return representanteLegalHelper;
    }

    /**
     * 
     * @param solicitanteHelper
     *            a fijar
     */
    public void setRepresentanteLegalHelper(RepresentanteLegal representanteLegalHelper) {
        this.representanteLegalHelper = representanteLegalHelper;
    }

    /**
     * 
     * @return medioDefensaHelper
     */
    public MedioDefensa getMedioDefensaHelper() {
        return medioDefensaHelper;
    }

    /**
     * 
     * @param medioDefensaHelper
     *            a fijar
     */
    public void setMedioDefensaHelper(MedioDefensa medioDefensaHelper) {
        this.medioDefensaHelper = medioDefensaHelper;
    }

    /**
     * 
     * @return personaORNHelper
     */
    public PersonaOirRecibirNotificaciones getPersonaORNHelper() {
        return personaORNHelper;
    }

    /**
     * 
     * @param personaORNHelper
     *            a fijar
     */
    public void setPersonaORNHelper(PersonaOirRecibirNotificaciones personaORNHelper) {
        this.personaORNHelper = personaORNHelper;
    }

    /**
     * 
     * @return personaExtranjeroHelper
     */
    public PersonaResidenteExtranjero getPersonaExtranjeroHelper() {
        return personaExtranjeroHelper;
    }

    /**
     * 
     * @param personaExtranjeroHelper
     *            a fijar
     */
    public void setPersonaExtranjeroHelper(PersonaResidenteExtranjero personaExtranjeroHelper) {
        this.personaExtranjeroHelper = personaExtranjeroHelper;
    }

    /**
     * @return the personaONHelperList
     */
    public List<PersonaOirRecibirNotificaciones> getPersonaONHelperList() {
        return personaONHelperList;
    }

    /**
     * @param personaONHelperList
     *            the personaONHelperList to set
     */
    public void setPersonaONHelperList(List<PersonaOirRecibirNotificaciones> personaONHelperList) {
        this.personaONHelperList = personaONHelperList;
    }

    /**
     * @return the personaResExtHelperList
     */
    public List<PersonaResidenteExtranjero> getPersonaResExtHelperList() {
        return personaResExtHelperList;
    }

    /**
     * @param personaResExtHelperList
     *            the personaResExtHelperList to set
     */
    public void setPersonaResExtHelperList(List<PersonaResidenteExtranjero> personaResExtHelperList) {
        this.personaResExtHelperList = personaResExtHelperList;
    }

    /**
     * @return the autoridadHelper
     */
    public Autoridad getAutoridadHelper() {
        return autoridadHelper;
    }

    /**
     * @param autoridadHelper
     *            the autoridadHelper to set
     */
    public void setAutoridadHelper(Autoridad autoridadHelper) {
        this.autoridadHelper = autoridadHelper;
    }

    /**
     * @return the autoridadSujetoHelper
     */
    public Autoridad getAutoridadSujetoHelper() {
        return autoridadSujetoHelper;
    }

    /**
     * @param autoridadSujetoHelper
     *            the autoridadSujetoHelper to set
     */
    public void setAutoridadSujetoHelper(Autoridad autoridadSujetoHelper) {
        this.autoridadSujetoHelper = autoridadSujetoHelper;
    }

    /**
     * @return the fraccionDudaHelperList
     */
    public List<FraccionDuda> getFraccionDudaHelperList() {
        return fraccionDudaHelperList;
    }

    /**
     * @param fraccionDudaHelperList
     *            the fraccionDudaHelperList to set
     */
    public void setFraccionDudaHelperList(List<FraccionDuda> fraccionDudaHelperList) {
        this.fraccionDudaHelperList = fraccionDudaHelperList;
    }

}
