package localhost.SoapMinAuxiliar;

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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;


@Service
public class PrevWriterReaderXmlSoapTests {
	
	private static Logger log = LogManager.getLogger(PrevWriterReaderXmlSoapTests.class);

    // private static final String MESSAGE = "<?xml version=\"1.0\"?><message xmlns=\"http://tempuri.org\">Hello Web Service World</message>";
	// private static final String MESSAGE = "";
	// private static final String MESSAGE = null;
	
	private static final String MESSAGE_0 = "<xml>blabla</xml>";
	
    private static final String strCodAge = "001234";
    private static final String strCodCli = "1234";
    private static final String strDepartamento = "99";
    private static final String strPass = "Z12345678";

	
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
    
    private static final String messageLoginDep2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
    + "<soapenv:Envelope "
    + "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
    + "xmlns:tem=\"http://tempuri.org/\" "
    + ">"
    + "<soapenv:Header>"
    + "<tem:ROClientIDHeader>"
    + "<tem:ID></tem:ID>"
    + "</tem:ROClientIDHeader>"
    + "</soapenv:Header>"
    + "<soapenv:Body>"
    + "<tem:LoginWSService___LoginDep2>"
    + "<tem:strCodAge>" + strCodAge + "</tem:strCodAge>"
    + "<tem:strCodCli>" + strCodCli + "</tem:strCodCli>"
    + "<tem:strDepartamento>" + strDepartamento + "</tem:strDepartamento>"
    + "<tem:strPass>" + strPass + "</tem:strPass>"
    + "</tem:LoginWSService___LoginDep2>"
    + "</soapenv:Body>"
    + "</soapenv:Envelope>";
    
    
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
    	    StreamSource source = new StreamSource(new StringReader(messageLoginDep2));
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
    
    
    public void pojoToXml() {
    	try {
           XmlMapper xmlMapper = new XmlMapper();
           PojoLoginDep2 pojo = new PojoLoginDep2();
           pojo.setStrCodAge("001234");
           pojo.setStrCodCli("1234");
           pojo.setStrDepartamento("99");
           pojo.setStrPass("Z12345678");
           String xml = xmlMapper.writeValueAsString(pojo);
           log.info("xml: {}", xml);
        } catch(Exception e) {
           log.error("e: ", e);
        }
     }


    public void xmlToPojo() {
    	try {
           XmlMapper xmlMapper = new XmlMapper();
           PojoLoginDep2 pojo  = xmlMapper.readValue(
        		   "<PojoLoginDep2><strCodAge>001234</strCodAge><strCodCli>1234</strCodCli><strDepartamento>99</strDepartamento><strPass>Z12345678</strPass></PojoLoginDep2>", 
        		   PojoLoginDep2.class
        		   );
           log.info("pojo: {}", pojo);
        } catch(Exception e) {
           log.error("e: ", e);
        }
    }

}