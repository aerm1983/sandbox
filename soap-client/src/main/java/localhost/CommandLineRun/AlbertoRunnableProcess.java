package localhost.CommandLineRun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

// import localhost.SoapFull.ShippingManager;
import localhost.SoapMin.MinWSClientOne;
import localhost.SoapMin.PrevWriterReaderXmlSoapTests;

@Component
public class AlbertoRunnableProcess implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(AlbertoRunnableProcess.class);
	
	// @Autowired
	// private ShippingManager shippingManager;

	@Autowired
	PrevWriterReaderXmlSoapTests prevWriterReaderXmlSoap;
	
	@Autowired
	private MinWSClientOne minWSClientOne;
	
	@Override
	public void run(String... args) throws Exception {
		main(args);
	}

	public void main(String[] args) {
		// TODO Auto-generated method stub
		
		log.debug("debugging RP!");
		log.info("hello world RP!");
		
		// ResponseEntity<?> response = shippingManager.getStatusModified();
		// log.info("response: {}", response);
		
		// prevWriterReaderXmlSoap.smallXmlTransformTest();
		
		// prevWriterReaderXmlSoap.smallStringWriterReaderTest();
		
		/* 
		try {
			prevWriterReaderXmlSoap.smallFileWriterReaderTest();
		} catch (Exception e) {
			log.error("e: ", e);
		}
		*/
		
		minWSClientOne.simpleSoapConsumption();

	}

}
