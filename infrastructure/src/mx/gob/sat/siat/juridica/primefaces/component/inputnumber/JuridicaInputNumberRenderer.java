package mx.gob.sat.siat.juridica.primefaces.component.inputnumber;

import org.primefaces.extensions.component.inputnumber.InputNumber;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class JuridicaInputNumberRenderer extends org.primefaces.extensions.component.inputnumber.InputNumberRenderer {
    private static final String INPUT_ATTRIBUTE = "input";
            private static final int QUINCE = 15;
            private static final int VEINTE = 20;
    public JuridicaInputNumberRenderer() {

    }

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
             {
        InputNumber inputNumber = (InputNumber) component;
        Converter converter = inputNumber.getConverter();
        String submittedValueString = (String) submittedValue;

        if (converter != null) {
            return converter.getAsObject(context, inputNumber, submittedValueString);
        }

        if ((submittedValueString != null) && (!submittedValueString.isEmpty())) {

            if ((inputNumber.getValue() instanceof BigDecimal)) {

                return new BigDecimal(submittedValueString);
            }

            return new BigDecimal(submittedValueString);
        }

        return null;
    }

    public void decode(FacesContext context, UIComponent component) {

        InputNumber inputNumber = (InputNumber) component;

        if ((inputNumber.isDisabled()) || (inputNumber.isReadonly())) {
            return;
        }

        decodeBehaviors(context, inputNumber);

        String inputId = inputNumber.getClientId(context) + "_hinput";

        String submittedValue = (String) context.getExternalContext().getRequestParameterMap().get(inputId);

        if (submittedValue != null) {
            inputNumber.setSubmittedValue(submittedValue);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        InputNumber inputNumber = (InputNumber) component;
        encodeMarkup(context, inputNumber);
        encodeScript(context, inputNumber);
    }

    protected void encodeMarkup(FacesContext context, InputNumber inputNumber) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String clientId = inputNumber.getClientId(context);

        String styleClass = inputNumber.getStyleClass();
        styleClass = "ui-inputNum ui-widget " + styleClass;

        writer.startElement("span", null);
        writer.writeAttribute("id", clientId, null);
        writer.writeAttribute("class", styleClass, "styleClass");

        encodeOutput(context, inputNumber, clientId);
        encodeInput(context, inputNumber, clientId);

        writer.endElement("span");
    }

    protected void encodeInput(FacesContext context, InputNumber inputNumber, String clientId) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String inputId = clientId + "_hinput";

        writer.startElement(INPUT_ATTRIBUTE, null);
        writer.writeAttribute("id", inputId, null);
        writer.writeAttribute("name", inputId, null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("autocomplete", "off", null);

        if (inputNumber.getOnchange() != null) {
            writer.writeAttribute("onchange", inputNumber.getOnchange(), null);
        }

        writer.endElement(INPUT_ATTRIBUTE);
    }

    protected void encodeOutput(FacesContext context, InputNumber inputNumber, String clientId) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String inputId = clientId + "_input";

        String defaultClass = "ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all";
        if(!inputNumber.isValid()){
            defaultClass = defaultClass + " ui-state-error";
        }

        writer.startElement(INPUT_ATTRIBUTE, null);
        writer.writeAttribute("id", inputId, null);
        writer.writeAttribute("name", inputId, null);
        writer.writeAttribute("type", "text", null);

        renderPassThruAttributes(context, inputNumber, HTML.INPUT_TEXT_ATTRS);

        
        if (inputNumber.isReadonly()) {
            writer.writeAttribute("readonly", "readonly", "readonly");
        }
        if (inputNumber.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", "disabled");
        }
        if (inputNumber.getStyle() != null) {
            writer.writeAttribute("style", inputNumber.getStyle(), "style");
        }

        writer.writeAttribute("class", defaultClass, "");

        writer.endElement(INPUT_ATTRIBUTE);
    }

    protected void encodeScript(FacesContext context, InputNumber inputNumber) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String clientId = inputNumber.getClientId(context);
        startScript(writer, clientId);
        String valueToRender = ComponentUtils.getValueToRender(context, inputNumber);
        if (valueToRender == null) {
            valueToRender = "";
        }

        writer.write("$(function() {");
        writer.write("PrimeFacesExt.cw('InputNumber','" + inputNumber.resolveWidgetVar() + "',{");
        writer.write("id:'" + clientId + "'");
        writer.write(",disabled:" + inputNumber.isDisabled());
        writer.write(",valueToRender:'" + formatForPlugin(valueToRender, inputNumber) + "'");

        String metaOptions = getOptions(inputNumber);
        if (!metaOptions.isEmpty()) {
            writer.write(",pluginOptions:" + metaOptions);
        }
        encodeClientBehaviors(context, inputNumber);
        writer.write("});});");

        endScript(writer);
    }

    private String getOptions(InputNumber inputNumber) {

        String decimalSeparator = inputNumber.getDecimalSeparator();
        String thousandSeparator = inputNumber.getThousandSeparator();
        String symbol = inputNumber.getSymbol();
        String symbolPosition = inputNumber.getSymbolPosition();
        String minValue = inputNumber.getMinValue();
        String maxValue = inputNumber.getMaxValue();
        String roundMethod = inputNumber.getRoundMethod();
        String decimalPlaces = inputNumber.getDecimalPlaces();
        String emptyValue = inputNumber.getEmptyValue();

        String options = "";
        options =
                options
                        + (decimalSeparator.isEmpty() ? "" : new StringBuilder().append("aDec: '")
                                .append(decimalSeparator).append("',").toString());

        options =
                options
                        + (thousandSeparator.isEmpty() ? "aSep:''," : new StringBuilder().append("aSep: '")
                                .append(thousandSeparator).append("',").toString());
        options =
                options
                        + (symbol.isEmpty() ? "" : new StringBuilder().append("aSign: '").append(symbol).append("',")
                                .toString());
        options =
                options
                        + (symbolPosition.isEmpty() ? "" : new StringBuilder().append("pSign: '")
                                .append(symbolPosition).append("',").toString());
        options =
                options
                        + (minValue.isEmpty() ? "" : new StringBuilder().append("vMin: '").append(minValue)
                                .append("',").toString());
        options =
                options
                        + (maxValue.isEmpty() ? "" : new StringBuilder().append("vMax: '").append(maxValue)
                                .append("',").toString());
        options =
                options
                        + (roundMethod.isEmpty() ? "" : new StringBuilder().append("mRound: '").append(roundMethod)
                                .append("',").toString());
        options =
                options
                        + (decimalPlaces.isEmpty() ? "" : new StringBuilder().append("mDec: '").append(decimalPlaces)
                                .append("',").toString());
        options = options + "wEmpty: '" + emptyValue + "',";

        if (options.isEmpty()) {
            return "";
        }

        int lastInd = options.length() - 1;
        if (options.charAt(lastInd) == ',') {
            options = options.substring(0, lastInd);
        }
        return "{" + options + "}";
    }

    private String formatForPlugin(String valueToRender, InputNumber inputNumber) {

        if ((valueToRender == null) || (valueToRender.isEmpty())) {
            return "";
        }
        try {
            Object objectToRender;

            objectToRender = new BigDecimal(valueToRender);

            NumberFormat formatter = new DecimalFormat("#0.0#");
            formatter.setRoundingMode(RoundingMode.UNNECESSARY);

            formatter.setMinimumFractionDigits(QUINCE);
            formatter.setMaximumFractionDigits(QUINCE);
            formatter.setMaximumIntegerDigits(VEINTE);
            String f = formatter.format(objectToRender);

            f = f.replace(',', '.');
            return f;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error converting  [" + valueToRender + "] to a double value;"
                    + inputNumber, e);
        }
    }

}
