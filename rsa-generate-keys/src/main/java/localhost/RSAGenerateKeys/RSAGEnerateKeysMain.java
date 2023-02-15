package localhost.RSAGenerateKeys;

public class RSAGEnerateKeysMain {

	public static void main(String[] args) {
		
		try {

			// RSAGenerateKeysLib.bashGenerateRSACert();

			RSAGenerateKeysLib.javaGenerateRSACert();
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		

	}
	

}
