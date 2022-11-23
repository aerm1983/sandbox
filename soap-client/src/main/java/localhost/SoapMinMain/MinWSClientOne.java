package localhost.SoapMinMain;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import localhost.SoapMinAuxiliar.PojoConsEnvEstadosRef;
import localhost.SoapMinAuxiliar.PojoConsEstadosRefResponse;
import localhost.SoapMinAuxiliar.PojoLoginDep2;
import localhost.SoapMinAuxiliar.PojoLoginDep2Response;


@Service
public class MinWSClientOne {
	
	private static Logger log = LogManager.getLogger(MinWSClientOne.class);

    // private static final String MESSAGE = "<?xml version=\"1.0\"?><message xmlns=\"http://tempuri.org\">Hello Web Service World</message>";
	// private static final String MESSAGE = "";
	// private static final String MESSAGE = null;
	
	private static final String MESSAGE_0 = "<xml>blabla</xml>";
    
    private static final String strCodAge = "001234";
    private static final String strCodCli = "1234";
    private static final String strDepartamento = "99";
    private static final String strPass = "Z12345678";

    private static final String urlMockable = "http://demo5636922.mockable.io/http://demo5636922.mockable.io/";
    private static final String urlLoginDep2 = "http://ws.envialia.com/SOAP?service=LoginService";
    private static final String urlConsEnvEstadosRef = "http://ws.envialia.com/SOAP?service=WebService";
    
    // LoginDep2
    private static final String messageLoginDep2 = "" // "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
    // + "<soapenv:Envelope "
    // + "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
    // + "xmlns:tem=\"http://tempuri.org/\" "
    // + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
    // + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " 
    // + ">"
    // + "<soapenv:Header>"
    // + "<tem:ROClientIDHeader>"
    // + "<tem:ID></tem:ID>"
    // + "</tem:ROClientIDHeader>"
    // + "</soapenv:Header>"
    // + "<soapenv:Body>"
    
    // original:
    /*
    + "<tem:LoginWSService___LoginDep2>"
    + "<tem:strCodAge>" + strCodAge + "</tem:strCodAge>"
    + "<tem:strCodCli>" + strCodCli + "</tem:strCodCli>"
    + "<tem:strDepartamento>" + strDepartamento + "</tem:strDepartamento>"
    + "<tem:strPass>" + strPass + "</tem:strPass>"
    + "</tem:LoginWSService___LoginDep2>"
    */

    // modified
    + "<LoginWSService___LoginDep2>"
    + "<strCodAge>" + strCodAge + "</strCodAge>"
    + "<strCodCli>" + strCodCli + "</strCodCli>"
    + "<strDepartamento>" + strDepartamento + "</strDepartamento>"
    + "<strPass>" + strPass + "</strPass>"
    + "</LoginWSService___LoginDep2>"

    		
    // + "</soapenv:Body>"
    // + "</soapenv:Envelope>"
    ;
    private static final String soapActionLoginDep2 = "urn:envialianet-LoginWSService#LoginDep2";
    private static final String soapActionConsEnvEstadosRef = "urn:envialianet-WebServService#ConsEnvEstadosRef";
    

    // LoginCli2
    private static final String messageLoginCli2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
    + "<soap:Envelope "
    + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "
    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
    + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
    + "<soap:Body>"
    + "<LoginWSService___LoginCli2>"
    + "<strCodAge>" + strCodAge + "</strCodAge>"
    + "<strCliente>" + strCodCli + "</strCliente>"
    + "<strPass>" + strPass + "</strPass>"
    + "</LoginWSService___LoginCli2>"
    + "</soap:Body>"
    + "</soap:Envelope>";
    private static final String soapActionLoginCli2 = "urn:envialianet-LoginWSService#LoginCli2";

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    

    
    public void simpleSoapConsumptionXmlWrapper(String ws) {

    	String url = null;
    	String soapAction = null;
    	String idSessionHeader = null;
    	String requestMessage = null;

    	
    	if ("LoginDep2".equalsIgnoreCase(ws)) {
    		url = urlLoginDep2;
    		soapAction = soapActionLoginDep2;
    		idSessionHeader = null;
    		requestMessage = messageLoginDep2;
    	} else if ("LoginCli2".equalsIgnoreCase(ws)) {
    	   	url = null; // TODO, define
        	idSessionHeader = null;
    		soapAction = soapActionLoginCli2;
    		requestMessage = messageLoginCli2;
     	} else {
    		return;
    	}
		
		// execute
    	simpleSoapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
    	
    }
    

    
    public void simpleSoapConsumptionPojoWrapper(String ws) {
    	
    	try {

        	XmlMapper xmlMapper = new XmlMapper();
    		JaxbAnnotationModule module = new JaxbAnnotationModule();
    		xmlMapper.registerModule(module);
        	ObjectMapper objectMapper = new ObjectMapper();
    		
        	String url = null;
        	String idSessionHeader = null;
    		String requestMessage = null;
        	String soapAction = null;
        	
        	String responseMessage = null;
        	String responseJsonString = null;
        	
        	if ("LoginDep2".equalsIgnoreCase(ws)) {
        		
        		PojoLoginDep2 requestPojoLoginDep2 = new PojoLoginDep2();
        		requestPojoLoginDep2.setStrCodAge(strCodAge);
        		requestPojoLoginDep2.setStrCodCli(strCodCli);
        		requestPojoLoginDep2.setStrDepartamento(strDepartamento);
        		requestPojoLoginDep2.setStrPass(strPass);
        		
        		url = urlLoginDep2;
        		soapAction = soapActionLoginDep2;
        		idSessionHeader = null;
        		requestMessage = xmlMapper.writeValueAsString(requestPojoLoginDep2);
        		

        	} else if ("LoginCli2".equalsIgnoreCase(ws)) {
        		// TODO
        		requestMessage = messageLoginCli2;
        		soapAction = soapActionLoginCli2; 
        	} else if ("Login2".equalsIgnoreCase(ws)) {
        		return;
        	}
    		
    		// execute
        	responseMessage = (String) simpleSoapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
        	PojoLoginDep2Response pojoLoginDep2Response = xmlMapper.readValue(responseMessage, PojoLoginDep2Response.class);
        	
        	responseJsonString = objectMapper.writeValueAsString(pojoLoginDep2Response);
        	log.info("responseJsonString, LoginDep2: {}", responseJsonString);
        	
        	
        	if ("LoginDep2".equalsIgnoreCase(ws)) {
        		PojoConsEnvEstadosRef pojoConsEnvEstadosRef = new PojoConsEnvEstadosRef();
        		pojoConsEnvEstadosRef.setStrRef("1234");
        		
        		url = urlConsEnvEstadosRef;
        		soapAction = soapActionConsEnvEstadosRef;
        		idSessionHeader = pojoLoginDep2Response.getStrSesion();
        		requestMessage = xmlMapper.writeValueAsString(pojoConsEnvEstadosRef);
        		
        		responseMessage = (String) simpleSoapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
            	PojoConsEstadosRefResponse pojoConsEstadosRefResponse = xmlMapper.readValue(responseMessage, PojoConsEstadosRefResponse.class);
            	
            	responseJsonString = objectMapper.writeValueAsString(pojoConsEstadosRefResponse);
            	log.info("responseJsonString, ConsEnvEstadosRef: {}", responseJsonString);

        	}
        	
        	return;
    		
    	} catch (Exception e) {
    		log.error("e: ", e);
    	}

    }
    

    
    public Object simpleSoapConsumptionWithHeader(String url, String soapAction, String idSessionHeader, String message) {
    	try {
        	
    		// transformer factory
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		final Transformer transformer = transformerFactory.newTransformer();
    		
    		// message to send
    		StringReader stringReaderForSource = new StringReader(message);
    		StringReader stringReaderForSourceToLog = new StringReader(message);
        	final StreamSource streamSource = new StreamSource(stringReaderForSource);
            
            // message to receive
    	    StringWriter writerForResult = new StringWriter();
    	    final StreamResult streamResult = new StreamResult(writerForResult);

    	    // default url
            webServiceTemplate.setDefaultUri(url);

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
                scanner = new Scanner(stringReaderForSourceToLog).useDelimiter("\\A");
                sRFSToLogStr = scanner.hasNext() ? scanner.next() : "";
                scanner.close();
                log.info("requestMessage: {}", sRFSToLogStr);
            } catch (Exception e) {
            	log.error("e: ", e);
            }

            
            // char[] charArray = new char[600];
    		// readerForSource.read(charArray);
            
            // String readerBufferString = new String(charArray);
            
            
            
            // execute soap service call
            Object responseObject = webServiceTemplate.sendAndReceive(
        		new WebServiceMessageCallback() {
    	            public void doWithMessage(WebServiceMessage webServiceMessage) {
    	            	Result senderResult = webServiceMessage.getPayloadResult();
    	            	try {
    	            		transformer.transform(streamSource, senderResult);	
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	// ((SoapMessage)webServiceMessage).setSoapAction("urn:envialianet-LoginWSService#LoginDep2");
    	            	SoapMessage senderSoapMessage = (SoapMessage) webServiceMessage;
    	            	senderSoapMessage.setSoapAction(soapAction); // debug - is this necessary?

    	            	// required vars, begin
	            		SaajSoapMessage senderSaajSoapMessage = (SaajSoapMessage) webServiceMessage;
    	            	SOAPMessage senderSOAPMessage = senderSaajSoapMessage.getSaajMessage();
    	            	SOAPPart senderSOAPPart = senderSOAPMessage.getSOAPPart();
    	            	
    	            	SOAPEnvelope senderSOAPEnvelope = null;
    	            	SOAPBody senderSOAPBody = null;
    	            	SOAPHeader senderSOAPHeader = null;
    	            	try {
    	            		senderSOAPEnvelope = senderSOAPPart.getEnvelope();
    	            		senderSOAPBody = senderSOAPMessage.getSOAPBody();
    	            		senderSOAPHeader = senderSOAPMessage.getSOAPHeader();
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	// required vars, end
    	            	
    	            	try {
        	            	// adjust namespace in body child elements, begin
        	            	senderSOAPEnvelope.addNamespaceDeclaration("tem", "http://tempuri.org");
        	            	
        	            	// trying to manually add prefix to SOAP-body child nodes fails
        	            	// senderSOAPBody.getFirstChild().setPrefix("tem"); // fail, exception
        	            	
        	            	// adjust namespace in body child elements, end
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	
    	            	try {
        	            	// add header, begin
        	            	SOAPElement roClientIDHeaderHSE = senderSOAPHeader.addChildElement("ROClientIDHeader","tem");  // "ROClientIDHeader"
        	            	SOAPElement idHSE = roClientIDHeaderHSE.addChildElement("ID", "tem");
        	            	if ( idSessionHeader != null && !idSessionHeader.isEmpty() ) {
        	            		idHSE.addTextNode(idSessionHeader);
        	            	/*
        	            	} else {
        	            		idHSE.addTextNode("");
    	            		*/
        	            	}
        	            	// add header, end
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	
    	            	return;
    	            }
        		},
                new WebServiceMessageExtractor<Object>() {
                    public Object extractData(WebServiceMessage webServiceMessage) throws IOException {
                        // do your own transforms with message.getPayloadResult()
                        //     or message.getPayloadSource()
                    	Source receiverSource = webServiceMessage.getPayloadSource();
    	            	try {
    	            	    transformer.transform(receiverSource, streamResult);
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	SoapMessage receiverSoapMessage = (SoapMessage) webServiceMessage;
	            	    String responseBodyStr = writerForResult.toString();	            	    
	            	    log.info("responseBodyStr: {}", responseBodyStr);
                    	return responseBodyStr;
                    	// return null // debug
                    }
                }
    		);
            
            return responseObject;
            
    	} catch (Exception e) {
    		log.error("e: ", e);
    		return null;
    	}
    }
    

}