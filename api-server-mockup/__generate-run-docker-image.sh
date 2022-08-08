#!/bin/bash

clear
mvn clean install
docker container stop my_container
docker container rm -f my_container
docker image rm -f my_image
docker build --tag my_image:1.0 .
docker run --publish 8000:8000 --name my_container my_image:1.0 &  

