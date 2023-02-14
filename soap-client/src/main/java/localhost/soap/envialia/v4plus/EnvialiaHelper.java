package localhost.soap.envialia.v4plus;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import localhost.soap.envialia.pojo.ConsEnvEstadosPojo;
import localhost.soap.envialia.pojo.ConsEnvEstadosResponsePojo;
import localhost.soap.envialia.pojo.ConsEnvio2Pojo;
import localhost.soap.envialia.pojo.ConsEnvio2ResponsePojo;
import localhost.soap.envialia.pojo.LoginDep2Pojo;
import localhost.soap.envialia.pojo.LoginDep2ResponsePojo;

public class EnvialiaHelper <Q,S> {
    
    private static final String urlLoginDep2 = "http://ws.envialia.com/SOAP?service=LoginService";
    private static final String urlConsEnvio2 = "http://ws.envialia.com/SOAP?service=WebService";
    private static final String urlConsEnvEstados = "http://ws.envialia.com/SOAP?service=WebService";
    
    private static final String soapActionLoginDep2 = "urn:envialianet-LoginWSService#LoginDep2";
    private static final String soapActionConsEnvio2 = "urn:envialianet-WebServService#ConsEnvio2";
    private static final String soapActionConsEnvEstados = "urn:envialianet-WebServService#ConsEnvEstados";

    private static Logger log = LogManager.getLogger(EnvialiaHelper.class);
    
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

	private Q requestPojo;
    private S responsePojo;
    
    private String requestSoapBodyXml;
    private String responseSoapBodyXml;
    
    private String idSessionHeader;

	private String vCodAgeOri;
    private String vCodAgeCargo;
    private String dFechaHoraAlta;
    
    
    
    public EnvialiaHelper (String idSessionHeader, Q pojoRequest, S pojoResponse) throws Exception {
    	super();
    	this.requestPojo = pojoRequest;
    	this.responsePojo = pojoResponse;
    	this.idSessionHeader = idSessionHeader;
    	
    	/* */
    	if ( ( pojoRequest instanceof LoginDep2Pojo ) && !( pojoResponse instanceof LoginDep2ResponsePojo )  ) {
    		throw new Exception("LoginDep2Pojo must be instantiated with LoginDep2ResponsePojo");
    	} else if ( ( pojoRequest instanceof ConsEnvio2Pojo ) && !( pojoResponse instanceof ConsEnvio2ResponsePojo )  ) {
    		throw new Exception("ConsEnvio2Pojo must be instantiated with ConsEnvio2ResponsePojo");
    	} else if ( ( pojoRequest instanceof ConsEnvEstadosPojo ) && !( pojoResponse instanceof ConsEnvEstadosResponsePojo )  ) {
    		throw new Exception("ConsEnvEstadosPojo must be instantiated with ConsEnvEstadosResponsePojo");
    	}
    	
    	
    	initialize();
    }
    
    
    
    public EnvialiaHelper () {
    	super();
    	initialize();
    }
    
    
    
    public void initialize() {
    	
    	this.requestSoapBodyXml = null;
    	this.responseSoapBodyXml = null;
    	
    	this.vCodAgeOri = null;
    	this.vCodAgeOri = null;
    	this.dFechaHoraAlta = null;
        
        // webServiceTemplate, messageSender
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        webServiceTemplate.setMessageSender( httpComponentsMessageSender );
        
        // webServiceTemplate, set interceptors to print http request headers 
        ClientInterceptor[] interceptors = webServiceTemplate.getInterceptors();
        interceptors = (ClientInterceptor[]) ArrayUtils.add(interceptors, new EnvialiaWSTInterceptor());
        webServiceTemplate.setInterceptors(interceptors);
    	
    }

    
	
    public void setIdSessionHeader(String idSessionHeader) {
		this.idSessionHeader = idSessionHeader;
	}

	public void setRequestPojo(Q pojoRequest) {
		this.requestPojo = pojoRequest;
	}

	public void setResponsePojo(S pojoResponse) {
		this.responsePojo = pojoResponse;
	}

	
	
	public String generateRequestSoapBodyXml () {
		
    	JAXBContext jaxbContext = null;
    	Marshaller marshaller = null;
    	StringWriter stringWriter = null;
		
		try {
			
			jaxbContext = JAXBContext.newInstance(requestPojo.getClass());   
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			stringWriter = new StringWriter();
			marshaller.marshal(requestPojo, stringWriter);
			requestSoapBodyXml = stringWriter.toString();

		} catch (Exception e) {
			log.error("e: {}", e.getMessage());
		}
		
		return (String) requestSoapBodyXml;
		
	}
	
    
    public String generateResponseSoapBodyXml() {
    	try {
    		
    		final String soapAction;
    		
    	    // define url and soapAction
    	    if (this.requestPojo instanceof LoginDep2Pojo) {
    	    	webServiceTemplate.setDefaultUri(urlLoginDep2);
    	    	soapAction = soapActionLoginDep2;
    	    } else if (this.requestPojo instanceof ConsEnvio2Pojo) {
    	    	webServiceTemplate.setDefaultUri(urlConsEnvio2);
    	    	soapAction = soapActionConsEnvio2;
    	    } else if (this.requestPojo instanceof ConsEnvEstadosPojo) {
    	    	webServiceTemplate.setDefaultUri(urlConsEnvEstados);
    	    	soapAction = soapActionConsEnvEstados;
    	    } else {
    	    	webServiceTemplate.setDefaultUri(null);
    	    	soapAction = null;
    	    }
    	    
    		// transformer factory
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
    		transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
    		
    		// message to send
    		StringReader stringReaderForSource = new StringReader(requestSoapBodyXml);
    		StringReader stringReaderForSourceToLog = new StringReader(requestSoapBodyXml);
        	StreamSource streamSource = new StreamSource(stringReaderForSource);
            
            // message to receive
    	    StringWriter writerForResult = new StringWriter();
    	    StreamResult streamResult = new StreamResult(writerForResult);

            // xml mapper
        	XmlMapper xmlMapper = new XmlMapper();
    		JaxbAnnotationModule module = new JaxbAnnotationModule();
    		xmlMapper.registerModule(module);

            
            // logging reference, url
            log.info("url: {}", webServiceTemplate.getDefaultUri());

            // logging reference, soapAction
            log.info("soapAction: {}", soapAction);
            
            // logging reference, soapAction
            log.info("idSessionHeader: {}", idSessionHeader);
            
            // logging reference, reader buffer
            Scanner scanner = null;
            String sRFSToLogStr = null;
            try {
                scanner = new Scanner(stringReaderForSourceToLog);
    			scanner.useDelimiter("\\A");
                sRFSToLogStr = scanner.hasNext() ? scanner.next() : "";
                scanner.close();
                log.info("requestSoapBodyXml: {}", sRFSToLogStr);
            } catch (Exception e) {
            	log.error("e: {}", e.getMessage());
            }
            
            
            // execute soap service call
            responseSoapBodyXml = (String) webServiceTemplate.sendAndReceive(
        		new WebServiceMessageCallback() {
    	            public void doWithMessage(WebServiceMessage webServiceMessage) {
    	            	
    	            	// soap action
    	            	SoapMessage senderSoapMessage = (SoapMessage) webServiceMessage; // debug
    	            	senderSoapMessage.setSoapAction(soapAction);
    	            	
    	            	
    	            	// debug, begin
    	            	Document documentDebug_Ini = senderSoapMessage.getDocument(); 
    	            	DOMSource domSourceDebug_Ini = new DOMSource(documentDebug_Ini);
    	        	    StringWriter stringWriterForResultDebug_Ini = new StringWriter();
    	        	    StreamResult streamResultDebug_Ini = new StreamResult(stringWriterForResultDebug_Ini);
    	        	    try {
    	        	    	transformer.transform(domSourceDebug_Ini, streamResultDebug_Ini);
    	        	    } catch (Exception e) {
    	        	    	log.error("e: {}", e.getMessage());
    	        	    }
    	        	    log.info("debug, sender, documentDebug_Ini: {}", stringWriterForResultDebug_Ini.toString());
    	        	    // debug, end

    	        	    
    	        	    // populate soap-body
    	            	Result senderResult = webServiceMessage.getPayloadResult();
    	            	try {
    	            		transformer.transform(streamSource, senderResult);	
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}

    	            	
    	            	// required vars, begin
	            		SaajSoapMessage senderSaajSoapMessage = (SaajSoapMessage) webServiceMessage;
    	            	SOAPMessage senderSOAPMessage = senderSaajSoapMessage.getSaajMessage();
    	            	SOAPPart senderSOAPPart = senderSOAPMessage.getSOAPPart();
    	            	
    	            	SOAPEnvelope senderSOAPEnvelope = null;
    	            	SOAPBody senderSOAPBody = null;
    	            	SOAPHeader senderSOAPHeader = null;
    	            	try {
    	            		senderSOAPMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
    	            		senderSOAPEnvelope = senderSOAPPart.getEnvelope();
    	            		senderSOAPHeader = senderSOAPMessage.getSOAPHeader();
    	            		senderSOAPBody = senderSOAPMessage.getSOAPBody();
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}
    	            	
    	            	// soap-envelope    	            	
    	            	try {
        	            	senderSOAPEnvelope.addNamespaceDeclaration("tem", "http://tempuri.org");
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}
    	            	
    	            	// soap-header
    	            	try {
        	            	SOAPElement roClientIDHeaderHSE = senderSOAPHeader.addChildElement("ROClientIDHeader","tem");  // "ROClientIDHeader"
        	            	SOAPElement idHSE = roClientIDHeaderHSE.addChildElement("ID", "tem");
        	            	if ( idSessionHeader != null && !idSessionHeader.isEmpty() ) {
        	            		idHSE.addTextNode(idSessionHeader);
        	            	}
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}
    	            	
	            		// soap-body    	            	
    	            	try {
    	            		Node bodyNode = senderSOAPBody.getFirstChild();
    	            		bodyNode.setPrefix("tem");
    	            		Node bodyNodeChild = bodyNode.getFirstChild();
    	            		while ( bodyNodeChild != null  ) {
    	            			bodyNodeChild.setPrefix("tem");
    	            			bodyNodeChild = bodyNodeChild.getNextSibling();
    	            		}
    	            		
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}
    	            	
    	            	// debug, begin
    	            	Document documentDebug_Fin = senderSoapMessage.getDocument(); 
    	            	DOMSource domSourceDebug_Fin = new DOMSource(documentDebug_Fin);
    	        	    StringWriter stringWriterForResultDebug_Fin = new StringWriter();
    	        	    StreamResult streamResultDebug_Fin = new StreamResult(stringWriterForResultDebug_Fin);
    	        	    try {
    	        	    	transformer.transform(domSourceDebug_Fin, streamResultDebug_Fin);
    	        	    } catch (Exception e) {
    	        	    	log.error("e: {}", e.getMessage());
    	        	    }
    	        	    log.info("debug, sender, documentDebug_Fin: {}", stringWriterForResultDebug_Fin.toString());
    	        	    // debug, end

    	            	
    	            	return;
    	            }
        		},
                new WebServiceMessageExtractor<Object>() {
                    public Object extractData(WebServiceMessage webServiceMessage) throws IOException {
                    	
                    	SoapMessage receiverSoapMessage = (SoapMessage) webServiceMessage; // debug 
                    	
    	            	// debug, begin
    	            	Document documentDebug_Ini = receiverSoapMessage.getDocument(); 
    	            	DOMSource domSourceDebug_Ini = new DOMSource(documentDebug_Ini);
    	        	    StringWriter stringWriterForResultDebug_Ini = new StringWriter();
    	        	    StreamResult streamResultDebug_Ini = new StreamResult(stringWriterForResultDebug_Ini);
    	        	    try {
    	        	    	transformer.transform(domSourceDebug_Ini, streamResultDebug_Ini);
    	        	    } catch (Exception e) {
    	        	    	log.error("e: {}", e.getMessage());
    	        	    }
    	        	    log.info("debug, receiver, documentDebug_Ini: {}", stringWriterForResultDebug_Ini.toString());
    	        	    // debug, end
                    	
                        // do your own transforms with message.getPayloadResult() or message.getPayloadSource()
                    	Source receiverSource = webServiceMessage.getPayloadSource();
    	            	try {
    	            	    transformer.transform(receiverSource, streamResult);
    	            	} catch (Exception e) {
    	            		log.error("e: {}", e.getMessage());
    	            	}
    	            	// SoapMessage receiverSoapMessage = (SoapMessage) webServiceMessage; // initial implementation
    	            	String responseBodyStr = writerForResult.toString();
    	            	
                    	return responseBodyStr;
                    }
                }
    		);
            
            return responseSoapBodyXml;
            
    	} catch (Exception e) {
    		log.error("e: ", e);
    		return null;
    	}
    }
    
    
    
    @SuppressWarnings("unchecked")
    public S generateResponseSoapBodyPojo () {
    	
    	if (responseSoapBodyXml == null) {
    		log.error("responseSoapBodyXml debe definirse primero");
    		return null;
    	}

    	JAXBContext jaxbContext = null;
    	Unmarshaller unmarshaller = null;
		
		try {
			jaxbContext = JAXBContext.newInstance(responsePojo.getClass());
			unmarshaller = jaxbContext.createUnmarshaller();
			responsePojo = (S) unmarshaller.unmarshal(new StringReader(responseSoapBodyXml));

		} catch (Exception e) {
			log.error("e: {} ", e.getMessage());
			return null;
		}
		
		return responsePojo;
    	
    }
    

    
    public Map<String,String> obtainAgencyCodes(String strEnvio) {
    	
    	if (!(this.requestPojo instanceof ConsEnvio2Pojo)) {
    		return null;
    	}
    	
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
    	
        try {
        	documentBuilder = documentBuilderFactory.newDocumentBuilder();
        	Document doc = documentBuilder.parse(new InputSource(new StringReader(strEnvio)));
            NodeList nl = doc.getElementsByTagName("ENVIOS");
            Element e = (Element)nl.item(0);
            
	            vCodAgeOri = e.getAttribute("V_COD_AGE_ORI");
	            vCodAgeCargo = e.getAttribute("V_COD_AGE_CARGO");
           	log.info("Envialia, V_COD_AGE_ORI={}, v_COD_AGE_CARGO={}", vCodAgeOri, vCodAgeCargo);
           	
           	if (vCodAgeOri == null || vCodAgeCargo == null) {
           		throw new Exception("vCodAgeOri o vCodAgeCargo es null");
           	}
           	
           	if (vCodAgeOri.isEmpty() || vCodAgeCargo.isEmpty()) {
           		throw new Exception("vCodAgeOri o vCodAgeCargo es string vacío");
           	}
        
        } catch (Exception e) {
            log.error("e: {}", e.getMessage());
    		return null;
        }
    	
        Map<String,String> responseMap = new HashMap<String,String>();
        responseMap.put("vCodAgeOri", vCodAgeOri);
        responseMap.put("vCodAgeCargo", vCodAgeCargo);
    	
        return responseMap;
    	
    }
    
    
    
    public String obtainDeliveryDateTime (String strEnvioEstado) {
    	
    	if (!(this.requestPojo instanceof ConsEnvEstadosPojo)) {
    		return null;
    	}
    	
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        dFechaHoraAlta = null;

        try {
        	documentBuilder = documentBuilderFactory.newDocumentBuilder();
        	Document doc = documentBuilder.parse(new InputSource(new StringReader(strEnvioEstado)));
            NodeList nl = doc.getElementsByTagName("ENV_ESTADOS");
            for(int i = 0 ; i < nl.getLength(); i++){
	            Element e = (Element)nl.item(i);
	            if ( "4".equalsIgnoreCase(e.getAttribute("V_COD_TIPO_EST")) ) {
    	            dFechaHoraAlta = e.getAttribute("D_FEC_HORA_ALTA");
    	            break;
	            }
            }
            
            if (dFechaHoraAlta == null || dFechaHoraAlta.isEmpty()) {
            	throw new Exception("dFechaHoraAlta es null");
            } else {
            	log.info("Envialia, V_COD_TIPO_EST=4 succesfully found, D_FEC_HORA_ALTA={}", dFechaHoraAlta);
            }
        
        } catch (Exception e) {
            log.error("e: {}", e.getMessage());
            return null;
        }
        
        return dFechaHoraAlta;
        
    }
    
}
