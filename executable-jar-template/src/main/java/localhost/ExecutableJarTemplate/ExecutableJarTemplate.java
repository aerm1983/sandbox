package localhost.ExecutableJarTemplate;

import java.util.Arrays;

import localhost.ExecutableJarTemplate.test.Test00;

public class ExecutableJarTemplate {

	public static void main(String[] args) {
		System.out.println("ExecutableJarTemplate -- Start main() -- args: " + Arrays.asList(args));
		Test00.main(args);
	}
}
