package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.util.CallUtil;

@RestController
//@RequestMapping(value = "/")
public class Controller {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postFer() {

        CallUtil callUtils = new CallUtil();
        return new ResponseEntity<String>(
                callUtils.call(
                        "https://localhost/server-A/client-B/",
                        "B_client_keystore.jks",
                        "B_keystore_password"
                ),
                 HttpStatus.OK
        );
    }

}
