#!/bin/bash -x

# --tag {user}/{name}:{version}
# example: --tag local/myimage:0
# {user} and {version} are optional

clear

sj8
sleep 2
mvn clean package -Dmaven.test.skip=true

eval $( minikube --profile minikube docker-env ) # mde
docker info | command grep -P -i name

docker image rm -f myimage
docker build --file ./k8s._Dockerfile --tag myimage . 

docker info | command grep -P -i name
docker image ls


kubectl delete -f k8s.deployment.service.yaml
sleep 2
kubectl delete -f k8s.configmap.yaml
sleep 2
kubectl delete -f k8s.secret.yaml


kubectl apply -f k8s.secret.yaml
sleep 2
kubectl apply -f k8s.configmap.yaml
sleep 2
kubectl apply -f k8s.deployment.service.yaml
sleep 5


kubectl get node
kubectl get pod
kubectl get service
echo 'wait to expose myimage-service...'
sleep 15


minikube service myimage-service &
