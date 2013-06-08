#!/bin/bash
mvn clean install && cp target/dmk-websocket.war ~/tomcat8.dev/webapps/.
