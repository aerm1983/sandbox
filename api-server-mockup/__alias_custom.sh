#! /bin/bash

# suggested alias:
# alias 0='source ./__alias_custom.sh'

# custom aliases - deployment
alias 00='clear ; ./__taskkill.sh ; mvn clean package ; java -jar ./target/api-server-mockup.jar & '
alias 01='./__taskkill.sh '


# custom aliases - requests
alias 10='./__curl-alberto01.sh'
alias 11='./__curl-udemy01-helloworld.sh'
alias 20='./__curl-udemy01-get-users.sh'
alias 21='./__curl-udemy01-get-one-user.sh'
alias 22='./__curl-udemy01-save-user.sh'


# print aliases
alias 00 01 
echo
alias 10 11 
echo
alias 20 21 22
echo


echo 'done!'
