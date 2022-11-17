#!/bin/bash
# EXAMPLE CURL SOAP -- updated 2022-11-14

'https://demo5636922.mockable.io/'
'http://demo5636922.mockable.io/'
'http://demo5636922.mockable.io/http://demo5636922.mockable.io/'
    --header "SOAPAction:Get" \


curl -s -i \
    -X POST \
    --header "Content-Type: text/xml;charset=UTF-8" \
    --data '' \
    --url 'http://demo5636922.mockable.io/http://demo5636922.mockable.io/'


# request
: '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inc="http://www.service-now.com/incident">
    <soapenv:Header/>
    <soapenv:Body>
        <inc:get>
            <sys_id>0</sys_id>
        </inc:get>
    </soapenv:Body>
</soapenv:Envelope>' 

# response
: '<?xml version="1.0"?>
<soap:Envelope
xmlns:soap="http://www.w3.org/2001/12/soap-envelope" 
  soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
<soap:Body xmlns:m="http://www.example.org/stock">
  <m:GetStockPriceResponse>
    <m:Price>34.5</m:Price>
  </m:GetStockPriceResponse>
</soap:Body>
</soap:Envelope>'



# from stack overflow: https://stackoverflow.com/questions/12222607/how-to-do-a-soap-wsdl-web-services-call-from-the-command-line
: curl -X POST -H "Content-Type: text/xml" -H 'SOAPAction: "http://api.eyeblaster.com/IAuthenticationService/ClientLogin"' --data-binary @request.xml https://sandbox.mediamind.com/Eyeblaster.MediaMind.API/V2/AuthenticationService.svc
: '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:api="http://api.eyeblaster.com/">
    <soapenv:Header/>
        <soapenv:Body>
            <api:ClientLogin>
                <api:username>user</api:username>
                    <api:password>password</api:password>
                <api:applicationKey>key</api:applicationKey>
            </api:ClientLogin>
        </soapenv:Body>
</soapenv:Envelope>'

: '<?xml version="1.0"?>
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
  <s:Body>
    <s:Fault>
      <faultcode>s:Security.Authentication.UserPassIncorrect</faultcode>
      <faultstring xml:lang="en-US">The username, password or application key is incorrect.</faultstring>
    </s:Fault>
  </s:Body>
</s:Envelope>'
