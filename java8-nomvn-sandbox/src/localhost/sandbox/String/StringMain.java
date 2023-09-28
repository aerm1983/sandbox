package localhost.sandbox.String;

import localhost.helper.ByteSizeHelper;

//# encoding tests:
//java -classpath . localhost.TemporalOutMain
//java -Dfile.encoding=UTF-8 -classpath . localhost.TemporalOutMain

public class StringMain {

	public static void main() {
		System.out.println("Hello from StringMain!");
		// other();
		RegExTest.stringMatchTest01();
	}
	
	public static void other() {
		
		long inSize = 123456789012L ;
		String outSize = ByteSizeHelper.writeHumanReadableByteSize(inSize);
		System.out.println("inSize: " + inSize + " ; outSize: " + outSize);
		
		/*
		String xmlStr= "&lt;/CONSULTA&gt;";
		System.out.println("xmlStr: " + xmlStr);
		xmlStr = xmlStr.replace("&lt;", "<").replace("&gt;", ">");
		System.out.println("xmlStr: " + xmlStr);
		*/
	}

}
