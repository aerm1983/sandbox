package localhost;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlbertoJmsAmqConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(AlbertoJmsAmqConsumer.class);

	public static void main(String[] args) {
		
		try {

			log.info("main program - JMS AMQ Consumer - begin");
			
			// ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("ws://localhost");
			
			ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			
			
			Connection conn = connFactory.createConnection();
			
			conn.start();
			
			conn.setExceptionListener(null); // this
			
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("alberto_queue");
			
			MessageConsumer mConsumer = session.createConsumer(destination) ;
			
			Message message = mConsumer.receive(1000);
			
			log.info("message received");
			
			TextMessage textMessage = null;
			String text = null;
			
			if ( message instanceof TextMessage ) {
				textMessage = (TextMessage) message;
				text = textMessage.getText();
				log.info("message content: " + text);
			} else {
				log.info("message content: " + message);
			}
			
			mConsumer.close();
			session.close();
			conn.close();
			
			log.info("main program - JMS AMQ Consumer - end");
			
		} catch (Exception e) {
			
			log.error("e: " + e);
			
		}
		
	}

}
