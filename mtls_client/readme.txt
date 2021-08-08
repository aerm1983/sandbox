= = = = = = = = = = = = = = =
MUTUAL TLS CLIENT REFERENCE
= = = = = = = = = = = = = = =


-> create keystore; "winpty" required if shell is gitbash
winpty openssl pkcs12 -export -in './client_cert_chain.pem' -inkey './client_key.pem' -name "mtls_client_key" -out './client_keystore.p12'


-> view keystore contents; "winpty" required if shell is gitbash
winpty openssl pkcs12 -info -in ./client_keystore.p12


-> view keystore contents
keytool -list -v -keystore './client_keystore.p12'


-> test url connectivity 
curl -s -v -X POST --key './client_key.pem' --cert './client_cert.pem' -H "content-type: application/json" --data '{"tokenRequestorID":"40010059239","tokenReferenceID":"DNITHE302030974091057746","deviceBindingInfo":true,"cardHolderVerificationRetrieve":true}' --url https://cert-api.novopayment.com/itsp/do-cibao/vtis/v1/tokens/details
