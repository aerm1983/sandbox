#! /bin/bash

method='POST'

url_domain='http://127.0.0.1'
url_port=':10000'
url_context=''
path_variable='alberto'
url_path='/udemy01/helloworld/'${path_variable}
url=${url_domain}${url_port}${url_context}${url_path}


hl=""
hl=${hl}" --header 'content-type: application/json'"
hl=${hl}" --header 'x-request-id: 1234'"
header_list=${hl}


d=''
d=${d}'{'
d=${d}'"codigo":"00",'
d=${d}'"mensajeCliente":"OPERACION EXITOSA"'
d=${d}'}'
data=${d}



cmd='curl -s -i --request '${method}
cmd=${cmd}' '${header_list}
cmd=${cmd}" --data '"${data}"'"
cmd=${cmd}" --url '"${url}"'"


echo -e $cmd '\n'

eval $cmd

echo -e "\n"

echo 'done!'
