package localhost.sandbox.ThreadRaceConditionSynchroWaitNotifyAtomic;

public class ThreadRaceConditionSynchroWaitNotifyAtomicMain {

	public static void main() {
		System.out.println("Hello from ThreadRaceCondition!");
//		Test00_SynchronizedStatic.test00_StaticSynchronizedOnly();
//		Test00_SynchronizedStatic.test01_StaticSynchroAndNonSynchro();
		Test01_SynchroWaitNotify.test00_SynchroWaitNotify();
//		Test02_AtomicReference.test00_AtomicReference();
	}
}
