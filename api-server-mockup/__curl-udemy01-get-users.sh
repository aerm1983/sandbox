#! /bin/bash

method='GET'

url_domain='http://127.0.0.1:9000'
path_variable=''
url_path='/udemy01/users/'${path_variable}


headers_list="
--header 'content-type: application/json'
--header 'x-request-id: 1234'
"

data='{"codigo":"00","mensajeCliente":"OPERACION EXITOSA"}'


cmd='curl -s -i --request '${method}
cmd=${cmd}' '${headers_list}
cmd=${cmd}" --data '"${data}"'"
cmd=${cmd}' --url "'${url_domain}${url_path}'"'

echo $cmd

eval $cmd

echo -e "\n"

echo 'done!'
