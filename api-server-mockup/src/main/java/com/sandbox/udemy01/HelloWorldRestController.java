package com.sandbox.udemy01;

// curl -s -i -X 'POST' -H 'content-type:application/json' --data '{"name":"Alberto"}' --url 'http://localhost:4000/rest/'
// curl -s -i --request POST --header 'content-type: application-json' --header 'api-key: aSBhbSBhbiBhcGkta2V5IHN0cmluZwo=' --header 'nonce: 1645467904' --header 'api-signature: OLBgp1GsljhM2TJ+sbHjaiH9txEUvgdDTAzHv2P24donTt6/529l+9Ua0vFImLlb' --data '{"codigo":"00","mensajeCliente":"OPERACION EXITOSA","mensajeSistema":"OPERACIÓN EXITOSA","referenciaBancoOrdenante":"430970001714","referenciaBancoBeneficiario":"430970001714","tipo":"R","bancoOrdenante":"0104","bancoBeneficiario":"0105","idCliente":"V000000001234567","numeroCliente":"00584241234104","numeroComercio":"00584143180388","idComercio":"J000000405175621","fecha":"20201104","hora":"0948","codigoMoneda":"0928","monto":"55.75","concepto":"PAGO MOVIL SMS"}' --url 'http://127.0.0.1:9000/mi-directorio-raiz/p2p/v1/registro'

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@RestController
@RequestMapping("/")
public class HelloWorldRestController {

	private static final Logger log = LoggerFactory.getLogger(HelloWorldRestController.class);
	private static Gson gson = new Gson();
	
	
	@PostMapping(
			path = { "/udemy01/helloworld/{myName}" },
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
		)
	public String controller ( 
			// MyHttpServletRequestWrapper myHttpServletRequestWrapper,
			@RequestHeader HttpHeaders requestHeaders, 
			@RequestBody Object requestBody,
			@PathVariable String myName
	) {
		
		return String.format("helloworld %s", myName) ;
	}
}
