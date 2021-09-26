/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.ca.dao.BandejaConsultasDAO;
import mx.gob.sat.siat.juridica.ca.service.BandejaConsultasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service
public class BandejaConsultasServicesImpl implements BandejaConsultasServices {

    /**
     * 
     */
    private static final long serialVersionUID = -5948491868894402396L;

    /**
     * Atributo privado "bandejaConsultasDAO" tipo BandejaConsultasDAO
     */
    @Autowired
    private transient BandejaConsultasDAO bandejaConsultasDAO;

    /**
     * Metodo para obtener una lista de trmites
     * 
     * @param numAsunto
     * @param fechaInicio
     * @param fechaFin
     * @param estadoProcesal
     * @param tipoTramite
     * @return
     */
    public List<Tramite> obtenerTramites(String numAsunto, Date fechaInicio, Date fechaFin, String estadoProcesal,
            String tipoTramite) {
        return bandejaConsultasDAO.obtenerTramites(numAsunto, fechaInicio, fechaFin, estadoProcesal, tipoTramite);
    }

    /**
     * Metodo para obtener las modalidades de un tramite
     * 
     * @param idServicio
     * @return
     */
    @Override
    public List<TipoTramite> obtenerModalidadesPorServicio(String idServicio) {
        // TODO Auto-generated method stub
        return bandejaConsultasDAO.obtenerModalidadesPorServicio(idServicio);
    }

}
