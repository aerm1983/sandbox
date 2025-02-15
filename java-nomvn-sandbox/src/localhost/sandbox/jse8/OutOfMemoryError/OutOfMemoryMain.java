package localhost.sandbox.jse8.OutOfMemoryError;

public class OutOfMemoryMain {

	public static void main() {
		System.out.println("Hello from OutOfMemoryMain!");
		Test03OutOfMemory.memoryArrayInInstantiatedThread();
	}

}
