package localhost.executablejartemplate;

import java.util.Arrays;

import localhost.executablejartemplate.service.SampleService;

public class ExecutableJarTemplateApplication {

	public static void main(String[] args) {
		System.out.println("ExecutableJarTemplateApplication -- Start main() -- args: " + Arrays.asList(args));
		SampleService.main(args);
	}
}
