package localhost.ftp.sandbox;

import java.io.File;

import localhost.ftp.sandbox.service.FTPService;

public class FTPSandboxMain {

	public static void main(String[] args) {
		System.out.println("Start main()");
		FTPService ftpService = new FTPService();
		try {
			// comment or disable by convenience
			ftpService.downloadFTPFile("./myfile.txt", "./myfile_downloaded.txt");
			ftpService.uploadFTPFile(new File("./myfile.txt"), "./myfile_uploaded.txt");
		} catch (Throwable e) {
			System.err.println("error -- " + e);
		}
	}
}
