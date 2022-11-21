package localhost.CommandLineRun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import localhost.SoapMinAuxiliar.PrevWriterReaderXmlSoapTests;
import localhost.SoapMinMain.MinWSClientOne;

@Component
public class MinSoapCommandLineRunner implements CommandLineRunner{
	
	private static Logger log = LoggerFactory.getLogger(MinSoapCommandLineRunner.class);
	
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
		
		// log.info("hello world RP!");
		
		/* 
		try {
			prevWriterReaderXmlSoap.smallFileWriterReaderTest();
		} catch (Exception e) {
			log.error("e: ", e);
		}
		*/
		
		// prevWriterReaderXmlSoap.smallXmlTransformTest();
		
		// prevWriterReaderXmlSoap.pojoToXml();
		// prevWriterReaderXmlSoap.xmlToPojo();;
		
		minWSClientOne.simpleSoapConsumptionPojoWrapper("LoginDep2");

	}

}
