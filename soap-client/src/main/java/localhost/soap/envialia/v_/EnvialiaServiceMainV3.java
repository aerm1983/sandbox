package localhost.soap.envialia.v_;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpComponentsConnection;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpUrlConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import localhost.__gitignore.envialia.credentials.EnvialiaCredentials;
import localhost.soap.envialia.pojo.ConsEnvEstadosPojo;
import localhost.soap.envialia.pojo.ConsEnvEstadosResponsePojo;
import localhost.soap.envialia.pojo.ConsEnvio2Pojo;
import localhost.soap.envialia.pojo.ConsEnvio2ResponsePojo;
import localhost.soap.envialia.pojo.LoginDep2Pojo;
import localhost.soap.envialia.pojo.LoginDep2ResponsePojo;

import org.apache.http.Header;


@Service
public class EnvialiaServiceMainV3 {

	private static Logger log = LogManager.getLogger(EnvialiaServiceMainV3.class);


	private static final String strCodAge = EnvialiaCredentials.strCodAge;
	private static final String strCodCli = EnvialiaCredentials.strCodCli;
	private static final String strDepartamento = EnvialiaCredentials.strDepartamento;
	private static final String strPass = EnvialiaCredentials.strPass;

	/*
    private static final String strCodAge = "001234";
    private static final String strCodCli = "1234";
    private static final String strDepartamento = "99";
    private static final String strPass = "Z12345678";
	 */

	// tracking test cases
	// private static final String tracking = "0028960141289842";
	// private static final String tracking = "0028960139822698";
	private static final String tracking = "0028960140010676";

	// albaran test cases
	// private static final String albaran = "0141289842";
	// private static final String albaran = "0139822698";
	// private static final String albaran = "0140010676";

	// albaran invalid case:
	// private static final String tracking = "0028960141281234";


	// private static final String urlMockable = "http://demo5636922.mockable.io/http://demo5636922.mockable.io/";

	private static final String urlLoginDep2 = "http://ws.envialia.com/SOAP?service=LoginService";
	private static final String urlConsEnvio2 = "http://ws.envialia.com/SOAP?service=WebService";
	private static final String urlConsEnvEstados = "http://ws.envialia.com/SOAP?service=WebService";

	private static final String soapActionLoginDep2 = "urn:envialianet-LoginWSService#LoginDep2";
	private static final String soapActionConsEnvio2 = "urn:envialianet-WebServService#ConsEnvio2";
	private static final String soapActionConsEnvEstados = "urn:envialianet-WebServService#ConsEnvEstados";

	private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();


	public void main () {
		log.info("hello from {}!", this.getClass());
		String fechaHoraEntrega = null;
		fechaHoraEntrega = getDeliveryDate();
		log.info("fechaHoraEntrega: {}", fechaHoraEntrega);
	}


	public String getDeliveryDate() {

		try {

			// ENVIALIA, get albaran from tracking
			String albaran = tracking.substring(6);
			log.info("tracking: {} ; albaran: {}", tracking, albaran);


			// ENVIALIA, general objects

			XmlMapper xmlMapper = new XmlMapper();
			JaxbAnnotationModule module = new JaxbAnnotationModule();
			xmlMapper.registerModule(module);
			xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			ObjectMapper objectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String url = null;
			String idSessionHeader = null;
			String requestMessage = null;
			String soapAction = null;

			String responseMessage = null;
			String responseJsonString = null;

			JAXBContext jaxbContext = null;
			Marshaller marshaller = null;
			StringWriter stringWriter = null;

			DocumentBuilderFactory dbc = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder;


			// webServiceTemplate, messageSender
			HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
			webServiceTemplate.setMessageSender( httpComponentsMessageSender );

			// webServiceTemplate, set interceptors to print http request headers 
			ClientInterceptor[] interceptors = webServiceTemplate.getInterceptors();

			interceptors = (ClientInterceptor[]) ArrayUtils.add(interceptors, new ClientInterceptor() {
				@Override
				public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {

					WebServiceConnection webServiceConnection = TransportContextHolder.getTransportContext().getConnection();
					HttpComponentsConnection HCConnection = null;
					HttpUrlConnection HUConnection = null;

					if (webServiceConnection instanceof HttpComponentsConnection ) {
						HCConnection = (HttpComponentsConnection) webServiceConnection;
						log.info("WebServiceTemplate - message sender is: {}", HCConnection.getClass());
					} else if (webServiceConnection instanceof HttpUrlConnection) {
						HUConnection = (HttpUrlConnection) webServiceConnection;
						log.info("WebServiceTemplate - message sender is: {}", HUConnection.getClass());
					}

					return true;
				}

				@Override
				public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
					return true;
				}

				@Override
				public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
					return true;
				}

				@Override
				public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

					WebServiceConnection webServiceConnection = TransportContextHolder.getTransportContext().getConnection();
					HttpComponentsConnection HCConnection = null;
					HttpUrlConnection HUConnection = null;

					if (webServiceConnection instanceof HttpComponentsConnection ) {

						HCConnection = (HttpComponentsConnection) webServiceConnection;

						String requestHeaders = "";
						for(Header header : HCConnection.getHttpPost().getAllHeaders()) {
							requestHeaders += header.getName() + ": " + header.getValue() + " --- ";
						}

						String responseHeaders = "";
						for(Header header : HCConnection.getHttpResponse().getAllHeaders()) {
							responseHeaders += header.getName() + ": " + header.getValue() + " --- ";
						}

						log.info("SOAP - http request headers: {}", requestHeaders);
						log.info("SOAP - http response headers: {}", responseHeaders);

					} else if (webServiceConnection instanceof HttpUrlConnection) {

						HUConnection = (HttpUrlConnection) webServiceConnection;
						HttpURLConnection jnConnection = HUConnection.getConnection();

						Map<String,List<String>> requestMap = jnConnection.getRequestProperties();
						Set<String> requestKeysSet = requestMap.keySet();
						Iterator<String> requestKeyIterator = requestKeysSet.iterator();

						String request = "";
						String reqKey = null;
						List<String> reqKeyList = null;

						while ( requestKeyIterator != null & requestKeyIterator.hasNext() ) {
							reqKey = requestKeyIterator.next();
							request = reqKey + ": ";

							reqKeyList = requestMap.get(reqKey);
							for (String reqKeyListElem : reqKeyList) {
								request += reqKeyListElem + ", ";
							}

							request += " ; ";
						}

						log.info("http request headers: {}", request);
					}
				}
			});

			webServiceTemplate.setInterceptors(interceptors);


			// ENVIALIA, LoginDep2

			LoginDep2Pojo requestPojoLoginDep2 = new LoginDep2Pojo();
			requestPojoLoginDep2.setStrCodAge(strCodAge);
			requestPojoLoginDep2.setStrCodCli(strCodCli);
			requestPojoLoginDep2.setStrDepartamento(strDepartamento);
			requestPojoLoginDep2.setStrPass(strPass);

			url = urlLoginDep2;
			soapAction = soapActionLoginDep2;
			idSessionHeader = null;

			// requestMessage = xmlMapper.writeValueAsString(requestPojoLoginDep2); // initial implementation, fails
			try {
				jaxbContext = JAXBContext.newInstance(requestPojoLoginDep2.getClass());   
				marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
				stringWriter = new StringWriter();
				marshaller.marshal(requestPojoLoginDep2, stringWriter);
				requestMessage = stringWriter.toString();
			} catch (Exception e) {
				log.error("e: ", e);
			}
			log.info("requestMessage: {}", requestMessage);

			responseMessage = (String) soapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
			log.info("responseMessage: {}", responseMessage);
			LoginDep2ResponsePojo pojoLoginDep2Response = xmlMapper.readValue(responseMessage, LoginDep2ResponsePojo.class);
			responseJsonString = objectMapper.writeValueAsString(pojoLoginDep2Response);
			log.info("responseJsonString, LoginDep2: {}", responseJsonString);

			if (pojoLoginDep2Response == null || pojoLoginDep2Response.getStrSesion() == null) {
				log.info("respuesta de Envialia no válida - retornar null");
				return null;
			}



			// ENVIALIA, ConsEnvio2
			ConsEnvio2Pojo requestPojoConsEnvio2 = new ConsEnvio2Pojo();
			requestPojoConsEnvio2.setStrCodAgeCargo(strCodAge);
			requestPojoConsEnvio2.setStrAlbaran(albaran);

			url = urlConsEnvio2;
			soapAction = soapActionConsEnvio2;
			idSessionHeader = pojoLoginDep2Response.getStrSesion();

			// requestMessage = xmlMapper.writeValueAsString(pojoConsEnvio2); // initial implementation, fails
			try {
				jaxbContext = JAXBContext.newInstance(requestPojoConsEnvio2.getClass());   
				marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
				stringWriter = new StringWriter();
				marshaller.marshal(requestPojoConsEnvio2, stringWriter);
				requestMessage = stringWriter.toString();
			} catch (Exception e) {
				log.error("e: ", e);
			}
			log.info("requestMessage: {}", requestMessage);

			responseMessage = (String) soapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
			log.info("responseMessage: {}", responseMessage);
			ConsEnvio2ResponsePojo pojoConsEnvio2Response = xmlMapper.readValue(responseMessage, ConsEnvio2ResponsePojo.class);
			responseJsonString = objectMapper.writeValueAsString(pojoConsEnvio2Response);
			log.info("responseJsonString, ConsEnvio2: {}", responseJsonString);

			if (pojoConsEnvio2Response == null || pojoConsEnvio2Response.getStrEnvio() == null) {
				log.info("respuesta de Envialia no válida - retornar null");
				return null;
			}


			// ENVIALIA, ConsEnvio2, obtain V_COD_AGE_ORI Y V_COD_AGE_CARGO
			String xmlStr = pojoConsEnvio2Response.getStrEnvio();
			String vCodAgeOri = null;
			String vCodAgeCargo = null;

			try {
				dbuilder = dbc.newDocumentBuilder();
				Document doc = dbuilder.parse(new InputSource(new StringReader(xmlStr)));
				NodeList nl = doc.getElementsByTagName("ENVIOS");
				Element e = (Element)nl.item(0);

				vCodAgeOri = e.getAttribute("V_COD_AGE_ORI");
				vCodAgeCargo = e.getAttribute("V_COD_AGE_CARGO");
				log.info("Envialia, V_COD_AGE_ORI={}, v_COD_AGE_CARGO={}", vCodAgeOri, vCodAgeCargo);

				if (vCodAgeOri == null || vCodAgeCargo == null) {
					throw new Exception("vCodAgeOri o vCodAgeCargo es null");
				}

			} catch (Exception e) {
				log.error("e: ", e);
				log.info("respuesta de Envialia no válida - retornar null");
				return null;
			}



			// ENVIALIA, ConsEnvEstados

			ConsEnvEstadosPojo requestPojoConsEnvEstados = new ConsEnvEstadosPojo();
			requestPojoConsEnvEstados.setStrCodAgeCargo(vCodAgeCargo);
			requestPojoConsEnvEstados.setStrCodAgeOri(vCodAgeOri);
			requestPojoConsEnvEstados.setStrAlbaran(albaran);

			url = urlConsEnvEstados;
			soapAction = soapActionConsEnvEstados;
			idSessionHeader = pojoLoginDep2Response.getStrSesion();

			// requestMessage = xmlMapper.writeValueAsString(pojoConsEnvEstados); // initial implementation, fails
			try {
				jaxbContext = JAXBContext.newInstance(requestPojoConsEnvEstados.getClass());   
				marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
				stringWriter = new StringWriter();
				marshaller.marshal(requestPojoConsEnvEstados, stringWriter);
				requestMessage = stringWriter.toString();
			} catch (Exception e) {
				log.error("e: ", e);
			}
			log.info("requestMessage: {}", requestMessage);

			responseMessage = (String) soapConsumptionWithHeader(url, soapAction, idSessionHeader, requestMessage);
			log.info("responseMessage: {}", responseMessage);
			ConsEnvEstadosResponsePojo pojoConsEnvEstadosResponse = xmlMapper.readValue(responseMessage, ConsEnvEstadosResponsePojo.class);
			responseJsonString = objectMapper.writeValueAsString(pojoConsEnvEstadosResponse);
			log.info("responseJsonString, ConsEnvEstados: {}", responseJsonString);

			if (pojoConsEnvEstadosResponse == null || pojoConsEnvEstadosResponse.getStrEnvEstados() == null ) {
				log.info("respuesta de Envialia no válida - retornar null");
				return null;
			}

			// ENVIALIA, ConsEnvEstados, obtain delivery date, V_COD_TIPO_EST="4", D_FEC_HORA_ALTA
			xmlStr = pojoConsEnvEstadosResponse.getStrEnvEstados();

			String dFechaHoraAlta = null;

			try {
				dbuilder = dbc.newDocumentBuilder();
				Document doc = dbuilder.parse(new InputSource(new StringReader(xmlStr)));
				NodeList nl = doc.getElementsByTagName("ENV_ESTADOS");
				for(int i = 0 ; i < nl.getLength(); i++){
					Element e = (Element)nl.item(i);
					if ( "4".equalsIgnoreCase(e.getAttribute("V_COD_TIPO_EST")) ) {
						dFechaHoraAlta = e.getAttribute("D_FEC_HORA_ALTA");
						break;
					}
				}

				if (dFechaHoraAlta == null) {
					throw new Exception("dFechaHoraAlta es null");
				} else {
					log.info("Envialia, V_COD_TIPO_EST=4 succesfully found, D_FEC_HORA_ALTA={}", dFechaHoraAlta);
				}

			} catch (Exception e) {
				log.error("e: ", e);
				log.info("respuesta de Envialia no válida - retornar null");
				return null;
			}

			// end
			return dFechaHoraAlta;

		} catch (Exception e) {
			log.error("e: ", e);
			return null;
		}

	}



	public Object soapConsumptionWithHeader(String url, String soapAction, String idSessionHeader, String requestMessage) {
		try {

			// transformer factory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");

			// message to send
			StringReader stringReaderForSource = new StringReader(requestMessage);
			StringReader stringReaderForSourceToLog = new StringReader(requestMessage);
			StreamSource streamSource = new StreamSource(stringReaderForSource);

			// message to receive
			StringWriter writerForResult = new StringWriter();
			StreamResult streamResult = new StreamResult(writerForResult);

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
				scanner = new Scanner(stringReaderForSourceToLog);
				scanner.useDelimiter("\\A");
				sRFSToLogStr = scanner.hasNext() ? scanner.next() : "";
				scanner.close();
				log.info("requestMessage: {}", sRFSToLogStr);
			} catch (Exception e) {
				log.error("e: ", e);
			}


			// execute soap service call
			Object responseObject = webServiceTemplate.sendAndReceive(
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
								log.error("e:", e);
							}
							log.info("debug, sender, documentDebug_Ini: {}", stringWriterForResultDebug_Ini.toString());
							// debug, end


							// populate soap-body
							Result senderResult = webServiceMessage.getPayloadResult();
							try {
								transformer.transform(streamSource, senderResult);	
							} catch (Exception e) {
								log.error("e: ", e);
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
								log.error("e: ", e);
							}

							// soap-envelope    	            	
							try {
								senderSOAPEnvelope.addNamespaceDeclaration("tem", "http://tempuri.org");
							} catch (Exception e) {
								log.error("e: ", e);
							}

							// soap-header
							try {
								SOAPElement roClientIDHeaderHSE = senderSOAPHeader.addChildElement("ROClientIDHeader","tem");  // "ROClientIDHeader"
								SOAPElement idHSE = roClientIDHeaderHSE.addChildElement("ID", "tem");
								if ( idSessionHeader != null && !idSessionHeader.isEmpty() ) {
									idHSE.addTextNode(idSessionHeader);
								}
							} catch (Exception e) {
								log.error("e: ", e);
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
								log.error("e: ", e);
							}

							// debug, begin
							Document documentDebug_Fin = senderSoapMessage.getDocument(); 
							DOMSource domSourceDebug_Fin = new DOMSource(documentDebug_Fin);
							StringWriter stringWriterForResultDebug_Fin = new StringWriter();
							StreamResult streamResultDebug_Fin = new StreamResult(stringWriterForResultDebug_Fin);
							try {
								transformer.transform(domSourceDebug_Fin, streamResultDebug_Fin);
							} catch (Exception e) {
								log.error("e:", e);
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
								log.error("e:", e);
							}
							log.info("debug, receiver, documentDebug_Ini: {}", stringWriterForResultDebug_Ini.toString());
							// debug, end

							// do your own transforms with message.getPayloadResult() or message.getPayloadSource()
							Source receiverSource = webServiceMessage.getPayloadSource();
							try {
								transformer.transform(receiverSource, streamResult);
							} catch (Exception e) {
								log.error("e: ", e);
							}
							// SoapMessage receiverSoapMessage = (SoapMessage) webServiceMessage; // initial implementation
							String responseBodyStr = writerForResult.toString();

							return responseBodyStr;
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