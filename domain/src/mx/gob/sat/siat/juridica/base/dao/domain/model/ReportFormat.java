/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

/**
 * 
 * @author softtek
 * 
 *         Enumeracion de formatos de los reportes
 */
public enum ReportFormat {
    PDF("pdf", "Portable Document Format (PDF)", "application/pdf"), EXCEL("xls",
            "Excel (XLS)",
            "application/vnd.ms-excel"), HTML("html", "HyperText Markup Language (HTML)", "text/html"), CSV("csv",
            "Comma-separated values (CSV)",
            "text/plain"), RTF("rtf", "Rich-Text Format (RTF)", "application/msword"), TXT("txt",
            "Plain text (TXT)",
            "text/plain"), XLSX("xlsx", "XLSX", "aapplication/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    /**
     * Atributo privado "id" tipo String
     */
    private String id;

    /**
     * Atributo privado "description" tipo String
     */
    private String description;

    /**
     * Atributo privado "contentType" tipo String
     */
    private String contentType;

    /**
     * Formato para el reporte
     * 
     * @param id
     * @param description
     * @param contentType
     */
    ReportFormat(final String id, final String description, final String contentType) {
        this.id = id;
        this.description = description;
        this.contentType = contentType;
    }

    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Construccion del formulario para el reporte
     * 
     * @param id
     * @return result
     */
    public static ReportFormat buildFromId(final String id) {
        ReportFormat result = null;

        if (null != id && !id.trim().isEmpty()) {
            for (final ReportFormat reportFormat : ReportFormat.values()) {
                if (reportFormat.getId().equalsIgnoreCase(id)) {
                    result = reportFormat;
                }
            }
        }

        if (null == result) {
            throw new IllegalArgumentException(id);
        }

        return result;
    }
}
