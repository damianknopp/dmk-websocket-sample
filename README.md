dmk-websocket-sample
====================

Tinkering with websockets (JSR 356), deploy this to tomcat 8 or tyrus grizzly reference implemenation

rm -rf ~/tomcat8.dev/webapps/dmk-websocket*

mvn clean install && cp ../target/dmk-websocket-sample.war ~/tomcat8.dev/webapps/.

Note: 

EchoEndpoint onOpen failed to deploy in Tomcat 8 with an additional parameter

  @OnOpen
  public void onOpen(Session session, EndpointConfig config)
  
  Instead it had to look like this, 

  @OnOpen
  public void onOpen(Session session)
  
  
  otherwise you get an IllegalArgumentException in PojoMethodMapper, it complains about not having a PathParam annotation
  
  