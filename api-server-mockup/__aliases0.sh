#! /bin/bash

# first run: source ./__aliases0.sh
# after: use alias 0


# custom alias - reload this script
alias 0='source ./__aliases0.sh'


# custom aliases - deployment
alias 00=' { 04 ; 01 ; } && { 02 ; } '
alias 01=' mvn clean package '
# alias 01=' mvn clean package -Dmaven.test.skip '
alias 02=' jarfile=$( ls ./target/*.jar ) ; str="java -jar ${jarfile} & " ; eval "$str" '

function f04 { jarfile=$( ls ./target/*.jar ) ; taskkill //f //pid $( jps -l | grep ${jarfile} | awk ' // { print $1 }' ) ; }
alias 04=' f04 '

alias 05=' jarfile=$( ls ./target/*.jar ) ;  jps -l | grep -v target ; echo ;  jps -l | grep target ; echo ; jps -l | grep ${jarfile} '

alias 09='. ./__generate-run-docker-image.sh'

# custom aliases - requests
alias 10='./__curl-alberto01.sh'
alias 11='./__curl-udemy01-helloworld.sh'

alias 20='./__curl-udemy01-get-users.sh'
alias 21='./__curl-udemy01-get-one-user.sh'
alias 22='./__curl-udemy01-save-user.sh'
alias 23='./__curl-udemy01-delete-user.sh'




# print aliases
alias 0
echo
alias 00 01 02
echo
alias 04
type f04
echo
alias 05
echo
alias 09
echo
alias 10 11 
echo
alias 20 21 22 23
echo

echo 'done!'
