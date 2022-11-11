package localhost;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlbertoJmsAmqProducer {
	
	private static final Logger log = LoggerFactory.getLogger(AlbertoJmsAmqProducer.class);

	public static void main(String[] args) {
		
		try {

			log.info("main program - JMS AMQ Producer - begin");
			
			// ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("ws://localhost");
			
			ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			
			
			Connection conn = connFactory.createConnection();
			
			conn.start();
			
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("TEST.FOO");
			
			MessageProducer mProducer = session.createProducer(destination) ;
			
			mProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			String text = "Hello World! From: " + Thread.currentThread().getName() + " : "; // + this.hashCode();
			
			TextMessage textMessage = session.createTextMessage(text);
			
			mProducer.send(textMessage);
			
			log.info("message sent");
			
			session.close();
			
			conn.close();
			
			log.info("main program - JMS AMQ Producer - end");
			
		} catch (Exception e) {
			
			log.error("e: " + e);
			
		}
		
	}

}
