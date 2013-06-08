dmk-websocket-sample
====================

Tinkering with websockets (JSR 356), deploy this to tomcat 8 or tyrus grizzly reference implemenation

rm -rf ~/tomcat8.dev/webapps/dmk-websocket*
mvn clean install && cp ../target/dmk-websocket-sample.war ~/tomcat8.dev/webapps/.
