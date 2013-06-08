package dmk.websocket.server;

import static javax.websocket.CloseReason.CloseCodes.GOING_AWAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * server test code taken and modified from, 
 * 	https://tyrus.java.net/documentation/1.0/user-guide.html
 * @author dmknopp
 *
 * Remove the Ignore once you deployed to tomcat8 and then run client
 */
@Ignore
public class EchoServerTest {
	private static final Logger logger = LoggerFactory
			.getLogger(EchoServerTest.class);

	private CountDownLatch messageLatch;
	private static final String SENT_MESSAGE = "Hello World";
	private final int numMessages = 5;
	
	private final int port = 8080;
	
	@Before
	public void setup() throws Exception{
		messageLatch = new CountDownLatch(numMessages);
		if(logger.isDebugEnabled()){
			logger.debug("setting up websocket server");
		}
	}
	
	@After public void breakdown(){
		if(logger.isDebugEnabled()){
			logger.debug("stopping websocket server");
		}
	}

	@Test
	public void sendMessages() throws Exception {
		final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
				.build();
		ClientManager client = ClientManager.createClient();
		client.connectToServer(new Endpoint() {

			@Override
			public void onOpen(Session session, EndpointConfig config) {
				session.addMessageHandler(new MessageHandler.Whole<String>() {

					@Override
					public void onMessage(String message) {
						assertNotNull(message);
						assertEquals(SENT_MESSAGE, message);
						messageLatch.countDown();
						if (logger.isDebugEnabled()) {
							logger.debug("client recieved message: " + message);
						}

					}
				});
				
				try{
					for(int i = 0; i < numMessages; i++){
						session.getBasicRemote().sendText(SENT_MESSAGE);
					}
				} catch(IOException ioe){
					throw new RuntimeException(ioe.getMessage());
				} finally{
					try {
						CloseReason cr = new CloseReason(GOING_AWAY,
								"bye");
						session.close(cr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, cec, new URI(String.format("ws://localhost:%d/dmk-websocket/echo", this.port)));

		messageLatch.await(1, TimeUnit.SECONDS);
	}
}
