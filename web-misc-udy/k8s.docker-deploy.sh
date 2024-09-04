#!/bin/bash -x

# --tag {user}/{name}:{version}
# example: --tag local/myimage:0
# {user} and {version} are optional

clear

sj8
sleep 2
mvn clean package -Dmaven.test.skip=true

eval $( minikube --profile minikube docker-env | sed -E 's/export ([A-Z_]+)=.*/unset \1/g' ) # mdeu
docker info | command grep -P -i name

docker container stop mycontainer
docker container rm -f mycontainer
echo -n '' > log.log
wc log.log

docker image rm -f myimage
docker build --file ./k8s._Dockerfile --tag myimage . 

docker info | command grep -P -i name
docker image ls

docker run --publish 10000:10000 --name mycontainer myimage > log.log & 
sleep 5

docker container ls
head -5 log.log
command grep --color=always -P -i 'profiles.*' log.log

