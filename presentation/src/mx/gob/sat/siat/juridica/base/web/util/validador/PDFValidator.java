package mx.gob.sat.siat.juridica.base.web.util.validador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class PDFValidator {

    private final static int OK = 0;
    private final static int NO_OK = -1;
    private final static int NOMBRE_CARACTER_INVALIDO = -2;
    private final static String CHAR_SPLIT = "\\|\\|\\?";
    private static final int BLANK_THRESHOLD = 40;
    private final static int PDF_INVALIDO = 4;
    
    private Logger log = Logger.getLogger(PDFValidator.class);


    public int validateBlankPages(InputStream contenido) {
        try {
            PdfReader pdfr = new PdfReader(contenido);
            Document document = new Document(pdfr.getPageSizeWithRotation(1));
            document.open();
            for (int i = 1; i <= pdfr.getNumberOfPages(); i++) {
                PdfReaderContentParser parser = new PdfReaderContentParser(pdfr);
                parser.processContent(i, new ImageRenderListener());
                byte bContent[] = pdfr.getPageContent(i);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bs.write(bContent);
                bs.close();
                log.debug("i:"+ i + " bs:"+bs.size());
                if (bs.size() <= BLANK_THRESHOLD) {
                    return PDFValidator.NO_OK;
                }
            }
            document.close();
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
            return PDFValidator.PDF_INVALIDO;
        }
        return PDFValidator.OK;
    }

    public int nameValidator(String nombrePDF, String nombresGuardados) {
        if (nombrePDF.contains(CHAR_SPLIT)) {
            return PDFValidator.NOMBRE_CARACTER_INVALIDO;
        }
        Set<String> nombresValidar = new HashSet<String>();
        String[] nombresValidador = nombresGuardados.split(CHAR_SPLIT);
        for (String nombre : nombresValidador) {
            nombresValidar.add(nombre);
        }
        if (nombresValidar.contains(nombrePDF)) {
            return PDFValidator.NO_OK;
        }
        return PDFValidator.OK;

    }

    private class ImageRenderListener implements RenderListener {
        @Override
        public void beginTextBlock() {
        }
        @Override
        public void endTextBlock() {
        }
        @Override
        public void renderImage(ImageRenderInfo arg0) {
        }
        @Override
        public void renderText(TextRenderInfo arg0) {
        }
    }

}
