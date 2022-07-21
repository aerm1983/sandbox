#! /bin/bash

api_key='aSBhbSBhbiBhcGkta2V5IHN0cmluZwo='

api_key_secret='aSBhbSBhIHNlY3JldCBzdHJpbmchCg=='

nonce=$(date +%s) # prod
# nonce='1645476399' # debug

d='{'
d=${d}'"codigo":"00",'
d=${d}'"mensajeCliente":"OPERACION EXITOSA",'
d=${d}'"mensajeSistema":"OPERACION EXITOSA",'
d=${d}'"referenciaBancoOrdenante":"430970001714",'
d=${d}'"referenciaBancoBeneficiario":"430970001714",'
d=${d}'"tipo":"R",'
d=${d}'"bancoOrdenante":"0104",'
d=${d}'"bancoBeneficiario":"0105",'
d=${d}'"idCliente":"V000000001234567",'
d=${d}'"numeroCliente":"00584241234104",'
d=${d}'"numeroComercio":"00584143180388",'
d=${d}'"idComercio":"J000000405175621",'
d=${d}'"fecha":"20201104",'
d=${d}'"hora":"0948",'
d=${d}'"codigoMoneda":"0928",'
d=${d}'"monto":"55.75",'
d=${d}'"concepto":"PAGO MOVIL SMS"'
d=${d}'}'
data=${d}


method='POST'


url_domain='http://127.0.0.1'
url_port=':9000'
url_context='/mi-directorio-raiz'
url_path='/p2p/v1/registro'
url=${url_domain}${url_port}${url_context}${url_path}


api_signature_input="${url_path}${nonce}${data}${api_key_secret}" # prod
# api_signature_input="errado_api_signature_que_debe_fallar" # debug

echo "signature_input: '${api_signature_input}'"

echo ''

api_signature_output=$( echo -n "${api_signature_input}" | openssl dgst -sha384 -binary | base64 )

echo 'calculated_signature: '${api_signature_output}

echo ''

hl=""
hl=${hl}" --header 'content-type: application/json'"
hl=${hl}" --header 'api-key: ${api_key}'"
hl=${hl}" --header 'nonce: ${nonce}'"
hl=${hl}" --header 'api-signature: ${api_signature_output}'"
header_list=${hl}


cmd='curl -s -i --request '${method}
cmd=${cmd}' '${header_list}
cmd=${cmd}" --data '"${data}"'"
cmd=${cmd}" --url '"${url}"'"


echo -e $cmd '\n'

eval $cmd

echo -e '\n'

echo 'done!'
