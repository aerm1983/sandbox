#! /bin/bash

method='GET'


url_domain='http://127.0.0.1'
url_port=':9000'
url_context=''
url_path_variable='/10'
url_path='/udemy01/users'${url_path_variable}
url=${url_domain}${url_port}${url_context}${url_path}


hl=""
hl=${hl}" --header 'content-type: application/json'"
hl=${hl}" --header 'x-request-id: 1234'"
header_list=${hl}


d=''
d=${d}'{'
d=${d}'"id":10,'
d=${d}'"name":"Juan",'
d=${d}'"birthDate":"Juan"'
d=${d}'}'
data=${d}


cmd='curl -s -i --request '${method}
cmd=${cmd}' '${header_list}
# cmd=${cmd}" --data '"${data}"'"
cmd=${cmd}" --url '"${url}"'"

echo -e $cmd '\n'

eval $cmd

echo -e '\n'

echo 'done!'
