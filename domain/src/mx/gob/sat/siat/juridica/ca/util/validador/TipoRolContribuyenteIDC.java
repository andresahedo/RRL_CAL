package mx.gob.sat.siat.juridica.ca.util.validador;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;

public class TipoRolContribuyenteIDC extends BaseControllerBean {
    private static final long serialVersionUID = 1L;

    private InputStream resultStream;
    private String resultString;
    private ArrayList<String> rolHidrocarburos;
    private String estadoContribuyente = "";
    private String URL = null;
    private String usuario = null;
    private String password = null;

    public TipoRolContribuyenteIDC() {

    }

    public String consultaHidrocarburos(String rfcUsuario) throws IOException {
        String tipoRolContribuyente = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        rolHidrocarburos = new ArrayList<String>();
        StringEntity se = null;
        try {
            se = new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tns0=\"http://www.sat.gob.mx/IdCInterno/messages\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                    + "<soapenv:Body>\n" + "<tns0:datosEntrada>\n" + "\t<rfc>" + rfcUsuario + "</rfc>\n" + "\t<curp>"
                    + "" + "</curp>\n" + "\t<nit>" + "" + "</nit>\n" + "\t<secciones>Roles</secciones>\n"
                    + "\t<secciones>Identificacion</secciones>\n"
                    + "\t<seccionesHistoricas>Roles</seccionesHistoricas>\n" + "\t<id_sucursal>" + ""
                    + "</id_sucursal>\n" + "\t<usuario>" + usuario + "</usuario>\n" + "\t<password>" + password
                    + "</password>\n" + "\t<t_relacion>" + "" + "</t_relacion>\n" + "\t<t_ubicacion>" + ""
                    + "</t_ubicacion>\n" + "\t<ef_ubic_especifica>" + "" + "</ef_ubic_especifica>\n"
                    + "</tns0:datosEntrada>\n" + "</soapenv:Body>\n" + "</soapenv:Envelope>");
            httppost.setEntity(se);
            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
            HttpResponse httpResponse = httpclient.execute(httppost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();

            if (responseCode == NumerosConstantes.DOSCIENTOS) {
                resultStream = httpResponse.getEntity().getContent();
                resultString = inputStreamToString(resultStream);

                try {
                    parseXML(resultString);
                    tipoRolContribuyente = determinaTipoRolContribuyente();
                } catch (Exception e) {
                    getLogger().debug("Error, no se pudo accesar al servicio");

                }
            } else {
                getLogger().debug("Error - Codigo:" + responseCode);
            }

        } catch (UnsupportedEncodingException e1) {
            getLogger().debug("Error UnsupportedEncodingException");

        } catch (ClientProtocolException e) {
            getLogger().debug("Error, ClientProtocolException");

        } catch (IOException e) {
            getLogger().debug("Error, IOException");

        }
        return tipoRolContribuyente;

    }

    public void parseXML(String xml) {
        try {
            
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setExpandEntityReferences(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(xml.getBytes()));
            doc.getDocumentElement().normalize();
            NodeList identificacionList = doc.getElementsByTagName("Identificacion");
            for (int temp = 0; temp < identificacionList.getLength(); temp++) {

                Node nNode = identificacionList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    this.setEstadoContribuyente(eElement.getElementsByTagName("d_Sit_Cont").item(0).getTextContent());
                }
            }

            NodeList nList = doc.getElementsByTagName("Roles");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    this.rolHidrocarburos.add(eElement.getElementsByTagName("c_Rol").item(0).getTextContent());

                }
            }

        } catch (ParserConfigurationException e) {

            getLogger().debug("ParserConfigurationException");
        } catch (SAXException e) {

            getLogger().debug("SAXException");
        } catch (IOException e) {

            getLogger().debug("IOException");
        } catch (Exception e) {
            getLogger().error(e.getMessage());

        }

    }

    public String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            getLogger().debug("Error de InputStreamReader");
        }
        return total.toString();

    }

    public String determinaTipoRolContribuyente() {

        String GRAN_CONTRIBUYENTE_PM = "300025";

        String SOC_MERCANTIL_CONTROLADORA = "300063";
        String SOC_CONTROL_DE_GRUPOS_FINANC = "300081";
        String CONTRIBUYENTE_DIVERSO = "300154";
        String EST_EXTRANJ_ORG_INTERNACIONAL = "300166";
        String EXTRANJ_LINEAS_AEREAS_EXTRANJ = "300201";
        String EXTRANJ_RESID_EN_EXTRANJ_PM = "300203";
        String RESID_EXTRANJ_C_EST_PERM_PM = "300204";
        String GRAN_CONTRIBUYENTE_PF = "300236";
        String LOS_ESTADOS_EXTRANJEROS = "300242";
        String SECTOR_FINANCIERO = "300252";
        String SF_BANCA_DE_DESARROLLO = "300254";
        String SF_BANCA_MULTIPLE = "300255";
        String SF_BANCO_DE_MEXICO = "300256";
        String SF_BOLSA_DE_VALORES = "300257";
        String SF_CASA_DE_BOLSA = "300259";
        String SF_FONDOS_FINANCIEROS = "300264";
        String SF_GRUPOS_FINANCIEROS_NACION = "300266";
        String SF_INMOBILIARIAS_FINANCIERAS = "300267";
        String SF_INSTITUCION_DE_FIANZAS = "300268";
        String SF_INSTITUCION_DE_SEGUROS = "300269";
        String SF_INST_P_DEPOSITO_DE_VALORES = "300274";
        String SF_COM_NAC_SEGUROS_Y_FIANZAS = "300279";
        String SF_FILIALES_DE_BANCOS_EXTRANJ = "300281";
        String SF_OTRA_ENTIDAD_O_INTER_FINANC = "300284";
        String SF_REP_DE_LA_BANCA_EXTRANJERA = "300285";
        String SOCIEDAD_MERCANTIL_CONTROLADA = "300293";
        String EXTRANJ_RESID_EN_EXTRANJ_PF = "300310";
        String SERVS_PUBL_RADIC_EN_EXTR = "300434";
        String SF_SOFOM_ER = "300437";
        String SEDES_DIPLOMATICAS_Y_OTRAS_UNI = "300455";
        String EXTRANJ_C_RESIDENCIA_EN_MEX_PF = "300495";
        String EXTRANJ_C_RESIDENCIA_EN_MEX_PM = "300497";
        String EXT_S_EST_PERM_C_RIQ_EN_MEX_PM = "300498";
        String INTEGRADORA_PM = "300531";
        String INTEGRADA_PM = "300532";
        String GC_ACFECF_PM = "300544";
        String EMPRESA_PRODUCTIVA_DEL_ESTADO = "300604";
        /** Subrol Hidrocarburos */
        String AGH_ACFH_PM = "300557";
        String AGH_ACFH_PF = "300558";
        String AGH_ACVH_PM = "300559";
        String AGH_ACVH_PF = "300560";

        int tipoRolHidrocarburos = 0;
        int tipoRolGranContribuyente = 0;

        for (int i = 0; i < this.rolHidrocarburos.size(); i++) {

            if (this.rolHidrocarburos.get(i).equals(AGH_ACFH_PM)) {
                tipoRolHidrocarburos = 1;
            } else if (this.rolHidrocarburos.get(i).equals(AGH_ACFH_PF)) {
                tipoRolHidrocarburos = 1;
            } else if (this.rolHidrocarburos.get(i).equals(AGH_ACVH_PM)) {
                tipoRolHidrocarburos = 1;
            } else if (this.rolHidrocarburos.get(i).equals(AGH_ACVH_PF)) {
                tipoRolHidrocarburos = 1;
            } else if (this.rolHidrocarburos.get(i).equals(GRAN_CONTRIBUYENTE_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SOC_MERCANTIL_CONTROLADORA)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SOC_CONTROL_DE_GRUPOS_FINANC)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(CONTRIBUYENTE_DIVERSO)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EST_EXTRANJ_ORG_INTERNACIONAL)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXTRANJ_LINEAS_AEREAS_EXTRANJ)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXTRANJ_RESID_EN_EXTRANJ_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(RESID_EXTRANJ_C_EST_PERM_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(GRAN_CONTRIBUYENTE_PF)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(LOS_ESTADOS_EXTRANJEROS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SECTOR_FINANCIERO)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_BANCA_DE_DESARROLLO)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_BANCA_MULTIPLE)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_BANCO_DE_MEXICO)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_BOLSA_DE_VALORES)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_CASA_DE_BOLSA)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_FONDOS_FINANCIEROS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_GRUPOS_FINANCIEROS_NACION)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_INMOBILIARIAS_FINANCIERAS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_INSTITUCION_DE_FIANZAS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_INSTITUCION_DE_SEGUROS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_INST_P_DEPOSITO_DE_VALORES)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_COM_NAC_SEGUROS_Y_FIANZAS)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_FILIALES_DE_BANCOS_EXTRANJ)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_OTRA_ENTIDAD_O_INTER_FINANC)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_REP_DE_LA_BANCA_EXTRANJERA)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SOCIEDAD_MERCANTIL_CONTROLADA)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXTRANJ_RESID_EN_EXTRANJ_PF)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SERVS_PUBL_RADIC_EN_EXTR)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SF_SOFOM_ER)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(SEDES_DIPLOMATICAS_Y_OTRAS_UNI)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXTRANJ_C_RESIDENCIA_EN_MEX_PF)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXTRANJ_C_RESIDENCIA_EN_MEX_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EXT_S_EST_PERM_C_RIQ_EN_MEX_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(INTEGRADORA_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(INTEGRADA_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(GC_ACFECF_PM)) {
                tipoRolGranContribuyente = 1;
            } else if (this.rolHidrocarburos.get(i).equals(EMPRESA_PRODUCTIVA_DEL_ESTADO)) {
                tipoRolGranContribuyente = 1;
            }
        }

        if (tipoRolHidrocarburos == 1) {
            return ProcesosConstantes.HIDROCARBUROS;
        } else if (tipoRolGranContribuyente == 1) {
            return ProcesosConstantes.GRAN_CONTRIBUYENTE;
        } else {
            return "";
        }

    }

    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
