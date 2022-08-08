#! /bin/bash

# first run: source ./__alias_custom.sh
# after: use alias 0

# custom aliases - deployment
alias 00='clear ; ./__taskkill.sh ; mvn clean package ; java -jar ./target/api-server-mockup.jar & '
alias 01='./__taskkill.sh '

# custom alias - reload this script
alias 0='source ./__alias_custom.sh'

# custom aliases - requests
alias 10='./__curl-alberto01.sh'
alias 11='./__curl-udemy01-helloworld.sh'

alias 20='./__curl-udemy01-get-users.sh'
alias 21='./__curl-udemy01-get-one-user.sh'
alias 22='./__curl-udemy01-save-user.sh'
alias 23='./__curl-udemy01-delete-user.sh'


# print aliases
alias 00 01 0
echo
alias 10 11 
echo
alias 20 21 22 23
echo

echo 'done!'
