/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author softtek
 * 
 */
 @Component
 public class MultiFormatReportVU extends MultiFormatReport {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 4856263046420653356L;

    /**
     * Logueo tipo publico
     */
    private final transient  Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Arreglo de bytes de salida
     * 
     * @param urlJasper
     * @param parameters
     * @return
     */
    public ByteArrayOutputStream getByteArrayOutputStream(String urlJasper, Map<String, Object> parameters) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        logger.debug("UrlJasper:" + urlJasper);
        logger.debug("parameters:" + parameters);
        if (!parameters.containsKey(JRParameter.REPORT_LOCALE)) {
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
            logger.debug("parameters modificados:" + parameters);
        }
        Connection con = null;
        try {
            con = super.getJdbcDataSource().getConnection();
            JasperReport jasperCompileManager = JasperCompileManager.compileReport(urlJasper);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperCompileManager, parameters, con);
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
            exporter.exportReport();
        }
        catch (Exception ex) {
            logger.error("Error al generar reporte ", ex);
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    logger.error("Error al generar reporte ", e);
                }
            }
        }

        return baos;
    }

    /**
     * 
     * @param urlJasper
     *            a fijar
     * @return getByteArrayOutputStream
     */
    public ByteArrayOutputStream getByteArrayOutputStream(String urlJasper) {
        return getByteArrayOutputStream(urlJasper, getParameters());
    }

}
