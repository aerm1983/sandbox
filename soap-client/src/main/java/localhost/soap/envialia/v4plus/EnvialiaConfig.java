package localhost.soap.envialia.v4plus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import localhost.soap.envialia.pojo.ConsEnvEstadosPojo;
import localhost.soap.envialia.pojo.ConsEnvEstadosResponsePojo;
import localhost.soap.envialia.pojo.ConsEnvio2Pojo;
import localhost.soap.envialia.pojo.ConsEnvio2ResponsePojo;
import localhost.soap.envialia.pojo.LoginDep2Pojo;
import localhost.soap.envialia.pojo.LoginDep2ResponsePojo;



@Configuration
public class EnvialiaConfig {

	private static Logger log = LogManager.getLogger(EnvialiaConfig.class);


	@Bean
	public EnvialiaHelper<LoginDep2Pojo,LoginDep2ResponsePojo> LoginDep2Helper() {

		String idSessionHeader = new String();
		LoginDep2Pojo loginDep2Pojo = new LoginDep2Pojo();
		LoginDep2ResponsePojo loginDep2ResponsePojo = new LoginDep2ResponsePojo();
		EnvialiaHelper<LoginDep2Pojo,LoginDep2ResponsePojo> loginDep2Helper = null;

		try {
			loginDep2Helper = new EnvialiaHelper<LoginDep2Pojo,LoginDep2ResponsePojo>(idSessionHeader, loginDep2Pojo, loginDep2ResponsePojo);

		} catch (Exception e) {
			log.error("e: {}", e.getMessage());
		}

		return loginDep2Helper;
	}



	@Bean
	public EnvialiaHelper<ConsEnvio2Pojo,ConsEnvio2ResponsePojo> ConsEnvio2Helper() {

		String idSessionHeader = new String();
		ConsEnvio2Pojo consEnvio2Pojo = new ConsEnvio2Pojo();
		ConsEnvio2ResponsePojo consEnvio2ResponsePojo = new ConsEnvio2ResponsePojo();
		EnvialiaHelper<ConsEnvio2Pojo,ConsEnvio2ResponsePojo> consEnvio2Helper = null;

		try {
			consEnvio2Helper = new EnvialiaHelper<ConsEnvio2Pojo,ConsEnvio2ResponsePojo>(idSessionHeader, consEnvio2Pojo, consEnvio2ResponsePojo);

		} catch (Exception e) {
			log.error("e: {}", e.getMessage());
		}

		return consEnvio2Helper;
	}


	@Bean
	public EnvialiaHelper<ConsEnvEstadosPojo,ConsEnvEstadosResponsePojo> ConsEnvEstadosHelper() {

		String idSessionHeader = new String();
		ConsEnvEstadosPojo consEnvEstadosPojo = new ConsEnvEstadosPojo();
		ConsEnvEstadosResponsePojo consEnvEstadosResponsePojo = new ConsEnvEstadosResponsePojo();
		EnvialiaHelper<ConsEnvEstadosPojo,ConsEnvEstadosResponsePojo> consEnvEstadosHelper = null;

		try {
			consEnvEstadosHelper = new EnvialiaHelper<ConsEnvEstadosPojo,ConsEnvEstadosResponsePojo>(idSessionHeader, consEnvEstadosPojo, consEnvEstadosResponsePojo);

		} catch (Exception e) {
			log.error("e: {}", e.getMessage());
		}

		return consEnvEstadosHelper;
	}


}