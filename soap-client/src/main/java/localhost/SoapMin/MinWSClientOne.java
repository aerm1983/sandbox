package localhost.SoapMin;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
import org.springframework.ws.soap.SoapMessage;


@Service
public class MinWSClientOne {
	
	private static Logger log = LogManager.getLogger(MinWSClientOne.class);

    // private static final String MESSAGE = "<?xml version=\"1.0\"?><message xmlns=\"http://tempuri.org\">Hello Web Service World</message>";
	// private static final String MESSAGE = "";
	// private static final String MESSAGE = null;
	
	private static final String MESSAGE_0 = "<xml>blabla</xml>";
	
    private static final String MESSAGE_1 = "<?xml version=\"1.0\"?>"
    + "<soap:Envelope "
    + "xmlns:soap=\"http://www.w3.org/2001/12/soap-envelope\" "
    + "soap:encodingStyle=\"http://www.w3.org/2001/12/soap-encoding\">"
    + "<soap:Body xmlns:m=\"http://www.example.org/stock\">"
    + "<m:GetStockPriceResponse>"
    + "<m:Price>34.5</m:Price>"
    + "</m:GetStockPriceResponse>"
    + "</soap:Body>"
    + "</soap:Envelope>";
    
    private static final String MESSAGE_LOGIN_DEP2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
    + "<soap:Envelope "
    + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "
    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
    + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
    + "<soap:Body>"
    + "<LoginWSService___LoginDep2>"
    + "<strCodAge>" + "001234" + "</strCodAge>"
    + "<strCodCli>" + "1234" + "</strCodCli>"
    + "<strDepartamento>" + "99" + "</strDepartamento>"
    + "<strPass>" + "Z12345678" + "</strPass>"
    + "</LoginWSService___LoginDep2>"
    + "</soap:Body>"
    + "</soap:Envelope>";
    
    
    // private static final String url = "http://demo5636922.mockable.io/http://demo5636922.mockable.io/"
    private static final String url = "http://ws.envialia.com/SOAP?service=LoginService";

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    
    
    public void simpleSoapConsumption() {
    	try {
        	
    		// transformer factory
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		final Transformer transformer = transformerFactory.newTransformer();
    		
    		// message to send
    		StringReader readerForSource = new StringReader(MESSAGE_LOGIN_DEP2);
        	final StreamSource streamSource = new StreamSource(readerForSource);
            
            // message to receive
    	    StringWriter writerForResult = new StringWriter();
    	    final StreamResult streamResult = new StreamResult(writerForResult);

    	    // default url
            webServiceTemplate.setDefaultUri(url);
            
            // logging reference, url
            log.info("url: {}", url);
            
            // logging reference, reader buffer
            char[] charArray = new char[500];
    		readerForSource.read(charArray);
            readerForSource.reset();
            String readerBufferString = new String(charArray);
            log.info("requestBody: {}", readerBufferString);
            
            // execute soap service call
            webServiceTemplate.sendAndReceive(
        		new WebServiceMessageCallback() {
    	            public void doWithMessage(WebServiceMessage webServiceMessage) {
    	            	Result senderResult = webServiceMessage.getPayloadResult();
    	            	try {
    	            		transformer.transform(streamSource, senderResult);	
    	            	} catch (Exception e) {
    	            		log.error("e: ", e);
    	            	}
    	            	// ((SoapMessage)webServiceMessage).setSoapAction("urn:envialianet-LoginWSService#LoginDep2");
    	            	SoapMessage senderSoapMessage = (SoapMessage)webServiceMessage; 
    	            	senderSoapMessage.setSoapAction("urn:envialianet-LoginWSService#LoginDep2");
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
    	            	SoapMessage receiverSoapMessage = (SoapMessage)webServiceMessage;
	            	    String responseBody = writerForResult.toString();	            	    
	            	    log.info("responseBody: {}", responseBody);
                    	return null;
                    }
                }
    		);
    	} catch (Exception e) {
    		log.error("e: ", e);
    	}
    }
    

}