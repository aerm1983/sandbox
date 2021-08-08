/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.debug;

import java.io.BufferedInputStream;

// import sun.security.x509.X500Name;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utilidades {
    
    private static final Logger log = LoggerFactory.getLogger(Utilidades.class);

    public static Object getEJBLocal(String iface) throws NamingException {

        String path = "java:module/";
        Context ctx = new InitialContext();
        String lookup = null;
        try {
            lookup = path + iface;

        } catch (Exception ex) {
            log.info("Error del metodo getEJBLocal: " + ex);

        }
        return ctx.lookup(lookup);

    }

    public static void generateCert_verBash(String clientID) throws Exception {
    	
    	Properties p = getConfig(clientID + ".properties");
    	
        // Cert cert = new Cert();
        List<String> lCert = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String dirTemp = "/tmp/";
            String dir = p.getProperty("api.bash.dir");
            String master_cert = p.getProperty("api.bash.master_cert");
    		String master_key = p.getProperty("api.bash.master_key");
            
            List<String> lCommand = new ArrayList<>();
            lCommand.add("cd ".concat(dirTemp));
            lCommand.add("openssl genrsa -out " + dirTemp + "pk" + i + ".pem 2048 -outform PEM");
            lCommand.add("openssl req -new -key " + dirTemp + "pk" + i + ".pem -out " + dirTemp + "pk" + i + ".csr -subj \"/C=US/ST=California/L=FosterCity/O=Visa Inc/OU=CBP/CN=MobileSDK/emailAddress=email@org.com\"");
            lCommand.add("openssl x509 -req -in " + dirTemp + "pk" + i + ".csr -CA " + dir + master_cert + ".pem -CAkey " + dir + master_key + ".pem -CAcreateserial -out " + dirTemp + "pk" + i + ".crt -days 500 -outform PEM");
            
            log.info("lCommand: " + lCommand);
            
            for (String command : lCommand) {

            	log.info("comando bash: " + command);
            	
                ProcessBuilder processBuilder = new ProcessBuilder();

                processBuilder.command("bash", "-c", command);
                
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                StringBuilder output = new StringBuilder();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                log.info("output y error del comando bash: " + output.toString());
                
                process.waitFor();
                
                log.info("exit value del comando bash: " + process.exitValue());
                
                if ( process.exitValue() != 0) {
                	log.info("ultimo comando openssl no se ejecuto correctamente");
                    // cert = null;
                    // return cert;
                	return;
                }
            }

            lCert.add(readFile(dirTemp + "pk" + i + ".crt"));
            lCert.add(readFile(dirTemp + "pk" + i + ".pem"));
        }

        

        /*
        cert.setConfidentiality(lCert.get(0));
        cert.setIntegrity(lCert.get(2));
        cert.setPkConfidentiality(lCert.get(1));
        cert.setPkIntegrity(lCert.get(3));
        */
        
        //===================pruebas =========================//
        /*cert.setConfidentiality(readFile("/tmp/pk1.crt"));
        cert.setIntegrity(readFile("/tmp/pk1.crt"));
        cert.setPkConfidentiality(readFile("/tmp/pk1.pem"));
        cert.setPkIntegrity(readFile("/tmp/pk2.pem"));*/
        
        // return cert;
        return;

    }

    
    
    
    
    
    
    
    
    

    public static void generateCert_verJava(String clientID) throws Exception {
    	
    	Properties p = getConfig(clientID + ".properties");
    	Writer writer = null;
    	byte[] certByteA = null;
    	InputStream inputStream = null;
    	List<String> lCert = new ArrayList<>();
    	String dirTemp = "/tmp/";
        String dir = p.getProperty("api.bash.dir");
        String master_cert = p.getProperty("api.bash.master_cert");
		String master_key = p.getProperty("api.bash.master_key");
		
		log.info("dir: " + dir);
		log.info("master_cert: " + master_cert);
		log.info("master_key: " + master_key);
		
		if ( dir.equals(null) || master_cert.equals(null) || master_key.equals(null) ) {
			return;
		}
		
		for ( int i = 0; i < 2; i++) {
			log.info("i: " + i);
			
	        // generate private key

	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	        keyPairGenerator.initialize(2048);
	        KeyPair keyPair = keyPairGenerator.generateKeyPair();
	        PrivateKey privateKey = keyPair.getPrivate();
	        PublicKey publicKey = keyPair.getPublic();
	        String privateKeyStr = Base64.getMimeEncoder().encodeToString( privateKey.getEncoded());
	        privateKeyStr = "-----BEGIN RSA PRIVATE KEY-----\n" + privateKeyStr + "\n-----END RSA PRIVATE KEY-----\n";

	        // parameters for certificate generation
	        
	        String CASerialNumberStr = readFile(dir + master_cert + ".srl");
	        CASerialNumberStr = CASerialNumberStr.replaceAll("[\r]", "").replaceAll("[\n]", "");
	        log.info("CASerialNumberStr: " + CASerialNumberStr);
	        BigInteger CASerialNumberBI = new BigInteger(CASerialNumberStr, 16 );
	        CASerialNumberBI = CASerialNumberBI.add(new BigInteger("1"));
	        log.info("CASerialNumberBI, incremented: " + CASerialNumberBI);
	        writer = new FileWriter(dir + master_cert + ".srl");
	        writer.write(CASerialNumberBI.toString(16) + "\n");
	        writer.close();
	        
	        long days = 500;
	        Date now = new Date();
	        Date notBefore = new Date(now.getTime() - 10 * 60 * 1000 ); // 10 minutes before present
	        Date notAfter = new Date(notBefore.getTime() + days * 24 * 60 * 60 * 1000);
	        
	        String caPrivateKeyStr = readFile(dir + master_key + ".pem");
	        log.info("caPrivateKeyStr: " + caPrivateKeyStr);
	        caPrivateKeyStr = caPrivateKeyStr.replaceAll("BEGIN RSA PRIVATE KEY", "");
	        caPrivateKeyStr = caPrivateKeyStr.replaceAll("END RSA PRIVATE KEY", "");
	        caPrivateKeyStr = caPrivateKeyStr.replaceAll("[-]", "");
	        caPrivateKeyStr = caPrivateKeyStr.replaceAll("[\n]", "");
	        log.info("caPrivateKeyStr: " + caPrivateKeyStr);
	        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(caPrivateKeyStr));
	        RSAPrivateKey caRSAPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(pKCS8EncodedKeySpec);
	        log.info("caRSAPrivateKey.getModulus(): " + caRSAPrivateKey.getModulus() );
	        log.info("caRSAPrivateKey.getPrivateExponent(): " + caRSAPrivateKey.getPrivateExponent() );

	        
	        String caCertStr = readFile(dir + master_cert + ".pem");
	        log.info("caCertStr: " + caCertStr);
	        caCertStr = caCertStr.replaceAll("BEGIN CERTIFICATE", "");
	        caCertStr = caCertStr.replaceAll("END CERTIFICATE", "");
	        caCertStr = caCertStr.replaceAll("[-]", "");
	        caCertStr = caCertStr.replaceAll("[\n]", "");
	        log.info("caCertStr: " + caCertStr);
	        certByteA = Base64.getDecoder().decode(caCertStr);
	        inputStream = new ByteArrayInputStream(certByteA);
	        log.info("inputStream.available(): " + inputStream.available() );
	        X509Certificate caX509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( inputStream );
	        inputStream.close();
	        certByteA = null;
	        log.info("caX509Certificate.getIssuerX500Principal(): " + caX509Certificate.getIssuerX500Principal());
	        log.info("caX509Certificate.getNotAfter(): " + caX509Certificate.getNotAfter());
	        log.info("caX509Certificate.getNotBefore(): " + caX509Certificate.getNotBefore());
	        log.info("caX509Certificate.getSubjectX500Principal(): " + caX509Certificate.getSubjectX500Principal());
	        log.info("caX509Certificate.getSigAlgName(): " + caX509Certificate.getSigAlgName());
	        
	        // X500Principal issuerX500Principal = new X500Principal("C=US, ST=Florida, L=Miami, O=\"NovoPayment, Inc.\", OU=Information Technology, CN=grupogente, EMAILADDRESS=infra@novopayment.com");
	        X500Principal issuerX500Principal = caX509Certificate.getIssuerX500Principal();
	        X500Name issuerX500Name_prev = new X500Name( issuerX500Principal.toString() ); 
	        X500NameBuilder issuerXNB = new X500NameBuilder(BCStyle.INSTANCE);
	        issuerXNB.addRDN(BCStyle.C, issuerX500Name_prev.getRDNs(BCStyle.C)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.ST, issuerX500Name_prev.getRDNs(BCStyle.ST)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.L, issuerX500Name_prev.getRDNs(BCStyle.L)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.O, issuerX500Name_prev.getRDNs(BCStyle.O)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.OU, issuerX500Name_prev.getRDNs(BCStyle.OU)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.CN, issuerX500Name_prev.getRDNs(BCStyle.CN)[0].getFirst().getValue());
	        issuerXNB.addRDN(BCStyle.EmailAddress, issuerX500Name_prev.getRDNs(BCStyle.EmailAddress)[0].getFirst().getValue());
	        X500Name issuerX500Name = issuerXNB.build();
	        
	        X500Principal subjectX500Principal = new X500Principal("C=US, ST=California, L=FosterCity, O=Visa Inc, OU=CBP, CN=MobileSDK, EMAILADDRESS=email@org.com");
	        X500Name subjectX500Name = new X500Name( subjectX500Principal.toString() );
	        
	        // certificate generation, deprecated tools
	        /*
	        X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
	        
	        certGen.setSerialNumber( CASerialNumberBI );
	        certGen.setIssuerDN(caX509Certificate.getSubjectX500Principal());
	        certGen.setNotBefore(notBefore);
	        certGen.setNotAfter(notAfter);
	        certGen.setSubjectDN(subjectX500Principal);
	        certGen.setPublicKey(publicKey);
	        certGen.setSignatureAlgorithm("SHA256withRSA");
	        
	        X509Certificate genX509Certificate_old = certGen.generate( (PrivateKey) caRSAPrivateKey, "BC");
	        String genX509CertificateStr_old = Base64.getMimeEncoder().encodeToString(genX509Certificate_old.getEncoded());
	        genX509CertificateStr_old = "-----BEGIN CERTIFICATE-----\n" + genX509CertificateStr_old + "\n-----END CERTIFICATE-----\n";
	        log.info("genX509CertificateStr_old: " + genX509CertificateStr_old);
	         */

	        // certificate generation, new tools
	        log.info("issuerX500Name.toString(): " + issuerX500Name.toString());
	        log.info("subjectX500Name.toString(): " + subjectX500Name.toString());
	        
	        X509v1CertificateBuilder certificateBuilder = new X509v1CertificateBuilder (
	        		issuerX500Name,
	        		CASerialNumberBI,
	        		notBefore, 
					notAfter,
					subjectX500Name, 
					SubjectPublicKeyInfo.getInstance( publicKey.getEncoded() ) );

	        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").build( caRSAPrivateKey );
	        certByteA = certificateBuilder.build(signer).getEncoded();
	        inputStream = new ByteArrayInputStream(certByteA);
	        log.info("inputStream.available(): " + inputStream.available() );
	        
	        X509Certificate genX509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate( inputStream );
	        
	        String genX509CertificateStr = Base64.getMimeEncoder().encodeToString(genX509Certificate.getEncoded());
	        genX509CertificateStr = "-----BEGIN CERTIFICATE-----\n" + genX509CertificateStr + "\n-----END CERTIFICATE-----\n";
	        log.info("genX509CertificateStr: " + genX509CertificateStr);

	        
	        lCert.add(genX509CertificateStr); //cert
	        lCert.add(privateKeyStr); //pem
	        
	        
	        
	        
	        // debug, private key file
	        /*
	        writer = new FileWriter("/tmp/pk0.pem");
	        writer.write(privateKeyStr);
	        writer.close();
	        // debug, certificate file
	        	// writer = new FileWriter("/tmp/pk0_old.crt");
		    	// writer.write(genX509CertificateStr_old);
		    	// writer.close();
	        // debug, certificate file, new tools
	        writer = new FileWriter("/tmp/pk0.crt");
	        writer.write(genX509CertificateStr);
	        writer.close();
			*/
			
			
		}

		/*
        cert.setConfidentiality(lCert.get(0));
        cert.setIntegrity(lCert.get(2));
        cert.setPkConfidentiality(lCert.get(1));
        cert.setPkIntegrity(lCert.get(3));
        */

        

    }


    
    
    
    
    
    
    
    
    

    
    
    public static String readFile(String path) throws IOException {
    	FileReader fileReader = new FileReader(path);
        BufferedReader br = new BufferedReader(fileReader);

        String st;
        StringBuilder certificado = new StringBuilder();
        List<String> lineasCertificado = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            lineasCertificado.add(st);
        }
        for (String lineas : lineasCertificado) {
            certificado.append(lineas.concat("\n"));

        }
        fileReader.close();
        return certificado.toString();
    }

    
	public static Properties getConfig(String nameFile) {
		
        String pathLocal = System.getProperty("user.dir") + System.getProperty("file.separator") + nameFile;
        String pathWeblogic = System.getProperty("catalina.home", ".") + System.getProperty("file.separator")
                + "parametros" + System.getProperty("file.separator") 
                + "TR-TSP" + System.getProperty("file.separator") 
                + "ConnectorCert" + System.getProperty("file.separator")
                + nameFile;

        Properties p = new Properties();
        try {
            FileInputStream fileIn = new FileInputStream(pathLocal);
            p.load(fileIn);
            fileIn.close();
            log.info("getConfig, pathLocal OK: " + pathLocal);
        } catch (Exception e) {
        	log.info("getConfig, pathLocal NOT_FOUND: " + pathLocal);
            log.debug("getConfig ERROR path[" + pathLocal + "]");
            log.debug("getConfig ERROR getMessage[" + e.getMessage() + "]");
            log.debug("getConfig ERROR getLocalizedMessage[" + e.getLocalizedMessage() + "]");
            // e.printStackTrace();
        }

        try {
            FileInputStream fileIn = new FileInputStream(pathWeblogic);
            p.load(fileIn);
            fileIn.close();
            log.info("getConfig, pathWeblogic OK: " + pathWeblogic);
        } catch (Exception e) {
        	log.info("getConfig, pathWeblogic NOT_FOUND: " + pathWeblogic);
        	log.debug("getConfig ERROR path[" + pathWeblogic + "]");
        	log.debug("getConfig ERROR getMessage[" + e.getMessage() + "]");
        	log.debug("getConfig ERROR getLocalizedMessage[" + e.getLocalizedMessage() + "]");
            // e.printStackTrace();
        }
        return (p);
    }
    
}
