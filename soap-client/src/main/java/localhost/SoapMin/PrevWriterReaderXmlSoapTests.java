package localhost.SoapMin;

import java.io.FileReader;
import java.io.FileWriter;
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
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.WebServiceTransformerException;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapMessageCreationException;
import org.springframework.ws.transport.WebServiceMessageSender;


@Service
public class PrevWriterReaderXmlSoapTests {
	
	private static Logger log = LogManager.getLogger(PrevWriterReaderXmlSoapTests.class);

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
    + "<strDepartamento>" + "88" + "</strDepartamento>"
    + "<strPass>" + "Z12345678" + "</strPass>"
    + "</LoginWSService___LoginDep2>"
    + "</soap:Body>"
    + "</soap:Envelope>";
    
    
    // private static final String url = "http://demo5636922.mockable.io/http://demo5636922.mockable.io/"
    private static final String url = "http://ws.envialia.com/SOAP?service=LoginService";

    

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    
    
    
    public void smallStringWriterReaderTest () {

    	// writer
    	StringWriter stringWriter = new StringWriter();
    	stringWriter.write("A");
    	stringWriter.write("B");
    	stringWriter.append("C"); // same as write(String)
    	stringWriter.write("D");
    	stringWriter.flush(); // seems to do nothing
    	String stringFromWriter = stringWriter.toString();
    	log.info("stringFromWriter: {}", stringFromWriter);
    	
    	// reader
    	StringReader stringReader = new StringReader("ABCD");
    	int i = 0;
    	do {
    		try {
    			i = stringReader.read();
    			log.info("i: {}", i);
    			if (i < 0) break;
    		} catch (Exception e) {
    			log.error("e: ", e);
    			break;
    		}
    		
    	} while (true);

    }
    

    
    public void smallFileWriterReaderTest () throws IOException {

    	// writer
    	FileWriter fileWriter = new FileWriter("test.txt");
    	
    	fileWriter.write("A");
    	fileWriter.write("B");
    	fileWriter.append("C"); // same as write(String)
    	fileWriter.write("D");
    	fileWriter.flush();

    	fileWriter.write("1");
    	fileWriter.write("2");
    	fileWriter.append("3"); // same as write(String)
    	fileWriter.write("4");
    	fileWriter.flush();

    	fileWriter.close();

    	
    	// reader
    	FileReader fileReader = new FileReader("test.txt");
    	int i = 0;
    	do {
    		try {
    			i = fileReader.read();
    			log.info("i: {}", i);
    			if (i < 0) break;
    		} catch (Exception e) {
    			log.error("e: ", e);
    		}
    	} while (true);
    	fileReader.close();
    	return;
    }
    
    
    
    public void smallXmlTransformTest() {
    	try {
    	    StreamSource source = new StreamSource(new StringReader(MESSAGE_LOGIN_DEP2));
    	    StringWriter writer = new StringWriter();
    	    StreamResult result = new StreamResult(writer);
    	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	    Transformer transformer = transformerFactory.newTransformer();
    	    transformer.transform(source, result);
    	    String strResult = writer.toString();
    	    log.info("strResult: {}", strResult);
    	    // strResult: <?xml version="1.0" encoding="UTF-8"?><xml>blabla</xml>
    	} catch (Exception e) {
    	    log.error("e: ", e);
    	}
    }


    
    /*
    public void setDefaultUri(String defaultUri) {
        webServiceTemplate.setDefaultUri(defaultUri);
    }
    */
    
    // send to the configured default URI
    /*
    public void simpleSendAndReceive() {
        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        webServiceTemplate.sendSourceAndReceiveToResult(source, result);
    }
    */

    
    
    // send to an explicit URI
    public void customSendAndReceiveOne() {
    	
    	Boolean mReceived = null;
    	
    	try {
    		
            StreamSource source = new StreamSource(new StringReader(MESSAGE_0));
            StreamResult result = new StreamResult(System.out);
            
            // original
            // webServiceTemplate.sendSourceAndReceiveToResult("http://localhost:8080/AnotherWebService", source, result);
            
            // modified
            // mReceived = webServiceTemplate.sendSourceAndReceiveToResult("http://demo5636922.mockable.io/http://demo5636922.mockable.io/", source, result);
            
            // modified
            mReceived = webServiceTemplate.sendSourceAndReceiveToResult(
            		"http://demo5636922.mockable.io/http://demo5636922.mockable.io/", 
            		source, 
            		new WebServiceMessageCallback() {

		                public void doWithMessage(WebServiceMessage message) {
		                	log.info("doWithMessage(), begin");
		                    // ((SoapMessage)message).setSoapAction("http://tempuri.org/Action");
		                    ((SoapMessage)message).setSoapAction("Get");
		                	log.info("doWithMessage(), end");
		                }
		            }, 
                    /*
            		new WebServiceMessageExtractor() {
		                public Object extractData(WebServiceMessage message) throws IOException
		                    // do your own transforms with message.getPayloadResult()
		                    //     or message.getPayloadSource()
		                }
		            },
		            */            		
            		result
            ); 
    	
    	} catch (WebServiceTransformerException wstfe) {
    		log.error("wstfe: ", wstfe);
    	} catch (WebServiceTransportException wstpe) {
    		log.error("wstpe: ", wstpe);
    	} catch (SoapMessageCreationException smce) {
    		log.error("smce: ", smce);
    	} catch (Exception e) {
    		log.error("e: ", e);
    	}
    	
    	log.info("mReceived: {}", mReceived);
        
    }
    
    
    
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