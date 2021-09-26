/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.bpm.helper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.bpm.excepcion.XMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que sirve de apoyo para las clases que manejan XML
 * 
 * @author softtek
 * 
 */
@Component("XMLHelper")
public class XMLHelper extends BaseHelper {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 462487396049051184L;

    /**
     * Constante de firma
     */
    private static final String SIGNATURE_ELEMENT_NAME = "Signature";

            private static final int QUINIENTOS_DOCE = 2048;

            

    /**
     * Instancia para la lectura de archivos XML
     */
    private final transient XStream xstream = new XStream(new DomDriver());

    /**
     * Instancia para el registro de eventos
     */
    private final transient Logger logger = LoggerFactory.getLogger(XMLHelper.class);

    /**
     * Constructor de clase
     */
    protected XMLHelper() {
        super();

        xstream.autodetectAnnotations(true);
        xstream.setMode(XStream.ID_REFERENCES);

    }

    /**
     * Metodo para convertir el model a un archivo XML
     * 
     * @param data
     * @return
     */
    public String convertToXML(final BaseModel data) {
        return null == data ? "" : xstream.toXML(data);
    }

    /**
     * metodo para convertir una coleccion de Model a archivo XML
     * 
     * @param data
     * @return
     */
    public String convertToXML(final Collection<? extends BaseModel> data) {
        return null == data || data.isEmpty() ? "" : xstream.toXML(data);
    }

    /**
     * Metodo para convertir a XML
     * 
     * @param reader
     * @return
     */
    public String convertToXML(final XMLReader reader) {
        return null == reader ? "" : xstream.toXML(reader);
    }

    /**
     * Metodo para convertir a XML sin referencias
     * 
     * @param data
     * @return
     */
    public String convertToXMLWithNoReferences(final BaseModel data) {
        return null == data ? "" : getXStreamForNoReferences().toXML(data);
    }

    /**
     * Metodo para convertir a XML una coleccion de Model sin
     * referencias
     * 
     * @param data
     * @return
     */
    public String convertToXMLWithNoReferences(final Collection<? extends BaseModel> data) {
        return null == data ? "" : getXStreamForNoReferences().toXML(data);
    }

    /**
     * Metodo para convertir a XML sin referencias
     * 
     * @param reader
     * @return
     */
    public String convertToXMLWithNoReferences(final XMLReader reader) {
        return null == reader ? "" : getXStreamForNoReferences().toXML(reader);
    }

    /**
     * Metodo para convertir un XML a Model
     * 
     * @param xmlAsString
     * @return
     */
    public Object convertToModel(final String xmlAsString) {
        return null == xmlAsString || xmlAsString.trim().isEmpty() ? null : xstream.fromXML(xmlAsString);
    }

    /**
     * 
     * @return xstream
     */
    private XStream getXStreamForNoReferences() {
        final XStream xStream = new XStream();

        xStream.autodetectAnnotations(true);
        xStream.setMode(XStream.NO_REFERENCES);

        return xStream;
    }

    /**
     * Metodo para firmar
     * 
     * @param xmlToSign
     * @param certificate
     * @return
     * @throws XMLException
     */
    public String sign(final String xmlToSign, final X509Certificate certificate)  {
        String signedXML = "";

        try {
            signedXML = convertDOMToString(buildSignedDOM(xmlToSign, certificate));
        }
        catch (ParserConfigurationException pce) {
            throw new XMLException(pce);
        }
        catch (SAXException se) {
            throw new XMLException(se);
        }
        catch (IOException ioe) {
            throw new XMLException(ioe);
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new XMLException(nsae);
        }
        catch (InvalidAlgorithmParameterException iae) {
            throw new XMLException(iae);
        }
        catch (KeyException ke) {
            throw new XMLException(ke);
        }
        catch (MarshalException me) {
            throw new XMLException(me);
        }
        catch (XMLSignatureException xse) {
            throw new XMLException(xse);
        }
        catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        catch (TransformerException te) {
            throw new XMLException(te);
        }

        return signedXML;
    }

    /**
     * Meotodo para crear firma
     * 
     * @param xmlToSign
     * @param certificate
     * @return
     * @throws XMLException
     */
    public String generateSignature(final String xmlToSign, final X509Certificate certificate)  {
        String signature = "";

        try {
            signature = extractSignature(buildSignedDOM(xmlToSign, certificate));
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new XMLException(nsae);
        }
        catch (InvalidAlgorithmParameterException iape) {
            throw new XMLException(iape);
        }
        catch (KeyException ke) {
            throw new XMLException(ke);
        }
        catch (TransformerException te) {
            throw new XMLException(te);
        }
        catch (ParserConfigurationException pce) {
            throw new XMLException(pce);
        }
        catch (SAXException se) {
            throw new XMLException(se);
        }
        catch (IOException ioe) {
            throw new XMLException(ioe);
        }
        catch (MarshalException me) {
            throw new XMLException(me);
        }
        catch (XMLSignatureException xe) {
            throw new XMLException(xe);
        }

        return signature;
    }

    /**
     * Metodo para extraer la firma de un documento
     * 
     * @param document
     * @return
     * @throws TransformerException
     */
    private String extractSignature(final Document document) throws TransformerException {
        String signature = "";

        if (null != document && document.hasChildNodes()) {
            final NodeList nodeList = document.getDocumentElement().getElementsByTagName(SIGNATURE_ELEMENT_NAME);

            Node node;

            for (int index = 0; index < nodeList.getLength(); index++) {
                node = nodeList.item(index);

                signature = convertNodeToString(node);
            }
        }

        // Returns last signature!
        return signature;
    }

    /**
     * Metodo para generar un documento firmado
     * 
     * @param xmlToSign
     * @param certificate
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws KeyException
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    private Document buildSignedDOM(final String xmlToSign, final X509Certificate certificate)
            throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, KeyException, MarshalException, XMLSignatureException {
        final Document document = buildDocument(xmlToSign);

        generateXMLSignature(document, buildKeyPair(), certificate);

        return document;
    }

    /**
     * Metodo para crear un documento
     * 
     * @param xmlToSign
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document buildDocument(final String xmlToSign) throws ParserConfigurationException, SAXException,
            IOException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        documentBuilderFactory.setNamespaceAware(true);

        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        return documentBuilder.parse(new ByteArrayInputStream(xmlToSign.getBytes(Charset.forName("UTF-8"))));
    }

    /**
     * Meotodo para construir llaves
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    private KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(QUINIENTOS_DOCE);

        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Metodo para crear archivos XML firmados
     * 
     * @param document
     * @param keyPair
     * @param certificate
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws KeyException
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    private void
            generateXMLSignature(final Document document, final KeyPair keyPair, final X509Certificate certificate)
                    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyException,
                    MarshalException, XMLSignatureException {
        final XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");

        final Reference reference =
                xmlSignatureFactory.newReference("#CODEH",
                        xmlSignatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                        buildTransformations(xmlSignatureFactory), null, null);

        final SignedInfo signedInfo =
                xmlSignatureFactory.newSignedInfo(xmlSignatureFactory.newCanonicalizationMethod(
                        CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null), xmlSignatureFactory
                        .newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(reference));

        logger.debug("Signed info: [" + signedInfo + ']');

        final XMLSignature xmlSignature =
                xmlSignatureFactory
                        .newXMLSignature(signedInfo, buildKeyInfo(xmlSignatureFactory, keyPair, certificate));

        xmlSignature.sign(buildSigningContext(document, keyPair));
    }

    /**
     * Metodo para generar transformaciones
     * 
     * @param xmlSignatureFactory
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    private List<Transform> buildTransformations(XMLSignatureFactory xmlSignatureFactory)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        final List<Transform> transforms = new LinkedList<Transform>();

        transforms.add(xmlSignatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
        transforms.add(xmlSignatureFactory.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#WithComments",
                (TransformParameterSpec) null));

        return transforms;
    }

    /**
     * Meotodo para generar el contexto de firmado
     * 
     * @param document
     * @param keyPair
     * @return
     */
    private DOMSignContext buildSigningContext(final Document document, final KeyPair keyPair) {
        return new DOMSignContext(keyPair.getPrivate(), document.getDocumentElement());
    }

    /**
     * Metodo para generar la lalve de informacion
     * 
     * @param xmlSignatureFactory
     * @param keyPair
     * @param certificate
     * @return
     * @throws KeyException
     */
    private KeyInfo buildKeyInfo(final XMLSignatureFactory xmlSignatureFactory, final KeyPair keyPair,
            final X509Certificate certificate) throws KeyException {
        final List<XMLStructure> keyInformationTypes = new LinkedList<XMLStructure>();

        final KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();

        final KeyValue keyValue = keyInfoFactory.newKeyValue(keyPair.getPublic());

        final X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));

        keyInformationTypes.add(keyValue);
        keyInformationTypes.add(x509Data);

        return keyInfoFactory.newKeyInfo(keyInformationTypes);
    }

    /**
     * Metodo para convertir documentos a String
     * 
     * @param document
     * @return
     * @throws TransformerException
     */
    private String convertDOMToString(final Document document) throws TransformerException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();

        final Transformer transformer = transformerFactory.newTransformer();

        transformer.transform(new DOMSource(document), new StreamResult(outputStream));

        try {
            return new String(outputStream.toByteArray(), "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("Error de encoding", e);
            throw new TransformerException(e);
        }
    }

    /**
     * Metodo para convertir Nodos a String
     * 
     * @param node
     * @return
     * @throws TransformerException
     */
    private String convertNodeToString(final Node node) throws TransformerException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();

        final Transformer transformer = transformerFactory.newTransformer();

        transformer.transform(new DOMSource(node), new StreamResult(outputStream));

        try {
            return new String(outputStream.toByteArray(), "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("Error de encoding", e);
            throw new TransformerException(e);
        }
    }
}
