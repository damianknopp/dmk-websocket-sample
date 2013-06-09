#!/bin/bash
ant
rm -rf ./tomcat8.dev
mkdir ./tomcat8.dev
cp -R output/build/* tomcat8.dev
tar -cvf tomcat8.dev.tar tomcat8.dev
gzip tomcat8.dev.tar 
rm -rf ~/tomcat8.dev
cp -R tomcat8.dev ~/.
