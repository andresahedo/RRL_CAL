package mx.gob.sat.siat.juridica.base.constantes;

public final class RolesConstantes {

    /**
     * Constantes para los procesos definidos para los roles de los
     * usuarioa
     */

    private RolesConstantes() {
        // Se coloca constructor privado para no instarciar esta clase
    }

    public static final String ROL_EMPLEADO_ABOGADO = "SAT_RRL_ABO_ACJ_ALJ";
    public static final String ROL_EMPLEADO_ADMINISTRADOR = "SAT_RRL_ADM_ACJ_ALJ";
    public static final String ROL_EMPLEADO_OFICIAL_PARTES = "SAT_OP_ADM_ACOJ_ALJ";
    public static final String ROL_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA = "SAT_USU_ADMUA_ACJ_ALJ";
    public static final String ROL_ADMINISTRADOR_GLOBAL = "SAT_USU_ADMG_ACJ_ALJ";

    public static final String ROL_INTERNO_EMPLEADO_ABOGADO = "abogado";
    public static final String ROL_INTERNO_EMPLEADO_ADMINISTRADOR = "administrador";
    public static final String ROL_INTERNO_EMPLEADO_OFICIAL_PARTES = "OficialDePartes";
    public static final String ROL_INTERNO_ADMINISTRADOR_UNIDAD_ADMINISTRATIVA = "AdministradorUA";
    public static final String ROL_INTERNO_ADMINISTRADOR_GLOBAL = "AdministradorGlobal";

}
