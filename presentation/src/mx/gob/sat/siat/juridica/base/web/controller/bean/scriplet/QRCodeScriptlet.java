package mx.gob.sat.siat.juridica.base.web.controller.bean.scriplet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class QRCodeScriptlet extends JRDefaultScriptlet {
    private static final String QR_CODE_VALUE_VARIABLE_NAME = "QR_CODE_VALUE";
    private static final String QR_CODE_WIDTH_VARIABLE_NAME = "QR_CODE_WIDTH";
    private static final String QR_CODE_HEIGHT_VARIABLE_NAME = "QR_CODE_HEIGHT";
    private static final String QR_CODE_IMAGE_VARIABLE_NAME = "QR_CODE_IMAGE";

    public void afterDetailEval() throws JRScriptletException {
        super.afterDetailEval();

        final String qrCodeValue = (String) this.getVariableValue(QR_CODE_VALUE_VARIABLE_NAME);

        if (null != qrCodeValue) {
            final int qrCodeWidth =
                    null == this.getVariableValue(QR_CODE_WIDTH_VARIABLE_NAME) ? NumerosConstantes.CIEN : (Integer) this
                            .getVariableValue(QR_CODE_WIDTH_VARIABLE_NAME);
            final int qrCodeHeight =
                    null == this.getVariableValue(QR_CODE_HEIGHT_VARIABLE_NAME) ? NumerosConstantes.CIEN : (Integer) this
                            .getVariableValue(QR_CODE_HEIGHT_VARIABLE_NAME);

            try {
                final Image qrCodeImage = buildQRCodeImage(qrCodeValue, qrCodeWidth, qrCodeHeight);

                this.setVariableValue(QR_CODE_IMAGE_VARIABLE_NAME, qrCodeImage);
            }
            catch (WriterException we) {
                throw new JRScriptletException(we);
            }
        }
    }

    private Image buildQRCodeImage(final String content, final int width, final int height) throws WriterException {
        final Map<EncodeHintType, Object> hints = new LinkedHashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        return MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,
                width, height, hints));
    }
}
