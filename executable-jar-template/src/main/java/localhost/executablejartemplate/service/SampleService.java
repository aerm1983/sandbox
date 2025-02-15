package localhost.executablejartemplate.service;

import java.util.Arrays;

public class SampleService {

	public static void main(String[] args) {
		System.out.println("SampleService -- Start main() -- args: " + Arrays.asList(args));
		sampleMethod(args);
	}

	private static void sampleMethod(String[] args) {
		System.out.println("SampleService -- Start sampleMethod() -- args: " + Arrays.asList(args));
	}
}
