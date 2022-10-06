package localhost;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class SecurityConfig {

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	public static Account encryptAccount(Account account) throws Exception {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		
		SecretKey secKey = getKeyFromPassword(account.getPassword(), account.getSalt());
		IvParameterSpec iv = generateIv();
		cipher.init(Cipher.ENCRYPT_MODE, secKey, iv);
		byte[] cipherText = cipher.doFinal(account.getPassword().getBytes());
		account.setPassword(Base64.getEncoder().encodeToString(cipherText));
		account.setEncode(secKey.getEncoded());
		account.setIv(iv.getIV());
		return account;
	}

	public static String decryptAccount(Account account)
			throws Exception {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(account.getIv());
		cipher.init(Cipher.DECRYPT_MODE, getPasswordFromKey(account.getEncode()), iv);
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(account.getPassword()));
		return new String(plainText);
	}

	private static SecretKey getKeyFromPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}

	private static SecretKey getPasswordFromKey(byte[] encoded)
			throws IllegalArgumentException{
		return new SecretKeySpec(encoded, "AES");
	}

	private static IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}

}
