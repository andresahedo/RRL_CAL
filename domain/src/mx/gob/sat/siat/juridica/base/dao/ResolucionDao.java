/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.*;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface ResolucionDao {

    /**
     * Metodo para crear una resolucion
     * 
     * @param resolucion
     * @return Resolucion
     */
    Resolucion crearResolucion(Resolucion resolucion);

    /**
     * Metodo para obtener una resolucion por id
     * 
     * @param idResolucion
     * @return Resolucion
     */
    Resolucion obtenerResolucion(Long idResolucion);

    /**
     * Metodo para obtener una resolucion impugnada por id
     * 
     * @param idResolucionImpugnada
     * @return Resolucion Impugnada
     */
    ResolucionImpugnada obtenerResolucionImpugnada(Long idResolucionImpugnada);

    /**
     * Metodo para crear una resolucion impugnada
     * 
     * @param resolucion
     * @return Resolucion Impugnada
     */
    ResolucionImpugnada crearResolucionImpugnada(ResolucionImpugnada resolucion);

    /**
     * Metodo para modificar una resolucion
     * 
     * @param resolucion
     */
    void modificarResolucion(Resolucion resolucion);

    /**
     * Metodo para modificar una resolucion impugnada
     * 
     * @param resolucion
     */
    void modificarResolucionImpugnada(ResolucionImpugnada resolucion);

    /**
     * Metodo para crear una resolucion de catalogo D
     * 
     * @param resolucionCatalogoD
     * @return ResolucionCatalogoD
     */
    ResolucionCatalogoD crearResolucionCatalogoD(ResolucionCatalogoD resolucionCatalogoD);

    /**
     * Metodo para modificar una resolucion de catalogo D
     * 
     * @param resolucionCatalogoD
     */
    void modificarResolucionCatalogoD(ResolucionCatalogoD resolucionCatalogoD);

    /**
     * Metodo para crear una resolucion impugnada de catalogo D
     * 
     * @param conceptoDetalle
     * @return Resolucion Impugnada Catalogo D
     */
    ResolucionImpugnadaCatalogoD crearResolucionImpugnadaCatalogoD(ResolucionImpugnadaCatalogoD conceptoDetalle);

    /**
     * Metodo para modificar una resolucion impugnada de catalogo D
     * 
     * @param conceptoDetalle
     */
    void modificarResolucionImpugnadaCatalogoD(ResolucionImpugnadaCatalogoD conceptoDetalle);

    /**
     * Metodo para obtener una resolucion por id de Tramite
     * 
     * @param idTramite
     * @return Resolucion
     */
    Resolucion obtenerResolucionIdTramite(String idTramite);

    /**
     * Metodo para obtener una una lista de resoluciones impugnadas
     * por id de resolucion
     * 
     * @param idResolucion
     * @return Lista de resoluciones impugnadas
     */
    List<ResolucionImpugnada> obtenerResolucionesImpugnadas(Long idResolucion);

    /**
     * Metodo para obtener una resolucion de catalogo D por id de
     * resolucion impugnada
     * 
     * @param idResolucionImpugnada
     * @return Resoluciones Impugnada
     */
    ResolucionImpugnada obtenerResolucionCatalogoDPorId(Long idResolucionImpugnada);

    /**
     * Metodo para obtener una lista de resoluciones de catalogo D por
     * id de resolucion
     * 
     * @param idResolucion
     * @return Lista de resoluciones de catalogo D
     */
    List<ResolucionCatalogoD> obtenerResolucionesCatalogoD(Long idResolucion);

    /**
     * Metodo para obtener unalista de detalles de conceptos por id de
     * resolucion impugnada
     * 
     * @param idResolucionImpugnada
     * @return Lista de ConceptoDetalle
     */
    List<ConceptoDetalle> obtenerConceptosPorIdImpugnada(Long idResolucionImpugnada);

    /**
     * Metodo para obtener una lista de detalles de agravios por id de
     * resolucion impugnada
     * 
     * @param idResolucionImpugnada
     * @return Lista de Agravio detalle
     */
    List<AgravioDetalle> obtenerAgraviosPorIdImpugnada(Long idResolucionImpugnada);

    /**
     * Metodo para eliminar resoluciones impugnadas por id
     * 
     * @param idsResolucionesImpugnadas
     *            , fechaBaja
     */
    void eliminarResolucionesImpugnadasDetallePorIdsImpugnadas(String idsResolucionesImpugnadas, Date fechaBaja);

    /**
     * Metodo para eliminar resoluciones impugnadas por id de
     * resolucion
     * 
     * @param idResolucion
     *            , fechaBaja
     */
    void eliminarResolucionesImpugnadasPorIdResolucion(Long idResolucion, Date fechaBaja);

    /**
     * Metodo para eliminar resoluciones de detalle por id de
     * resolucion
     * 
     * @param idResolucion
     *            , fechaBaja
     */
    void eliminarResolucionesDetallePorIdResolucion(Long idResolucion, Date fechaBaja);

    /**
     * Metodo para obtener la ultima resolucion de tramite
     * 
     * @param numeroAsunto
     * @return Resolucion
     */
    Resolucion obtenerUltimaResolucionPorTramite(String numeroAsunto);

}
