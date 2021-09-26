package mx.gob.sat.siat.juridica.base.dao.domain.constants;

public enum TipoParametroSincronizacion {

    ULTIMA_FECHA_SINCRONIZACION("TPSINC.UF", "La \u00FAltima fecha de sincronizaci\u00F3n"),
    PERMITE_SINCRONIZACION("TPSINC.PS", "Permite ejecurar la sincronizaci\u00F3n");

    private String id;
    private String descripcion;

    private TipoParametroSincronizacion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static TipoParametroSincronizacion parse(String id) {
        for (TipoParametroSincronizacion tipo : TipoParametroSincronizacion.values()) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }
        return null;
    }

}
