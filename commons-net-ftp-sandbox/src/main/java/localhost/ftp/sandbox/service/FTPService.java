package localhost.ftp.sandbox.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
// import org.apache.commons.net.ftp.FTPFile;

import localhost.ftp.sandbox.util.ByteSizeHelper;
import localhost.ftp.sandbox.util.ExecTimeSecsHelper;
import localhost.ftp.sandbox.util.LogHelper;


public class FTPService {

	private LogHelper log = new LogHelper();

	public static void main() {
		System.out.println("Start main()");
		FTPService ftpService = new FTPService();
		try {
			// comment or disable by convenience
			ftpService.downloadFTPFileV1("./myfile.txt", "./myfile_downloaded.txt");
			ftpService.uploadFTPFileV1(new File("./myfile.txt"), "./myfile_uploaded.txt");
		} catch (Throwable e) {
			System.err.println("error -- " + e);
		}
	}


	public File downloadFTPFileV0(String remoteSrcFilename, String localTgtFilename)
			throws Throwable {
		log.info("Start downloadFTPFile() -- params -- 1st void: {} ; 2nd remoteFilename: {} ; 3rd localFilename: {}", "void", remoteSrcFilename, localTgtFilename);
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("localhost");
		ftpClient.login("root", "1234");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		log.info("elapsed time until ftp connection established -- execTimeSecs: {}", etsh.get());

		File localFile = new File(localTgtFilename);
		createDirs(localFile);
		log.info("reference before FTP retrieval -- localFile.getAbsolutePath(): {} ; localFile.length(): {} ; execTimeSecs: {}", localFile.getAbsolutePath(), ByteSizeHelper.writeHumanReadableByteSize(localFile.length()), etsh.get());

		// non used block, begin
		//		int ftpIntStatus = ftpClient.mdtm(remoteSrcFilename);
		//		FTPFile ftpFile = ftpClient.mdtmFile(remoteSrcFilename);
		//		String ftpFileRawListing = null;
		//		if (ftpFile != null) {
		//			ftpFileRawListing = ftpFile.getRawListing();
		//		}
		// non used block, end

		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
		boolean ftpFileRetrievedOk = false;
		String ftpMethod = null;

		// original block, using "retrieveFile()", begin
		ftpMethod = "retrieveFile";
		ftpFileRetrievedOk = ftpClient.retrieveFile(remoteSrcFilename, bufferedOutputStream);
		bufferedOutputStream.flush();

		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		// original block, using "retrieveFile()", end


		//		// modified block, using "retireveFileStream()", begin
		//		ftpMethod = "retrieveFileStream";
		//		InputStream inputStream = ftpClient.retrieveFileStream(remoteFilename);
		//		byte[] bytesArray = new byte[4096];
		//		int bytesRead = -1;
		//		while ((bytesRead = inputStream.read(bytesArray)) != -1) {
		//			bufferedOutputStream.write(bytesArray, 0, bytesRead);
		//		}
		//
		//		ftpFileRetrievedOk = ftpClient.completePendingCommand();
		//		bufferedOutputStream.flush();
		//		bufferedOutputStream.close();
		//		inputStream.close();
		//		// modified block, using "retireveFileStream()", end

		ftpClient.disconnect();
		log.info("Finish downloadFTPFile() -- params -- 1st void: {} ; 2nd remoteFilename: {} ; 3rd localFilename: {} -- results -- ftpFileRawListing: {} ; ftpMethod: {} ; ftpFileRetrievedOk: {} ; localFile.getAbsolutePath(): {} ; localFile.length(): {} ; execTimeSecs: {}", "void", remoteSrcFilename, localTgtFilename, /*ftpFileRawListing*/ null, ftpMethod, ftpFileRetrievedOk, localFile.getAbsolutePath(), ByteSizeHelper.writeHumanReadableByteSize(localFile.length()), etsh.get());
		return localFile;
	}

	private void createDirs(File file) {
		File path = new File(file.getParent());
		if (!path.exists()) {
			path.mkdirs();
		}
	}



	public void uploadFTPFileV0(File localSrcFile, String remoteTgtFilename)
			throws Throwable {
		log.info("Start uploadFTPFile() -- params -- 1st void: {} ; 2nd file.getName(): {} ; 3rd outputfilename: {}", "void", localSrcFile.getName(), remoteTgtFilename);
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("localhost");
		ftpClient.login("root", "1234");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		log.info("elapsed time until ftp connection established -- execTimeSecs: {}", etsh.get());

		InputStream inputStream = new FileInputStream(localSrcFile);
		ftpClient.storeFile(remoteTgtFilename, inputStream);
		inputStream.close();
		log.info("Finish uploadFTPFile() -- params -- 1st void: {} ; 2nd file.getName(): {} ; 3rd outputfilename: {} -- results -- execTimeSecs: {}", "void", localSrcFile.getName(), remoteTgtFilename, etsh.get());
	}



	public File downloadFTPFileV1(String remoteSrcFilename, String localTargetFilename)
			throws SocketException, IOException {
		log.info("Start downloadFTPFile() -- params -- void: {} ; remoteSrcFilename: {} ; localTargetFilename: {}", "void", remoteSrcFilename, localTargetFilename);
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();
		// client session
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("localhost");
		ftpClient.login("root", "1234");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// actual download
		File localFile = new File(localTargetFilename);
		createDirs(localFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
		boolean downloadSuccess = false;
		downloadSuccess = ftpClient.retrieveFile(remoteSrcFilename, bufferedOutputStream);
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		ftpClient.disconnect();
		long fileSize = localFile.length();
		log.info("Finish downloadFTPFile() -- params -- void: {} ; remoteSrcFilename: {} ; localTargetFilename: {} -- results -- downloadSuccess: {} ; localFile.getAbsolutePath(): {} ; fileSize: {} ; execTimeSecs: {}", "void", remoteSrcFilename, localTargetFilename, downloadSuccess, localFile.getAbsolutePath(), ByteSizeHelper.writeHumanReadableByteSize(fileSize), etsh.get());
		return localFile;
	}

	public Boolean uploadFTPFileV1(File localSrcFile, String remoteTargetFilename)
			throws SocketException, IOException {
		log.info("Start uploadFTPFile() -- params -- void: {} ; localSrcFile.getName(): {} ; remoteTargetFilename: {}", "void", localSrcFile.getName(), remoteTargetFilename);
		ExecTimeSecsHelper etsh = ExecTimeSecsHelper.getInstance();
		// client session
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("localhost");
		ftpClient.login("root", "1234");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// actual process
		InputStream inputStream = new FileInputStream(localSrcFile);
		Boolean uploadSuccess = ftpClient.storeFile(remoteTargetFilename, inputStream);
		inputStream.close();
		log.info("Finish uploadFTPFile() -- params -- void: {} ; localSrcFile.getName(): {} ; remoteTargetFilename: {} -- results -- uploadSuccess: {} ; execTimeSecs: {}", "void", localSrcFile.getName(), remoteTargetFilename, uploadSuccess, etsh.get());
		return uploadSuccess;
	}


}
