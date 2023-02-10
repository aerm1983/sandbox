package localhost.SoapTest;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import localhost.SoapMinAuxiliar.PojoEnvioEstado;
import localhost.SoapMinAuxiliar.PojoLoginDep2;
import localhost.SoapMinAuxiliar.PojoLoginDep2Response;
import localhost.__gitignore.envialia.apachecxf.Login.LoginWSServiceLoginDep2;
import localhost.__gitignore.envialia.credentials.EnvialiaCredentials;


@Service
public class TestWriterReaderXmlSoap {
	
	private static Logger log = LogManager.getLogger(TestWriterReaderXmlSoap.class);

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
    
    
    public void smallXmlCorrector () {
		String xmlStr= "&lt;/CONSULTA&gt;";
		System.out.println("xmlStr: " + xmlStr);
		xmlStr = xmlStr.replace("&lt;", "<").replace("&gt;", ">");
		System.out.println("xmlStr: " + xmlStr);
		return;
    }
    
    
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
    
    
    public void jacksonPojoToXml() {
    	
    	/*
    	// case 1, begin
    	try {
           XmlMapper xmlMapper = new XmlMapper();
			JaxbAnnotationModule module = new JaxbAnnotationModule();
			xmlMapper.registerModule(module);
			xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

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
    	// case 1, end
        */
    	
    	
    	// case 2, begin
    	try {
            XmlMapper xmlMapper = new XmlMapper();
    		JaxbAnnotationModule module = new JaxbAnnotationModule();
    		xmlMapper.registerModule(module);
    		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            
            PojoEnvioEstado pojo = new PojoEnvioEstado();
            pojo.setdFecHoraAlta("2021-01-01");
            pojo.setiId("1");;
            pojo.setvCodTipoEst("A");;

            String xml = xmlMapper.writeValueAsString(pojo);
            log.info("xml: {}", xml);
         } catch(Exception e) {
            log.error("e: ", e);
         }
    	// case 2, end

    	
    	
     }


    @SuppressWarnings("unchecked")
	public void jacksonXmlToPojo() {
    	
    	XmlMapper xmlMapper = new XmlMapper();
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		xmlMapper.registerModule(module);
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String xmlStr = null;
		String jsonStr = null;
		
    	
		// case 1, begin
		/**/
		log.info(" CASE 1");
		
		
		// original, minimal:
		xmlStr = "<PojoLoginDep2><strCodAge>001234</strCodAge><strCodCli>1234</strCodCli><strDepartamento>99</strDepartamento><strPass>Z12345678</strPass></PojoLoginDep2>";
		PojoLoginDep2 pojoLoginDep2  = null;
		jsonStr = null;
		
		log.info("xmlStr: {}", xmlStr);
    	
		try {
           pojoLoginDep2 = xmlMapper.readValue(xmlStr, PojoLoginDep2.class);
           jsonStr = objectMapper.writeValueAsString(pojoLoginDep2);
        } catch(Exception e) {
           log.error("e: ", e);
        }
    	log.info("jsonStr: {}", jsonStr);
    	/**/
		// case 1, end
    	
    	
    	
		// case 2 begin
		/**/
		log.info(" CASE 2");
		
		// soap-client, LoginDep2Response good
		xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:LoginWSService___LoginDep2Response xmlns:v1=\"http://tempuri.org/\"><v1:Result>true</v1:Result><v1:strCodAgeOut>002896</v1:strCodAgeOut><v1:strCodCliOut>1023</v1:strCodCliOut><v1:strCod>11</v1:strCod><v1:strNom>GANT - LOGISFASHION</v1:strNom><v1:strCodCR>CT</v1:strCodCR><v1:strTipo>5</v1:strTipo><v1:strVersion>0.00.71</v1:strVersion><v1:strError>0</v1:strError><v1:strSesion>{42B6978E-149B-44A2-B66A-27EDA7F8F19D}</v1:strSesion><v1:strURLDetSegEnv>http://seguimiento.envialia.com/envialianetweb/detalle_envio.php?servicio={GUID}&amp;fecha={FECHA}</v1:strURLDetSegEnv></v1:LoginWSService___LoginDep2Response>";
		PojoLoginDep2Response pojoLoginDep2Response  = null;
		jsonStr = null;
		
		log.info("xmlStr: {}", xmlStr);
		try {
           pojoLoginDep2Response = xmlMapper.readValue(xmlStr, PojoLoginDep2Response.class);
           jsonStr = objectMapper.writeValueAsString(pojoLoginDep2Response);
        } catch(Exception e) {
           log.error("e: ", e);
        }
    	log.info("jsonStr: {}", jsonStr);
		/**/
		// case 2 end
    	
    	
        
    	
    	
    	// case 3 begin
		/*
		log.info(" CASE 3");
    	
    	// bad encoded character
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:WebServService___ConsEnvio2Response xmlns:v1=\"http://tempuri.org/\"><v1:strEnvio><CONSULTA><ENVIOS V_COD_AGE_CARGO=\"002896\" V_COD_AGE_ORI=\"002896\" V_ALBARAN=\"0141289842\" D_FECHA=\"11/10/2022 00:00:00\" V_COD_AGE_DES=\"002896\" V_COD_TIPO_SERV=\"24\" V_COD_CLI=\"1023\" V_COD_CLI_DEP=\"11\" V_NOM_ORI=\"Logisfashion\" V_TIPO_VIA_ORI=\"\" V_DIR_ORI=\"Av. Larona, 7\" V_NUM_ORI=\"\" V_PISO_ORI=\"\" V_POB_ORI=\"Cabanillas del Campo\" V_CP_ORI=\"19171\" V_COD_PRO_ORI=\"19\" V_TLF_ORI=\"34 949 32 96 00\" V_NOM_DES=\"El Corte Ingl▒s Goya 0003\" V_TIPO_VIA_DES=\"\" V_DIR_DES=\"Calle de Goya, 85\" V_NUM_DES=\"\" V_PISO_DES=\"\" V_POB_DES=\"Madrid\" V_CP_DES=\"28001\" V_COD_PRO_DES=\"28\" V_TLF_DES=\"+34914329300\" I_DOC=\"0\" I_PAQ=\"1\" I_BUL=\"1\" F_PESO_ORI=\"1\" F_ALTO_ORI=\"9.56\" F_ANCHO_ORI=\"9.56\" F_LARGO_ORI=\"9.56\" F_PESO_VOLPES=\"0.9\" F_ALTO_VOLPES=\"24.0045321975935\" F_ANCHO_VOLPES=\"24.0045321975935\" F_LARGO_VOLPES=\"24.0045321975935\" F_REEMBOLSO=\"0\" F_VALOR=\"99.95\" F_ANTICIPO=\"0\" F_COB_CLI=\"0\" F_PORTE_DEB=\"0\" V_OBS=\"\" D_FEC_ENTR=\"11/11/2022 00:00:00\" B_SABADO=\"False\" B_TECLE_DES=\"False\" B_RETORNO=\"False\" B_GEST_ORI=\"False\" B_GEST_DES=\"False\" B_ANULADO=\"False\" B_ACUSE=\"False\" V_COD_REP=\"2012\" V_COD_USU_ALTA=\"\" V_REF=\"PC000025|#|0010090066626292022\" V_ASOCIADO_RET=\"\" V_TIPO_ASOC=\"\" V_COD_SAL_RUTA=\"\" V_TIPO_ENV=\"L\" F_BASE_IMP=\"0\" F_IMPUESTO=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" B_VALIDADO=\"True\" B_CLIENTE=\"True\" B_PORTE_DEB_CLI=\"False\" V_COD_AGE_ALTA=\"002896\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" U_GUID=\"{C4FA7FD6-91AB-47CA-BD1B-BD3942921891}\" V_PERS_CONTACTO=\"\" V_COD_PAIS=\"\" V_COD_REC_ASOC=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\" V_FRANJA_HORARIA=\" \" SD_HORA_ENV_INI=\"12/30/1899 00:00:00\" SD_HORA_ENV_FIN=\"12/30/1899 00:00:00\" V_DES_MOVILES=\"\" V_DES_DIR_EMAILS=\"\"/></CONSULTA></v1:strEnvio></v1:WebServService___ConsEnvio2Response>";
    	// good string
		// String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:WebServService___ConsEnvio2Response xmlns:v1=\"http://tempuri.org/\"><v1:strEnvio><CONSULTA><ENVIOS V_COD_AGE_CARGO=\"002896\" V_COD_AGE_ORI=\"002896\" V_ALBARAN=\"0141289842\" D_FECHA=\"11/10/2022 00:00:00\" V_COD_AGE_DES=\"002896\" V_COD_TIPO_SERV=\"24\" V_COD_CLI=\"1023\" V_COD_CLI_DEP=\"11\" V_NOM_ORI=\"Logisfashion\" V_TIPO_VIA_ORI=\"\" V_DIR_ORI=\"Av. Larona, 7\" V_NUM_ORI=\"\" V_PISO_ORI=\"\" V_POB_ORI=\"Cabanillas del Campo\" V_CP_ORI=\"19171\" V_COD_PRO_ORI=\"19\" V_TLF_ORI=\"34 949 32 96 00\" V_NOM_DES=\"El Corte Ingles Goya 0003\" V_TIPO_VIA_DES=\"\" V_DIR_DES=\"Calle de Goya, 85\" V_NUM_DES=\"\" V_PISO_DES=\"\" V_POB_DES=\"Madrid\" V_CP_DES=\"28001\" V_COD_PRO_DES=\"28\" V_TLF_DES=\"+34914329300\" I_DOC=\"0\" I_PAQ=\"1\" I_BUL=\"1\" F_PESO_ORI=\"1\" F_ALTO_ORI=\"9.56\" F_ANCHO_ORI=\"9.56\" F_LARGO_ORI=\"9.56\" F_PESO_VOLPES=\"0.9\" F_ALTO_VOLPES=\"24.0045321975935\" F_ANCHO_VOLPES=\"24.0045321975935\" F_LARGO_VOLPES=\"24.0045321975935\" F_REEMBOLSO=\"0\" F_VALOR=\"99.95\" F_ANTICIPO=\"0\" F_COB_CLI=\"0\" F_PORTE_DEB=\"0\" V_OBS=\"\" D_FEC_ENTR=\"11/11/2022 00:00:00\" B_SABADO=\"False\" B_TECLE_DES=\"False\" B_RETORNO=\"False\" B_GEST_ORI=\"False\" B_GEST_DES=\"False\" B_ANULADO=\"False\" B_ACUSE=\"False\" V_COD_REP=\"2012\" V_COD_USU_ALTA=\"\" V_REF=\"PC000025|#|0010090066626292022\" V_ASOCIADO_RET=\"\" V_TIPO_ASOC=\"\" V_COD_SAL_RUTA=\"\" V_TIPO_ENV=\"L\" F_BASE_IMP=\"0\" F_IMPUESTO=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" B_VALIDADO=\"True\" B_CLIENTE=\"True\" B_PORTE_DEB_CLI=\"False\" V_COD_AGE_ALTA=\"002896\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" U_GUID=\"{C4FA7FD6-91AB-47CA-BD1B-BD3942921891}\" V_PERS_CONTACTO=\"\" V_COD_PAIS=\"\" V_COD_REC_ASOC=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\" V_FRANJA_HORARIA=\" \" SD_HORA_ENV_INI=\"12/30/1899 00:00:00\" SD_HORA_ENV_FIN=\"12/30/1899 00:00:00\" V_DES_MOVILES=\"\" V_DES_DIR_EMAILS=\"\"/></CONSULTA></v1:strEnvio></v1:WebServService___ConsEnvio2Response>";
		// short string
		// xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:WebServService___ConsEnvio2Response xmlns:v1=\"http://tempuri.org/\"><v1:strEnvio><CONSULTA><ENVIOS V_COD_AGE_CARGO=\"002896\" V_COD_AGE_ORI=\"002896\" /></CONSULTA></v1:strEnvio></v1:WebServService___ConsEnvio2Response>";
		// renamed object string
		// xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><v1:PojoConsEnvio2Response xmlns:v1=\"http://tempuri.org/\"><v1:strEnvio><CONSULTA><ENVIOS V_COD_AGE_CARGO=\"002896\" V_COD_AGE_ORI=\"002896\" /></CONSULTA></v1:strEnvio></v1:PojoConsEnvio2Response>";
		// almost minimal xml
		xmlStr = "<PojoConsEnvio2Response><strEnvio>&lt;CONSULTA&gt;&lt;ENVIOS V_COD_AGE_CARGO=\"002896\" V_COD_AGE_ORI=\"002896\" /&gt;&lt;/CONSULTA&gt;</strEnvio></PojoConsEnvio2Response>";
		// minimal xml OK
		// xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PojoConsEnvio2Response><strEnvio>hello world</strEnvio></PojoConsEnvio2Response>";

		PojoConsEnvio2Response pojoConsEnvio2Response = null;
		jsonStr = null;

		log.info("xmlStr: {}", xmlStr);
    	
    	try {
    		pojoConsEnvio2Response = xmlMapper.readValue(xmlStr, PojoConsEnvio2Response.class);
    		jsonStr = objectMapper.writeValueAsString(pojoConsEnvio2Response);
    	} catch (Exception e) {
    		log.error("e: ", e);
    	}
    	log.info("jsonStr: {}", jsonStr);
    	*/
		// case 3 end
		
    	
    	
        
        
        // case 4 begin
		// ConsEnvEstados attributes processing
    	log.info("case 4");

    	// xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
    	xmlStr = "<ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\" />";
    	
		PojoEnvioEstado envioEstado  = null;
		jsonStr = null;
		
		log.info("xmlStr: {}", xmlStr);
    	
		try {
			envioEstado = xmlMapper.readValue(xmlStr, PojoEnvioEstado.class);
			jsonStr = objectMapper.writeValueAsString(envioEstado);
        } catch(Exception e) {
           log.error("e: ", e);
        }
    	log.info("jsonStr: {}", jsonStr);

        // case 4 end

    	
    	
        // case 5 begin
		// ConsEnvEstados attributes processing
    	log.info("case 5");

    	xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
    	// xmlStr = "<ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\" />";
    	
		ArrayList<PojoEnvioEstado> envioEstadoList  = new ArrayList<PojoEnvioEstado>();
		jsonStr = null;
		
		log.info("xmlStr: {}", xmlStr);
    	
		try {
			envioEstadoList = xmlMapper.readValue(xmlStr, envioEstadoList.getClass());
			jsonStr = objectMapper.writeValueAsString(envioEstadoList);
        } catch(Exception e) {
           log.error("e: ", e);
        }
    	log.info("jsonStr: {}", jsonStr);

        // case 5 end

    	
    	
    	
    	// case 6 begin 
		// ConsEnvEstados attributes processing
    	/**/
    	log.info("case 6");

    	xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
    	
    	DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder;
        
        String vCodTipoEst = null;
        String dFecHoraAlta = null;
        
        log.info(xmlStr);

        try {
            
        	dbuilder = dbc.newDocumentBuilder();
            
        	Document doc = dbuilder.parse(new InputSource(new StringReader(xmlStr)));
            NodeList nl = doc.getElementsByTagName("ENV_ESTADOS");
            
            // TODO: PojoEnvioEstadoElement with XmlAccesType.PROPERTY
            
            for(int i = 0 ; i < nl.getLength(); i++){
	            Element e = (Element)nl.item(i);
	            vCodTipoEst = e.getAttribute("V_COD_TIPO_EST");
	            dFecHoraAlta = e.getAttribute("D_FEC_HORA_ALTA");
	            log.info("i, vCodTipoEst, dFecHoraAlta: {}, {}, {}", i, vCodTipoEst, dFecHoraAlta);
            }
    
        } catch (Exception e) {
            log.error("e: ", e);
        }
		/**/
        // case 6 end
        

        
    	return;
    	
    }
    
    
    @SuppressWarnings("unchecked")
    public void jaxbXmlToPojo () {
    	
    	String xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
		ArrayList<PojoEnvioEstado> envioEstadoList  = new ArrayList<PojoEnvioEstado>();
		String jsonStr = null;
  	
    	JAXBContext jaxbContext = null;
    	Unmarshaller unmarshaller = null;
    	StreamSource source = new StreamSource(new StringReader(xmlStr));
    	
		ObjectMapper objectMapper = new ObjectMapper();

		log.info("xmlStr: {}", xmlStr);
    	try {
    		jaxbContext = JAXBContext.newInstance(envioEstadoList.getClass());   
    		unmarshaller = jaxbContext.createUnmarshaller();
    		envioEstadoList = (ArrayList<PojoEnvioEstado>) unmarshaller.unmarshal(source);
    		jsonStr = objectMapper.writeValueAsString(envioEstadoList);
    		
		} catch (Exception e) {
			log.error("e: ", e);
		}
    	log.info("jsonStr: {}", jsonStr);
    			

    	
    }
    
    
    @SuppressWarnings("unchecked")
    public void jaxbPojoToXmlFalse () {
    	
    	// case 1 begin
    	// log.info("case 1");
    	// String xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
    	String xmlStr = null;
		PojoEnvioEstado pojoEnvioEstado = new PojoEnvioEstado(); 
		pojoEnvioEstado.setdFecHoraAlta("2021-01-01");
		pojoEnvioEstado.setiId("1");
		pojoEnvioEstado.setvCodTipoEst("A");
		
		String jsonStr = null;
  	
    	JAXBContext jaxbContext = null;
    	Marshaller marshaller = null;
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
   	
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			jsonStr = objectMapper.writeValueAsString(pojoEnvioEstado);	
		} catch (Exception e) {
			log.error("e: ", e);
		}
		log.info("jsonStr: {}", jsonStr);
		
    	try {
    		jaxbContext = JAXBContext.newInstance(pojoEnvioEstado.getClass());   
    		marshaller = jaxbContext.createMarshaller();
    		// marshaller.marshal(envioEstadoList, result);
    		marshaller.marshal(pojoEnvioEstado, System.out);
    		xmlStr = writer.toString();
    		
		} catch (Exception e) {
			log.error("e: ", e);
		}
    	log.info("xmlStr: {}", xmlStr);
    	
    	// case 1 end

    	
    	
    	
    	// case 2 begin
    	log.info("case 2");
    	// String xmlStr = "<CONSULTA><ENV_ESTADOS I_ID=\"1\" V_COD_TIPO_EST=\"0\" D_FEC_HORA_ALTA=\"11/08/2022 12:54:48\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"1023\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"2\" V_COD_TIPO_EST=\"1\" D_FEC_HORA_ALTA=\"11/10/2022 18:56:36\" V_COD_USU_ALTA=\"19020\" V_COD_AGE_ALTA=\"\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"3\" V_COD_TIPO_EST=\"11\" D_FEC_HORA_ALTA=\"11/11/2022 03:31:53\" V_COD_USU_ALTA=\"28A24\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"4\" V_COD_TIPO_EST=\"2\" D_FEC_HORA_ALTA=\"11/11/2022 08:19:19\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/><ENV_ESTADOS I_ID=\"5\" V_COD_TIPO_EST=\"4\" D_FEC_HORA_ALTA=\"11/11/2022 10:02:30\" V_COD_USU_ALTA=\"\" V_COD_AGE_ALTA=\"002896\" V_COD_REP_ALTA=\"2012\" V_COD_CLI_ALTA=\"\" V_COD_CLI_DEP_ALTA=\"\" V_CAMPO_1=\"NA\" V_CAMPO_2=\"\" V_CAMPO_3=\"\" V_CAMPO_4=\"\" B_CAMPO_5=\"False\"/></CONSULTA>";
    	/*
    	// String xmlStr = null;
		ArrayList<PojoEnvioEstado> envioEstadoList  = new ArrayList<PojoEnvioEstado>();
		
		pojoEnvioEstado = null; 
		
		pojoEnvioEstado =new PojoEnvioEstado();
		pojoEnvioEstado.setdFecHoraAlta("2021-01-01");
		pojoEnvioEstado.setiId("1");
		pojoEnvioEstado.setvCodTipoEst("A");
		envioEstadoList.add(pojoEnvioEstado);
		
		pojoEnvioEstado = new PojoEnvioEstado();
		pojoEnvioEstado.setdFecHoraAlta("2022-02-02");
		pojoEnvioEstado.setiId("2");
		pojoEnvioEstado.setvCodTipoEst("B");
		envioEstadoList.add(pojoEnvioEstado);
		
		jsonStr = null;
  	
    	jaxbContext = null;
    	marshaller = null;
	    writer = new StringWriter();
	    result = new StreamResult(writer);
   	
		objectMapper = new ObjectMapper();
		
		try {
			jsonStr = objectMapper.writeValueAsString(envioEstadoList);	
		} catch (Exception e) {
			log.error("e: ", e);
		}
		log.info("jsonStr: {}", jsonStr);
		
    	try {
    		jaxbContext = JAXBContext.newInstance(envioEstadoList.getClass());   
    		marshaller = jaxbContext.createMarshaller();
    		// marshaller.marshal(envioEstadoList, result);
    		marshaller.marshal(envioEstadoList, System.out);
    		xmlStr = writer.toString();
    		
		} catch (Exception e) {
			log.error("e: ", e);
		}
    	log.info("xmlStr: {}", xmlStr);
    	*/
    	// case 2 end
    			

    	
    }

    

    
    
    
    
    @SuppressWarnings("unchecked")
    public void jaxbMarshalPojoToXmlAgain () {
    	
    	String xmlStr = null;
    	LoginWSServiceLoginDep2 loginWSServiceLoginDep2 = new LoginWSServiceLoginDep2();
    	loginWSServiceLoginDep2.setStrCodAge(EnvialiaCredentials.strCodAge);
    	loginWSServiceLoginDep2.setStrCodCli(EnvialiaCredentials.strCodCli);
    	loginWSServiceLoginDep2.setStrDepartamento(EnvialiaCredentials.strDepartamento);
    	loginWSServiceLoginDep2.setStrPass(EnvialiaCredentials.strPass);
  	
    	JAXBContext jaxbContext = null;
    	Marshaller marshaller = null;
	    StringWriter writer = new StringWriter();
	    // StreamResult result = new StreamResult(writer); // not necessary
		
    	try {
    		jaxbContext = JAXBContext.newInstance(loginWSServiceLoginDep2.getClass());   
    		
    		marshaller = jaxbContext.createMarshaller();
    		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
    		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    		
    		marshaller.marshal(loginWSServiceLoginDep2, writer);
    		xmlStr = writer.toString();
    		
		} catch (Exception e) {
			log.error("e: ", e);
		}
    	log.info("xmlStr: {}", xmlStr);
    	

    	
    	
    	
    }

    
    
    
    
    
}