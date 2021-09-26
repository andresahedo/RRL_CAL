/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes;

/**
 * 
 * @author softtek
 * 
 */
public enum UrlsAdminConstantes {

    /**
     * Enumeracion de tipo de BITACORA
     */
    INDEX("indexAdmin", "/resources/pages/configurarusuarios/indexAdmin.jsf"), SELECCION_UA("seleccionarUA",
            "/resources/pages/configurarusuarios/seleccionarUA.jsf"), ADMINISTRAR_USUARIOS_UA("administrarUsuariosUA",
            "/resources/pages/configurarusuarios/administrarUsuariosUA.jsf"),
    CONSULTA_ADMIN_USUARIOS("consultaAdminUsuarios", "/resources/pages/configurarusuarios/consultaAdminUsuarios.jsf"),
    CONSULTAR_EMPLEADO("consultarEmpleado", "/resources/pages/configurarusuarios/consultarEmpleado.jsf"),
    ADMINISTRAR_EMPLEADOS("administrarEmpleados", "/resources/pages/configurarusuarios/administrarEmpleados.xhtml"),
    MENU("menu", "/resources/pages/configurarusuarios/menuAdmin.jsf");

    /**
     * Atributo privado "clave" tipo String
     */
    private String clave;
    /**
     * Atributo privado "url" tipo String
     */
    private String url;

    /**
     * Constructor
     * 
     * @param clave
     * @param url
     */
    private UrlsAdminConstantes(final String clave, final String url) {
        this.clave = clave;
        this.url = url;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Metodo para parsear tipo EnumeracionParamtro
     * 
     * @param clave
     * @return right
     */
    public static UrlsAdminConstantes parse(String clave) {
        UrlsAdminConstantes right = null; 
        // Default

        for (UrlsAdminConstantes item : UrlsAdminConstantes.values()) {
            if (item.getClave().equals(clave)) {
                right = item;
                break;
            }
        }

        return right;
    }
}
