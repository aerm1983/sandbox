#! /bin/bash

api_key='aSBhbSBhbiBhcGkta2V5IHN0cmluZwo='

api_key_secret='aSBhbSBhIHNlY3JldCBzdHJpbmchCg=='

nonce=$(date +%s) # prod
# nonce='1645476399' # debug

data='{"codigo":"00","mensajeCliente":"OPERACION EXITOSA","mensajeSistema":"OPERACION EXITOSA","referenciaBancoOrdenante":"430970001714","referenciaBancoBeneficiario":"430970001714","tipo":"R","bancoOrdenante":"0104","bancoBeneficiario":"0105","idCliente":"V000000001234567","numeroCliente":"00584241234104","numeroComercio":"00584143180388","idComercio":"J000000405175621","fecha":"20201104","hora":"0948","codigoMoneda":"0928","monto":"55.75","concepto":"PAGO MOVIL SMS"}'

method='POST'
url_domain='http://127.0.0.1:9000/mi-directorio-raiz'
url_path='/p2p/v1/registro'

api_signature_input="${url_path}${nonce}${data}${api_key_secret}" # prod
# api_signature_input="errado_api_signature_que_debe_fallar" # debug

echo "signature_input: '${api_signature_input}'"

echo ''

api_signature_output=$( echo -n "${api_signature_input}" | openssl dgst -sha384 -binary | base64 )

echo 'calculated_signature: '${api_signature_output}

echo ''

headers_list="
--header 'content-type: application/json'
--header 'api-key: ${api_key}'
--header 'nonce: ${nonce}'
--header 'api-signature: ${api_signature_output}'
"

cmd='curl -s -i --request '${method}
cmd=${cmd}' '${headers_list}
cmd=${cmd}" --data '"${data}"'"
cmd=${cmd}' --url "'${url_domain}${url_path}'"'


echo $cmd

eval $cmd

echo -e "\n"

echo 'done!'
