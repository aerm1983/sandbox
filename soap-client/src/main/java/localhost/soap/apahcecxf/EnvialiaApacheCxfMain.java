package localhost.soap.apahcecxf;

import javax.xml.soap.SOAPHeader;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

// import org.apache.cxf.endpoint.Client;
// import org.apache.cxf.frontend.ClientProxy;
// import org.apache.cxf.interceptor.LoggingInInterceptor;
// import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import localhost.__gitignore.envialia.apachecxf.pojo.Login.LoginWSService;
import localhost.__gitignore.envialia.apachecxf.pojo.Login.LoginWSServiceLoginDep2;
import localhost.__gitignore.envialia.apachecxf.pojo.Login.LoginWSServiceLoginDep2Response;
import localhost.__gitignore.envialia.apachecxf.pojo.Login.LoginWSService_Service;
import localhost.__gitignore.envialia.apachecxf.pojo.Login.ObjectFactory;
import localhost.__gitignore.envialia.apachecxf.pojo.Login.ROClientIDHeader;
import localhost.__gitignore.envialia.credentials.EnvialiaCredentials;

@Component
public class EnvialiaApacheCxfMain{

	private static Logger log = LoggerFactory.getLogger(EnvialiaApacheCxfMain.class);

	public void main() {

		log.info("hello from {}!", this.getClass());

		// This is the closest apache-cxf implementation attempted.
		// Disadvantages
		// (1) Response from server is error, but not possible to print debug/trace request
		// (2) "ClientProxy" object for debug-logging was attempted, compatibility problems, required additional dependencies.
		// (3) It seems apache-cfx does not use spring-webtemplate.

		ObjectMapper objectMapper = new ObjectMapper();

		LoginWSService_Service loginWSService_Service = new LoginWSService_Service();
		LoginWSService clientPort = loginWSService_Service.getLoginWSServicePort();

		ObjectFactory objectFactory = new ObjectFactory();
		LoginWSServiceLoginDep2 loginWSServiceLoginDep2 = objectFactory.createLoginWSServiceLoginDep2();
		ROClientIDHeader roClientIDHeader = objectFactory.createROClientIDHeader();
		// LoginWSServiceLoginDep2Response loginWSServiceLoginDep2Response = objectFactory.createLoginWSServiceLoginDep2Response();



		/*
		BindingProvider bindingProvider = (BindingProvider) clientPort;
	    // bindingProvider.getRequestContext().put(SOAPHeader, headers);
		bindingProvider.getRequestContext();
		 */



		loginWSServiceLoginDep2.setStrCodAge(EnvialiaCredentials.strCodAge);
		loginWSServiceLoginDep2.setStrCodCli(EnvialiaCredentials.strCodCli);
		loginWSServiceLoginDep2.setStrDepartamento(EnvialiaCredentials.strDepartamento);
		loginWSServiceLoginDep2.setStrPass(EnvialiaCredentials.strPass);

		roClientIDHeader.setID(null);
		Holder<ROClientIDHeader> holder = new Holder<ROClientIDHeader>();

		// Client clientProxy = ClientProxy.getClient(clientPort);
		// clientProxy.getInInterceptors().add(new LoggingInInterceptor());
		// clientProxy.getOutInterceptors().add(new LoggingOutInterceptor()); 

		// LoginWSServiceLoginDep2Response loginWSServiceLoginDep2Response = clientPort.loginDep2(loginWSServiceLoginDep2, holder); // did not work
		LoginWSServiceLoginDep2Response loginWSServiceLoginDep2Response = clientPort.loginDep2(loginWSServiceLoginDep2, null);

		String json = null;
		try {
			json = objectMapper.writeValueAsString(loginWSServiceLoginDep2Response);	
		} catch (Exception e) {
			log.error("e: ", e);
		}

		log.info("json response: {}", json);


	}

}
