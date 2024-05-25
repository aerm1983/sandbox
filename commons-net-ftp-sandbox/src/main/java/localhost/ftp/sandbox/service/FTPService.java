package localhost.ftp.sandbox.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
// import org.apache.commons.net.ftp.FTPFile;

import localhost.ftp.sandbox.util.ByteSizeHelper;
import localhost.ftp.sandbox.util.ExecTimeSecsHelper;
import localhost.ftp.sandbox.util.LogHelper;


public class FTPService {

	private LogHelper log = new LogHelper();


	public File downloadFTPFile(String remoteSrcFilename, String localTgtFilename)
			throws Throwable {
		log.info("Start downloadFTPFile() -- params -- 1st extFtp.getStoreId: {} ; 2nd remoteFilename: {} ; 3rd localFilename: {}", "storeId", remoteSrcFilename, localTgtFilename);
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
		log.info("Finish downloadFTPFile() -- params -- 1st extFtp.getStoreId(): {} ; 2nd remoteFilename: {} ; 3rd localFilename: {} -- results -- ftpFileRawListing: {} ; ftpMethod: {} ; ftpFileRetrievedOk: {} ; localFile.getAbsolutePath(): {} ; localFile.length(): {} ; execTimeSecs: {}", "storeId", remoteSrcFilename, localTgtFilename, /*ftpFileRawListing*/ null, ftpMethod, ftpFileRetrievedOk, localFile.getAbsolutePath(), ByteSizeHelper.writeHumanReadableByteSize(localFile.length()), etsh.get());
		return localFile;
	}

	private void createDirs(File file) {
		File path = new File(file.getParent());
		if (!path.exists()) {
			path.mkdirs();
		}
	}



	public void uploadFTPFile(File localSrcFile, String remoteTgtFilename)
			throws Throwable {
		log.info("Start uploadFTPFile() -- params -- 1st ftp.getStoreId(): {} ; 2nd file.getName(): {} ; 3rd outputfilename: {}", "storeId", localSrcFile.getName(), remoteTgtFilename);
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
		log.info("Finish uploadFTPFile() -- params -- 1st ftp.getStoreId(): {} ; 2nd file.getName(): {} ; 3rd outputfilename: {} -- results -- execTimeSecs: {}", "storeId", localSrcFile.getName(), remoteTgtFilename, etsh.get());
	}


}
