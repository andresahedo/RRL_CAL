/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Secuencia;
import mx.gob.sat.siat.juridica.base.service.NumeroAsuntoServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.rrl.service.GenerarNumeroAsuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author softtek
 * 
 */
@Service("generarNumeroAsuntoService")
@Scope("singleton")
public class GenerarNumeroAsuntoServiceImpl extends BaseSerializableBusinessServices implements
        GenerarNumeroAsuntoService {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado tramiteDAO tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDAO;

    @Autowired
    private NumeroAsuntoServices numeroAsuntoServices;

    /**
     * Metodo para obtener una secuencia
     * 
     * @param tipoSecuencia
     * @param claveUno
     * @param claveDos
     * @return
     */
    public String obtenerSecuencia(String tipoSecuencia, String claveUno, String claveDos) {

        String secuencia = "";
        Secuencia seqBucar = new Secuencia(tipoSecuencia, claveUno, claveDos);
        synchronized (this) {
            // Obtiene el valor de la secuencia en base de datos
            Secuencia parametroSecuencia = tramiteDAO.obtenerSecuenciaFolio(seqBucar);
            Integer secuenciaActual;
            if (parametroSecuencia != null) {
                // Si se obtuvo de base, se toma de ahi el valor
                secuenciaActual = parametroSecuencia.getSecuencia();
            }
            else {
                // No se obtuvo de base, se guarda el valor 1
                secuenciaActual = Integer.valueOf(1);
                seqBucar.setSecuencia(secuenciaActual);
                tramiteDAO.guardarSecuenciaFolio(seqBucar);
                parametroSecuencia = tramiteDAO.obtenerSecuenciaFolio(seqBucar);
            }

            // Se arma identificador de la secuencia para el mapa
            StringBuffer sb = new StringBuffer().append(tipoSecuencia).append(claveUno).append(claveDos);

            secuenciaActual = numeroAsuntoServices.obtenerSecuencia(sb.toString(), secuenciaActual);
            getLogger().debug(" secuenciaActual: {}" + secuenciaActual);

            secuencia = secuenciaActual.toString();
            secuenciaActual++;
            parametroSecuencia.setSecuencia(secuenciaActual);
            tramiteDAO.actualizarSecuenciaFolio(parametroSecuencia);
        }
        return secuencia;
    }
}
