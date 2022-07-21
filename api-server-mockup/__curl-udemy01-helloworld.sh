#! /bin/bash

method='POST'

url_domain='http://127.0.0.1:9000'
path_variable='alberto'
url_path='/udemy01/helloworld/'${path_variable}


headers_list="
--header 'content-type: application/json'
--header 'x-request-id: 1234'
"

data='{"codigo":"00","mensajeCliente":"OPERACION EXITOSA"}'



cmd='curl -s -i --request '${method}' '${headers_list}' --data '"'"${data}"'"' --url '"'"${url_domain}${url_path}"'"

echo $cmd

eval $cmd

echo -e "\n"

echo 'done!'
