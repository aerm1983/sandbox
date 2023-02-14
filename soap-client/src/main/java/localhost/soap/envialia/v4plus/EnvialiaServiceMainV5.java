package localhost.soap.envialia.v4plus;

import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import localhost.__gitignore.envialia.credentials.EnvialiaCredentials;
import localhost.soap.envialia.pojo.ConsEnvEstadosPojo;
import localhost.soap.envialia.pojo.ConsEnvEstadosResponsePojo;
import localhost.soap.envialia.pojo.ConsEnvio2Pojo;
import localhost.soap.envialia.pojo.ConsEnvio2ResponsePojo;
import localhost.soap.envialia.pojo.LoginDep2Pojo;
import localhost.soap.envialia.pojo.LoginDep2ResponsePojo;



@Service
public class EnvialiaServiceMainV5 {
	
	private static Logger log = LogManager.getLogger(EnvialiaServiceMainV5.class);
    
    private static final String strCodAge = EnvialiaCredentials.strCodAge;
    private static final String strCodCli = EnvialiaCredentials.strCodCli;
    private static final String strDepartamento = EnvialiaCredentials.strDepartamento;
    private static final String strPass = EnvialiaCredentials.strPass;
    private static final String tracking = EnvialiaCredentials.tracking;
    
    @Autowired
    EnvialiaHelper<LoginDep2Pojo,LoginDep2ResponsePojo> loginDep2Helper;
    
    @Autowired
    EnvialiaHelper<ConsEnvio2Pojo,ConsEnvio2ResponsePojo> consEnvio2Helper;
    
    @Autowired
    EnvialiaHelper<ConsEnvEstadosPojo,ConsEnvEstadosResponsePojo> consEnvEstadosHelper;
    
    
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
        	
    		ObjectMapper objectMapper = new ObjectMapper()
    				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		
        	String idSessionHeader = null;
    		String requestSoapBodyXml = null;
        	String responseSoapBodyXml = null;
        	String responseSoapBodyJson = null;
        	
            
        	
        	// ENVIALIA, LoginDep2
            
        	idSessionHeader = null;
        	LoginDep2Pojo loginDep2Pojo = new LoginDep2Pojo();
    		loginDep2Pojo.setStrCodAge(strCodAge);
    		loginDep2Pojo.setStrCodCli(strCodCli);
    		loginDep2Pojo.setStrDepartamento(strDepartamento);
    		loginDep2Pojo.setStrPass(strPass);

    		loginDep2Helper.setIdSessionHeader(idSessionHeader);
    		loginDep2Helper.setRequestPojo(loginDep2Pojo);
    		
    		requestSoapBodyXml = loginDep2Helper.generateRequestSoapBodyXml();
    		log.info("requestSoapBodyXml: {}", requestSoapBodyXml);

    		responseSoapBodyXml = (String) loginDep2Helper.generateResponseSoapBodyXml();
        	log.info("responseSoapBodyXml: {}", responseSoapBodyXml);
        	
        	LoginDep2ResponsePojo loginDep2ResponsePojo = (LoginDep2ResponsePojo) loginDep2Helper.generateResponseSoapBodyPojo();
        	responseSoapBodyJson = objectMapper.writeValueAsString(loginDep2ResponsePojo);
        	log.info("responseSoapBodyJson, LoginDep2: {}", responseSoapBodyJson);
        	
        	if (loginDep2ResponsePojo == null || loginDep2ResponsePojo.getStrSesion() == null || loginDep2ResponsePojo.getStrSesion().isEmpty()) {
        		log.info("respuesta de Envialia no válida - retornar null");
        		return null;
        	}

        	
        	
        	// ENVIALIA, ConsEnvio2
        	
        	idSessionHeader = loginDep2ResponsePojo.getStrSesion();
    		ConsEnvio2Pojo consEnvio2Pojo = new ConsEnvio2Pojo();
    		consEnvio2Pojo.setStrCodAgeCargo(strCodAge);
    		consEnvio2Pojo.setStrAlbaran(albaran);
    		
    		consEnvio2Helper.setIdSessionHeader(idSessionHeader);
    		consEnvio2Helper.setRequestPojo(consEnvio2Pojo);
    		
    		requestSoapBodyXml = consEnvio2Helper.generateRequestSoapBodyXml();
    		log.info("requestSoapBodyXml: {}", requestSoapBodyXml);
    		
    		responseSoapBodyXml = consEnvio2Helper.generateResponseSoapBodyXml();
        	log.info("responseSoapBodyXml: {}", responseSoapBodyXml);
        	
        	ConsEnvio2ResponsePojo consEnvio2ResponsePojo = (ConsEnvio2ResponsePojo) consEnvio2Helper.generateResponseSoapBodyPojo();
        	responseSoapBodyJson = objectMapper.writeValueAsString(consEnvio2ResponsePojo);
        	log.info("responseSoapBodyJson, ConsEnvio2: {}", responseSoapBodyJson);
        	
        	if ( consEnvio2ResponsePojo == null || consEnvio2ResponsePojo.getStrEnvio() == null || consEnvio2ResponsePojo.getStrEnvio().isEmpty() ) {
        		log.info("respuesta de Envialia no válida - retornar null");
        		return null;
        	}
        	
        	
        	
        	// ENVIALIA, ConsEnvio2, obtain V_COD_AGE_ORI Y V_COD_AGE_CARGO
        	
        	String strEnvio = consEnvio2ResponsePojo.getStrEnvio();
        	
        	Map<String,String> agenciesMap = consEnvio2Helper.obtainAgencyCodes(strEnvio);
            
        	String vCodAgeOri = null;
            String vCodAgeCargo = null;

            try {
            	vCodAgeOri = agenciesMap.get("vCodAgeOri");
            	vCodAgeCargo = agenciesMap.get("vCodAgeCargo");
            } catch (Exception e) {
                log.error("e: ", e);
        		log.info("respuesta de Envialia no válida - retornar null");
        		return null;
            }
        	
            
            
        	// ENVIALIA, ConsEnvEstados
            
    		idSessionHeader = loginDep2ResponsePojo.getStrSesion();
            ConsEnvEstadosPojo consEnvEstadosPojo = new ConsEnvEstadosPojo();
    		consEnvEstadosPojo.setStrCodAgeCargo(vCodAgeCargo);
    		consEnvEstadosPojo.setStrCodAgeOri(vCodAgeOri);
    		consEnvEstadosPojo.setStrAlbaran(albaran);
    		
    		consEnvEstadosHelper.setIdSessionHeader(idSessionHeader);
    		consEnvEstadosHelper.setRequestPojo(consEnvEstadosPojo);
    		
    		requestSoapBodyXml = consEnvEstadosHelper.generateRequestSoapBodyXml();
    		log.info("requestSoapBodyXml: {}", requestSoapBodyXml);
    		
    		responseSoapBodyXml = consEnvEstadosHelper.generateResponseSoapBodyXml();
        	log.info("responseSoapBodyXml: {}", responseSoapBodyXml);

    		ConsEnvEstadosResponsePojo consEnvEstadosResponsePojo = (ConsEnvEstadosResponsePojo) consEnvEstadosHelper.generateResponseSoapBodyPojo();
        	responseSoapBodyJson = objectMapper.writeValueAsString(consEnvEstadosResponsePojo);
        	log.info("responseSoapBodyJson, ConsEnvEstados: {}", responseSoapBodyJson);
        	
        	if ( consEnvEstadosResponsePojo == null || consEnvEstadosResponsePojo.getStrEnvEstados() == null || consEnvEstadosResponsePojo.getStrEnvEstados().isEmpty() ) {
        		log.info("respuesta de Envialia no válida - retornar null");
        		return null;
        	}
        	
        	// ENVIALIA, ConsEnvEstados, obtain delivery date, V_COD_TIPO_EST="4", D_FEC_HORA_ALTA

        	String strEnvEstados = consEnvEstadosResponsePojo.getStrEnvEstados();
            
        	String dFechaHoraAlta = consEnvEstadosHelper.obtainDeliveryDateTime(strEnvEstados);

            if (dFechaHoraAlta == null || dFechaHoraAlta.isEmpty()) {
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

}