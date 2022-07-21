package com.sandbox.udemy01;

// curl -s -i -X 'POST' -H 'content-type:application/json' --data '{"name":"Alberto"}' --url 'http://localhost:4000/rest/'
// curl -s -i --request POST --header 'content-type: application-json' --header 'api-key: aSBhbSBhbiBhcGkta2V5IHN0cmluZwo=' --header 'nonce: 1645467904' --header 'api-signature: OLBgp1GsljhM2TJ+sbHjaiH9txEUvgdDTAzHv2P24donTt6/529l+9Ua0vFImLlb' --data '{"codigo":"00","mensajeCliente":"OPERACION EXITOSA","mensajeSistema":"OPERACIÓN EXITOSA","referenciaBancoOrdenante":"430970001714","referenciaBancoBeneficiario":"430970001714","tipo":"R","bancoOrdenante":"0104","bancoBeneficiario":"0105","idCliente":"V000000001234567","numeroCliente":"00584241234104","numeroComercio":"00584143180388","idComercio":"J000000405175621","fecha":"20201104","hora":"0948","codigoMoneda":"0928","monto":"55.75","concepto":"PAGO MOVIL SMS"}' --url 'http://127.0.0.1:9000/mi-directorio-raiz/p2p/v1/registro'

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@RestController
@RequestMapping("/")
public class UserRestController {

	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	private static Gson gson = new Gson();
	private UserDao userDao = new UserDao();
	
	
	
	@GetMapping(
			path = { "/udemy01/users" },
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
		)
	public ArrayList<UserPojo> retrieveAllUsers ( 
			@RequestHeader HttpHeaders requestHeaders 
			// @RequestBody Object requestBody
			// @PathVariable String myName
	) {
		
		return userDao.findAll();
	}
	
	
	
	@GetMapping(
			path = { "/udemy01/users/{userId}" },
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
		)
	public UserPojo retrieveOneUser ( 
			@RequestHeader HttpHeaders requestHeaders, 
			// @RequestBody Object requestBody
			@PathVariable int userId
	) {
		UserPojo user = userDao.findOne(userId);
		if (user == null) {
			throw new UserNotFoundException("userId: " + userId);
		}
		return user;
	}

	
	
	@PostMapping(
			path = { "/udemy01/users" },
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE } 
		)
	public ResponseEntity<Object> saveUser ( 
			@RequestHeader HttpHeaders requestHeaders,
			@RequestBody UserPojo user
			// @PathVariable int userId
	) {
		
		UserPojo savedUser = userDao.save(user);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{userId}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
		
	}

	
}
