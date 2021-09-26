/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.util.exception.BaseBussinessException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author softtek
 * 
 */
public class MultiFormatReport extends AbstractJasperReportsSingleFormatView implements Serializable {
    private static final long serialVersionUID = 1503371890241180724L;

    /**
     * Mapa de parametros tipo privado
     */
    private Map<String, Object> parameters = new LinkedHashMap<String, Object>();

    /**
     * Reporte formato pdf
     */
    private ReportFormat reportFormat = ReportFormat.PDF;

    /**
     * Logueo tipo protegido
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     * @return parameters
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * 
     * @param parameters
     *            a fijar
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * 
     * @return reportFormat
     */
    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    /**
     * 
     * @param reportFormat
     *            a fijar
     */
    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     * 
     * @return buildReportFileName
     */
    public String getFilename() {
        return buildReportFileName(getBeanName(), reportFormat);
    }

    /**
     * Metodo de renderizado
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    public void render(final HttpServletRequest request, final HttpServletResponse response)
            throws BaseBussinessException {
        logger.debug("render()");

        configureReport();

        try {
            render(parameters, request, response);
        }
        catch (Exception ex) {

            throw new BaseBussinessException("Error en el render del reporte ", ex);
        }
    }

    /**
     * Metodo buildReportAsByteArray
     * 
     * @return
     * @throws JRException
     * @throws Exception
     */
    public byte[] buildReportAsByteArray() throws JRException, BaseBussinessException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            JasperReportsUtils.render(createExporter(), fillReport(getParameters()), outputStream);
        }
        catch (Exception ex) {

            throw new BaseBussinessException("Error en el render del reporte ", ex);
        }

        return outputStream.toByteArray();
    }

    /**
     * Metodo para crear la exportacion del formato del reporte
     */
    protected JRExporter createExporter() {
        JRExporter exporter = null;

        logger.debug("report format: [{}]", reportFormat);

        if (ReportFormat.CSV.equals(reportFormat)) {
            exporter = new JRCsvExporter();
        }
        else if (ReportFormat.HTML.equals(reportFormat)) {
            exporter = new JRHtmlExporter();
        }
        else if (ReportFormat.PDF.equals(reportFormat)) {
            exporter = new JRPdfExporter();
        }

        logger.debug("exporter: [{}]", exporter);

        return exporter;
    }

    /**
     * Metodo useWriter
     * 
     * @return boolean
     */
    protected boolean useWriter() {
        return false;
    }

    /**
     * Metodo que construye el formato reporte
     * 
     * @param beanName
     * @param reportFormat
     * @return
     */
    private String buildReportFileName(final String beanName, final ReportFormat reportFormat) {
        StringBuffer reportFileName = new StringBuffer(beanName);

        if (ReportFormat.CSV.equals(reportFormat)) {
            reportFileName.append(".csv");
        }
        else if (ReportFormat.HTML.equals(reportFormat)) {
            reportFileName.append(".html");
        }
        else if (ReportFormat.PDF.equals(reportFormat)) {
            reportFileName.append(".pdf");

        }
        return reportFileName.toString();
    }

    /**
     * Metodo que configura el reporte
     */
    private void configureReport() {
        setContentType(reportFormat.getContentType());

        final Properties headers = new Properties();

        headers.put("Content-Disposition", "attachment; filename=" + getFilename());

        setHeaders(headers);
    }

    /**
     * Metodo toString
     * 
     * @return "MultiFormatReport getFilename [{}]" + getFilename();
     */
    public String toString() {
        return "MultiFormatReport getFilename [{}]" + getFilename();
    }
}
