package localhost.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import localhost.soap.envialia.test.TestWriterReaderXmlSoapMain;
import localhost.soap.envialia.v4plus.EnvialiaServiceMainV5;

@Component
public class SoapClientCLR implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(SoapClientCLR.class);
	
	// @Autowired
	// private ShippingManager shippingManager;

	@Autowired
	TestWriterReaderXmlSoapMain testWriterReaderXmlSoap;
	
	@Autowired 
	EnvialiaServiceMainV5 envialiaServiceMainV5;
	
	@Override
	public void run(String... args) throws Exception {
		main(args);
	}

	public void main(String[] args) {
		
		log.info("hello from {}!", this.getClass());
		
		envialiaServiceMainV5.main();
		
	}
	
}
