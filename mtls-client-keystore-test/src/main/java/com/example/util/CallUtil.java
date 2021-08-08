/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author default
 */
public class CallUtil {

    private static final Logger LOG = Logger.getLogger(CallUtil.class);


    public CallUtil() {
    }

    public String call(
            String url,
            String keyStore,
            String keyPassword
    ) {
        try {
            LOG.info("call url " + url);
            RestTemplate restTemplate = new RestTemplate(
                    getClientHttpRequestFactory(
                            getKeyStore(keyStore, keyPassword),
                            keyPassword
                    )
            );

            restTemplate.setErrorHandler(new DefaultError());

            HttpEntity<Void> entityBody = new HttpEntity<>(
                    null
            //,
            //getHeader(username, password)
            );

            ResponseEntity<String> responseEntity
                    = restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entityBody,
                            String.class
                    );
            LOG.info(responseEntity.getBody());
            return responseEntity.getBody();
        } catch (Exception ex) {
            LOG.error("Certificate error", ex);
        }
        return "Error";

    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(
            KeyStore keysStore,
            String keyPassword
    ) throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException, KeyManagementException {

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .loadKeyMaterial(keysStore, keyPassword.toCharArray()).build());
        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        return requestFactory;
    }

    public KeyStore getKeyStore(
            String key,
            String keyPassword
    ) {
        
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(
                    new FileInputStream(key),
                    keyPassword.toCharArray()
            );
            return keyStore;
        } catch (Exception ex) {}
        return null;
    }

    private class DefaultError extends DefaultResponseErrorHandler {

        @Override
        public boolean hasError(HttpStatus statusCode) {
            return false;
        }
    }

}
