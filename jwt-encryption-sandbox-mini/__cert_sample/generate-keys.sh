#!/bin/bash
# generate self-signed certificate and private key
openssl req -x509 -nodes -newkey rsa:4096 -keyout B_jws_key.pem -out B_jws_cert.pem -days 365
