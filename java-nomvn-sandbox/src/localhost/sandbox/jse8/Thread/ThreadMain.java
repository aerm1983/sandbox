package localhost.sandbox.jse8.Thread;

public class ThreadMain {

	public static void main() {
		System.out.println("hello from ThreadMain!");
		// Test00_ExtendedThread.test00ExtendedThread();
		// Test01_StaticSyncMethodRaceCondition.test00_StaticSyncMethodRaceCondition();
		Test02_StaticSyncMethodRaceCondition.test01_StaticSyncMethodAndBlockRaceCondition();
	}

}
