#!/bin/bash -x

clear
mvn clean package
docker container stop mycontainer
docker container rm -f mycontainer
docker image rm -f myimage:0
docker build --tag myimage:0 .
docker run --publish 10000:10000 --name mycontainer myimage:0 > log.log & 

