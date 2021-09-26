package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model;

import java.io.Serializable;

public class ArbolTramites implements Serializable, Comparable<ArbolTramites> {

    /**
     * 
     */
    private static final long serialVersionUID = -1897242245597778963L;

    private Integer idTipoTramite;

    private String idServicio;

    private String modalidad;

    private Boolean tramite;

    private Boolean responsable;

    private String header;

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the tramite
     */
    public Boolean getTramite() {

        return tramite == null ? Boolean.FALSE : this.tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(Boolean tramite) {
        this.tramite = (tramite == null ? Boolean.FALSE : tramite);
    }

    /**
     * @return the responsable
     */
    public Boolean getResponsable() {
        return (responsable == null ? Boolean.FALSE : this.responsable);
    }

    /**
     * @param responsable
     *            the responsable to set
     */
    public void setResponsable(Boolean responsable) {
        this.responsable = (responsable == null ? Boolean.FALSE : responsable);
    }

    public ArbolTramites(String header, Integer idTipoTramite, String idServicio, String modalidad, Boolean tramite,
            Boolean responsable) {
        this.idTipoTramite = idTipoTramite;
        this.idServicio = idServicio;
        this.modalidad = modalidad;
        this.tramite = tramite;
        this.responsable = responsable;
        this.header = header;

    }

    // Eclipse Generated hashCode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modalidad == null) ? 0 : modalidad.hashCode());
        result = prime * result + ((tramite == null) ? 0 : tramite.hashCode());
        result = prime * result + ((responsable == null) ? 0 : responsable.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ArbolTramites other = (ArbolTramites) obj;
        if (modalidad == null) {
            if (other.modalidad != null) {
                return false;
            }
        }
        else if (!modalidad.equals(other.modalidad)) {
            return false;
        }
        if (tramite == null) {
            if (other.tramite != null) {
                return false;
            }
        }
        else if (!tramite.equals(other.tramite)) {
            return false;
        }
        if (responsable == null) {
            if (other.responsable != null) {
                return false;
            }
        }
        else if (!responsable.equals(other.responsable)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return modalidad;
    }

    public int compareTo(ArbolTramites arbolTramites) {
        return this.getModalidad().compareTo(arbolTramites.getModalidad());
    }

    /**
     * @return the idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite
     *            the idTipoTramite to set
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * @return the idServicio
     */
    public String getIdServicio() {
        return idServicio;
    }

    /**
     * @param idServicio
     *            the idServicio to set
     */
    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header
     *            the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }
}
