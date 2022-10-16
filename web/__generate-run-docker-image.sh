#!/bin/bash -x

# --tag {user}/{name}:{version}
# example: --tag local/myimage:0
# {user} and {version} are optional

clear

mvn clean package

docker container stop mycontainer
docker container rm -f mycontainer

docker image rm -f myimage
docker build --tag myimage . 

docker run --publish 10000:10000 --name mycontainer myimage > log.log & 

