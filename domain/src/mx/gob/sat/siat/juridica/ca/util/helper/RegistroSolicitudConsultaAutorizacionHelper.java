/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.util.helper;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Catalogo;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.base.util.constants.CommonConstants;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Component
public class RegistroSolicitudConsultaAutorizacionHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -9055885499392462912L;

    /**
     * Metodo para obtener una lista de catalogos
     * 
     * @return lista
     */
    public List<Catalogo> obtenerCatalogoSiNo() {
        List<Catalogo> lista = new ArrayList<Catalogo>();
        lista.add(new Catalogo("1", CommonConstants.SI));
        lista.add(new Catalogo("0", CommonConstants.NO));
        return lista;
    }

    public List<Catalogo> obtenerTipoPersona() {
        List<Catalogo> lista = new ArrayList<Catalogo>();
        lista.add(new Catalogo("0", "Persona F\u00EDsica"));
        lista.add(new Catalogo("1", "Persona Moral"));
        return lista;
    }

    /**
     * Metodo para descargar un archivo
     * 
     * @param ruta
     * @return InputStream
     */
    public InputStream descargarArchivo(String ruta) {
        InputStream input = null;
        try {
            File file = new File(ruta);
            input = new FileInputStream(file);
            return input;
        }
        catch (FileNotFoundException e) {
            getLogger().error("", e);
        }
        return input;
    }

    /**
     * Metodo para generar la cadena originar
     * 
     * @param solicitud
     * @param fechaFirma
     * @return String
     */
    public String generarCadenaOriginal(SolicitudConsultaAutorizacion solicitud, Date fechaFirma) {
        return "|tramite2|cadena|original|";
    }

}
