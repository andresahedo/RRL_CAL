/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes;

/**
 * 
 * @author softtek
 * 
 */
public interface AdministracionConstantes {

    /**
     * Constante para la url de contexto
     */
    String CONTEXT_URL = "";

    /**
     * Constantes de la bandeja
     */
    String MENU_ADMIN = "/resources/pages/configurarusuarios/menuAdmin.jsf";
    String ASIGNAR_TRAMITES = "/resources/pages/configurarusuarios/asignarTramitesFuncionario.jsf";
    String ADMIN_TRAMITES_CONSULTA = "/resources/pages/configurarusuarios/administrarUsuariosUA.jsf";

    String BUSCAR_EMPLEADO = "/resources/pages/configurarusuarios/buscarEmpleado.jsf";
    String ADMINISTRAR_EMPLEADO = "/resources/pages/configurarusuarios/administrarEmpleados.jsf";

    String TIPO_PERSONA_MORAL = "TIPER.MR";
    String TIPO_PERSONA_FISICA = "TIPER.FI";

    String ADMIN_GLOBAL = "AdministradorGlobal";
    String ADMIN_UA = "AdministradorUA";

    String REASIGNAR_TAREA = "/resources/pages/configurarusuarios/reasignadorUsuarios/reasignarTarea.jsf";

}
