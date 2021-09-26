package mx.gob.sat.siat.juridica.nube.azure.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;

public class SimpleErrorHandler implements ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> errors;

    public SimpleErrorHandler() {
        errors = new ArrayList<String>();
    }

    public void warning(SAXParseException e) {
        errors.add(e.getMessage());
        logger.debug("[{}] : {}", e.getLineNumber(), e.getMessage());
    }

    public void error(SAXParseException e) {
        errors.add(e.getMessage());
        logger.debug("[{}] : {}", e.getLineNumber(), e.getMessage());
    }

    public void fatalError(SAXParseException e) {
        errors.add(e.getMessage());
        logger.debug("[{}] : {}", e.getLineNumber(), e.getMessage());
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean tieneErrores() {
        return !errors.isEmpty();
    }

    public Logger getLogger() {
        return this.logger;
    }
}
