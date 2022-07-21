#! /bin/bash

# suggested alias:
# alias 0='source ./__alias_custom.sh'

# custom aliases - deployment
alias 00='clear ; ./__taskkill.sh ; mvn clean package ; java -jar ./target/api-server-mockup.jar &'
alias 01='./__taskkill.sh '


# custom aliases - requests
alias 10='./__curl-alberto01.sh'
alias 11='./__curl-udemy01-helloworld.sh'
alias 12='./__curl-udemy01-get-users.sh'


# print aliases
alias 00 01 
echo 
alias 10 11 12

echo 'done!'
